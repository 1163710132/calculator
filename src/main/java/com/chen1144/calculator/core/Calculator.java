package com.chen1144.calculator.core;

import com.chen1144.calculator.core.context.*;
import com.chen1144.calculator.core.util.CompositedSession;
import com.chen1144.calculator.core.util.CompressedForwardConverter;
import com.chen1144.calculator.core.util.DerivedCalculateRule;
import com.chen1144.calculator.plugin.CalculatePackage;
import com.chen1144.calculator.plugin.rational.RationalNumberPackage;
import com.chen1144.calculator.plugin.base.Parentheses;
import com.chen1144.calculator.util.DAG;
import com.chen1144.calculator.util.LinkedList;
import com.chen1144.calculator.util.Pair;
import com.chen1144.calculator.util.Trie;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Calculator implements CalculateContext, Parser, Evaluator, Tokenizer, PackageSet {
    private Map<Operator, Trie<NumberType, CalculateRule>> ruleMap;
    private List<Tokenizer> tokenizerList;
    private DAG<NumberType, ForwardConverter> converterDAG;
    private Map<Class, CalculatePackage> classMap;

    public Calculator(){
        ruleMap = new HashMap<>();
        tokenizerList = new ArrayList<>();
        converterDAG = new DAG<>(CompressedForwardConverter::new);
        classMap = new HashMap<>();
    }

    public synchronized boolean importPackage(CalculatePackage calculatePackage){
        if(!classMap.containsKey(calculatePackage.getClass())){
            classMap.putIfAbsent(calculatePackage.getClass(), calculatePackage);
            calculatePackage.rules().forEach(this::addRule);
            calculatePackage.tokenParsers().forEach(this::registerTokenParser);
            calculatePackage.converters().forEach(this::addConverter);
            return true;
        }else{
            return false;
        }
    }

    public void addConverter(ForwardConverter converter){
        converterDAG.put(converter.getSourceType(), converter.getTargetType(), converter);
    }

    public void addRule(CalculateRule rule){
        ruleMap.computeIfAbsent(rule.getOperator(), operator -> new Trie<>(NumberType::match))
                .insert(rule.getParams(), rule);
    }

    public CalculateRule queryRule(Operator operator, Stream<NumberType> numberTypes){
        List<NumberType> list = numberTypes.collect(Collectors.toList());
        Trie<NumberType, CalculateRule> ruleTrie = ruleMap.get(operator);
        if(ruleTrie == null){
            return null;
        }
        Optional<CalculateRule> optionalRule = ruleTrie.get(list.stream()).findAny();
        if(optionalRule.isPresent()){
            return optionalRule.get();
        }else{
            Stream<CalculateRule> rules = ruleTrie.getByPredicate(list.stream().map(numberType -> param -> numberType.equals(param) || converterDAG.get(numberType, param)!=null));
            Optional<CalculateRule> rule = rules.map(calculateRule -> {
                int distance = calculateRule.getParams().mapToInt(new ToIntFunction<NumberType>() {
                    int i = -1;
                    @Override
                    public int applyAsInt(NumberType value) {
                        i++;
                        return list.get(i).equals(value) ? 0 : converterDAG.get(list.get(i), value).getDistance();
                    }
                }).sum();
                return new Pair<>(calculateRule, distance);
            }).min(Comparator.comparing(Pair<CalculateRule, Integer>::getValue)).map(Pair::getKey);
            if(!rule.isPresent()){
                return null;
            }else{
                ForwardConverter[] converters = new ForwardConverter[(int)rule.get().getParams().count()];
                rule.get().getParams().forEachOrdered(new Consumer<>() {
                    int i = -1;
                    @Override
                    public void accept(NumberType type) {
                        i++;
                        if(!type.equals(list.get(i))){
                            converters[i] = converterDAG.get(list.get(i), type);
                        }
                    }
                });
                DerivedCalculateRule derivedRule = new DerivedCalculateRule(rule.get(), Arrays.asList(converters));
                addRule(derivedRule);
                return derivedRule;
            }
        }
    }

    public void registerTokenParser(Tokenizer tokenizer){
        this.tokenizerList.add(tokenizer);
    }

    public List<NumberOrOp> parse(CharSequence input){
        return createSession().mapTo(IntStream.concat(input.chars(), IntStream.of(-1))).collect(Collectors.toList());
    }

    public Number eval(CharSequence charSequence){
        Number number = eval(compile(charSequence));
        return number;
    }

    @Override
    public Number eval(Expression expression){
        if(expression.getValue() != null){
            return expression.getValue();
        }else if(expression.getCaptured() != null){
            Stream<Expression> paramsStream = Arrays.stream(expression.getCaptured());
            CalculateRule calculateRule = queryRule(expression.getOperator(), paramsStream.map(exp -> eval(exp).getNumberType()));
            if(calculateRule == null){
                throw new RuntimeException("Not this rule. Expression:" + expression.getOperator());
            }
            Number calResult = calculateRule.calculate(i -> eval(expression.getCaptured()[i]), this);
            expression.setValue(calResult);
            return calResult;
        }else throw new RuntimeException("Params are not captured yet.");
    }

    public void preProcess(com.chen1144.calculator.util.LinkedList<Expression> list){
        com.chen1144.calculator.util.LinkedList<Expression>.Iterator iterator = list.iterator();
        while (iterator.moveNext()){
            if(iterator.get().getOperator() == Parentheses.LEFT && iterator.get().getCaptured() == null){
                iterator.remove();
                com.chen1144.calculator.util.LinkedList<Expression> subList = new com.chen1144.calculator.util.LinkedList<>();
                int count = 1;
                while (iterator.moveNext()){
                    if(iterator.get().getOperator() == Parentheses.LEFT){
                        count++;
                    }else if(iterator.get().getOperator() == Parentheses.RIGHT){
                        if(--count == 0){
                            break;
                        }
                    }
                    subList.pushBack(iterator.remove());
                }
                if(count == 0){
                    Expression compiled = compile(subList);
                    Expression subExpression = new Expression(Parentheses.LEFT);
                    Expression right = new Expression(Parentheses.RIGHT);
                    right.setCaptured(new Expression[0]);
                    subExpression.setCaptured(new Expression[]{compiled, right});
                    iterator.set(subExpression);
                }else{
                    throw new RuntimeException("Unmatched left parentheses.");
                }
            }
        }
    }

    public Expression compile(com.chen1144.calculator.util.LinkedList<Expression> list){
        preProcess(list);
        Set<Operator> operatorSet = new HashSet<>();
        {
            LinkedList<Expression>.Iterator iterator = list.iterator();
            while (iterator.moveNext()){
                iterator.get().forEach(numberOrOp -> {
                    if (numberOrOp.isOperator()){
                        operatorSet.add(numberOrOp.asOperator());
                    }
                });
            }
        }
        IntStream priorities = operatorSet.stream()
                .mapToInt(Operator::getPriority)
                .filter(i -> i > 0)
                .distinct()
                .map(i -> Integer.MAX_VALUE - i)
                .sorted()
                .map(i -> Integer.MAX_VALUE - i);
        priorities.forEachOrdered(i -> {
            final int priority = i;
            com.chen1144.calculator.util.LinkedList<Expression>.Iterator iterator = list.iterator();
            while (iterator.moveNext()){
                Expression expression = iterator.get();
                if(expression.getOperator() != null
                        && expression.getCaptured() == null
                        && expression.getOperator().getPriority() == priority){
                    Operator operator = expression.getOperator().asOperator();
                    int forward = operator.captureForward();
                    int backward = operator.captureBackward();
                    Expression[] captured = new Expression[forward + backward];
                    LinkedList<Expression>.Iterator prevIterator = iterator.fork();
                    for(int ptr = forward;ptr > 0;ptr--){
                        if(prevIterator.movePrev()){
                            captured[ptr - 1] = prevIterator.remove();
                        }
                    }
                    for(int ptr = forward;ptr < captured.length;ptr++){
                        if(iterator.moveNext()){
                            captured[ptr] = iterator.remove();
                        }
                    }
                    expression.setCaptured(captured);
                }
            }
        });
        assert list.size() == 1;
        return list.iterator().next().get();
    }

    public Expression compile(CharSequence input){
        List<NumberOrOp> rawList = parse(input);
        final com.chen1144.calculator.util.LinkedList<Expression> list = new LinkedList<>();
        rawList.forEach(numberOrOp ->
                list.pushBack(numberOrOp.isNumber()? new Expression(numberOrOp.asNumber()) : new Expression(numberOrOp.asOperator())));
        //preProcess(list);
        return compile(list);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.importPackage(new RationalNumberPackage());
    }

    @Override
    public Parser getParser() {
        return this;
    }

    @Override
    public Tokenizer getTokenizer() {
        return this;
    }

    @Override
    public Evaluator getEvaluator() {
        return this;
    }

    @Override
    public PackageSet getPackageSet() {
        return this;
    }

    @Override
    public Expression parse(Stream<NumberOrOp> stream) {
        LinkedList<Expression> linkedList = new LinkedList<>();
        stream.map(numberOrOp -> {
            if(numberOrOp.isNumber()){
                return new Expression(numberOrOp.asNumber());
            }else if(numberOrOp.isOperator()){
                return new Expression(numberOrOp.asOperator());
            }else throw new RuntimeException("neither number or operator");
        }).forEach(linkedList::pushBack);
        return compile(linkedList);
    }

    @Override
    public Session createSession() {
        return new CompositedSession(tokenizerList.stream().map(Tokenizer::createSession));
    }

    @Override
    public <T extends CalculatePackage> T getPackage(Class<? extends T> pkg) {
        return pkg.cast(classMap.get(pkg));
    }
}

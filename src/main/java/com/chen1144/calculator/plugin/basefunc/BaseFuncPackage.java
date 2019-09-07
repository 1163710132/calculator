package com.chen1144.calculator.plugin.basefunc;

import com.chen1144.calculator.core.*;
import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.context.CalculateContext;
import com.chen1144.calculator.core.context.Tokenizer;
import com.chen1144.calculator.plugin.CalculatePackage;
import com.chen1144.calculator.plugin.base.BaseOperator;
import com.chen1144.calculator.plugin.base.NullNumber;
import com.chen1144.calculator.plugin.base.RawNumber;
import com.chen1144.calculator.plugin.pair.PairNumber;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class BaseFuncPackage implements CalculatePackage {
    @Override
    public Stream<CalculateRule> rules() {
        return Stream.of(new CalculateRule() {
            @Override
            public Operator getOperator() {
                return UniFunction.SUM;
            }

            @Override
            public Stream<NumberType> getParams() {
                return Stream.of(NumberType.ANY);
            }

            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                Number number = list.apply(0);
                if(number == NullNumber.INSTANCE){
                    return NullNumber.INSTANCE;
                }else{
                    Function<Number, Expression> collector= new Function<>() {
                        @Override
                        public Expression apply(Number nb) {
                            if (nb instanceof PairNumber) {
                                PairNumber pair = (PairNumber) nb;
                                Expression expression = new Expression(BaseOperator.PLUS);
                                expression.setCaptured(new Expression[2]);
                                expression.getCaptured()[0] = apply(pair.getFirst());
                                expression.getCaptured()[1] = apply(pair.getSecond());
                                return expression;
                            } else {
                                return new Expression(nb);
                            }
                        }
                    };
                    return context.getEvaluator().eval(collector.apply(number));
                }
            }
        });
    }

//    @Override
//    public Stream<Operator> operators() {
//        return null;
//    }

    @Override
    public Stream<Tokenizer> tokenParsers() {
        return Stream.of(()->new Tokenizer.Session() {
            StringBuilder builder = new StringBuilder();
            @Override
            public Tokenizer.TokenizeResult apply(int value) {
                if(Character.isUpperCase(value)){
                    builder.append((char)value);
                    return Tokenizer.TokenizeResult.ofUndetermined();
                }else{
                    String string = builder.toString();
                    builder.setLength(0);
                    if(string.length() > 0){
                        return Tokenizer.TokenizeResult.ofSucceed(new UniFunction(string), 1);
                    }else{
                        return Tokenizer.TokenizeResult.ofFailed();
                    }
                }
            }
        });
    }

    @Override
    public Stream<ForwardConverter> converters() {
        return Stream.empty();
    }
}

package com.chen1144.calculator.plugin.base;

import com.chen1144.calculator.core.*;
import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.context.CalculateContext;
import com.chen1144.calculator.core.context.Tokenizer;
import com.chen1144.calculator.core.util.AbstractCalculateRule;
import com.chen1144.calculator.plugin.CalculatePackage;
import com.chen1144.calculator.plugin.rational.RationalNumber;

import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static com.chen1144.calculator.plugin.base.BaseOperator.*;
import static com.chen1144.calculator.plugin.base.BaseOperator.DOT;

public class BasePackage implements CalculatePackage {

    @Override
    public Stream<CalculateRule> rules() {
        return Stream.of(new AbstractCalculateRule(Parentheses.RIGHT) {
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                return NullNumber.INSTANCE;
            }
        }, new AbstractCalculateRule(Parentheses.LEFT, NumberType.ANY, NullNumber.TYPE){
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                return list.apply(0);
            }
        });
    }

//    @Override
//    public Stream<Operator> operators() {
//        return Stream.of(Parentheses.LEFT, Parentheses.RIGHT, PLUS, MINUS, MUL, DIV, DOT);
//    }

    @Override
    public Stream<Tokenizer> tokenParsers() {
        return Stream.of(()->new Tokenizer.Session() {
            @Override
            public Tokenizer.TokenizeResult apply(int value) {
                switch (value){
                    case '(':
                        return Tokenizer.TokenizeResult.ofSucceed(Parentheses.LEFT, 0);
                    case ')':
                        return Tokenizer.TokenizeResult.ofSucceed(Parentheses.RIGHT, 0);
                    default:
                        return Tokenizer.TokenizeResult.ofFailed();
                }
            }
        }, ()->new Tokenizer.Session() {
            StringBuilder builder = new StringBuilder();
            @Override
            public Tokenizer.TokenizeResult apply(int value) {
                if(Character.isDigit(value)){
                    builder.append((char)value);
                    return Tokenizer.TokenizeResult.ofUndetermined();
                }else{
                    String string = builder.toString();
                    builder.setLength(0);
                    if(string.length() > 0){
                        return Tokenizer.TokenizeResult.ofSucceed(new RawNumber(string), 1);
                    }else{
                        return Tokenizer.TokenizeResult.ofFailed();
                    }
                }
            }
        }, ()-> value -> {
            if(value == ' '){
                return Tokenizer.TokenizeResult.ofSucceed(BaseOperator.PLACE_HOLDER, 0);
            }else{
                return Tokenizer.TokenizeResult.ofFailed();
            }
        });
    }

    @Override
    public Stream<ForwardConverter> converters() {
        return Stream.of();
    }
}

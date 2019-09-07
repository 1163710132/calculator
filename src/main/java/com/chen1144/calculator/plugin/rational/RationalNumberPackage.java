package com.chen1144.calculator.plugin.rational;

import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.*;
import com.chen1144.calculator.core.context.CalculateContext;
import com.chen1144.calculator.core.context.Tokenizer;
import com.chen1144.calculator.core.util.AbstractCalculateRule;
import com.chen1144.calculator.plugin.CalculatePackage;
import com.chen1144.calculator.plugin.base.NullNumber;
import com.chen1144.calculator.plugin.base.BaseOperator;
import com.chen1144.calculator.plugin.base.Parentheses;
import com.chen1144.calculator.plugin.base.RawNumber;

import static com.chen1144.calculator.plugin.base.BaseOperator.*;

import java.math.BigInteger;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RationalNumberPackage implements CalculatePackage {
    @Override
    public Stream<CalculateRule> rules() {
        return Stream.of(new AbstractCalculateRule(PLUS, RationalNumber.TYPE, RationalNumber.TYPE) {
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                RationalNumber r0 = (RationalNumber) list.apply(0);
                RationalNumber r1 = (RationalNumber) list.apply(1);
                return RationalNumber.opPlus(r0, r1);
            }
        }, new AbstractCalculateRule(MINUS, RationalNumber.TYPE, RationalNumber.TYPE) {
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                RationalNumber r0 = (RationalNumber) list.apply(0);
                RationalNumber r1 = (RationalNumber) list.apply(1);
                return RationalNumber.opSub(r0, r1);
            }
        }, new AbstractCalculateRule(MUL, RationalNumber.TYPE , RationalNumber.TYPE){
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                RationalNumber r0 = (RationalNumber) list.apply(0);
                RationalNumber r1 = (RationalNumber) list.apply(1);
                return RationalNumber.opMul(r0, r1);
            }
        }, new AbstractCalculateRule(DIV, RationalNumber.TYPE, RationalNumber.TYPE){
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                RationalNumber r0 = (RationalNumber) list.apply(0);
                RationalNumber r1 = (RationalNumber) list.apply(1);
                return RationalNumber.opDiv(r0, r1);
            }
        }, new AbstractCalculateRule(DOT, RawNumber.TYPE, RawNumber.TYPE){
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                RawNumber r0 = (RawNumber)list.apply(0);
                RawNumber r1 = (RawNumber)list.apply(1);
                BigInteger b0 = new BigInteger(r0.toString() + r1.toString());
                StringBuilder builder = new StringBuilder();
                builder.append('1');
                IntStream.range(0, r1.toString().length()).forEach(i -> builder.append('0'));
                BigInteger b1 = new BigInteger(builder.toString());
                return new RationalNumber(b0, b1);
            }
        }, new AbstractCalculateRule(SQUARE, RationalNumber.TYPE) {
            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                RationalNumber r0 = (RationalNumber) list.apply(0);
                return RationalNumber.opMul(r0, r0);
            }
        });
    }

//    @Override
//    public Stream<Operator> operators() {
//        return Stream.of(PLUS, MINUS, MUL, DIV, DOT);
//    }

    @Override
    public Stream<Tokenizer> tokenParsers() {
        return Stream.of(()->new Tokenizer.Session() {
            @Override
            public Tokenizer.TokenizeResult apply(int value) {
                Operator operator;
                switch (value){
                    case '+':
                        operator = BaseOperator.PLUS;
                        break;
                    case '-':
                        operator = BaseOperator.MINUS;
                        break;
                    case '*':
                        operator = BaseOperator.MUL;
                        break;
                    case '/':
                        operator = BaseOperator.DIV;
                        break;
                    case '.':
                        operator = BaseOperator.DOT;
                        break;
                    case '²':
                        operator = BaseOperator.SQUARE;
                        break;
                    default:
                        return Tokenizer.TokenizeResult.ofFailed();
                }
                return Tokenizer.TokenizeResult.ofSucceed(operator, 0);
            }
        });
    }

    @Override
    public Stream<ForwardConverter> converters() {
        return Stream.of(new ForwardConverter<RawNumber, RationalNumber>() {
            @Override
            public NumberType<RawNumber> getSourceType() {
                return RawNumber.TYPE;
            }

            @Override
            public NumberType<RationalNumber> getTargetType() {
                return RationalNumber.TYPE;
            }

            @Override
            public RationalNumber convert(RawNumber source) {
                return new RationalNumber(source);
            }

            @Override
            public int getDistance() {
                return 1;
            }
        });
    }


}

package com.chen1144.calculator.plugin.pair;

import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.*;
import com.chen1144.calculator.core.context.CalculateContext;
import com.chen1144.calculator.core.context.Tokenizer;
import com.chen1144.calculator.plugin.CalculatePackage;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class PairPackage implements CalculatePackage {
    @Override
    public Stream<CalculateRule> rules() {
        return Stream.of(new CalculateRule() {
            @Override
            public Operator getOperator() {
                return COMMA;
            }

            @Override
            public Stream<NumberType> getParams() {
                return Stream.of(NumberType.ANY, NumberType.ANY);
            }

            @Override
            public Number calculate(IntFunction<Number> list, CalculateContext context) {
                return new PairNumber<>(list.apply(0), list.apply(1));
            }
        });
    }

//    @Override
//    public Stream<Operator> operators() {
//        return Stream.of(COMMA);
//    }

    @Override
    public Stream<Tokenizer> tokenParsers() {
        return Stream.of(()->new Tokenizer.Session() {
            @Override
            public Tokenizer.TokenizeResult apply(int value) {
                return value == ','
                        ? Tokenizer.TokenizeResult.ofSucceed(COMMA, 0)
                        : Tokenizer.TokenizeResult.ofFailed();
            }
        });
    }

    @Override
    public Stream<ForwardConverter> converters() {
        return Stream.empty();
    }

    public static Operator COMMA = new Operator() {
        @Override
        public String getCode() {
            return ",";
        }

        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public int captureForward() {
            return 1;
        }

        @Override
        public int captureBackward() {
            return 1;
        }

        @Override
        public String getMarkdown(List<String> params) {
            return params.get(0) + "," + params.get(1);
        }

        @Override
        public String toMath() {
            return ",";
        }
    };
}

package com.chen1144.calculator.core.context;

import com.chen1144.calculator.core.NumberOrOp;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Tokenizer {
    Session createSession();

    class TokenizeResult{
        public static final int UNDETERMINED = 0;
        public static final int SUCCEED = 1;
        public static final int FAILED = 2;

        public static final TokenizeResult RESULT_UNDETERMINED
                = new TokenizeResult(UNDETERMINED, 0, null);
        public static final TokenizeResult RESULT_FAILED
                = new TokenizeResult(FAILED, 0, null);

        private int status;
        private int rollback;
        private NumberOrOp result;

        private TokenizeResult(){}

        private TokenizeResult(int status, int rollback, NumberOrOp result) {
            this.status = status;
            this.rollback = rollback;
            this.result = result;
        }

        public boolean succeed(){
            return status == SUCCEED;
        }

        public boolean failed(){
            return status == FAILED;
        }

        public boolean undetermined() {
            return status == UNDETERMINED;
        }

        public int status(){
            return status;
        }

        public int rollback(){
            return rollback;
        }

        public NumberOrOp get(){
            return result;
        }

        public static TokenizeResult ofFailed(){
            return RESULT_FAILED;
        }

        public static TokenizeResult ofSucceed(NumberOrOp result, int rollback){
            TokenizeResult tokenizeResult = new TokenizeResult();
            tokenizeResult.status = SUCCEED;
            tokenizeResult.rollback = rollback;
            tokenizeResult.result = result;
            return tokenizeResult;
        }
        public static TokenizeResult ofUndetermined(){
            return RESULT_UNDETERMINED;
        }

    }

    interface Session extends IntFunction<Tokenizer.TokenizeResult> {
        @Override
        TokenizeResult apply(int value);

        default Stream<NumberOrOp> mapTo(IntStream stream){
            return stream.boxed().flatMap(new Function<Integer, Stream<NumberOrOp>>() {
                List<Integer> temp = new ArrayList<>();
                int ptr = -1;

                @Override
                public Stream<NumberOrOp> apply(Integer value) {
                    temp.add(value);
                    List<NumberOrOp> result = new ArrayList<>();
                    while (ptr < temp.size() - 1){
                        apply2(value).ifPresent(result::add);
                    }
                    return result.stream();
                }

                public Optional<NumberOrOp> apply2(int value){
                    ptr++;
                    TokenizeResult result = Session.this.apply(value);
                    switch (result.status()){
                        case TokenizeResult.UNDETERMINED:
                            return Optional.empty();
                        case TokenizeResult.SUCCEED:
                            int rollback = result.rollback();
                            ptr -= rollback;
                            return Optional.of(result.get());
                        case TokenizeResult.FAILED:
                            if(value == -1){
                                return Optional.empty();
                            }
                        default:
                            throw new RuntimeException("invalid input");
                    }
                }
            });
        }
    }
}

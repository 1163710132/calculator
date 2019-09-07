package com.chen1144.calculator.core;

import java.util.List;

public interface Operator extends DisplayOperator, CalculateOperator, CaptureOperator, NumberOrOp {
    String getCode();
    int getPriority();

    @Override
    int captureForward();

    @Override
    int captureBackward();

    //@Override
    //Number calculate(IntFunction<Number> params);

    @Override
    String getMarkdown(List<String> params);

    @Override
    default boolean isNumber(){
        return false;
    }
    @Override
    default boolean isOperator(){
        return true;
    }
    @Override
    default Number asNumber(){
        throw new UnsupportedOperationException("Cannot regard operator as number.");
    }
    @Override
    default Operator asOperator(){
        return this;
    }
}

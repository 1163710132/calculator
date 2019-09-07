package com.chen1144.calculator.core;

public interface Number extends NumberOrOp {

    @Override
    default boolean isNumber(){
        return true;
    }

    @Override
    default boolean isOperator(){
        return false;
    }

    @Override
    default Number asNumber(){
        return this;
    }

    @Override
    default Operator asOperator(){
        throw new UnsupportedOperationException("Cannot regard number as an operator.");
    }

    @Override
    String toMath();

    NumberType getNumberType();
}

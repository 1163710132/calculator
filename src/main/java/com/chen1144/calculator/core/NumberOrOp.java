package com.chen1144.calculator.core;

public interface NumberOrOp {
    boolean isNumber();
    boolean isOperator();
    Number asNumber();
    Operator asOperator();
    String toMath();
}

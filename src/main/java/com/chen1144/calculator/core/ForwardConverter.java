package com.chen1144.calculator.core;

public interface ForwardConverter<N1 extends Number, N2 extends Number> {
    NumberType<N1> getSourceType();
    NumberType<N2> getTargetType();
    N2 convert(N1 source);
    int getDistance();
}

package com.chen1144.calculator.core.util;

import com.chen1144.calculator.core.ForwardConverter;
import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.NumberType;

public class CompressedForwardConverter<P1 extends Number, P2 extends Number, P3 extends Number> implements ForwardConverter<P1, P3> {
    private ForwardConverter<P1, P2> converter1;
    private ForwardConverter<P2, P3> converter2;

    public CompressedForwardConverter(ForwardConverter<P1, P2> converter1, ForwardConverter<P2, P3> converter2){
        this.converter1 = converter1;
        this.converter2 = converter2;
    }

    @Override
    public NumberType<P1> getSourceType() {
        return converter1.getSourceType();
    }

    @Override
    public NumberType<P3> getTargetType() {
        return converter2.getTargetType();
    }

    @Override
    public P3 convert(P1 source) {
        return converter2.convert(converter1.convert(source));
    }

    @Override
    public int getDistance() {
        return converter1.getDistance() + converter2.getDistance();
    }
}

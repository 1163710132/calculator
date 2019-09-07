package com.chen1144.calculator.core.util;

import com.chen1144.calculator.core.ForwardConverter;
import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.NumberType;

public class SelfConverter<TN extends Number> implements ForwardConverter<TN, TN> {
    private NumberType<TN> numberType;

    public SelfConverter(NumberType<TN> numberType){
        this.numberType = numberType;
    }

    @Override
    public NumberType<TN> getSourceType() {
        return numberType;
    }

    @Override
    public NumberType<TN> getTargetType() {
        return numberType;
    }

    @Override
    public TN convert(TN source) {
        return source;
    }

    @Override
    public int getDistance() {
        return 0;
    }
}

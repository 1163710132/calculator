package com.chen1144.calculator.plugin.base;

import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.NumberType;

public class NullNumber implements Number {
    public static final NullNumber INSTANCE = new NullNumber();

    @Override
    public String toMath() {
        return "null";
    }

    @Override
    public NumberType getNumberType() {
        return TYPE;
    }

    public static final NumberType<NullNumber> TYPE = ()->"NullNumber";
}

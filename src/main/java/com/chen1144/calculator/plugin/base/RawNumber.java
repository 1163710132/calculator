package com.chen1144.calculator.plugin.base;

import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.NumberType;

public class RawNumber implements Number {
    private String string;

    public RawNumber(String string){
        this.string = string;
    }

    @Override
    public String toMath() {
        return string;
    }

    @Override
    public NumberType getNumberType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return string;
    }

    static public final NumberType<RawNumber> TYPE = () -> "RawNumber";

}

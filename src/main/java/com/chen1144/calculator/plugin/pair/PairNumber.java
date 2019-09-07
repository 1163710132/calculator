package com.chen1144.calculator.plugin.pair;

import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.NumberType;

public class PairNumber<F extends Number, S extends Number> implements Number {
    private F first;
    private S second;

    public PairNumber(F first, S second){
        this.first = first;
        this.second = second;
    }

    public F getFirst(){
        return first;
    }

    public S getSecond(){
        return second;
    }

    @Override
    public String toMath() {
        return first.toMath() + "," + second.toMath();
    }

    @Override
    public NumberType getNumberType() {
        return TYPE;
    }

    public static final NumberType<PairNumber> TYPE = ()->"PairNumber";
}

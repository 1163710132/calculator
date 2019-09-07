package com.chen1144.calculator.core;

import java.lang.reflect.Type;

public interface NumberType<T extends Number> extends Type {
    @Override
    String getTypeName();

    static boolean match(NumberType pattern, NumberType type){
        if(pattern == ANY){
            return true;
        }else{
            return pattern == type;
        }
    }

    static public NumberType ANY = ()->"ANY";
}

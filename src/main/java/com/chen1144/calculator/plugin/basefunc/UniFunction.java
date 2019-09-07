package com.chen1144.calculator.plugin.basefunc;

import com.chen1144.calculator.core.Operator;

import java.util.List;

public class UniFunction implements Operator {
    private String code;

    public static final UniFunction SUM = new UniFunction("SUM");

    UniFunction(String code){
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getPriority() {
        return 90;
    }

    @Override
    public int captureForward() {
        return 0;
    }

    @Override
    public int captureBackward() {
        return 1;
    }

    @Override
    public String getMarkdown(List<String> params) {
        StringBuilder builder = new StringBuilder(code);
        if(params.size() > 1 && params.get(0) != null){
            return code + "(" + params.get(0) + ")";
        }else{
            return code + "()";
        }
    }

    @Override
    public String toMath() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UniFunction
                && ((UniFunction)obj).code.equals(code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}

package com.chen1144.calculator;

import com.chen1144.calculator.core.Operator;

import java.util.List;

public class NullOperator implements Operator {
    private String code;

    public NullOperator(String code){
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public int captureForward() {
        return 0;
    }

    @Override
    public int captureBackward() {
        return 0;
    }

    @Override
    public String getMarkdown(List<String> params) {
        return code;
    }

    @Override
    public String toMath() {
        return code;
    }
}

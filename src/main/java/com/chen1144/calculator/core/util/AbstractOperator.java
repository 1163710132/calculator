package com.chen1144.calculator.core.util;

import com.chen1144.calculator.core.Operator;

import java.util.List;

public class AbstractOperator implements Operator {
    private String code;
    private int priority;
    private int captureForward;
    private int captureBackward;

    public AbstractOperator(String code, int priority, int captureForward, int captureBackward){
        this.code = code;
        this.priority = priority;
        this.captureForward = captureForward;
        this.captureBackward = captureBackward;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toMath() {
        return code;
    }

    @Override
    public String getMarkdown(List<String> params) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i < captureForward;i++){
            builder.append(params.get(i)).append(' ');
        }
        builder.append(code);
        for(int i = 0;i < captureBackward;i++){
            builder.append(' ').append(params.get(i + captureForward));
        }
        return builder.toString();
    }

    @Override
    public int captureForward() {
        return captureForward;
    }

    @Override
    public int captureBackward() {
        return captureBackward;
    }

    @Override
    public String toString() {
        return code;
    }
}

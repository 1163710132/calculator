package com.chen1144.calculator.plugin.base;

import com.chen1144.calculator.core.Operator;

import java.util.List;

public enum Parentheses implements Operator {
    LEFT{
        @Override
        public String toMath() {
            return "(";
        }

        @Override
        public String getCode() {
            return "(";
        }

        @Override
        public int captureForward() {
            return 0;
        }

        @Override
        public int captureBackward() {
            return 2;
        }

        @Override
        public String getMarkdown(List<String> params) {
            return "(" + params.get(0) + params.get(1);
        }
    },
    RIGHT{
        @Override
        public String toMath() {
            return ")";
        }

        @Override
        public String getCode() {
            return ")";
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
            return ")";
        }
    };

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public abstract String getCode();

    @Override
    public abstract int captureForward();

    @Override
    public abstract int captureBackward();

    @Override
    public abstract String getMarkdown(List<String> params);
}

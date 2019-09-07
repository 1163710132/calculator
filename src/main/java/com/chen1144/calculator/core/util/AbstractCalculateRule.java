package com.chen1144.calculator.core.util;

import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.NumberType;
import com.chen1144.calculator.core.Operator;
import com.chen1144.calculator.core.context.CalculateContext;
import com.chen1144.calculator.core.CalculateRule;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public abstract class AbstractCalculateRule implements CalculateRule {
    private Operator operator;
    private List<NumberType> params;

    public AbstractCalculateRule(Operator operator, NumberType... params) {
        this.operator = operator;
        this.params = Arrays.asList(params);
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public Stream<NumberType> getParams() {
        return params.stream();
    }

    @Override
    public abstract Number calculate(IntFunction<Number> list, CalculateContext context);
}

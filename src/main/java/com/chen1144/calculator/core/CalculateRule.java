package com.chen1144.calculator.core;

import com.chen1144.calculator.core.context.CalculateContext;

import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface CalculateRule {

    Operator getOperator();

    Stream<NumberType> getParams();

    Number calculate(IntFunction<Number> list, CalculateContext context);
}

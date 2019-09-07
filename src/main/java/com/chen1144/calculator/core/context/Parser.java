package com.chen1144.calculator.core.context;

import com.chen1144.calculator.core.Expression;
import com.chen1144.calculator.core.NumberOrOp;

import java.util.stream.Stream;

public interface Parser {
    public Expression parse(Stream<NumberOrOp> stream);
}

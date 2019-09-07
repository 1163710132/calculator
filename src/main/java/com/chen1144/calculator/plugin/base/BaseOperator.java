package com.chen1144.calculator.plugin.base;

import com.chen1144.calculator.NullOperator;
import com.chen1144.calculator.core.Operator;
import com.chen1144.calculator.core.util.AbstractOperator;

import java.util.List;

public abstract class BaseOperator{
    public static final Operator PLUS = new AbstractOperator("+", 20, 1, 1);
    public static final Operator MINUS = new AbstractOperator("-", 20, 1, 1);
    public static final Operator MUL = new AbstractOperator("*", 30, 1, 1);
    public static final Operator DIV = new AbstractOperator("/", 30, 1, 1);
    public static final Operator DOT = new AbstractOperator(".", 100, 1, 1);
    public static final Operator SQUARE = new AbstractOperator("²", 50, 1, 0);
    public static final Operator PLACE_HOLDER = new AbstractOperator(" ", 1000, 0, 0);
}

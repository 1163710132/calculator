package com.chen1144.calculator.core.context;

import com.chen1144.calculator.core.Expression;
import com.chen1144.calculator.core.Number;

public interface Evaluator {
    Number eval(Expression expression);
}

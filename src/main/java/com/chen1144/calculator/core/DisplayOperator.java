package com.chen1144.calculator.core;

import java.util.List;
import java.util.function.IntFunction;

public interface DisplayOperator {
    String getMarkdown(List<String> params);
}

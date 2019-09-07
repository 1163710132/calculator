package com.chen1144.calculator.core.context;

import com.chen1144.calculator.core.context.Evaluator;
import com.chen1144.calculator.core.context.Parser;
import com.chen1144.calculator.core.context.Tokenizer;

public interface CalculateContext {
    Parser getParser();
    Tokenizer getTokenizer();
    Evaluator getEvaluator();
    PackageSet getPackageSet();
}

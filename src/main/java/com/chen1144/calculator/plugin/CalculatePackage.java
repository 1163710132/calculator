package com.chen1144.calculator.plugin;

import com.chen1144.calculator.core.CalculateRule;
import com.chen1144.calculator.core.ForwardConverter;
import com.chen1144.calculator.core.Operator;
import com.chen1144.calculator.core.context.Tokenizer;

import java.util.stream.Stream;

public interface CalculatePackage {
    Stream<CalculateRule> rules();
    //Stream<Operator> operators();
    Stream<Tokenizer> tokenParsers();
    Stream<ForwardConverter> converters();
}

package com.chen1144.calculator.core.util;

import com.chen1144.calculator.core.ForwardConverter;
import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.NumberType;
import com.chen1144.calculator.core.Operator;
import com.chen1144.calculator.core.context.CalculateContext;
import com.chen1144.calculator.core.CalculateRule;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class DerivedCalculateRule implements CalculateRule {
    private CalculateRule sourceRule;
    private List<ForwardConverter> converters;

    public DerivedCalculateRule(CalculateRule source, List<ForwardConverter> converters){
        this.sourceRule = source;
        this.converters = converters;
    }

    @Override
    public Operator getOperator() {
        return sourceRule.getOperator();
    }

    @Override
    public Stream<NumberType> getParams() {
        return sourceRule.getParams().map(new Function<>() {
            int counter = -1;
            @Override
            public NumberType apply(NumberType type) {
                counter++;
                if(converters.size() > counter
                        && converters.get(counter) != null){
                    return converters.get(counter).getSourceType();
                }else{
                    return type;
                }
            }
        });
    }

    @Override
    public Number calculate(IntFunction<Number> list, CalculateContext context) {
        return sourceRule.calculate(i ->
            converters.size() > i && converters.get(i) != null
                    ? converters.get(i).convert(list.apply(i))
                    : list.apply(i), context);
    }
}

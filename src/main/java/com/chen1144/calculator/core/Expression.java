package com.chen1144.calculator.core;

import java.util.Arrays;
import java.util.function.Consumer;

public final class Expression {
    private Expression[] captured;
    private Operator operator;
    private Number value;

    public Expression(Number value){
        this.value = value;
    }

    public Expression(Operator operator){
        this.operator = operator;
    }

    public Expression[] getCaptured() {
        return captured;
    }

    public void setCaptured(Expression[] captured) {
        this.captured = captured;
    }

    public Operator getOperator() {
        return operator;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public void forEach(Consumer<NumberOrOp> consumer){
        if(operator != null){
            consumer.accept(operator);
        }
        if(value != null){
            consumer.accept(value);
        }
        if(captured != null){
            for(Expression expression : captured){
                expression.forEach(consumer);
            }
        }
    }

    @Override
    public String toString() {
        if(operator != null){
            return operator.toString();
        }else if(value != null){
            return value.toString();
        }else{
            return "";
        }
    }
}

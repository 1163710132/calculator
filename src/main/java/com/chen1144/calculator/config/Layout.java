package com.chen1144.calculator.config;

import com.chen1144.calculator.util.Property;

public class Layout {
    private Size size;
    private Size expressionSize;
    private Size resultSize;
    private InputLayout inputLayout;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Property<Size> size(){
        return new Property<>(this::getSize, this::setSize);
    }

    public InputLayout getInputLayout() {
        return inputLayout;
    }

    public void setInputLayout(InputLayout inputLayout) {
        this.inputLayout = inputLayout;
    }

    public Property<InputLayout> inputLayout(){
        return Property.of(this::getInputLayout, this::setInputLayout);
    }

    public Size getExpressionSize() {
        return expressionSize;
    }

    public void setExpressionSize(Size expressionSize) {
        this.expressionSize = expressionSize;
    }

    public Property<Size> expressionSize(){
        return Property.of(this::getExpressionSize ,this::setExpressionSize);
    }

    public Size getResultSize() {
        return resultSize;
    }

    public void setResultSize(Size resultSize) {
        this.resultSize = resultSize;
    }

    public Property<Size> resultSize(){
        return Property.of(this::getResultSize ,this::setResultSize);
    }
}

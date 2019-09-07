package com.chen1144.calculator.config;

import com.chen1144.calculator.util.Property;

public class Size {
    private Integer width;
    private Integer height;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Property<Integer> width(){
        return Property.of(this::getWidth, this::setWidth);
    }

    public Property<Integer> height(){
        return Property.of(this::getHeight, this::setHeight);
    }
}

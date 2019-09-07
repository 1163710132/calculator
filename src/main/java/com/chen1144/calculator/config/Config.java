package com.chen1144.calculator.config;

import com.chen1144.calculator.util.Property;

import java.util.List;

public class Config {
    private List<Packages> packages;
    private Layout layout;

    public List<Packages> getPackages() {
        return packages;
    }

    public void setPackages(List<Packages> packages) {
        this.packages = packages;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Property<List<Packages>> packages(){
        return Property.of(this::getPackages, this::setPackages);
    }

    public Property<Layout> layout(){
        return Property.of(this::getLayout, this::setLayout);
    }
}

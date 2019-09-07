package com.chen1144.calculator.config;

import com.chen1144.calculator.util.Property;

import java.util.List;

public class Packages {
    private String namespace;
    private List<String> classes;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Property<String> namespace(){
        return Property.of(this::getNamespace, this::setNamespace);
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public Property<List<String>> classes(){
        return Property.of(this::getClasses, this::setClasses);
    }
}

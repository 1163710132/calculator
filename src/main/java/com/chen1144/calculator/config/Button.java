package com.chen1144.calculator.config;

import com.chen1144.calculator.util.Property;

import java.awt.*;

public class Button {
    private Integer column;
    private Integer row;
    private String keyCode;
    private Integer rowSpan;
    private Integer columnSpan;
    private int[] color;

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Property<Integer> column(){
        return new Property<>(this::getColumn, this::setColumn);
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Property<Integer> row(){
        return new Property<>(this::getRow, this::setRow);
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public Property<String> keyCode(){
        return new Property<>(this::getKeyCode, this::setKeyCode);
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Property<Integer> rowSpan(){
        return new Property<>(this::getRowSpan, this::setRowSpan);
    }


    public Integer getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(Integer columnSpan) {
        this.columnSpan = columnSpan;
    }

    public Property<Integer> columnSpan(){
        return Property.of(this::getColumnSpan, this::setColumnSpan);
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public Property<int[]> color(){
        return Property.of(this::getColor, this::setColor);
    }

    public void format(Button another){
        another.rowSpan().putIfAbsent(1);
        another.columnSpan().putIfAbsent(1);
        another.keyCode().putIfAbsent(keyCode);
        another.color().putIfAbsent(color);
    }
}

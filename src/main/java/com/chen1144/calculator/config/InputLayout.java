package com.chen1144.calculator.config;

import com.chen1144.calculator.util.Property;

import java.util.List;

public class InputLayout {
    private Integer rowCount;
    private Integer columnCount;
    private List<Button> buttons;
    private Button defaultButton;
    private Size size;

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Property<Integer> rowCount(){
        return Property.of(this::getRowCount, this::setRowCount);
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    public Property<Integer> columnCount(){
        return Property.of(this::getColumnCount, this::setColumnCount);
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public Property<List<Button>> buttons(){
        return Property.of(this::getButtons, this::setButtons);
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Property<Size> size(){
        return Property.of(this::getSize, this::setSize);
    }

    public Button getDefaultButton() {
        return defaultButton;
    }

    public void setDefaultButton(Button defaultButton) {
        this.defaultButton = defaultButton;
    }

    public Property<Button> defaultButton(){
        return Property.of(this::getDefaultButton, this::setDefaultButton);
    }
}

package com.chen1144.calculator.swing;

import com.chen1144.calculator.config.Button;
import com.chen1144.calculator.config.InputLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.function.Consumer;

public class CalculatorInput extends JComponent {
//    public static final String BUTTONS = "Buttons";
//    public static final String ROW = "Row";
//    public static final String COLUMN = "Column";
//    public static final String ROW_SPAN = "RowSpan";
//    public static final String COLUMN_SPAN = "ColumnSpan";
//    public static final String ROW_COUNT = "RowCount";
//    public static final String COLUMN_COUNT = "Column";
//    public static final String KEY_CODE = "KeyCode";
//    public static final String BTN_WIDTH = "BtnWidth";
//    public static final String BTN_HEIGHT = "BtnHeight";
//    public static final String INPUT_WIDTH = "InputWidth";
//    public static final String INPUT_HEIGHT = "InputHeight";

    private Consumer<String> keyListener;
    private GridBagLayout layout;

    public CalculatorInput(){
        setLayout(layout = new GridBagLayout());
    }

    public void addKeyListener(Consumer<String> keyListener){
        this.keyListener = this.keyListener == null
                ? keyListener
                : this.keyListener.andThen(keyListener);
    }

    public void load(InputLayout layout){
        List<Button> buttons = layout.getButtons();
        int rowCount = layout.getRowCount();
        int columnCount = layout.getColumnCount();
        int inputWidth = layout.getSize().getWidth();
        int inputHeight = layout.getSize().getHeight();
        int btnWidth = inputWidth / columnCount;
        int btnHeight = inputHeight / rowCount;
        Dimension dimension = new Dimension(inputWidth, inputHeight);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        buttons.forEach(buttonConfig->{
            layout.getDefaultButton().format(buttonConfig);
            int row = buttonConfig.getRow();
            int column = buttonConfig.getColumn();
            int rowSpan = buttonConfig.getRowSpan();
            int columnSpan = buttonConfig.getColumnSpan();
            String keyCode = buttonConfig.getKeyCode();
            Color color = new Color(buttonConfig.getColor()[0],
                    buttonConfig.getColor()[1],
                    buttonConfig.getColor()[2]);
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = column;
            constraints.gridy = row;
            constraints.gridwidth = columnSpan;
            constraints.gridheight = rowSpan;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 0;
            constraints.weighty = 0;
            JButton button = new JButton();
            button.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(keyListener != null)
                        keyListener.accept(keyCode);
                }
            });
            button.setText(keyCode);
            button.setBackground(color);
            Border border = BorderUIResource.getRaisedBevelBorderUIResource();
            button.setBorder(border);
            button.setFocusable(false);
            Dimension btnDimension = new Dimension(btnWidth * columnSpan, btnHeight * rowSpan);
            button.setMaximumSize(btnDimension);
            button.setMinimumSize(btnDimension);
            button.setPreferredSize(btnDimension);
            this.add(button, constraints);
            this.layout.setConstraints(button, constraints);
        });
    }

    public void clear() {
        this.removeAll();
    }
}

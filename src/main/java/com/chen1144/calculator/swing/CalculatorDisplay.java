package com.chen1144.calculator.swing;

import com.chen1144.calculator.config.Size;
import net.sourceforge.jeuclid.swing.JMathComponent;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CalculatorDisplay extends JComponent {
    private JTextField expressionField;
    private JMathComponent jMathComponent;
    private GridBagLayout layout;

    public static final int INPUT_LINE_HEIGHT = 48;
    public static final int OUTPUT_LINE_HEIGHT = 48;
    public static final int WIDTH = 256;

    public CalculatorDisplay(){
        setLayout(layout = new GridBagLayout());
        expressionField = new JTextField(){
            {
                setFont(new Font("宋体",Font.PLAIN,32));
                GridBagConstraints constraints = new GridBagConstraints(){
                    {
                        gridwidth = 1;
                        gridheight = 1;
                        gridx = 0;
                        gridy = 0;
                        weightx = 100;
                    }
                };
                CalculatorDisplay.this.add(this, constraints);
            }
        };
        {
            Dimension expressionFieldDimension = new Dimension(WIDTH, OUTPUT_LINE_HEIGHT);
            expressionField.setPreferredSize(expressionFieldDimension);
            expressionField.setMaximumSize(expressionFieldDimension);
            expressionField.setMinimumSize(expressionFieldDimension);
        }
        jMathComponent = new JMathComponent(){
            {
                setFont(new Font("宋体",Font.PLAIN,32));
                GridBagConstraints constraints = new GridBagConstraints(){
                    {
                        gridwidth = 1;
                        gridheight = 1;
                        gridx = 0;
                        gridy = 1;
                        weightx = 100;
                    }
                };
                CalculatorDisplay.this.add(this, constraints);
            }
        };
        {
            Dimension dimension = new Dimension(WIDTH, INPUT_LINE_HEIGHT);
            jMathComponent.setMinimumSize(dimension);
            jMathComponent.setPreferredSize(dimension);
            jMathComponent.setMaximumSize(dimension);
        }
        jMathComponent.setHorizontalAlignment(JMathComponent.RIGHT);
        jMathComponent.setVerticalAlignment(JMathComponent.BOTTOM);
    }

    public void setExpression(String expressionText){
        expressionField.setText(expressionText);
    }

    public String getExpression(){
        return expressionField.getText();
    }

    public void append(String string){
        String text = expressionField.getText();
        int cursor = expressionField.getCaretPosition();
        String newText = new StringBuilder().append(text.subSequence(0, cursor)).append(string).append(text.subSequence(cursor, text.length())).toString();
        expressionField.setText(newText);
    }

    public void setResult(String string){
        jMathComponent.setContent(string);
    }

    public void delete(){
        String expressionText = expressionField.getText();
        int length = expressionText.length();
        if(length > 0){
            expressionField.setText(expressionText.substring(0, length - 1));
        }
    }

    public void setExpressionSize(Size expressionSize){
        Dimension dimension = new Dimension(expressionSize.getWidth(), expressionSize.getHeight());
        expressionField.setPreferredSize(dimension);
        expressionField.setMinimumSize(dimension);
        expressionField.setMaximumSize(dimension);
    }

    public void setResultSize(Size resultSize){
        Dimension dimension = new Dimension(resultSize.getWidth(), resultSize.getHeight());
        jMathComponent.setPreferredSize(dimension);
        jMathComponent.setMinimumSize(dimension);
        jMathComponent.setMaximumSize(dimension);
    }

    public void clear(){
        expressionField.setText("");
    }
}

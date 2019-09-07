package com.chen1144.calculator.swing;

import com.chen1144.calculator.core.Calculator;
import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.core.Operator;
import com.chen1144.calculator.config.Config;
import com.chen1144.calculator.config.Layout;
import com.chen1144.calculator.config.Size;
import com.chen1144.calculator.plugin.base.BasePackage;
import com.chen1144.calculator.plugin.CalculatePackage;
import com.chen1144.calculator.plugin.rational.RationalNumberPackage;
import com.google.gson.Gson;
import uk.ac.ed.ph.snuggletex.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CalculatorView extends JInternalFrame {
    private CalculatorInput calculatorInput;
    private CalculatorDisplay calculatorDisplay;
    private Calculator calculator;
    private SnuggleEngine snuggleEngine;
    private Map<Operator, JButton> operatorMap;

    public CalculatorView(){
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        calculatorInput = new CalculatorInput();
        calculatorDisplay = new CalculatorDisplay();
        calculator = new Calculator();
        snuggleEngine = new SnuggleEngine();
        operatorMap = new HashMap<>();
        add(calculatorDisplay);
        add(calculatorInput);
        calculatorInput.addKeyListener(keyCode->{
            switch (keyCode){
                case "OK":
                    calculate();
                    break;
                case "CE":
                    calculatorDisplay.clear();
                    break;
                case "DEL":
                    calculatorDisplay.delete();
                    break;
                default:
                    calculatorDisplay.append(keyCode);
                    break;
            }
        });
    }

    public void importPackage(CalculatePackage calculatePackage){
        calculator.importPackage(calculatePackage);
        //calculatorInput.clear();
        calculatorDisplay.clear();
    }

    public void calculate(){
        String expressionText = calculatorDisplay.getExpression();
        SnuggleSession session = snuggleEngine.createSession();
        try{
            try{
                Number number = calculator.eval(expressionText);
                session.parseInput(new SnuggleInput("$$" + number.toMath() + "$$"));
                session.buildXMLString();
                calculatorDisplay.setResult(session.buildXMLString());
            }catch (RuntimeException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load(Config config){
        Layout layout = config.getLayout();
        Dimension dimension = new Dimension(layout.getSize().getWidth(), layout.getSize().getHeight());
        calculator = new Calculator();
        config.getPackages().stream()
                .flatMap(packages ->
                        packages.getClasses().stream()
                                .map(className->packages.getNamespace() + "." + className))
                .forEach(className->{
                    try{
                        Class classObject = Class.forName(className);
                        try{
                            Constructor constructor = classObject.getConstructor();
                            try{
                                CalculatePackage pkg = (CalculatePackage) constructor.newInstance();
                                importPackage(pkg);
                            }catch (InstantiationException | IllegalAccessException |
                                    IllegalArgumentException | InvocationTargetException e){
                                e.printStackTrace();
                            }
                        }catch (NoSuchMethodException e){
                            e.printStackTrace();
                        }
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                });
        layout.expressionSize()
                .computeIfAbsent(Size::new)
                .exec(size -> {
                    size.width().putIfAbsent(layout.getSize().getWidth());
                    size.height().putIfAbsent(CalculatorDisplay.INPUT_LINE_HEIGHT);
                });
        layout.resultSize()
                .computeIfAbsent(Size::new)
                .exec(size -> {
                    size.width().putIfAbsent(layout.getSize().getWidth());
                    size.height().putIfAbsent(CalculatorDisplay.OUTPUT_LINE_HEIGHT);
                });
        layout.getInputLayout().size()
                .computeIfAbsent(Size::new)
                .exec(size -> {
                    size.width().putIfAbsent(layout.getSize().getWidth());
                    size.height().computeIfAbsent(()->{
                        return layout.getSize().getHeight()
                                - layout.getExpressionSize().getHeight()
                                - layout.getResultSize().getHeight();
                    });
                });
        calculatorDisplay.setExpressionSize(layout.getExpressionSize());
        calculatorDisplay.setResultSize(layout.getResultSize());
        calculatorInput.load(layout.getInputLayout());
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        CalculatorView calculatorView = new CalculatorView();
        calculatorView.importPackage(new BasePackage());
        calculatorView.importPackage(new RationalNumberPackage());
        calculatorView.setVisible(true);
        jFrame.setContentPane(calculatorView);
        URL layout = CalculatorView.class.getClassLoader().getResource("config.json");
        try {
            InputStream stream = layout.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new Gson();
            Config config = gson.fromJson(bufferedReader, Config.class);
            calculatorView.load(config);
        }catch (IOException e){
            e.printStackTrace();
        }
        //jFrame.setSize(400, 400);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

package com.chen1144.calculator;

import com.chen1144.calculator.core.Calculator;
import com.chen1144.calculator.core.Number;
import com.chen1144.calculator.plugin.rational.RationalNumberPackage;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.converter.Converter;
import org.w3c.dom.Document;
//import org.w3c.dom.events.CustomEvent;
import uk.ac.ed.ph.snuggletex.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Calculator calculator = new Calculator();
        calculator.importPackage(new RationalNumberPackage());
        Number result = calculator.eval("(1+1+2)");
        SnuggleEngine engine = new SnuggleEngine();
        SnuggleSession session = engine.createSession();
        session.parseInput(new SnuggleInput("$$" + result.toMath() + "$$"));
        Document document = session.createWebPage(WebPageOutputOptionsTemplates.createWebPageOptions(WebPageOutputOptions.WebPageType.CROSS_BROWSER_XHTML));
        BufferedImage image = Converter.getConverter().render(document, LayoutContextImpl.getDefaultLayoutContext());
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(label);
        jFrame.setVisible(true);
    }
}

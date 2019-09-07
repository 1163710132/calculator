package com.chen1144.calculator.config;

import com.chen1144.calculator.util.Random;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ButtonTest {

    @Test
    public void testFormat() {
        Button prototype = new Button();
        prototype.setColor(Random.randomInts(3));
        prototype.setColumn(Random.randomInt());
        prototype.setRow(Random.randomInt());
        prototype.setKeyCode(Random.randomString());
        Button button = new Button();
        prototype.format(button);
        assertEquals(button.getRowSpan().intValue(), 1);
        assertEquals(button.getColumnSpan().intValue(), 1);
        assertEquals(button.getKeyCode(), prototype.getKeyCode());
        assertEquals(button.getColor(), prototype.getColor());
    }
}
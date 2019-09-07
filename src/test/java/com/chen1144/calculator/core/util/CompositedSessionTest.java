package com.chen1144.calculator.core.util;

import com.chen1144.calculator.core.context.Tokenizer;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static org.testng.Assert.*;

public class CompositedSessionTest {

    @Test
    public void testApply() {
        CompositedSession compositedSession = new CompositedSession(Stream.empty());
        assertEquals(compositedSession.apply(0), Tokenizer.TokenizeResult.ofFailed());
    }
}
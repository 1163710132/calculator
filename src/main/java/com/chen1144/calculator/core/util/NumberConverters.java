package com.chen1144.calculator.core.util;

import com.chen1144.calculator.plugin.rational.RationalNumber;
import com.chen1144.calculator.plugin.base.RawNumber;

import java.util.function.Function;

public class NumberConverters {
    public static final Function<RawNumber, RationalNumber> RAW2RATIONAL = RationalNumber::new;
}

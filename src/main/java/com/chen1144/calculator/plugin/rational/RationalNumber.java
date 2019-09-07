package com.chen1144.calculator.plugin.rational;

import com.chen1144.calculator.core.NumberType;
import com.chen1144.calculator.plugin.base.RealNumber;
import com.chen1144.calculator.plugin.base.RawNumber;

import java.math.BigInteger;
import java.util.Objects;

public final class RationalNumber implements RealNumber {
    private BigInteger dividend;
    private BigInteger divisor;
    private boolean optimized;

    public RationalNumber(BigInteger value){
        this.dividend = value;
        this.divisor = BigInteger.ONE;
        this.optimized = true;
    }

    public RationalNumber(RawNumber rawNumber){
        this(new BigInteger(rawNumber.toString()));
    }

    public RationalNumber(BigInteger dividend, BigInteger divisor){
        this.dividend = dividend;
        this.divisor = divisor;
    }

    public void optimize(){
        if(!optimized){
            BigInteger gcd = divisor.gcd(dividend);
            if(!gcd.equals(BigInteger.ONE)){
                synchronized (this){
                    dividend = dividend.divide(gcd);
                    divisor = divisor.divide(gcd);
                    optimized = true;
                }
            }
        }
    }

    @Override
    public int hashCode() {
        optimize();
        return Objects.hash(dividend, divisor);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RationalNumber){
            RationalNumber other = (RationalNumber)obj;
            if(optimized && other.optimized){
                return dividend.equals(other.dividend) && divisor.equals(other.divisor);
            }else{
                return divisor.multiply(other.dividend).equals(dividend.multiply(other.divisor));
            }
        }else{
            return false;
        }
    }

    public boolean isZero(){
        return dividend.equals(BigInteger.ZERO);
    }

    public RationalNumber negate(){
        RationalNumber negate;
        if(optimized){
            negate = new RationalNumber(dividend.negate(), divisor);
            negate.optimized = true;
        }else{
            negate = new RationalNumber(dividend.negate(), divisor);
        }
        return negate;
    }

    public RationalNumber reciprocal(){
        return new RationalNumber(divisor, dividend);
    }

    public static RationalNumber opPlus(RationalNumber r0, RationalNumber r1){
        if(r0.divisor.equals(r1.divisor)){
            return new RationalNumber(r0.dividend.add(r1.dividend), r0.divisor);
        }
        else{
            return new RationalNumber(r0.dividend.multiply(r1.divisor).add(r1.dividend.multiply(r0.divisor)),
                    r0.divisor.multiply(r1.divisor));
        }
    }

    public static RationalNumber opSub(RationalNumber r0, RationalNumber r1){
        return opPlus(r0, r1.negate());
    }

    public static RationalNumber opMul(RationalNumber r0, RationalNumber r1){
        return new RationalNumber(r0.dividend.multiply(r1.dividend), r0.divisor.multiply(r1.divisor));
    }

    public static RationalNumber opDiv(RationalNumber r0, RationalNumber r1){
        if(r1.isZero()){
            throw new UnsupportedOperationException("Divide in zero error.");
        }else{
            return opMul(r0, r1.reciprocal());
        }
    }

    public static final NumberType<RationalNumber> TYPE = new NumberType<RationalNumber>() {
        @Override
        public String getTypeName() {
            return "RationalNumber";
        }
    };

    @Override
    public String toMath() {
        optimize();
        if(divisor.equals(BigInteger.ONE)){
            return dividend.toString();
        }else{
            return "\\frac{" + dividend + "}{" + divisor + "}";
        }
    }

    @Override
    public NumberType getNumberType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return dividend.toString() + "/" + divisor.toString();
    }
}

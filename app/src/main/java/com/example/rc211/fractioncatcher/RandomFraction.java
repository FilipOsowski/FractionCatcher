package com.example.rc211.fractioncatcher;

public class RandomFraction {
    private int numerator;
    private int denominator;

    public RandomFraction() {
        do {
            generateRandomFraction();
        } while (getDecimal() == (float)3/2);
    }

    public void generateRandomFraction() {
        int multiplier = (int) (Math.random() * 5 + 1);
        this.denominator = (int) (Math.random() * 4) + 1;
        this.denominator *= multiplier;
        this.numerator = (int) (Math.random() * 5) + 1;
        this.numerator *= multiplier;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public float getDecimal() {
        return (float)numerator/denominator;
    }
}

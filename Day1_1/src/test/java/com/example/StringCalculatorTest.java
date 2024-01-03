package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

    @Test
    public void testEmptyString() {
        assertEquals(0, new StringCalculator().add(""));
    }

    @Test
    public void testOneNumber() {
        assertEquals(1, new StringCalculator().add("1"));
    }

    @Test
    public void testTwoNumbers() {
        assertEquals(3, new StringCalculator().add("1,2"));
    }

    @Test
    public void testNewLineDelimiter() {
        assertEquals(6, new StringCalculator().add("1\n2,3"));
    }
}

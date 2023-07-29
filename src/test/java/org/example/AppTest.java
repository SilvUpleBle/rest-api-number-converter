package org.example;

import com.service.algorithm.resources.NumberConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void test1() {
        Assertions.assertEquals("сто двадцать три", NumberConverter.numberToString((long) 123));
    }

    @Test
    public void test2() {
        Assertions.assertEquals("", NumberConverter.numberToString((100001000003L)));
    }

    @Test
    public void test3() {
        Assertions.assertEquals(1, NumberConverter.stringToNumber("сто двадцать один миллион две тысячи тринадцать"));
    }
}
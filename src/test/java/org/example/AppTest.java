package org.example;

import com.service.algorithm.resources.NumberConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void test1() {
        try {

            Assertions.assertEquals("сто двадцать три", NumberConverter.numberToString((long) 123));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void test2() {
        try {

            Assertions.assertEquals("", NumberConverter.numberToString((100001000003L)));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void test3() {
        try {
            Assertions.assertEquals(1,
                    NumberConverter.stringToNumber("сто двадцать один миллион две тысячи тринадцать"));
        } catch (Exception e) {
        }
    }
}
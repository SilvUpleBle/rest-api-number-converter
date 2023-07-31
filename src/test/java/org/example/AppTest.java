package org.example;

import com.service.algorithm.resources.NumberConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void test1() {
        try {
            Assertions.assertEquals("сто двадцать тысяч", NumberConverter.numberToString(120000L));
        } catch (Exception e) {
        }
    }

    @Test
    public void test2() {
        try {
            Assertions.assertEquals("девятьсот девяносто девять миллиардов девятьсот девяносто девять миллионов девятьсот девяносто девять тысяч девятьсот девяносто девять", NumberConverter.numberToString((999999999999L)));
        } catch (Exception e) {
        }
    }

    @Test
    public void test3() {
        try {
            Assertions.assertEquals(121002013L, NumberConverter.stringToNumber("сто двадцать один миллион две тысячи тринадцать"));
        } catch (Exception e) {
        }
    }

    @Test
    public void test4() {
        try {
            Assertions.assertEquals(-120000L, NumberConverter.stringToNumber("минус сто двадцать тысяч"));
        } catch (Exception e) {
        }
    }
}
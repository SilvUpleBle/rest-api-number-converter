package org.example;

import com.service.algorithm.exceptions.IncorrectInputOrderException;
import com.service.algorithm.exceptions.InvalidValueException;
import com.service.algorithm.exceptions.NumberOutOfRangeException;
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

    @Test
    public void test5() {
        try {
            Assertions.assertEquals("минус сто двадцать тысяч", NumberConverter.numberToString(-120000L));
        } catch (Exception e) {
        }
    }

    @Test
    public void test6() {
        try {
            Assertions.assertEquals("ноль", NumberConverter.numberToString(0L));
        } catch (Exception e) {
        }
    }

    // здесь ошибка придёт с сообщением о том, как правильно написать
    @Test
    public void test7() {
        try {
            Assertions.assertEquals(new InvalidValueException(), NumberConverter.stringToNumber("две тысяч"));
        } catch (Exception e) {
        }
    }

    // здесь ошибка придёт с сообщением о том, что слова нет в map
    @Test
    public void test8() {
        try {
            Assertions.assertEquals(new InvalidValueException(), NumberConverter.stringToNumber("sdafdsafd"));
        } catch (Exception e) {
        }
    }

    // здесь ошибка придёт с сообщением о том, что нельзя использовать разряд два раза
    @Test
    public void test9() {
        try {
            Assertions.assertEquals(new IncorrectInputOrderException(), NumberConverter.stringToNumber("два пять"));
        } catch (Exception e) {
        }
    }

    // здесь ошибка придёт с сообщением о том, что нужно классы чисел нужно записывать в правильном порядке
    @Test
    public void test10() {
        try {
            Assertions.assertEquals(new IncorrectInputOrderException(), NumberConverter.stringToNumber("пять тысяч сто миллионов"));
        } catch (Exception e) {
        }
    }

    // здесь ошибка придёт с сообщением о том, что нужно разряды класса нужно записывать в правильном порядке
    @Test
    public void test11() {
        try {
            Assertions.assertEquals(new IncorrectInputOrderException(), NumberConverter.stringToNumber("пять сто"));
        } catch (Exception e) {
        }
    }

    // здесь ошибка придёт с сообщением о том, что нельзя записывать название класса без числа перед ним
    @Test
    public void test12() {
        try {
            Assertions.assertEquals(new IncorrectInputOrderException(), NumberConverter.stringToNumber("тысяча сто"));
        } catch (Exception e) {
        }
    }

    // здесь ошибка придёт с сообщением о том, что число превышает 12 разрядов
    @Test
    public void test13() {
        try {
            Assertions.assertEquals(new NumberOutOfRangeException(), NumberConverter.numberToString(1234567891011L));
        } catch (Exception e) {
        }
    }

    @Test
    public void test14() {
        try {
            Assertions.assertEquals(504L, NumberConverter.stringToNumber("пятьсот четыре"));
        } catch (Exception e) {
        }
    }

    @Test
    public void test15() {
        try {
            Assertions.assertEquals(203L, NumberConverter.stringToNumber("двести три"));
        } catch (Exception e) {
        }
    }

    @Test
    public void test16() {
        try {
            Assertions.assertEquals(1_000_001L, NumberConverter.stringToNumber("один миллион один"));
        } catch (Exception e) {
        }
    }

    @Test
    public void test17() {
        try {
            Assertions.assertEquals(new InvalidValueException(), NumberConverter.stringToNumber("миллион один"));
        } catch (Exception e) {
        }
    }
}
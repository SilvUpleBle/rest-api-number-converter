package com.service.algorithm.resources;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.service.algorithm.exceptions.IncorrectInputOrder;
import com.service.algorithm.exceptions.InvalidValueException;
import com.service.algorithm.exceptions.NumberOutOfRangeException;

public class NumberConverter {

    private NumberConverter() {
        // закрывам конструктор
    }

    // ЧИСЛО В СТРОКУ
    public static String numberToString(Long number) throws NumberOutOfRangeException {
        if (Math.abs(number) > 999999999999L) {
            throw new NumberOutOfRangeException("Сonverter works with numbers up to and including 12 digits");
        }

        return prepareStringForNumberToString(number);
    }

    // СТРОКА В ЧИСЛО
    public static Long stringToNumber(String str) throws InvalidValueException, IncorrectInputOrder {
        for (String element : str.split(" ")) {
            if (!(GlobalVars.numbersAndNames.containsValue(element)
                    || GlobalVars.namesOfClasses.containsValue(element))) {
                throw new InvalidValueException("Input contains an unknown word: " + element);
            }
        }

        return prepareNumberForStringToNumber(str);
    }

    private static String getNameOfClass(Integer classNumber, Integer classOfNumber) {
        if (classOfNumber == 0)
            return null;

        Integer lastNumberOfClass;
        if (classOfNumber % 100 > 10 && classOfNumber % 100 < 20)
            lastNumberOfClass = classOfNumber % 100;
        else
            lastNumberOfClass = classOfNumber % 10;

        switch (classNumber) {
            case 1 -> {
                return null;
            }
            case 2 -> { // ТЫСЯЧИ
                return switch (lastNumberOfClass) {
                    case 0, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19 -> GlobalVars.namesOfClasses.get("21"); // тысяч
                    case 1 -> GlobalVars.namesOfClasses.get("22"); // тысяча
                    default -> GlobalVars.namesOfClasses.get("23"); // тысячи
                };
            }
            case 3 -> { // МИЛЛИОНЫ
                return switch (lastNumberOfClass) {
                    case 1 -> GlobalVars.namesOfClasses.get("31"); // миллион
                    case 2, 3, 4 -> GlobalVars.namesOfClasses.get("32"); // миллиона
                    default -> GlobalVars.namesOfClasses.get("33"); // миллионов
                };
            }
            case 4 -> { // МИЛЛИАРДЫ
                return switch (lastNumberOfClass) {
                    case 1 -> GlobalVars.namesOfClasses.get("41"); // миллиард
                    case 2, 3, 4 -> GlobalVars.namesOfClasses.get("42"); // миллиарда
                    default -> GlobalVars.namesOfClasses.get("43"); // миллиардов
                };
            }
            default -> {
                return "ошибка"; // TODO выбрасывать ошибку
            }
        }
    }

    private static char[][] parsNumberByDigits(Integer number) {
        char[][] digits = { { '#', '#', '#' }, { '#', '#' }, { '#' } };

        if (number > 99)
            digits[0][0] = (char) (number / 100 + '0');

        if (number % 100 > 19)
            digits[1][0] = (char) (number / 10 % 10 + '0');
        else if (number % 100 >= 10 && number % 100 <= 19) {
            digits[1][0] = (char) (number / 10 % 10 + '0');
            digits[1][1] = (char) (number % 10 + '0');
        }

        if (digits[1][1] == '#' && (number % 10) != 0)
            digits[2][0] = (char) (number % 10 + '0');

        return digits;
    }

    private static LinkedList<char[][]> parseNumberByClasses(Long number) {
        LinkedList<char[][]> list = new LinkedList<>();
        do {
            list.add(parsNumberByDigits((int) (number % 1000)));
            number /= 1000;
        } while (number > 0);

        Collections.reverse(list); // переворачиваем лист, чтобы начать записывать в StrBuilder с высшего класса
        return list;
    }

    private static String prepareStringForNumberToString(Long number) {
        StringBuilder sb = new StringBuilder();
        List<char[][]> listOfClasses = parseNumberByClasses(number); // разбитие числа на классы (по тройкам)
        String[] stringForClass = new String[4];

        for (int i = 0; i < listOfClasses.size(); i++) {
            stringForClass[0] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[0])); // записываем сотни
            stringForClass[1] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[1])); // записываем
                                                                                                     // десятки

            if (listOfClasses.size() - i == 2
                    && (listOfClasses.get(i)[2][0] == '1' || listOfClasses.get(i)[2][0] == '2'))
                stringForClass[2] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[2]) + ".0"); // записываем
                                                                                                                // единицы
                                                                                                                // для
                                                                                                                // тысяч
                                                                                                                // (т.к.
                                                                                                                // другой
                                                                                                                // род)
            else
                stringForClass[2] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[2])); // записываем
                                                                                                         // единицы в
                                                                                                         // остальных
                                                                                                         // случаях

            stringForClass[3] = getNameOfClass(listOfClasses.size() - i,
                    (int) (number / Math.pow(1000, listOfClasses.size() - i - 1) % 1000)); // записываем записываем
                                                                                           // название класса тройки
                                                                                           // чисел

            for (int j = 0; j < 4; j++) {
                if (stringForClass[j] != null) {
                    stringForClass[j] += ' ';
                    sb.append(stringForClass[j]);
                }
            }
        }

        sb.deleteCharAt(sb.length() - 1); // убираем лишний пробел в конце
        return sb.toString();
    }

    private static Long prepareNumberForStringToNumber(String str) throws IncorrectInputOrder {
        try {
            Long number = 0L;
            Long buff = 0L;
            String[] words = str.split(" ");
            StringBuilder sb = new StringBuilder();

            for (String string : words) {
                if (getKey(GlobalVars.numbersAndNames, string) != null) {
                    sb.append(getKey(GlobalVars.numbersAndNames, string));

                    // TODO в отдельный метод
                    for (int i = 0; i < sb.length(); i++) {
                        if (sb.charAt(i) == '#') {
                            sb.setCharAt(i, '0');
                        }
                    }

                    buff += (long) Double.parseDouble(sb.toString());
                } else {
                    buff *= (long) Math.pow(1000, Integer.parseInt(getKey(GlobalVars.namesOfClasses, string)) / 10 - 1);
                    number += buff;
                    buff = 0L;
                }

                sb.setLength(0);
            }
            number += buff;

            return number;
        } catch (Exception e) {
            throw new IncorrectInputOrder("Incorrect input order");
        }

    }

    private static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
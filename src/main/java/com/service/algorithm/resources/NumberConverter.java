package com.service.algorithm.resources;

import java.util.*;

import com.service.algorithm.exceptions.IncorrectInputOrderException;
import com.service.algorithm.exceptions.InvalidValueException;
import com.service.algorithm.exceptions.NumberOutOfRangeException;

import static com.service.algorithm.service.Serivce.getKey;
import static com.service.algorithm.service.Serivce.replaceAll;

public class NumberConverter {

    private NumberConverter() {
        // закрываю конструктор
    }

    // ЧИСЛО В СТРОКУ
    public static String numberToString(Long number) throws NumberOutOfRangeException {
        // по ТЗ только до 12 разряда
        if (Math.abs(number) > 999999999999L) {
            throw new NumberOutOfRangeException("Сonverter works with numbers up to and including 12 digits");
        }

        if (number < 0) {
            return "минус " + prepareStringForNumberToString(Math.abs(number));
        }
        return prepareStringForNumberToString(number);
    }

    // СТРОКА В ЧИСЛО
    public static Long stringToNumber(String str) throws IncorrectInputOrderException, InvalidValueException, NumberOutOfRangeException {
        boolean minusOnFirstPosition = false;
        if (str.split(" ")[0].equals("минус")) {
                minusOnFirstPosition = true;
                str = str.substring(6);
        }

        // проверка на то, чтобы все слова входили в словари
        for (String element : str.split(" ")) {
            if (!(GlobalVars.numbersAndNames.containsValue(element)
                    || GlobalVars.namesOfClasses.containsValue(element))) {
                throw new InvalidValueException("Input contains an unknown word: " + element);
            }
        }

        if (minusOnFirstPosition) {
            return -1 * prepareNumberForStringToNumber(str);
        }
        return prepareNumberForStringToNumber(str);
    }

    // возвращает название класса в правильном роду и числе
    // classNumber - номер класса чисел (от 1 до 4)
    // classOfNumber - самая тройка (двойка или одно) чисел
    private static String getNameOfClass(Integer classNumber, Integer classOfNumber) {
        if (classOfNumber == 0)
            return null;

        int lastNumberOfClass;
        if (classOfNumber % 100 > 10 && classOfNumber % 100 < 20)
            lastNumberOfClass = classOfNumber % 100;
        else
            lastNumberOfClass = classOfNumber % 10;

        switch (classNumber) {
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
            default -> { return null; }
        }
    }

    // разбивает пришедшие классы (1-3 цифры) по маске для последующего поиска в map
    // пример:
    // 763   214     5
    // 7##   2##   ###
    //  6#    14    ##
    //   3     #     5
    // '0' добавляется к каждому число, чтобы корректно записать в char
    private static char[][] parseNumberByDigits(Integer number) {
        char[][] digits = { { '#', '#', '#' }, { '#', '#' }, { '#' } };

        if (number > 99)
            digits[0][0] = (char) (number / 100 + '0');

        if (number % 100 > 19)
            digits[1][0] = (char) (number / 10 % 10 + '0');
        else if (number % 100 >= 10 && number % 100 <= 19) { // 10-19 собираются отдельно, т.к. цельное название, а не состоящее из двух
            digits[1][0] = (char) (number / 10 % 10 + '0');
            digits[1][1] = (char) (number % 10 + '0');
        }

        if (digits[1][1] == '#')
            if ((number % 10) != 0)
                digits[2][0] = (char) (number % 10 + '0');
            else if (digits[1][0] == '#' && digits[0][0] == '#')
                digits[2][0] = (char) (number % 10 + '0');

        return digits;
    }

    // собирает все маски (в виде двойного массива символов) в list и переворачивает
    private static LinkedList<char[][]> parseNumberByClasses(Long number) {
        LinkedList<char[][]> list = new LinkedList<>();
        do {
            list.add(parseNumberByDigits((int) (number % 1000)));
            number /= 1000;
        } while (number > 0);

        Collections.reverse(list); // переворачиваем лист, чтобы начать записывать в StrBuilder с высшего класса
        if (list.size() > 1)
            for (int i = 0; i < list.size(); i++) {
                if (Arrays.deepEquals(list.get(i), new char[][]{{'#','#','#'},{'#','#'},{'0'}}))
                    list.set(i, new char[][]{{'#','#','#'},{'#','#'},{'#'}});
            }
        return list;
    }

    // собирает строку из масок и названий классов цифр
    private static String prepareStringForNumberToString(Long number) {
        StringBuilder sb = new StringBuilder();
        List<char[][]> listOfClasses = parseNumberByClasses(number); // разбитие числа на классы (по тройкам)
        String[] stringForClass = new String[4]; // для записи слов, из которых состоит класс

        for (int i = 0; i < listOfClasses.size(); i++) {
            stringForClass[0] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[0])); // записывает сотни
            stringForClass[1] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[1])); // записывает десятки

            if (listOfClasses.size() - i == 2 && (listOfClasses.get(i)[2][0] == '1' || listOfClasses.get(i)[2][0] == '2'))
                stringForClass[2] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[2]) + ".0"); // записывает единицы для тысяч (т.к. другой род)
            else
                stringForClass[2] = GlobalVars.numbersAndNames.get(new String(listOfClasses.get(i)[2])); // записывает единицы в остальных случаях

            stringForClass[3] = getNameOfClass(listOfClasses.size() - i, (int) (number / Math.pow(1000, listOfClasses.size() - i - 1) % 1000)); // записывает название класса тройки чисел

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

    // собирает число для вывода в ответ из пришедшей строки
    private static Long prepareNumberForStringToNumber(String str) throws InvalidValueException, IncorrectInputOrderException, NumberOutOfRangeException {
        long number = 0L; // для конечного числа
        long buff = 0L; // буферная переменная
        int classNumber; // номер класса чисел на проверке
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder(); // записывается каждое слово класса, чтобы потом сравнить правильность написания
        List<Boolean> classesFlag = Arrays.asList(false, false, false, false); // для проверки правильности ввода классов чисел
        List<Boolean> digitsFlag = Arrays.asList(false, false, false); // для проверки правильности ввода разрядов класса

        for (String string : str.split(" ")) {
            if (getKey(GlobalVars.numbersAndNames, string) != null) {
                // проверка на то, чтобы не приходил ноль после другого разряда (пятьдесят ноль -> ошибка)
                if (((classesFlag.get(3) || classesFlag.get(2) || classesFlag.get(1)) || (digitsFlag.get(2) || digitsFlag.get(1))) && string.equals("ноль")) {
                    throw new InvalidValueException("Incorrect input! There should not be a zero after any digit!");
                }

                sb.append((int) Double.parseDouble(getKey(GlobalVars.numbersAndNames, string).replace('#', '0')));
                checkDigitsForCorrectInput(digitsFlag, sb.length() - 1);
                digitsFlag.set(sb.length() - 1, true);

                sb2.append(string).append(" ");
                buff += (long) Double.parseDouble(sb.toString()); // DoubleParse, т.к могут прийти 1.0 и 2.0
            } else {
                classNumber = Integer.parseInt(getKey(GlobalVars.namesOfClasses, string)) / 10;
                sb2.append(string);

                // проверка на то, чтобы не приходило название класса без числа (миллион две тысячи -> ошибка)
                if (sb2.toString().split(" ").length < 2 && classNumber > 1) {
                    throw new InvalidValueException("Incorrect input! Each class must have a number!");
                }

                checkClassesForCorrectInput(classesFlag, classNumber);
                classesFlag.set(classNumber - 1, true);

                buff *= (long) Math.pow(1000, classNumber - 1);
                number += buff;
                buff = 0L;
                sb2.append(" ");
                digitsFlag = Arrays.asList(false, false, false);
            }
            sb.setLength(0);
        }
        number += buff;
        sb2.deleteCharAt(sb2.length() - 1);
        checkForCorrectInputOfWords(sb2.toString(), number);
        return number;
    }

    // проверка на то, чтобы классы не повторялись (три тысячи шесть тысяч -> ошибка)
    // и на то, чтобы классы записывались в правильно порядке (пять тысяч один миллион -> ошибка)
    private static void checkClassesForCorrectInput(List<Boolean> classesFlag, int classNumber) throws IncorrectInputOrderException {
        if (!classesFlag.get(classNumber - 1)) {
            switch (classNumber - 1) {
                case 1 -> {
                    if (classesFlag.get(0)) {
                        throw new IncorrectInputOrderException("Incorrect input order! The classes should go in ascending order (billions, millions, thousands, units).");
                    }
                }
                case 2 -> {
                    if (classesFlag.get(0) || classesFlag.get(1)) {
                        throw new IncorrectInputOrderException("Incorrect input order! The classes should go in ascending order (billions, millions, thousands, units).");
                    }
                }
                case 3 -> {
                    if (classesFlag.get(0) || classesFlag.get(1) || classesFlag.get(2)) {
                        throw new IncorrectInputOrderException("Incorrect input order! The classes should go in ascending order (billions, millions, thousands, units).");
                    }
                }
            }
        } else {
            throw new IncorrectInputOrderException("Incorrect input order! The classes should not be repeated.");
        }

    }

    // проверка на то, чтобы разряды числе не повторялись (двадцать двадцать один -> ошибка)
    // и на то, чтобы разряды записывались в правильно порядке (двадцать сто один -> ошибка)
    private static void checkDigitsForCorrectInput(List<Boolean> digitsFlag, int digitNumber) throws IncorrectInputOrderException {
        if (!digitsFlag.get(digitNumber)) {
            switch (digitNumber) {
                case 1 -> {
                    if (digitsFlag.get(0)) {
                        throw new IncorrectInputOrderException("Incorrect input order! The digits should go in ascending order (hundreds, tens, ones).");
                    }
                }
                case 2 -> {
                    if (digitsFlag.get(0) || digitsFlag.get(1)) {
                        throw new IncorrectInputOrderException("Incorrect input order! The digits should go in ascending order (hundreds, tens, ones).");
                    }
                }
            }
        } else {
            throw new IncorrectInputOrderException("Incorrect input order! The digits should not be repeated.");
        }
    }

    // проверка на правильность написания слов (две тысяч -> ошибка)
    private static void checkForCorrectInputOfWords (String string, Long number) throws NumberOutOfRangeException, InvalidValueException {
        if (!string.equals(numberToString(number))) {
            throw new InvalidValueException("Incorrect input. Expected: " + numberToString(number) + ". Actual: " + string + ".");
        }
    }
}
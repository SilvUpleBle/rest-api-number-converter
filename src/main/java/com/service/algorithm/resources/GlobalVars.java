package com.service.algorithm.resources;

import java.util.Map;

public class GlobalVars {

        private GlobalVars() {
        }

        public static final Map<String, String> numbersAndNames = Map.ofEntries(
                        Map.entry("0", "ноль"),
                        Map.entry("1", "один"),
                        Map.entry("1.0", "одна"), // для тысяч
                        Map.entry("2", "два"),
                        Map.entry("2.0", "две"), // для тысяч
                        Map.entry("3", "три"),
                        Map.entry("4", "четыре"),
                        Map.entry("5", "пять"),
                        Map.entry("6", "шесть"),
                        Map.entry("7", "семь"),
                        Map.entry("8", "восемь"),
                        Map.entry("9", "девять"),
                        Map.entry("10", "десять"),
                        Map.entry("11", "одиннадцать"),
                        Map.entry("12", "двенадцать"),
                        Map.entry("13", "тринадцать"),
                        Map.entry("14", "четырнадцать"),
                        Map.entry("15", "пятнадцать"),
                        Map.entry("16", "шестнадцать"),
                        Map.entry("17", "семнадцать"),
                        Map.entry("18", "восемнадцать"),
                        Map.entry("19", "девятнадцать"),
                        Map.entry("2#", "двадцать"),
                        Map.entry("3#", "тридцать"),
                        Map.entry("4#", "сорок"),
                        Map.entry("5#", "пятьдесят"),
                        Map.entry("6#", "шестьдесят"),
                        Map.entry("7#", "семьдесят"),
                        Map.entry("8#", "восемьдесят"),
                        Map.entry("9#", "девяносто"),
                        Map.entry("1##", "сто"),
                        Map.entry("2##", "двести"),
                        Map.entry("3##", "триста"),
                        Map.entry("4##", "четыреста"),
                        Map.entry("5##", "пятьсот"),
                        Map.entry("6##", "шестьсот"),
                        Map.entry("7##", "семьсот"),
                        Map.entry("8##", "восемьсот"),
                        Map.entry("9##", "девятьсот"));

        public static final Map<String, String> namesOfClasses = Map.ofEntries(
                        Map.entry("21", "тысяч"),
                        Map.entry("22", "тысяча"),
                        Map.entry("23", "тысячи"),
                        Map.entry("31", "миллион"),
                        Map.entry("32", "миллиона"),
                        Map.entry("33", "миллионов"),
                        Map.entry("41", "миллиард"),
                        Map.entry("42", "миллиарда"),
                        Map.entry("43", "миллиардов"));

}
package com.service.algorithm.service;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class Serivce {
    public static StringBuilder replaceAll(StringBuilder sb, String find, String replace){
        return new StringBuilder(Pattern.compile(find).matcher(sb).replaceAll(replace));
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
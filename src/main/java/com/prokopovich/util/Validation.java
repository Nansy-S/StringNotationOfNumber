package com.prokopovich.util;

public class Validation {

    public static boolean isNumber(String number) {
        String pattern = "[-]?[\\d]+[.,]?[\\d]*";
        return number.matches(pattern);
    }
}

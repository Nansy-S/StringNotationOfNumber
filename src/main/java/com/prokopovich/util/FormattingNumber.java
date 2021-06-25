package com.prokopovich.util;

public class FormattingNumber {

    public static String concatZeroes(boolean isDecimalNumber, String number) {
        if(isDecimalNumber) {
            while(number.charAt(number.length() - 1) == '0' && number.length() > 1) {
                number = number.substring(0, number.length() - 1);
            }
        } else {
            while (number.charAt(0) == '0' && number.length() > 1) {
                number = number.substring(1);
            }
        }
        return number;
    }

    public static String roundTheNumber(String number) {
        if(Character.getNumericValue(number.charAt(9)) < 5) {
            number = number.substring(0, 9);
        }
        if(Character.getNumericValue(number.charAt(9)) >= 5) {
            int numberTmp = Character.getNumericValue(number.charAt(8)) + 1;
            number = number.substring(0, 8) + numberTmp;
        }
        return number;
    }
}

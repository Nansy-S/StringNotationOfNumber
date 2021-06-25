package com.prokopovich;

import com.prokopovich.parser.StringNotationStaxParser;
import com.prokopovich.util.FormattingNumber;
import com.prokopovich.util.Validation;

import java.util.ArrayList;
import java.util.Collections;

public class ConverterNumber {

    public String convert(String digitNotationNumber) {
        String stringNotationNumber = "";
        String integerPart;
        String decimalPart;
        if (!Validation.isNumber(digitNotationNumber)) {
            return null;
        }
        if(digitNotationNumber.matches("[0.,-]+")) {
            stringNotationNumber += getStringNotationOfPartNumber("string", "0");
        } else {
            if(digitNotationNumber.contains("-")) {
                stringNotationNumber += "минус ";
                digitNotationNumber = digitNotationNumber.substring(1);
            }
            boolean isDecimalNumber = (digitNotationNumber.contains(".") || digitNotationNumber.contains(","));
            if (isDecimalNumber) {
                integerPart = digitNotationNumber.split("[.,]")[0];
                stringNotationNumber += convertIntegerPart(true, integerPart);
                stringNotationNumber += wholeWordForm(integerPart);

                decimalPart = digitNotationNumber.split("[.,]")[1];
                if(decimalPart.length() > 9) {
                    System.out.println("\nДробная часть введенного числа будет округлена до миллиардных.");
                    decimalPart = FormattingNumber.roundTheNumber(decimalPart);
                    System.out.println("Округленное число: " + integerPart + "," + decimalPart);
                }
                stringNotationNumber += convertDecimalPart(decimalPart);
            } else {
                stringNotationNumber += convertIntegerPart(false, digitNotationNumber);
            }
        }
        stringNotationNumber = stringNotationNumber.trim();
        return stringNotationNumber;
    }

    private String convertIntegerPart(boolean isDecimalNumber, String integerPart) {
        String stringNotation = "";
        boolean endDigit = false;

        if (integerPart.equals("0")) {
            stringNotation = getStringNotationOfPartNumber("string", "0");
        } else {
            integerPart = FormattingNumber.concatZeroes(false, integerPart);
            ArrayList<String> partArray = divisionByRank(integerPart);
            int numberZeroes = (partArray.size() - 1) * 3;
            for (int i = 0; i < partArray.size(); i++) {
                String element = partArray.get(i);
                if(!element.matches("[0]+")) {
                    if (element.length() == 3) {
                        stringNotation += convertHundredthPart(partArray.get(i));
                        element = element.substring(1, 3);
                    }
                    if (element.length() == 2 && element.charAt(0) != '0') {
                        stringNotation += convertTenthPart(element);
                    }
                    if (i == (partArray.size() - 1)) {
                        endDigit = true;
                    }
                    stringNotation += convertDigit(element, numberZeroes, isDecimalNumber, endDigit);
                    if (numberZeroes > 0) {
                        stringNotation += convertDegree(element, numberZeroes);
                    }
                }
                numberZeroes -= 3;
            }
        }
        return stringNotation;
    }

    private String convertDecimalPart(String decimalPart) {
        decimalPart = FormattingNumber.concatZeroes(true, decimalPart);
        int decimalPlace = decimalPart.length();
        String stringNotation = convertIntegerPart(true, decimalPart);
        stringNotation += convertDecimalPlace(decimalPart, decimalPlace);
        return stringNotation;
    }

    private String convertHundredthPart(String element) {
        String hundredthPart = element.charAt(0) + "00";
        return getStringNotationOfPartNumber("string", hundredthPart);
    }

    private String convertTenthPart(String element) {
        int tenthPart = Integer.parseInt(element);
        if(tenthPart > 19) {
            tenthPart /= 10;
            return getStringNotationOfPartNumber("string", tenthPart + "0");
        } else {
            if(tenthPart > 9) {
                String tenthPartStr = Integer.toString(tenthPart);
                return getStringNotationOfPartNumber("string", tenthPartStr);
            }
        }
        return "";
    }

    private String convertDigit(String element, int numberZeroes, boolean isDecimalNumber, boolean endDigit) {
        String stringNotation = "";
        String tag = "string";

        int number = Integer.parseInt(element);
        if(number < 10 || number > 20) {
            number %= 10;
            if(numberZeroes == 3 && (number == 1 || number == 2) ||
                    isDecimalNumber && endDigit && (number == 1 || number == 2)) {
                tag = "female";
            }
            if(number != 0) {
                stringNotation = getStringNotationOfPartNumber(tag, Integer.toString(number));
            }
        }
        return stringNotation;
    }

    private String convertDegree(String element, int numberZeroes) {
        String form = "parental";
        int elementInt = Integer.parseInt(element);
        if(elementInt >= 5 && elementInt <= 20) {
            form = "parental";
        } else if((elementInt % 10) == 1) {
            form = "singular";
        } else if((elementInt % 10) >= 2 && (elementInt % 10) <= 4) {
            form = "plural";
        }
        return getStringNotationOfNumberDegree(form, Integer.toString(numberZeroes));
    }

    private String convertDecimalPlace(String element, int decimalPlace) {
        String form = "parental";
        int elementInt = Integer.parseInt(element) % 100;
        if(elementInt >= 5 && elementInt <= 20) {
            form = "parental";
        } else if((elementInt % 10) == 1) {
            form = "singular";
        }
        return getStringNotationOfFractionalNumber(form, Integer.toString(decimalPlace));
    }

    private String wholeWordForm(String endDigit) {
        String wholeWordForm = "целых ";

        if(endDigit.length() > 2) {
            endDigit = endDigit.substring(endDigit.length() - 2);
        }
        int endDigitInt = Integer.parseInt(endDigit);
        if((endDigitInt % 10) == 1 && endDigitInt != 11) {
            wholeWordForm = "целая ";
        }
        return wholeWordForm;
    }

    private ArrayList<String> divisionByRank(String number) {
        ArrayList<String> numberArray = new ArrayList<>();
        while(number.length() > 3) {
            String segment = number.substring(number.length() - 3);
            numberArray.add(segment);
            number = number.substring(0, number.length() - 3);
        }
        numberArray.add(number);
        Collections.reverse(numberArray);
        return numberArray;
    }

    private String getStringNotationOfPartNumber(String tag, String digit) {
        return StringNotationStaxParser.parseNotationOfNumber(tag, digit) + " ";
    }

    private String getStringNotationOfNumberDegree(String form, String numberOfZeroes) {
        return StringNotationStaxParser.parseNotationOfNumberDegree(form, numberOfZeroes) + " ";
    }

    private String getStringNotationOfFractionalNumber(String form, String decimalPlace) {
        return StringNotationStaxParser.parseNotationOfFractionalNumber(form, decimalPlace) + " ";
    }
}

package com.prokopovich;

import com.prokopovich.util.Validation;

import java.util.Scanner;

public class StringNotationOfNumber {

    private static String digitNotationNumber;
    private static String stringNotationNumber;

    public static void main(String[] args) {
        ConverterNumber converterNumber = new ConverterNumber();
        Scanner in = new Scanner(System.in);
        boolean choice = true;

        System.out.println("Привет!");
        while(choice) {
            if(inputNumber()) {
                System.out.println("\nВведенная цифровая запись числа: " + digitNotationNumber);
                stringNotationNumber = converterNumber.convert(digitNotationNumber);
                System.out.println("\nСтроковая запись числа: " + stringNotationNumber);
            }
            System.out.print("\nВвести ещё одно число?\n\tДА - 1 \n\tНЕТ - любой символ \nВаш выбор: ");
            if(!in.next().equals("1")) {
                choice = false;
            }
        }
        System.out.println("До новых встреч!");
    }

    public static boolean inputNumber() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите число: ");
        digitNotationNumber = in.next();
        if (!Validation.isNumber(digitNotationNumber)) {
            System.out.println("\nОшибка. Введенная строка не является числом.");
            return false;
        }
        return true;
    }
}

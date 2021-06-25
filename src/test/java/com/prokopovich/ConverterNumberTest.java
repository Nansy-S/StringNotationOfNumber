package com.prokopovich;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterNumberTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-data.csv", delimiter = ':')
    void convertTest(String input, String expected) {
        ConverterNumber converterNumber = new ConverterNumber();
        String actualValue = converterNumber.convert(input);
        assertEquals(expected, actualValue);
    }
}

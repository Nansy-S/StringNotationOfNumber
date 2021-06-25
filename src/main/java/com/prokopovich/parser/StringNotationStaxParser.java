package com.prokopovich.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StringNotationStaxParser {

    private static String notationOfNumberFile;
    private static String notationOfNumberDegreeFile;
    private static String notationOfFractionalNumberFile;

    static {
        Properties property = new Properties();

        try {
            FileInputStream fis = new FileInputStream("src/main/resources/xml-file.properties");
            property.load(fis);
            notationOfNumberFile = property.getProperty("notationOfNumberFile");
            notationOfNumberDegreeFile = property.getProperty("notationOfNumberDegreeFile");
            notationOfFractionalNumberFile = property.getProperty("notationOfFractionalNumberFile");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String parseNotationOfNumber(String searchedTag, String attrValue) {
        return parseFile(notationOfNumberFile, "number", "value", searchedTag, attrValue);
    }

    public static String parseNotationOfNumberDegree(String searchedTag, String attrValue) {
        return parseFile(notationOfNumberDegreeFile, "degree", "zeros", searchedTag, attrValue);
    }

    public static String parseNotationOfFractionalNumber(String searchedTag, String attrValue) {
        return parseFile(notationOfFractionalNumberFile, "decimal", "place", searchedTag, attrValue);
    }

    private static String parseFile(String fileName, String startTag, String nameAttribute, String searchedTag, String valueZeros) {
        String stringNotation = "";
        boolean isDegreeEquals = false;
        boolean isFound = false;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals(startTag)) {
                        Attribute zerosAttr = startElement.getAttributeByName(new QName(nameAttribute));
                        if (zerosAttr.getValue().equals(valueZeros)) {
                            isDegreeEquals = true;
                        }
                    } else if (startElement.getName().getLocalPart().equals(searchedTag) && isDegreeEquals) {
                        xmlEvent = reader.nextEvent();
                        stringNotation = xmlEvent.asCharacters().getData();
                        isFound = true;
                    }
                }
                if(isFound) {
                    break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            System.out.println(e.getMessage());
        }
        return stringNotation;
    }
}

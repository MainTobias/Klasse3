/*
 * Copyright (c) 2022 by MainTobias
 */

package Serialization;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void serializeAndDeserialize() throws IOException, ClassNotFoundException {
        List<Country> countries = Country.fromCsv(Path.of("resources/Serialization/countries.csv"));
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(countries);
        out.close();
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<Country> deserializedCountries = (List<Country>) in.readObject();
        assertEquals(countries, deserializedCountries);
    }

    @Test
    void serializeAndDeserializeWithCapitalLetters() throws IOException, ClassNotFoundException {
        List<Country> countries = Country.fromCsv(Path.of("resources/Serialization/countries.csv"));
        Country.setCapitalize(true);
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(countries);
        out.close();
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<Country> deserializedCountries = (List<Country>) in.readObject();
        System.out.println(countries);
        assertEquals(countries, deserializedCountries);
    }
}
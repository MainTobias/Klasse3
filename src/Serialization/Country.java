/*
 * Copyright (c) 2022 by MainTobias
 */

package Serialization;


import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Country implements Serializable {
    String countryCode;
    String name;
    String capital;
    String area;
    int population;

    static private boolean capitalize = false;

    public Country(String countryCode, String name, String capital, String area, int population) {
        this.countryCode = countryCode;
        this.name = name;
        this.capital = capital;
        this.area = area;
        this.population = population;
    }

    public static List<Country> fromCsv(Path csv) throws IOException {
        return Files.lines(csv).map(line -> {
            String[] parts = line.split(line.contains(",") ? "," : ";");
            if (parts.length != 5) {
                System.out.println("Invalid line: " + line);
                return null;
            }
            return new Country(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static void setCapitalize(boolean capitalize) {
        Country.capitalize = capitalize;
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(countryCode);
        out.writeObject(capitalize ? name.toUpperCase() : name);
        out.writeObject(capitalize ? capital.toUpperCase() : capital);
        out.writeObject(area);
        out.writeInt(population);
    }

    @Serial
    private void readObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(countryCode);
        out.writeObject(capitalize ? name.toUpperCase() : name);
        out.writeObject(capitalize ? capital.toUpperCase() : capital);
        out.writeObject(area);
        out.writeInt(population);
    }

}

/*
 * Copyright (c) 2022 by MainTobias
 */

package Serialization;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Country implements Serializable{
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
    private void writeObject(ObjectOutputStream out) throws IOException {
        //out.defaultWriteObject();
        out.writeUTF(countryCode);
        out.writeUTF(capitalize ? name.toUpperCase() : name);
        out.writeUTF(capitalize ? capital.toUpperCase() : capital);
        out.writeUTF(area);
        out.writeInt(population);
        //out.flush();
        //out.close();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException
    {
        countryCode = in.readUTF();
        name = in.readUTF();
        capital = in.readUTF();
        area = in.readUTF();
        population = in.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return population == country.population && (countryCode.equals(country.countryCode) || (capitalize && countryCode.equalsIgnoreCase(country.countryCode))) && (name.equals(country.name) || (capitalize && name.equalsIgnoreCase(country.name))) && area.equals(country.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode.toLowerCase(), name.toLowerCase(), capital.toLowerCase(), area.toLowerCase(), population);
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                ", area='" + area + '\'' +
                ", population=" + population +
                '}';
    }
}

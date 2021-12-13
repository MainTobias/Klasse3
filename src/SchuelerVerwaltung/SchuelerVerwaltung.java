package SchuelerVerwaltung;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SchuelerVerwaltung {
    final private Collection<Schueler> schuelers;

    private SchuelerVerwaltung(Collection<Schueler> schuelers) {
        this.schuelers = schuelers;
    }

    public SchuelerVerwaltung(String filename) throws IOException {
        this(readFrom(filename));
    }

    private static Collection<Schueler> readFrom(String filename) throws IOException {
        Set<Schueler> schuelers = new TreeSet<Schueler>();
        Scanner sc = new Scanner(Path.of(filename));
        if (sc.hasNextLine()) sc.nextLine();
        String line;
        String[] strings;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            try {
                schuelers.add(Schueler.makeSchueler(line));
            } catch (Exception e) {
                System.out.println("Malformed line: " + line);
            }
        }
        return schuelers;
    }

    public Set<Schueler> getSchuelerFromKlasse(String klasse) {
        return schuelers.stream().filter(s -> Objects.equals(klasse, s.klasse())).collect(Collectors.toSet());
    }

    public Set<Schueler> containsName(String name, boolean komplett) {
        return schuelers.stream().filter(s -> (komplett ? Objects.equals(name, s.name()) : s.name().contains(name))).collect(Collectors.toSet());
    }

    public Set<Schueler> getAllWith(char geschlecht) {
        return schuelers.stream().filter(s -> s.geschlecht() == geschlecht).collect(Collectors.toSet());
    }

    public Set<Schueler> getGeborenBis(LocalDate datum, boolean vorNach) {
        return schuelers.stream().filter(s -> true).collect(Collectors.toSet());
    }

    public Map<String, Integer> getKlassenAnzahl() {
        Map<String, Integer> klassen = new TreeMap<>();
        schuelers.forEach(s -> klassen.put(s.klasse(), klassen.getOrDefault(s.klasse(), 1)));
        return klassen;
    }

    public Map<String, Map<String, List<String>>> getReligionsZugehoerigkeit() {
        Map<String, Map<String, List<String>>> relis = new TreeMap<>();
        schuelers.forEach(s -> relis.putIfAbsent(s.religion(), schuelers.stream().collect(Collectors.toMap(x -> x.klasse(), x -> schuelers.stream().filter(sch -> Objects.equals(s.religion(), sch.religion()) && Objects.equals(s.klasse(), sch.klasse())).collect(Collectors.toList())))));
        return relis;
    }

    public static void main(String[] args) throws IOException {
        SchuelerVerwaltung s = new SchuelerVerwaltung("resources/Schuelerverwaltung/Schueler_SortName.csv");
        System.out.println(s.schuelers);
    }

}

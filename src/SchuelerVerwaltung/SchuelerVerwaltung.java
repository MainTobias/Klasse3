package SchuelerVerwaltung;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Predicate;
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
        return schuelers.stream().filter(s -> s.geschlecht() == geschlecht).collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<Schueler> getGeborenBis(LocalDate datum, boolean vorNach) {
        return schuelers.stream().filter(s -> {
            boolean b = Period.between(s.geboren(), datum).isNegative();
            if (vorNach) return !b;
            return b;
        }).collect(Collectors.toSet());
    }

    public Map<String, Integer> getKlassenAnzahl() {
        return schuelers.stream().collect(Collectors.toMap(Schueler::klasse, x -> getSchuelerFromKlasse(x.klasse()).size(), (o, n) -> o));
    }

    public Map<String, Map<String, List<String>>> getReligionsZugehoerigkeit() {
        return schuelers.stream().map(Schueler::religion).distinct().collect(Collectors.toMap(x -> x, x -> schuelers.stream().filter(schueler -> schueler.religion().equals(x)).collect(Collectors.toMap(s -> s.klasse(), s -> new ArrayList<>(Collections.singleton(s.name())), (o, n) -> {
            o.addAll(n);
            return o;
        }))));
    }

    public Map<LocalDate, Set<String>> getGeburtstagsListe(final int jahr) {
        return schuelers.stream().collect(Collectors.toMap(x -> x.geboren().withYear(jahr), x -> new TreeSet<>(Collections.singleton(String.format("%s %s %s %d", x.name(), x.vorname(), x.klasse(), jahr-x.geboren().getYear()))), (o, n) -> {
            o.addAll(n);
            return o;
        }, TreeMap::new));
        //return schuelers.stream().map(Schueler::geboren).filter(d -> d.getYear() < jahr).collect(Collectors.toMap(LocalDate::getYear, v -> v, (o, n) -> o)).values().stream().collect(Collectors.toMap(k -> k, k -> schuelers.stream().filter(s -> Objects.equals(k.getYear(), s.geboren().getYear())).map(Schueler::name).collect(Collectors.toSet())));
    }

    public Map<LocalDate, Set<String>> getGeburtstagsListe() {
        return schuelers.stream().map(Schueler::geboren).collect(Collectors.toMap(LocalDate::getYear, v -> v, (o, n) -> o)).values().stream().collect(Collectors.toMap(k -> k, k -> schuelers.stream().filter(s -> Objects.equals(k.getYear(), s.geboren().getYear())).map(Schueler::name).collect(Collectors.toSet())));
    }

    public Set<Schueler> getSchuelerMatching(Predicate<Schueler> p) {
        return schuelers.stream().filter(p).collect(Collectors.toSet());
    }

    public static void main(String[] args) throws IOException {
        SchuelerVerwaltung s = new SchuelerVerwaltung("resources/Schuelerverwaltung/Schueler_SortName.csv");
        System.out.println(s.getGeburtstagsListe());
    }

}

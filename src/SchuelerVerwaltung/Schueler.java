package SchuelerVerwaltung;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Objects;

public record Schueler(String klasse, String name, String vorname, char geschlecht,
                       LocalDate geboren, String religion) implements Comparable<Schueler> {
    final private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static Schueler makeSchueler(String s) {
        String[] strings = s.split(";");
        return new Schueler(strings[0], strings[1], strings[2], strings[3].charAt(0), LocalDate.parse(strings[4], formatter), strings[5]);
    }

    @Override
    public int compareTo(Schueler o) {
        int ret = klasse.compareTo(o.klasse);
        if (ret == 0) {
            ret = name.compareTo(o.name);
        }
        if (ret == 0) {
            ret = vorname.compareTo(o.vorname);
        }
        if (ret == 0) {
            ret = geboren.compareTo(o.geboren);
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schueler schueler = (Schueler) o;
        return Objects.equals(klasse, schueler.klasse) && Objects.equals(name, schueler.name) && Objects.equals(vorname, schueler.vorname) && Objects.equals(geboren, schueler.geboren);
    }

    @Override
    public int hashCode() {
        return Objects.hash(klasse, name, vorname, geboren);
    }

    @Override
    public String toString() {
        return "Schueler{" +
                "klasse='" + klasse + '\'' +
                ", name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", geschlecht=" + geschlecht +
                ", geboren=" + geboren +
                ", religion='" + religion + '\'' +
                '}';
    }

    public int getAge(LocalDate date) {
        Period p = Period.between(geboren, date);
        if (p.isNegative()) throw new IllegalArgumentException("Student not born yet: " + date);
        return p.getYears();
    }
}

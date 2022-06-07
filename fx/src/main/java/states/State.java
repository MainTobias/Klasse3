package states;

import javafx.beans.property.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class State {
    private StringProperty wappen = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty hauptstadt = new SimpleStringProperty();
    private StringProperty landeshauptmann = new SimpleStringProperty();
    private Map<String, Integer> parteien;
    private IntegerProperty einwohner = new SimpleIntegerProperty();
    private DoubleProperty flaeche = new SimpleDoubleProperty();

    public static List<State> readFile(InputStream in) throws IOException {
        List<State> states = new ArrayList<>();
        BufferedReader din = new BufferedReader(new InputStreamReader(in));
        din.readLine();
        String line;
        while ((line = din.readLine()) != null) {
            State s = new State();
            String[] lines = line.split(",");
            s.setWappen(lines[0]);
            s.setName(lines[1]);
            s.setHauptstadt(lines[2]);
            s.setLandeshauptmann(lines[3]);
            Map<String, Integer> parties = new HashMap<>();
            if (!lines[4].isBlank()) parties.put("ÖVP", Integer.valueOf(lines[4]));
            if (!lines[5].isBlank()) parties.put("SPÖ", Integer.valueOf(lines[5]));
            if (!lines[6].isBlank()) parties.put("FPÖ", Integer.valueOf(lines[6]));
            if (!lines[7].isBlank()) parties.put("Grüne", Integer.valueOf(lines[7]));
            if (!lines[8].isBlank()) parties.put("NEOS", Integer.valueOf(lines[8]));
            if (!lines[9].isBlank()) parties.putAll(Arrays.stream(lines[9].split(";")).collect(Collectors.toMap((String x) -> x.split(" ")[0], (String x) -> Integer.valueOf(x.split(" ")[1]))));
            s.setParteien(parties);
            s.setEinwohner(Integer.parseInt(lines[11]));
            s.setFlaeche(Double.parseDouble(lines[12]));
            states.add(s);
        }
        return states;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(readFile(Files.newInputStream(Path.of("C:\\Users\\20190632\\IdeaProjects\\Klasse3\\fx\\src\\main\\resources\\states\\bundesländer.csv"))));
    }



    @Override
    public String toString() {
        return "State{" +
                "wappen='" + wappen + '\'' +
                ", name='" + name + '\'' +
                ", hauptstadt='" + hauptstadt + '\'' +
                ", landeshauptmann='" + landeshauptmann + '\'' +
                ", parteien=" + parteien +
                ", einwohner=" + einwohner +
                ", flaeche=" + flaeche +
                '}';
    }

    public String getWappen() {
        return wappen.get();
    }

    public StringProperty wappenProperty() {
        return wappen;
    }

    public void setWappen(String wappen) {
        this.wappen.set(wappen);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getHauptstadt() {
        return hauptstadt.get();
    }

    public StringProperty hauptstadtProperty() {
        return hauptstadt;
    }

    public void setHauptstadt(String hauptstadt) {
        this.hauptstadt.set(hauptstadt);
    }

    public String getLandeshauptmann() {
        return landeshauptmann.get();
    }

    public StringProperty landeshauptmannProperty() {
        return landeshauptmann;
    }

    public void setLandeshauptmann(String landeshauptmann) {
        this.landeshauptmann.set(landeshauptmann);
    }

    public Map<String, Integer> getParteien() {
        return parteien;
    }

    public void setParteien(Map<String, Integer> parteien) {
        this.parteien = parteien;
    }

    public int getEinwohner() {
        return einwohner.get();
    }

    public IntegerProperty einwohnerProperty() {
        return einwohner;
    }

    public void setEinwohner(int einwohner) {
        this.einwohner.set(einwohner);
    }

    public double getFlaeche() {
        return flaeche.get();
    }

    public DoubleProperty flaecheProperty() {
        return flaeche;
    }

    public void setFlaeche(double flaeche) {
        this.flaeche.set(flaeche);
    }

    public int getSitze() {
        return parteien.values().stream().mapToInt(x -> x).sum();
    }
}

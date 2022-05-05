/*
 * Copyright (c) 2022 by MainTobias
 */

package Serialization;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Schueler implements Serializable {
    String name;
    String vorname;
    transient Integer kennung;
    transient Long sozialversicherungsnummer;
    private final static int KEY = 4;

    public Schueler(String name, String vorname, Integer kennung, Long sozialversicherungsnummer) {
        setName(name);
        setVorname(vorname);
        setKennung(kennung);
        setSozialversicherungsnummer(sozialversicherungsnummer);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name darf nicht leer sein");
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        if (vorname == null || vorname.isBlank()) throw new IllegalArgumentException("Vorname darf nicht leer sein");
        this.vorname = vorname;
    }

    public Integer getKennung() {
        return kennung;
    }

    public void setKennung(Integer kennung) {
        if (kennung == null || kennung < 0) throw new IllegalArgumentException("Kennung darf nicht negativ sein");
        this.kennung = kennung;
    }

    public Long getSozialversicherungsnummer() {
        return sozialversicherungsnummer;
    }

    public void setSozialversicherungsnummer(Long sozialversicherungsnummer) {
        if (sozialversicherungsnummer == null || sozialversicherungsnummer < 0)
            throw new IllegalArgumentException("Sozialversicherungsnummer darf nicht negativ sein");
        if (sozialversicherungsnummer.toString().length() != 10)
            throw new IllegalArgumentException("Sozialversicherungsnummer muss 10 Stellen haben");
        if (sozialversicherungsnummer.toString().charAt(0) == '0')
            throw new IllegalArgumentException("Sozialversicherungsnummer darf nicht mit 0 beginnen");
        if (!isValidSNR(sozialversicherungsnummer))
            throw new IllegalArgumentException("Sozialversicherungsnummer ist ungültig");
        this.sozialversicherungsnummer = sozialversicherungsnummer;
    }
    private static boolean isValidSNR(long n) {
        int x1 = (int) (n / 1_000_000_000);
        int x2 = (int) (n / 100_000_000 % 10);
        int x3 = (int) (n / 10_000_000 % 10);
        int p = (int) (n / 1_000_000 % 10);
        int t1 = (int) (n / 100_000 % 10);
        int t2 = (int) (n / 10_000 % 10);
        int m1 = (int) (n / 1000 % 10);
        int m2 = (int) (n / 100 % 10);
        int j1 = (int) (n / 10 % 10);
        int j2 = (int) (n % 10);
        return (x1 * 3 + x2 * 7 + x3 * 9 + t1 * 5 + t2 * 8 + m1 * 4 + m2 * 2 + j1 + j2 * 6) % 11 == p;
    }

    @Override
    public String toString() {
        return "Serialization.SerializableSchueler{" +
                "name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", kennung=" + kennung +
                ", sozialversicherungsnummer=" + sozialversicherungsnummer +
                '}';
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // reverse ceasar cipher of kennung and sozialversicherungsnummer
        out.writeUTF(CipherUtil.reverseCeaser(new GermanNumber(kennung).toString(), KEY) + " " + CipherUtil.reverseCeaser(new GermanNumber(sozialversicherungsnummer).toString(), KEY));
    }


    @Serial
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        String[] s = in.readUTF().split(" ");
        kennung = Math.toIntExact(new GermanNumber(DecipherUtil.reverseCeaser(s[0], KEY)).number);
        sozialversicherungsnummer = new GermanNumber(DecipherUtil.reverseCeaser(s[1], KEY)).number;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // create array of students
        Schueler[] schueler = new Schueler[5];
        schueler[0] = new Schueler("Mustermann", "Max", 1, 1000000056L);
        schueler[1] = new Schueler("Musterfrau", "Mia", 2, 1000000072L);
        schueler[2] = new Schueler("Trumpler", "Tobias", 3, 1000000013L);
        schueler[3] = new Schueler("Strauß", "Sandra", 4, 1000000099L);
        schueler[4] = new Schueler("Mueller", "Marlene", 5, 1000000048L);
        System.out.println(Arrays.toString(schueler));


        // serialize array of students
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(schueler);
        out.close();

        // deserialize array of students
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        Schueler[] deserializedSchuelers = (Schueler[]) in.readObject();
        System.out.println(Arrays.toString(deserializedSchuelers));
    }
}

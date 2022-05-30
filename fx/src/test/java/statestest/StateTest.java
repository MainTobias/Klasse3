package statestest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import states.State;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class StateTest {
    public static Path TEST_FILE_PATH;

    @BeforeAll
    static void createTestFile() throws IOException {
        TEST_FILE_PATH = Files.createTempFile("bundesländer", ".csv");
    }

    @AfterEach
    void deleteTestFile() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
    }

    @Test
    void readFile_validLinesOnly_returnsStates() throws IOException {
        writeValidTestData();

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual)
                .extracting(
                        State::getName,
                        State::getHauptstadt,
                        State::getLandeshauptmann,
                        State::getSitze,
                        State::getEinwohner,
                        State::getFlaeche)
                .containsExactly(
                        tuple("Kärnten",
                                "Klagenfurt am Wörthersee",
                                "Peter Kaiser",
                                Map.of("FPÖ", 9,
                                        "ÖVP", 6,
                                        "SPÖ", 18,
                                        "TK", 3),
                                560939,
                                9538.01),
                        tuple("Niederösterreich",
                                "St. Pölten",
                                "Johanna Mikl-Leitner",
                                Map.of("FPÖ", 7,
                                        "Grüne", 3,
                                        "NEOS", 3,
                                        "ÖVP", 29,
                                        "SPÖ", 13,
                                        "Wilde", 1),
                                1677542,
                                19186.27),
                        tuple("Wien",
                                "Wien",
                                "Michael Ludwig",
                                Map.of("DAÖ", 3,
                                        "FPÖ", 31,
                                        "Grüne", 10,
                                        "NEOS", 5,
                                        "ÖVP", 7,
                                        "SPÖ", 44),
                                1897491,
                                414.87));
    }

    private void writeValidTestData() throws IOException {
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(StateTest.TEST_FILE_PATH, StandardOpenOption.CREATE))) {
            writer.println("Wappen,State,Hauptstadt,Landeshauptmann/frau," +
                    "ÖVP,SPÖ,FPÖ,Grüne,NEOS,Andere,Wilde,Einwohner,Fläche");
            writer.println("kaernten.png,Kärnten,Klagenfurt am Wörthersee,Peter Kaiser,6,18,9,,,TK 3,,560939,9538.01");
            writer.println("noe.png,Niederösterreich,St. Pölten,Johanna Mikl-Leitner,29,13,7,3,3,,1,1677542,19186.27");
            writer.println("wien.png,Wien,Wien,Michael Ludwig,7,44,31,10,5,DAÖ 3,,1897491,414.87");
        }
    }

    @Test
    void readFile_linesWithIllegalDataCount_returnsValidStates() throws IOException {
        writeInvalidTestData(
                "noe.png,bl,hs,lh,,,,,,,,1",
                "noe.png,bl,hs,lh,,,,,,,,1,1, ");

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual).extracting(State::getName).containsExactly("Kärnten", "Niederösterreich", "Wien");
    }

    private void writeInvalidTestData(String... lines) throws IOException {
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(StateTest.TEST_FILE_PATH, StandardOpenOption.CREATE))) {
            writer.println("Wappen,State,Hauptstadt,Landeshauptmann/frau," +
                    "ÖVP,SPÖ,FPÖ,Grüne,NEOS,Andere,Wilde,Einwohner,Fläche");
            writer.println(lines[0]);
            writer.println("kaernten.png,Kärnten,Klagenfurt am Wörthersee,Peter Kaiser,6,18,9,,,TK 3,,560939,9538.01");
            writer.println(lines[1]);
            writer.println("noe.png,Niederösterreich,St. Pölten,Johanna Mikl-Leitner,29,13,7,3,3,,1,1677542,19186.27");
            writer.println("wien.png,Wien,Wien,Michael Ludwig,7,44,31,10,5,DAÖ 3,,1897491,414.87");
            for (int i = 2; i < lines.length; i++)
                writer.println(lines[i]);
        }
    }

    @Test
    void readFile_linesWithIllegalWappenResource_returnsValidStates() throws IOException {
        writeInvalidTestData("wappen,bl,hs,lh,,,,,,,,1,1");

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual).extracting(State::getName).containsExactly("Kärnten", "Niederösterreich", "Wien");
    }

    private void writeInvalidTestData(String line) throws IOException {
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(StateTest.TEST_FILE_PATH, StandardOpenOption.CREATE))) {
            writer.println("Wappen,State,Hauptstadt,Landeshauptmann/frau," +
                    "ÖVP,SPÖ,FPÖ,Grüne,NEOS,Andere,Wilde,Einwohner,Fläche");
            writer.println(line);
            writer.println("kaernten.png,Kärnten,Klagenfurt am Wörthersee,Peter Kaiser,6,18,9,,,TK 3,,560939,9538.01");
            writer.println("noe.png,Niederösterreich,St. Pölten,Johanna Mikl-Leitner,29,13,7,3,3,,1,1677542,19186.27");
            writer.println("wien.png,Wien,Wien,Michael Ludwig,7,44,31,10,5,DAÖ 3,,1897491,414.87");
        }
    }

    @Test
    void readFile_linesWithNoName_returnsValidStates() throws IOException {
        writeInvalidTestData("noe.png,,hs,lh,,,,,,,,1,1");

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual).extracting(State::getName).containsExactly("Kärnten", "Niederösterreich", "Wien");
    }

    @Test
    void readFile_linesWithInvalidAndereData_returnsValidStates() throws IOException {
        writeInvalidTestData("noe.png,bl,hs,lh,,,,,,1,,1,1",
                "noe.png,bl,hs,lh,,,,,,Andere,,1,1",
                "noe.png,bl,hs,lh,,,,,,Andere NaN,,1,1",
                "noe.png,bl,hs,lh,,,,,,Andere -1,,1,1");

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual).extracting(State::getName).containsExactly("Kärnten", "Niederösterreich", "Wien");
    }

    @Test
    void readFile_linesWithInvalidEinwohner_returnsValidStates() throws IOException {
        writeInvalidTestData("noe.png,bl,hs,lh,,,,,,,,ew,1",
                "noe.png,bl,hs,lh,,,,,,,,,-1,1");

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual).extracting(State::getName).containsExactly("Kärnten", "Niederösterreich", "Wien");
    }

    @Test
    void readFile_linesWithInvalidFlaeche_returnsValidStates() throws IOException {
        writeInvalidTestData("noe.png,bl,hs,lh,,,,,,,,1,fl",
                "noe.png,bl,hs,lh,,,,,,,,,1,-1");

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual).extracting(State::getName).containsExactly("Kärnten", "Niederösterreich", "Wien");
    }

    @Test
    void readFile_linesWithInvalidSitze_returnsValidStates() throws IOException {
        writeInvalidTestData("noe.png,bl,hs,lh,-1,,,,,,,1,1",
                "noe.png,bl,hs,lh,ÖVP,,,,,,,1,1",
                "noe.png,bl,hs,lh,,-1,,,,,,1,1",
                "noe.png,bl,hs,lh,,SPÖ,,,,,,1,1",
                "noe.png,bl,hs,lh,,,-1,,,,,1,1",
                "noe.png,bl,hs,lh,,,FPÖ,,,,,1,1",
                "noe.png,bl,hs,lh,,,,-1,,,,1,1",
                "noe.png,bl,hs,lh,,,,Grüne,,,,1,1",
                "noe.png,bl,hs,lh,,,,,-1,,,1,1",
                "noe.png,bl,hs,lh,,,,,NEOS,,,1,1",
                "noe.png,bl,hs,lh,,,,,,,-1,1,1",
                "noe.png,bl,hs,lh,,,,,,,Wilde,1,1");

        List<State> actual = State.readFile(Files.newInputStream(TEST_FILE_PATH));

        assertThat(actual).extracting(State::getName).containsExactly("Kärnten", "Niederösterreich", "Wien");
    }
}
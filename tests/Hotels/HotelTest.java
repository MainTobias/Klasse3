/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Hotels;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Die Tests greifen direkt auf die Datei zu, um nicht den Produktionscode zu spoilen.
 */
public class HotelTest {

    private static Map<String, Short> getColumnsMap() {
        Map<String, Short> columnLengths = new LinkedHashMap<>();
        columnLengths.put("name", (short) 64);
        columnLengths.put("location", (short) 64);
        columnLengths.put("size", (short) 4);
        columnLengths.put("smoking", (short) 1);
        columnLengths.put("rate", (short) 8);
        columnLengths.put("date", (short) 10);
        columnLengths.put("owner", (short) 8);
        return columnLengths;
    }

    @Test
    public void readsColumns() throws IOException {
        Map<String, Short> expected = getColumnsMap();

        Map<String, Short> result = Hotels.readColumns("hotels.db");

        assertIterableEquals(expected.entrySet(), result.entrySet());
    }

    @Test
    public void getsStartingOffset() throws IOException {
        int offset = Hotels.getStartingOffset("hotels.db");

        assertEquals(74, offset);
    }

    @Test
    public void createsHotel() throws IOException {
        byte[] data = {0, 0, 80, 97, 108, 97, 105, 115, 32, 67, 111, 98, 117, 114, 103, 87, 105, 101, 110, 51, 50, 32, 32, 89, 36, 53, 48, 48, 50, 48, 50, 50, 47, 48, 49, 47, 48, 49, 32, 32, 32, 32, 32, 32, 32, 32};
        LinkedHashMap<String, Short> columns = new LinkedHashMap<>();
        columns.put("name", (short) 13);
        columns.put("location", (short) 4);
        columns.put("size", (short) 4);
        columns.put("smoking", (short) 1);
        columns.put("rate", (short) 4);
        columns.put("date", (short) 10);
        columns.put("owner", (short) 8);

        Hotel hotel = new Hotel(data, columns);
        assertEquals("Palais Coburg", hotel.name);
        assertEquals("Wien", hotel.location);
    }

    @Test
    public void cannotReadFromInvalidFile() {
        String filename = "invalid.db";

        String errorMsg = assertThrows(IllegalArgumentException.class, () -> Hotels.readHotels(filename)).getMessage();
        assertTrue(errorMsg.contains(filename));
    }

    @Test
    public void readsAllUndeletedHotelsFromGivenFile() throws IOException {
        Hotel contained = new Hotel("Mausefalle",
                "Krems",
                36,
                true,
                10_000,
                LocalDate.of(2019, 11, 12),
                "MAUS");

        Set<Hotel> result = Hotels.readHotels("hotels.db");

        assertEquals(31, result.size());
        assertTrue(result.contains(contained));
    }

    @Test
    public void readsAllHotelsFromGivenFile() throws IOException {
        Hotel deleted = new Hotel("Hotel Nr. Eins",
                "Wien",
                4,
                false,
                40_000,
                LocalDate.of(2018, 12, 10),
                "Michael");

        Set<Hotel> result = Hotels.readHotels("hotels.db");

        assertFalse(result.contains(deleted));
    }

    @Test
    public void readsHotelsInCorrectOrder() throws IOException {
        Hotel first = new Hotel("Bed & Breakfast & Business",
                "Atlantis",
                4,
                false,
                19_000,
                LocalDate.of(2003, 10, 5),
                "");
        Hotel last = new Hotel("Dew Drop Inn",
                "Xanadu",
                4,
                true,
                20_000,
                LocalDate.of(2003, 1, 19),
                "");

        SortedSet<Hotel> result = (SortedSet<Hotel>) Hotels.readHotels("hotels.db");

        assertEquals(first, result.first());
        assertEquals(last, result.last());
    }
}
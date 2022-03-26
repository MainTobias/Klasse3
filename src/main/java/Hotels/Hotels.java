/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Hotels;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Hotels {
    public static int getStartingOffset(String filename) throws IOException {
        assureHotelFileInfo(Path.of(filename));
        return files.get(Path.of(filename)).offset;
    }

    private record HotelFileInfo(int id, int offset, short columnCount) {
        public static final int headerLength = 4 + 4 + 2;
    }

    private static final Map<Path, HotelFileInfo> files = new HashMap<>();

    private static void assureHotelFileInfo(Path path) throws IOException {
        if (!Files.exists(path)) throw new FileNotFoundException("File " + path + " does not exist!");
        if (!files.containsKey(path)) {
            try (DataInputStream din = new DataInputStream(new FileInputStream(path.toString()))) {
                files.put(path, new HotelFileInfo(din.readInt(), din.readInt(), din.readShort()));
            }
        }
    }

    public static LinkedHashMap<String, Short> readColumns(@NotNull String filename) throws IOException {
        assureHotelFileInfo(Path.of(filename));
        LinkedHashMap<String, Short> columns = new LinkedHashMap<>(files.get(Path.of(filename)).columnCount);
        try (DataInputStream din = new DataInputStream(new FileInputStream(filename))) {
            din.skipBytes(HotelFileInfo.headerLength);
            for (int i = 0; i < files.get(Path.of(filename)).columnCount; i++) {
                short nameLength = din.readShort();
                byte[] b = new byte[nameLength];
                int read = din.read(b);
                if (read == -1 || read != nameLength) {
                    throw new EOFException("Stream ended to soon to read full name!");
                }
                String name = new String(b, StandardCharsets.UTF_8);
                columns.put(name, din.readShort());
            }
        }
        return columns;
    }

    public static Set<Hotel> readHotels(@NotNull String filename) throws IOException {
        assureHotelFileInfo(Path.of(filename));
        LinkedHashMap<String, Short> columns = readColumns(filename);
        System.out.println(columns);
        Set<Hotel> hotels = new TreeSet<>();
        System.out.println(columns);
        try (DataInputStream din = new DataInputStream(new FileInputStream(filename))) {
            din.skipBytes(files.get(Path.of(filename)).offset);
            while (din.available() > 0) {
                Hotel x = new Hotel(din, columns);
                if (x.isDeleted()) continue;
                hotels.add(x);
                System.out.println("Added " + x);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException(filename + " is malformed!");
        }
        return hotels;
    }

}
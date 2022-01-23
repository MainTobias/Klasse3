/*
 * Copyright (c) 2022. Tobias Hammerer
 */

package BinaryFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Numbers {
    public static List<Number> getContents(String filename) throws IOException {
        try (DataInputStream din = new DataInputStream(new FileInputStream(filename))) {
            ArrayList<Number> nums = new ArrayList<>();
            int b;
            while ((b = din.read()) != -1) {
                try {
                    try {
                        if (b == 0) {
                            int x = din.readInt();
                            nums.add(x);
                        } else if (b == 1) {
                            double x = din.readDouble();
                            nums.add(x);
                        } else {
                            throw new IllegalArgumentException("File not valid");
                        }
                    } catch (EOFException e) {
                        throw new IllegalArgumentException("File not valid");
                    }
                } catch (EOFException e) {
                    return nums;
                }
            }
            return nums;
        }
    }

    public static Map<String, Set<Number>> groupByType(List<? extends Number> numbers) {
        Map<String, Set<Number>> grouped = new TreeMap<>();
        numbers.forEach(x -> {
            String name = x.getClass().getSimpleName();
            grouped.put(name, add(grouped.getOrDefault(name, new TreeSet<>()), x.getClass().equals(Integer.class) ? x.intValue() : x));
        });
        return grouped;
    }

    private static <E, C extends Collection<E>> C add(C c, E e) {
        c.add(e);
        return c;
    }

    public static void createFile(String filename, List<? extends Number> values) throws IOException {
        Path file = Path.of(filename).toAbsolutePath();
        Files.createFile(file);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.toString()))) {
            for (Number n : values) {
                if (n.getClass().equals(Integer.class)) {
                    dos.write(0);
                    dos.writeInt(n.intValue());
                } else {
                    dos.write(1);
                    dos.writeDouble(n.doubleValue());
                }
            }
        }
    }
}

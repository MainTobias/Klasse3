/*
 * Copyright (c) 2022 by MainTobias
 */

package DeleteLines;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class DeleteLines {
    public static void main(String[] args) throws IOException {
        String inputFile = ensureExists(args[0]);
        String outputFile = args[1];
        if (Files.notExists(Path.of(outputFile))) Files.createFile(Path.of(outputFile));
        Set<Integer> linesToBeDeleted = new TreeSet<>();
        for (int i = 2; i < args.length; i++) {
            if (args[i].contains("-")) {
                String[] range = args[i].split("-");
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                for (int j = start; j <= end; j++) {
                    linesToBeDeleted.add(j);
                }
            } else {
                linesToBeDeleted.add(Integer.valueOf(args[i]));
            }
        }
        System.out.println("Deleting lines " + linesToBeDeleted);
        writeNotDeleted(inputFile, outputFile, linesToBeDeleted);
    }

    private static String ensureExists(String file) {
        if (!Files.exists(Path.of(file))) {
            throw new IllegalArgumentException("File " + file + " does not exist");
        }
        return file;
    }

    private static void writeNotDeleted(String inputFile, String outputFile, Set<Integer> linesToBeDeleted) {
        int i = 0;
        AsciiInputStream inputStream;
        FileOutputStream outputStream;
        try {
            inputStream = new AsciiInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);
        } catch (Exception ignored) {
            return;
        }

        while (true) {
            try {
                String s = inputStream.readLine();
                System.out.println("Line " + i + ": " + s);
                if (!linesToBeDeleted.contains(i)) {
                    outputStream.write((s + "\n").getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException ioe) {
                break;
            }
            i++;
        }
    }

}

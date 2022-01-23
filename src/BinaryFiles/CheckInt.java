package BinaryFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CheckInt {

    public static void createFile(String filename, double... values) throws IOException {
        Path file = Path.of(filename).toAbsolutePath();
        if (Files.deleteIfExists(file))
        Files.createFile(file);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.toString()))) {
            dos.writeInt(values.length);
            for (double d : values) {
                dos.writeDouble(d);
            }
        }
    }

    public static boolean isValidFile(String filename) throws IOException {
        try(RandomAccessFile rf = new RandomAccessFile(filename, "r")) {
            rf.seek(0);
            int len = rf.readInt();
            return len == (rf.length() - Integer.BYTES) / (double)Double.BYTES;
        }
    }

    public static String getFileInfo(String filename) throws IOException {
        if (!isValidFile(filename)) return "invalid";
        try(DataInputStream din = new DataInputStream(new FileInputStream(filename))) {
            StringBuilder sb = new StringBuilder("Saved values: ");
            int len = din.readInt();
            sb.append(len).append("\n");
            while (len > 0) {
                sb.append(String.format("%.2f", din.readDouble())).append(len > 1 ? " " : "");
                len--;
            }
            return sb.toString();
        }
    }

    public static void append(String filename, double toAppend) throws IOException {
        if (!isValidFile(filename)) throw new IllegalArgumentException(filename + " is malformed");
        try(RandomAccessFile rf = new RandomAccessFile(filename, "rw")) {
            rf.seek(0);
            int x = rf.readInt();
            rf.seek(0);
            rf.writeInt(x + 1);
            rf.seek(rf.length());
            rf.writeDouble(toAppend);
        }
    }
}

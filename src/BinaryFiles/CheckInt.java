package BinaryFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CheckInt {

    public static void createFile(String filename, double... values) throws IOException {
        Path file = Path.of(filename).toAbsolutePath();
        Files.createFile(file);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.toString()));
        dos.writeInt(values.length);
        for (double d : values) {
            dos.writeDouble(d);
        }
    }

    public static boolean isValidFile(String filename) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(filename, "r");
        rf.seek(0);
        int len = rf.readInt();
        return len == (rf.length() - Integer.BYTES) / Double.BYTES;
    }

    public static String getFileInfo(String filename) throws IOException {
        if (!isValidFile(filename)) throw new IllegalStateException(filename + " is malformed");
        DataInputStream din = new DataInputStream(new FileInputStream(filename));
        StringBuilder sb = new StringBuilder("Saved values: ");
        int len = din.readInt();
        sb.append(len).append("\n");
        while (len > 0) {
            sb.append(din.readDouble()).append(len > 1 ? " " : "");
            len--;
        }
        return sb.toString();
    }

    public static void append(String filename, double toAppend) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(filename, "rw");
        rf.seek(0);
        rf.writeInt(rf.readInt()+1);
        rf.seek(rf.length());
        rf.writeDouble(toAppend);
    }
}

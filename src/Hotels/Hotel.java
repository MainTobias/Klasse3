/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Hotels;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


enum DeletionFlag {
    NOT_DELETED(0x0000),
    DELETED(0x8000);

    private final int mark;

    DeletionFlag(int mark) {
        this.mark = mark;
    }

    public static DeletionFlag from(int x) {
        for (DeletionFlag f : values()) {
            if (f.mark == x) {
                return f;
            }
        }
        throw new IllegalArgumentException("No such DeletionFlag: " + x);
    }


    @Override
    public String toString() {
        return super.toString() + "(0x" + Integer.toHexString(mark) + ")";
    }
}


public record Hotel(DeletionFlag deletionFlag, String name) {
    public static void main(String[] args) {
        System.out.println(DeletionFlag.from(0x7000));
    }
    public static <I extends InputStream> Column read(final I in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        short nameLength = din.readShort();
        byte[] b = new byte[nameLength];
        int read = din.read(b);
        if (read == -1 || read != nameLength) {
            throw new EOFException("Stream ended to soon to read full name!");
        }
        String name = new String(b, StandardCharsets.UTF_8);
        return new Column(name, din.readShort());
    }
}

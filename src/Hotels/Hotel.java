/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Hotels;


import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


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


public record Hotel(DeletionFlag deletionFlag, Map<String, String> values) {
    public static void main(String[] args) {
        System.out.println(DeletionFlag.from(0x8000));
    }

    public static <I extends InputStream, C extends LinkedHashMap<String, Short>> Hotel read(@NotNull final I in, @NotNull final C columns) throws IOException {
        DataInputStream din = new DataInputStream(in);
        DeletionFlag deleted = DeletionFlag.from(din.readShort());
        Map<String, String> values = new LinkedHashMap<>();
        for (String key : columns.keySet()) {
            byte[] bytes = new byte[columns.get(key)];
            din.readFully(bytes);
            values.put(key, new String(bytes, StandardCharsets.UTF_8));
        }
        return new Hotel(deleted, values);
    }
}

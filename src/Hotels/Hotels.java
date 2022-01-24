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
import java.util.Map;

public class Hotels {
    private final String filename;
    private final int id;
    private final int offset;
    private final short columnCount;

    public Hotels(@NotNull String filename) throws IOException {
        this.filename = filename;
        try (DataInputStream din = new DataInputStream(new FileInputStream(filename))) {
            id = din.readInt();
            offset = din.readInt();
            columnCount = din.readShort();
            if (id < 0 || offset < 0 || columnCount < 0) {
                throw new IllegalArgumentException("Header invalid. Cannot be negative.");
            }
        }
    }

    public Map<String, Short> readColumns() throws IOException {
        try (DataInputStream din = new DataInputStream(new FileInputStream(filename))) {
            din.skipBytes(10);
            for (int i = 0; i < columnCount; i++) {
                Column c = Column.read(din);
            }
        }
    }
}

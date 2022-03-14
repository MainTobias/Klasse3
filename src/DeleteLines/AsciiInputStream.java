/*
 * Copyright (c) 2022 by MainTobias
 */

package DeleteLines;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class AsciiInputStream extends FileInputStream {
    public AsciiInputStream(@NotNull String name) throws FileNotFoundException {
        super(name);
    }

    public String readLine() throws IOException {
        StringBuilder builder = new StringBuilder();
        int c;
        while (true) {
            c = read();
            if (c == -1) {
                throw new EOFException();
            }
            if ((char)c == '\r') {
                continue;
            }
            if ((char)c == '\n') {
                break;
            }
            builder.append((char) c);
        }
        return builder.toString();
    }
}

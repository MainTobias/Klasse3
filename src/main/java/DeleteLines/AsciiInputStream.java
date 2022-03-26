/*
 * Copyright (c) 2022 by MainTobias
 */

package DeleteLines;

import org.jetbrains.annotations.NotNull;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

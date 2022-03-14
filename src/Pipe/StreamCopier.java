/*
 * Copyright (c) 2022 by MainTobias
 */

package Pipe;

import java.io.*;
import java.nio.file.Files;


public class StreamCopier {
    public static void main(String[] args) throws IOException {
        PipedOutputStream pout = new PipedOutputStream();
        PipedInputStream pin = new PipedInputStream(pout);
        while (copyByte(System.in, pout) != -1) ;
        File temp = Files.createTempFile("test", null).toFile();
        FileOutputStream fout = new FileOutputStream(temp);
        copyBuffered(pin, fout, 4);
        System.out.println(temp.getAbsolutePath());
    }

    private static int copyByte(InputStream a, OutputStream b) throws IOException {
        int r = a.read();
        if (r == -1) {
            return -1;
        }
        b.write(r);
        return r;
    }

    private static int copyBuffered(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] b = new byte[bufferSize];
        int read = 0;
        int r;
        while (in.available() > 0 && (r = in.read(b)) != -1) {
            read += r;
            out.write(b);
        }
        return read;
    }


}

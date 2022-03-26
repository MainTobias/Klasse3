package DataStreams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

enum Type {
    i,
    d,
    t,
    f
}

public class DataStream {
    public static void main(String[] args) throws IOException {
        var x = new ByteArrayOutputStream();
        txtToBin(Files.newInputStream(Path.of("resources/DataStreams/test.txt")), x);
        Map<String, List<Object>> m = binToTxt(new ByteArrayInputStream(x.toByteArray()), Files.newOutputStream(Path.of("resources/DataStreams/test-res.txt")));
        System.out.println(m);
    }

    public static void txtToBin(InputStream txt, OutputStream bin) throws IOException {
        DataOutputStream out = new DataOutputStream(bin);
        StringBuilder s = new StringBuilder();
        int c;
        while (true) {
            c = txt.read();

            if ((char) c == '\r') {
                continue;
            }
            if (c == -1 || (char) c == '\n') {
                try {
                    int i = Integer.parseInt(s.toString());
                    out.writeChar('i');
                    out.writeInt(i);
                    s.setLength(0);
                    continue;
                } catch (NumberFormatException ignored) {
                }
                try {
                    double d = Double.parseDouble(s.toString());
                    out.writeChar('d');
                    out.writeDouble(d);
                    s.setLength(0);
                    continue;
                } catch (NumberFormatException ignored) {
                }
                if (s.toString().equals("true")) {
                    out.writeChar('t');
                } else if (s.toString().equals("false")) {
                    out.writeChar('f');
                }
                s.setLength(0);
                if (c == -1) {
                    return;
                }
            } else {
                s.append((char) c);
            }
        }
    }

    public static Map<String, List<Object>> binToTxt(InputStream bin, OutputStream txt) throws IOException {
        Map<String, List<Object>> ret = new HashMap<>();
        List<Type> readOrder = new ArrayList<>();
        DataInputStream in = new DataInputStream(bin);
        PrintWriter out = new PrintWriter(txt);
        int c;
        while (true) {
            c = in.read();
            if (c == -1) {
                break;
            }
            if (c == 'i') {
                final int x = in.readInt();
                ret.compute("Integer", (String a, List<Object> b) -> {
                    if (b == null) {
                        return new ArrayList<>(Collections.singleton(x));
                    } else {
                        b.add(x);
                        return b;
                    }
                });
                readOrder.add(Type.i);
            } else if (c == 'd') {
                final double x = in.readDouble();
                ret.compute("Double", (String a, List<Object> b) -> {
                    if (b == null) {
                        return new ArrayList<>(Collections.singleton(x));
                    } else {
                        b.add(x);
                        return b;
                    }
                });
                readOrder.add(Type.d);
            } else if (c == 't') {
                ret.compute("Boolean", (String a, List<Object> b) -> {
                    if (b == null) {
                        return new ArrayList<>(Collections.singleton(true));
                    } else {
                        b.add(true);
                        return b;
                    }
                });
                readOrder.add(Type.t);
            } else if (c == 'f') {
                ret.compute("Boolean", (String a, List<Object> b) -> {
                    if (b == null) {
                        return new ArrayList<>(Collections.singleton(false));
                    } else {
                        b.add(false);
                        return b;
                    }
                });
                readOrder.add(Type.f);
            }
        }
        int iCount = 0;
        int dCount = 0;
        for (Type t : readOrder) {
            switch (t) {
                case i -> out.println(ret.get("Integer").get(iCount++).toString());
                case d -> out.println(ret.get("Double").get(dCount++).toString());
                case t -> out.println("true");
                case f -> out.println("false");
            }
        }
        out.flush();
        return ret;
    }
}

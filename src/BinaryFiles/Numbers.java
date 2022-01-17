package BinaryFiles;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Numbers {
    public static List<Number> getContents(String filename) throws IOException {
        DataInputStream din = new DataInputStream(new FileInputStream(filename));
        ArrayList<Number> nums = new ArrayList<>();
        while (true) {
            try {
                int b = din.read();
                try {

                    if (b == 0) {
                        int x = din.readInt();
                        nums.add(x);
                    } else if (b == 1) {
                        double x = din.readDouble();
                        nums.add(x);
                    } else {
                        throw new IllegalArgumentException("File not valid");
                    }
                } catch (EOFException e) {
                    throw new IllegalArgumentException("File not valid");
                }
            } catch (EOFException e) {
                return nums;
            }
        }
    }


}

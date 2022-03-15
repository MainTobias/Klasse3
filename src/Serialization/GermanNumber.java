/*
 * Copyright (c) 2022 by MainTobias
 */

package Serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GermanNumber {
    private static final String[] germanWords = {"null", "eins", "zwei", "drei", "vier", "fuenf", "sechs", "sieben", "acht", "neun"};
    public final long number;

    public GermanNumber(long number) {
        this.number = number;
    }

    public GermanNumber(String number) {
        List<Integer> digits = new ArrayList<>();
        while (number.length() > 0) {
            int n = -1;
            for (int i = 0; i < germanWords.length; i++) {
                if (number.startsWith(germanWords[i])) {
                    n = i;
                    break;
                }
            }
            if (n == -1) throw new IllegalArgumentException("Invalid input: " + number);
            digits.add(n);
            number = number.substring(germanWords[n].length());
        }
        this.number = digits.stream().mapToLong(i -> i).reduce(0, (a, b) -> a * 10 + b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : Arrays.stream(String.valueOf(number).split("")).map(Integer::parseInt).toList()) {
            sb.append(germanWords[i]);
        }
        return sb.toString();
    }
}

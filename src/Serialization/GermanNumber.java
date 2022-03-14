/*
 * Copyright (c) 2022 by MainTobias
 */

package Serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GermanNumber {
    private long number;

    public GermanNumber(long number) {
        this.number = number;
    }

    public GermanNumber(String number) {
        List<Integer> digits = new ArrayList<>();
        while (number.length() > 0) {
            digits.add(switch (number){
                case "null" -> 0;
                case "eins" -> 1;
                case "zwei" -> 2;
                case "drei" -> 3;
                case "vier" -> 4;
                case "fünf" -> 5;
                case "sechs" -> 6;
                case "sieben" -> 7;
                case "acht" -> 8;
                case "neun" -> 9;
                default -> throw new IllegalStateException("Unexpected value: " + number);
            });
            
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : Arrays.stream(String.valueOf(number).split("")).map(Integer::parseInt).toList()) {
            sb.append(switch (i) {
                case 0 -> "null";
                case 1 -> "eins";
                case 2 -> "zwei";
                case 3 -> "drei";
                case 4 -> "vier";
                case 5 -> "fünf";
                case 6 -> "sechs";
                case 7 -> "sieben";
                case 8 -> "acht";
                case 9 -> "neun";
                default -> throw new IllegalStateException("Cannot be thrown");
            });
        }
        return sb.toString();
    }
}

/*
 * Copyright (c) 2022 by MainTobias
 */

package Serialization;

public class CipherUtil {
    public static void main(String[] args) {
        System.out.println(reverseCeaser("abcdefghijklmnopqrstuvwxyz", 4));
    }

    public static String reverseCeaser(String text, int key) {
        StringBuilder sb = new StringBuilder();
        text = text.toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) ((c - 'a' + key) % 26 + 'A');
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
            sb.append(c);
        }
        return sb.reverse().toString();
    }
}

class DecipherUtil {
    public static void main(String[] args) {
        System.out.println(reverseCeaser("abcdefghijklmnopqrstuvwxyz", 4));
    }

    public static String reverseCeaser(String text, int key) {
        StringBuilder sb = new StringBuilder();
        text = text.toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) ((c - 'a' - key) % 26 + 'A');
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
            sb.append(c);
        }
        return sb.reverse().toString();
    }
}

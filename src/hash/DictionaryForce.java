/*
 * Copyright (c) 2022 by MainTobias
 */

package hash;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HexFormat;

public class DictionaryForce {
    private static final String testHash = "9c8844145a7aeea05a0a8582e3f38b02e759061a0536d2e103607ac5f28b5d88";
    private static final String testHashAlgo = "SHA-256";
    private static final String[] words;

    static {
        try {
            System.out.println("Loading dictionary...");
            words = new BufferedReader(new InputStreamReader(new BufferedInputStream(new URL("https://raw.githubusercontent.com/dwyl/english-words/master/words.txt").openStream()))).lines().toArray(String[]::new);
            System.out.println("Dictionary loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not load dictionary");
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Instant start = Instant.now();
        System.out.println(wordAttack(testHash, testHashAlgo));
        Instant end = Instant.now();
        Duration d = Duration.between(start, end);
        System.out.format("Berechnungsdauer: %d Millsekunden\n", d.toMillis());
    }

    public static String wordAttack(String hash, String hashAlgo) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(hashAlgo);
        final byte[] hashBytes = HexFormat.of().parseHex(hash);
        final String[] symbols = "!$%&/()=?".split("");
        for (String word : words) {
            for (String symbol : symbols) {
                for (String symbol2 : symbols) {
                    String s = symbol + word + symbol2;
                    if (Arrays.equals(md.digest(s.getBytes()), hashBytes)) {
                        return s;
                    }
                }
            }

        }
        throw new IllegalArgumentException("No password found");
    }
}

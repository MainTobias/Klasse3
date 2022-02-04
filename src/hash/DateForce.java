/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HexFormat;

public class DateForce {
    private static final String testHash = "5DD0952C60FAE4725FDBC06A72A8A446F2DFB32BA2612C76AFEA1A134ADED108";
    private static final String testHashAlgo = "SHA-256";


    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(dateAttack(testHash, testHashAlgo));
    }

    public static String dateAttack(String hash, String hashAlgo) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(hashAlgo);
        final byte[] hashBytes = HexFormat.of().parseHex(hash);
        for (int y = 1900; y <= 2022; y++) {
            for (int m = 1; m <= 12; m++) {
                for (int d = 1; d <= 31; d++) {
                    String s1 = String.format("%d%d%04d", d, m, y);
                    String s2 = String.format("%02d%02d%04d", d, m, y);
                    if (Arrays.equals(md.digest(s1.getBytes()), hashBytes)) {
                        return s1;
                    }
                    if (Arrays.equals(md.digest(s2.getBytes()), hashBytes)) {
                        return s2;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Could not crack hash");
    }
}

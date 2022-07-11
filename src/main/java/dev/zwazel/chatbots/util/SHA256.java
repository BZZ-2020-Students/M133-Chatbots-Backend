package dev.zwazel.chatbots.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class provides a SHA256 hash function.
 *
 * @author Zwazel
 * @since 1.4
 */
public class SHA256 {
    /**
     * Function to get the bytes of a String
     *
     * @param string the String which will be used.
     * @return the bytes of the String
     * @author Zwazel
     * @since 1.4
     */
    public static byte[] getSHA(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        return md.digest(string.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Function to the Hex String of a number
     *
     * @param hash the Hash being used
     * @return the Hex String
     * @author Zwazel
     * @since 1.4
     */
    public static String getHexString(byte[] hash) {
        BigInteger num = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(num.toString(16));

        while (hexString.length() < 32)
            hexString.insert(0, '0');

        return hexString.toString();
    }

    /**
     * Function to get the SHA256 Hash of a string.
     *
     * @param string the String being hashed.
     * @return the SHA256 Hash of the String
     * @author Zwazel
     * @since 1.4
     */
    public static String getHexStringInstant(String string) {
        return getHexString(getSHA(string));
    }
}
package com.ldxx.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 */
public final class MD5Utils {


    /**
     *
     */
    private MD5Utils() {
    }

    /**
     *
     * @param input
     * @return
     */
    public static String getHash(final String input) {

        return input == null ? null : getHash(input.getBytes());
    }

    /**
     *
     * @param input
     * @return
     */
    public static String getHash(final byte[] input) {

        MessageDigest digest = null;

        try {
            if (input != null) {
                digest = MessageDigest.getInstance("MD5");
                digest.update(input);
            }

        } catch (final NoSuchAlgorithmException e) {
           e.printStackTrace();
        }

        return digest == null ? null : toHexString(digest.digest());
    }

    /**
     *
     * @param input
     * @return
     */
    private static String toHexString(final byte[] input) {

        final StringBuilder hexString = new StringBuilder();

        for (final byte yetAnotherByte : input) {
            hexString.append(Integer.toHexString(0xFF & yetAnotherByte));
        }

        return hexString.toString();
    }

}

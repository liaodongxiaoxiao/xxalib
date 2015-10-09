package com.ldxx.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 字符传加密工具类
 */
public final class MD5Utils {


    /** Private constructor to avoid instantiation of static utility class. */
    private MD5Utils() {
    }

    /**
     * Gets md5 hash for a given input.
     * 
     * @param input
     *            text to create hash for
     * @return the hash or <code>null</code> if input has been <code>null</code>
     */
    public static String getHash(final String input) {

        return input == null ? null : getHash(input.getBytes());
    }

    /**
     * Gets md5 hash for a given input.
     * 
     * @param input
     *            text to create hash for
     * @return the hash or <code>null</code> if input has been <code>null</code>
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
     * Creates a hex representation for bytes.
     * 
     * @param input
     *            a bunch of bytes
     * @return hex values of bytes
     */
    private static String toHexString(final byte[] input) {

        final StringBuilder hexString = new StringBuilder();

        for (final byte yetAnotherByte : input) {
            hexString.append(Integer.toHexString(0xFF & yetAnotherByte));
        }

        return hexString.toString();
    }

}

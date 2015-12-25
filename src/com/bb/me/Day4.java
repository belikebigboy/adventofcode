package com.bb.me;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * --- Day 4: The Ideal Stocking Stuffer ---
 * <p>
 * Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts for all the economically forward-thinking little girls and boys.
 * <p>
 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal. To mine AdventCoins, you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
 * <p>
 * For example:
 * <p>
 * If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
 * If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....
 */
public class Day4 {

    public static boolean gotcha(String input, boolean isPartTwo) throws NoSuchAlgorithmException {
        return startsWithZeroes(calculateMD5(input), isPartTwo);
    }


    private static String calculateMD5(String input) throws NoSuchAlgorithmException {
        byte[] bytes = input.getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger bigInt = new BigInteger(1, md.digest(bytes));
        String hexaString = bigInt.toString(16);
        while (hexaString.length() < 32) {
            hexaString = "0" + hexaString;
        }
        return hexaString;
    }

    public static boolean startsWithZeroes(String hexaString, boolean isPartTwo) {
        if (isPartTwo) {
            return hexaString.startsWith("000000");
        }
        return hexaString.startsWith("00000");
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String key = "iwrupvqb";

        boolean gotcha = false;
        int i = 0;
        long time = System.currentTimeMillis();
        while (!gotcha) {
            i++;
            gotcha = gotcha(key + i, true);
        }
        time = System.currentTimeMillis() - time;

        System.out.println("Processing took " + time + " miliseconds");

        System.out.println("Minimum int is " + i);

    }
}

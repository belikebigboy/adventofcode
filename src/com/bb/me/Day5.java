package com.bb.me;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 5: Doesn't He Have Intern-Elves For This? ---
 * <p>
 * Santa needs help figuring out which strings in his text file are naughty or nice.
 * <p>
 * A nice string is one with all of the following properties:
 * <p>
 * It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
 * It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
 * It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
 * For example:
 * <p>
 * ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
 * aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
 * jchzalrnumimnmhp is naughty because it has no double letter.
 * haegwjzuvuyypxyu is naughty because it contains the string xy.
 * dvszwmarrgswjxmb is naughty because it contains only one vowel.
 * How many strings are nice?
 */
public class Day5 {

    private static final String[] vowelsList = {"a", "e", "i", "o", "u"};
    private static final String[] badExp = {"ab", "cd", "pq", "xy"};

    private static String[] loadFile(String fileMap) {
        return fileMap.split("\r\n");
    }

    private static boolean isNiceString(String string, boolean isPartTwo) {
        if (isPartTwo) {
            return containsDoubleExpression(string) && containsDoubleLetterWithOneBetween(string);
        }
        return containsThreeVowels(string, vowelsList) && containsDoubleLetter(string) && !containsBadExpressions(string, badExp);

    }

    private static boolean containsThreeVowels(String string, String... vowels) {
        int count = 0;
        for (String vowel : vowels) {
            count += string.length() - string.replace(vowel, "").length();
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsDoubleLetter(String string) {
        Pattern pattern = Pattern.compile("(\\w)\\1+");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    private static boolean containsBadExpressions(String string, String... expressions) {
        for (String exp : expressions) {
            if (string.contains(exp)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsDoubleExpression(String input) {
        Pattern pattern = Pattern.compile("(\\w{2,})(\\w*)\\1");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    private static boolean containsDoubleLetterWithOneBetween(String input) {
        Pattern pattern = Pattern.compile("(\\w)(\\w)\\1");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static void main(String[] args) throws IOException {
        String fileMap = FileReader.getString("day5.txt");

        String[] lines = loadFile(fileMap);

        int count = 0;
        for (String line : lines) {
            if (isNiceString(line, false)) {
                count++;
            }
        }

        count = 0;
        for (String line : lines) {
            if (isNiceString(line, true)) {
                count++;
            }
        }

        System.out.println("There are " + count + " nice strings");

    }
}

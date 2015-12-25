package com.bb.me;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * --- Day 2: I Was Told There Would Be No Math ---

 The elves are running low on wrapping paper, and so they need to submit an order for more. They have a list of the dimensions (length l, width w, and height h) of each present, and only want to order exactly as much as they need.

 Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating the required wrapping paper for each gift a little easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper for each present: the area of the smallest side.

 For example:

 A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus 6 square feet of slack, for a total of 58 square feet.
 A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus 1 square foot of slack, for a total of 43 square feet.
 All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?
 */
public class Day2 {

    public static int calculateResult(String input) {
        String[] inputLines = input.split("\r\n");

        int[] values;
        int total = 0;

        for (String inputLine : inputLines) {
            values = parseValues(inputLine);
            int l = values[0];
            int w = values[1];
            int h = values[2];

            total += calculateSurfaceArea(values[0], values[1], values[2]) + minInteger(l * w, w * h, l * h);
        }

        return total;
    }

    public static int calculateRibbon(String input) {

        String[] inputLines = input.split("\r\n");

        int[] values;
        int total = 0;

        for (String inputLine : inputLines) {
            values = parseValues(inputLine);
            int l = values[0];
            int w = values[1];
            int h = values[2];

            total += calculateVolume(l, w, h) + minInteger(2 * (l + w), 2 * (l + h), 2 * (h + w));
        }

        return total;

    }

    /**
     * Parses an entry of form l x w x h to an int array
     */
    private static int[] parseValues(String input) {
        String[] values = input.split("x");
        int[] result = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = Integer.parseInt(values[i]);
        }

        return result;
    }

    /**
     * Area formula is 2*l*w + 2*w*h + 2*h*l
     */
    private static int calculateSurfaceArea(int l, int w, int h) {
        return 2 * l * w + 2 * w * h + 2 * h * l;
    }

    private static int calculateVolume(int l, int w, int h) {
        return l * w * h;
    }
    private static int minInteger(Integer... numbers) {
        return Collections.min(Arrays.asList(numbers));
    }

    public static void main(String[] args) throws IOException {

        String input = FileReader.getString("day2.txt");

        //part 1
        System.out.println("Amount of paper needed is " + calculateResult(input) + " square feet");

        //part 2
        System.out.println("Amount of ribbon needed is " + calculateRibbon(input) + " feet");
    }
}

package com.bb.me;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * --- Day 17: No Such Thing as Too Much ---
 * <p>
 * The elves bought too much eggnog again - 150 liters this time. To fit it all into your refrigerator, you'll need to move it into smaller containers. You take an inventory of the capacities of the available containers.
 * <p>
 * For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters. If you need to store 25 liters, there are four ways to do it:
 * <p>
 * 15 and 10
 * 20 and 5 (the first 5)
 * 20 and 5 (the second 5)
 * 15, 5, and 5
 * Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of eggnog?
 */
public class Day17 {

    static int count;
    private static Stack<Integer> stack = new Stack<Integer>();
    private static int sumInStack = 0;

    private static String[] loadFile(String fileMap) {
        return fileMap.split("\r\n");
    }

    private static void recursiveComb(int quantity, ArrayList<Integer> containers, int startIndex, int endIndex) {
        if (sumInStack > quantity) {
            return;
        }
        if (sumInStack == quantity) {
            System.out.println(stack);
            count++;
        }
        for (int currentIndex = startIndex; currentIndex < endIndex; currentIndex++) {
            Integer currentItem = containers.get(currentIndex);
            if (sumInStack + currentItem <= quantity) {
                stack.push(currentItem);
                sumInStack += currentItem;

                recursiveComb(quantity, containers, currentIndex + 1, endIndex);
                sumInStack -= stack.pop();
            }
        }

    }


    private static int countCombinations(int quantity, ArrayList<Integer> containers) {

        recursiveComb(quantity, containers, 0, containers.size());
        return count;
    }

    public static void main(String[] args) throws IOException {
        String fileMap = FileReader.getString("day17.txt");

        String[] lines = loadFile(fileMap);

        ArrayList<Integer> containers = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            containers.add(new Integer(line));
        }

        int count = countCombinations(150, containers);

        System.out.println("There are " + count + " combinations");
    }
}

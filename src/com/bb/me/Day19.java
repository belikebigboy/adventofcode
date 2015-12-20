package com.bb.me;

import java.io.IOException;
import java.util.*;

/**
 * --- Day 19: Medicine for Rudolph ---
 * <p>
 * Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very brightly, and he needs medicine.
 * <p>
 * Red-Nosed Reindeer biology isn't similar to regular reindeer biology; Rudolph is going to need custom-made medicine. Unfortunately, Red-Nosed Reindeer chemistry isn't similar to regular reindeer chemistry, either.
 * <p>
 * The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission plant, capable of constructing any Red-Nosed Reindeer molecule you need. It works by starting with some input molecule and then doing a series of replacements, one per step, until it has the right molecule.
 * <p>
 * However, the machine has to be calibrated before it can be used. Calibration involves determining the number of molecules that can be generated in one step from a given starting point.
 * <p>
 * For example, imagine a simpler machine that supports only the following replacements:
 * <p>
 * H => HO
 * H => OH
 * O => HH
 * Given the replacements above and starting with HOH, the following molecules could be generated:
 * <p>
 * HOOH (via H => HO on the first H).
 * HOHO (via H => HO on the second H).
 * OHOH (via H => OH on the first H).
 * HOOH (via H => OH on the second H).
 * HHHH (via O => HH).
 * So, in the example above, there are 4 distinct molecules (not five, because HOOH appears twice) after one replacement from HOH. Santa's favorite molecule, HOHOHO, can become 7 distinct molecules (over nine replacements: six from H, and three from O).
 * <p>
 * The machine replaces without regard for the surrounding characters. For example, given the string H2O, the transition H => OO would result in OO2O.
 * <p>
 * Your puzzle input describes all of the possible replacements and, at the bottom, the medicine molecule for which you need to calibrate the machine. How many distinct molecules can be created after all the different ways you can do one replacement on the medicine molecule?
 */
public class Day19 {


    private static Map<String, ArrayList<String>> loadTransormations(String input) {
        Map<String, ArrayList<String>> transformationMap = new HashMap<>();
        String[] lines = input.split("\r\n");
        for (String line : lines) {
            String[] mapping = line.split(" => ");
            String key = mapping[0];
            String value = mapping[1];
            ArrayList<String> mappingList = transformationMap.get(key);
            if (null == mappingList) {
                mappingList = new ArrayList<>();
            }
            mappingList.add(value);
            transformationMap.put(key, mappingList);
        }

        return transformationMap;
    }

    private static int noOfCalibrations(Map<String, ArrayList<String>> trMap, String moleculeMachine) {
        Set<String> uniqueCalibrations = new HashSet<>();
        Set<Map.Entry<String, ArrayList<String>>> entries = trMap.entrySet();
        for (Map.Entry<String, ArrayList<String>> entry : entries) {
            String key = entry.getKey();
            ArrayList<String> values = entry.getValue();
            for (String newValue : values) {
                int index = moleculeMachine.indexOf(key);
                while (index >= 0) {
                    String replaced = replaceAtIndex(moleculeMachine, key, newValue, index);
                    uniqueCalibrations.add(replaced);
                    index = moleculeMachine.indexOf(key, index + 1);
                }
            }
        }

        return uniqueCalibrations.size();

    }

    public static String replaceAtIndex(String text, String old, String newT, int pos) {
        if (pos > text.length() - 1) {
            System.out.println("Invalid position. Higher than text length: " + pos);
            return text;
        }

        if (!text.substring(pos, pos + old.length()).equals(old)) {
            System.out.println("Invalid position for function. Not changing " + pos);
            return text;
        }

        return text.substring(0, pos) + newT + text.substring(pos + old.length());
    }

    public static void main(String[] args) throws IOException {
        String transforms = FileReader.getString("day19_1.txt");

        String molecule = FileReader.getString("day19_2.txt");

        System.out.println(noOfCalibrations(loadTransormations(transforms), molecule));
    }
}

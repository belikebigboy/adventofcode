package com.bb.me;

import java.io.IOException;

/**
 * --- Day 6: Probably a Fire Hazard ---
 * <p>
 * Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to deploy one million lights in a 1000x1000 grid.
 * <p>
 * Furthermore, because you've been especially nice this year, Santa has mailed you instructions on how to display the ideal lighting configuration.
 * <p>
 * Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at 0,0, 0,999, 999,999, and 999,0. The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs. Each coordinate pair represents opposite corners of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.
 * <p>
 * To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.
 * <p>
 * For example:
 * <p>
 * turn on 0,0 through 999,999 would turn on (or leave on) every light.
 * toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
 * turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
 * After following the instructions, how many lights are lit?
 */
public class Day6 {

    public static final String TURN = "turn";
    public static final String OFF = "off";
    public static final String ON = "on";
    private static final String TOGGLE = "toggle";


    private static String[] loadFile(String fileMap) {
        return fileMap.split("\r\n");
    }

    //TODO replace string parsing with regexp
    private static void executeCommand(int[][] grid, String line, boolean isP2) {
        String[] words = line.split(" ");
        if (line.startsWith(TURN)) {
            String[] origin = words[2].split(",");
            String[] dest = words[4].split(",");
            int x1 = new Integer(origin[0]);
            int y1 = new Integer(origin[1]);
            int x2 = new Integer(dest[0]);
            int y2 = new Integer(dest[1]);
            if (line.contains(OFF)) {
                if (isP2) {
                    turnOffV2(grid, x1, y1, x2, y2);
                } else {
                    turnOff(grid, x1, y1, x2, y2);
                }
            } else {
                if (isP2) {
                    turnOnV2(grid, x1, y1, x2, y2);
                } else {
                    turnOn(grid, x1, y1, x2, y2);
                }
            }
        } else {
            String[] origin = words[1].split(",");
            String[] dest = words[3].split(",");
            int x1 = new Integer(origin[0]);
            int y1 = new Integer(origin[1]);
            int x2 = new Integer(dest[0]);
            int y2 = new Integer(dest[1]);
            if (isP2) {
                toggleV2(grid, x1, y1, x2, y2);
            } else {
                toggle(grid, x1, y1, x2, y2);
            }
        }
    }


    private static void turnOn(int[][] grid, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                grid[i][j] = 1;
            }
        }
    }

    private static void turnOff(int[][] grid, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private static void toggle(int[][] grid, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                grid[i][j] = 1 - grid[i][j];
            }
        }
    }

    private static void turnOnV2(int[][] grid, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                grid[i][j] += 1;
            }
        }
    }

    private static void turnOffV2(int[][] grid, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (grid[i][j] > 0) {
                    grid[i][j] -= 1;
                }

            }
        }
    }

    private static void toggleV2(int[][] grid, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                grid[i][j] += 2;
            }
        }
    }

    private static int countLights(int[][] map) {
        int count = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] > 0) {
                    count += map[i][j];
                }
            }
        }

        return count;
    }


    public static void main(String[] args) throws IOException {
        String fileMap = FileReader.getString("day6.txt");

        String[] lines = loadFile(fileMap);

        int grid[][] = new int[1000][1000];
        for (String line : lines) {
            executeCommand(grid, line, false);
        }


        int count = countLights(grid);

        System.out.println("There are " + count + " lights on");

        int gridP2[][] = new int[1000][1000];
        for (String line : lines) {
            executeCommand(gridP2, line, true);
        }

        count = countLights(gridP2);

        System.out.println("There are " + count + " brightness");
    }
}

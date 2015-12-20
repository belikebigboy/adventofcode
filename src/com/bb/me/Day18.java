package com.bb.me;

import java.io.IOException;
import java.util.Arrays;

/**
 * After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed. You arrange them in a 100x100 grid.
 * <p>
 * Never one to let you down, Santa again mails you instructions on the ideal lighting configuration. With so few lights, he says, you'll have to resort to animation.
 * <p>
 * Start by setting your lights to the included initial configuration (your puzzle input). A # means "on", and a . means "off".
 * <p>
 * Then, animate your grid in steps, where each step decides the next configuration based on the current one. Each light's next state (either on or off) depends on its current state and the current states of the eight lights adjacent to it (including diagonals). Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".
 * <p>
 * For example, in a simplified 6x6 grid, the light marked A has the neighbors numbered 1 through 8, and the light marked B, which is on an edge, only has the neighbors marked 1 through 5:
 * <p>
 * 1B5...
 * 234...
 * ......
 * ..123.
 * ..8A4.
 * ..765.
 * The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:
 * <p>
 * A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
 * A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
 * All of the lights update simultaneously; they all consider the same current state before moving to the next.
 * <p>
 * Here's a few steps from an example configuration of another 6x6 grid:
 * <p>
 * Initial state:
 * .#.#.#
 * ...##.
 * #....#
 * ..#...
 * #.#..#
 * ####..
 * <p>
 * After 1 step:
 * ..##..
 * ..##.#
 * ...##.
 * ......
 * #.....
 * #.##..
 * <p>
 * After 2 steps:
 * ..###.
 * ......
 * ..###.
 * ......
 * .#....
 * .#....
 * <p>
 * After 3 steps:
 * ...#..
 * ......
 * ...#..
 * ..##..
 * ......
 * ......
 * <p>
 * After 4 steps:
 * ......
 * ......
 * ..##..
 * ..##..
 * ......
 * ......
 * After 4 steps, this example has four lights on.
 * <p>
 * In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
 */
public class Day18 {

    private static final int size = 6;

    private static char[][] map;

    private static char[][] loadMap(String fileMap) {
        String[] lines = fileMap.split("\r\n");
        map = new char[lines.length][lines.length];
        int i = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            int j = 0;
            for (char ch : chars) {
                map[i][j] = ch;
                j++;
            }
            i++;
        }
        return map;
    }

    private static int countNeighbours(char[][] map, int x, int y, char type) {
        int count = 0;
        if (x > 0 && x < map.length - 1) {
            if (y > 0 && y < map.length - 1) {
                count += match(map, x - 1, y - 1, type);
                count += match(map, x - 1, y, type);
                count += match(map, x - 1, y + 1, type);
                count += match(map, x, y - 1, type);
                count += match(map, x, y + 1, type);
                count += match(map, x + 1, y - 1, type);
                count += match(map, x + 1, y, type);
                count += match(map, x + 1, y + 1, type);
            } else {//we are on top or bottom of the map
                if (y == 0) {//top
                    count += match(map, x - 1, y, type);
                    count += match(map, x - 1, y + 1, type);
                    count += match(map, x, y + 1, type);
                    count += match(map, x + 1, y, type);
                    count += match(map, x + 1, y + 1, type);
                } else {//bottom
                    count += match(map, x - 1, y - 1, type);
                    count += match(map, x - 1, y, type);
                    count += match(map, x, y - 1, type);
                    count += match(map, x + 1, y - 1, type);
                    count += match(map, x + 1, y, type);
                }
            }
        } else {
            if (x == 0) { //left but not corner
                if (y > 0 && y < map.length - 1) {
                    count += match(map, x, y - 1, type);
                    count += match(map, x + 1, y - 1, type);
                    count += match(map, x, y + 1, type);
                    count += match(map, x + 1, y, type);
                    count += match(map, x + 1, y + 1, type);
                } else {
                    if (y == 0) { //left upper corner
                        count += match(map, x, y + 1, type);
                        count += match(map, x + 1, y, type);
                        count += match(map, x + 1, y + 1, type);
                    } else {// left bottom corner
                        count += match(map, x, y - 1, type);
                        count += match(map, x + 1, y - 1, type);
                        count += match(map, x + 1, y, type);
                    }
                }
            } else if (x == map.length - 1) {
                if (y > 0 && y < map.length - 1) {//right
                    count += match(map, x - 1, y - 1, type);
                    count += match(map, x - 1, y, type);
                    count += match(map, x - 1, y + 1, type);
                    count += match(map, x, y - 1, type);
                    count += match(map, x, y + 1, type);
                } else {
                    if (y == 0) {
                        count += match(map, x - 1, y, type);
                        count += match(map, x - 1, y + 1, type);
                        count += match(map, x, y + 1, type);
                    } else {
                        count += match(map, x - 1, y - 1, type);
                        count += match(map, x - 1, y, type);
                        count += match(map, x, y - 1, type);
                    }
                }
            }
        }

        return count;
    }

    private static int match(char[][] map, int x, int y, char type) {
        if (map[x][y] == type) {
            return 1;
        }
        return 0;
    }

    private static char[][] processMap(char[][] map) {
        char[][] resultMap = new char[map.length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (isOn(map[i][j])) {
                    int count = countNeighbours(map, i, j, '#');
                    //System.out.println("ON - "+ i + ", " + j + " has " + count + " neighbours ON" );
                    if (count == 2 || count == 3) {
                        resultMap[i][j] = '#';
                    } else {
                        resultMap[i][j] = '.';
                    }
                } else {
                    int count = countNeighbours(map, i, j, '#');
                    //System.out.println("OFF - "+ i + ", " + j + " has " + count + " neighbours ON" );
                    if (count == 3) {
                        resultMap[i][j] = '#';
                    } else {
                        resultMap[i][j] = '.';
                    }
                }
            }
        }

        return resultMap;
    }

    private static int countLights(char[][] map) {
        int count = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == '#') {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean isOn(char ch) {
        return ch == '#';
    }

    public static void main(String[] args) throws IOException {
        String fileMap = FileReader.getString("day18.txt");

        /*fileMap = ".#.#.#\r\n" +
                "...##.\r\n" +
                "#....#\r\n" +
                "..#...\r\n" +
                "#.#..#\r\n" +
                "####..";*/

        map = loadMap(fileMap);

        for (int i = 0; i < 20000; i++) {
            map = processMap(map);
        }

        int count = countLights(map);

        System.out.println(Arrays.deepToString(map));

        System.out.println(count + " lights are on");
    }
}

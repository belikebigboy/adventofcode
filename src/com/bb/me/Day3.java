package com.bb.me;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
 * <p>
 * Santa is delivering presents to an infinite two-dimensional grid of houses.
 * <p>
 * He begins by delivering a present to the house at his starting location, and then an elf at the North Pole calls him via radio and tells him where to move next. Moves are always exactly one house to the north (^), south (v), east (>), or west (<). After each move, he delivers another present to the house at his new location.
 * <p>
 * However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off, and Santa ends up visiting some houses more than once. How many houses receive at least one present?
 * <p>
 * For example:
 * <p>
 * > delivers presents to 2 houses: one at the starting location, and one to the east.
 * ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
 * ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
 */
public class Day3 {

    private static int countVisitedHouses(String input) {
        Point location = new Point();

        Set<Point> visited = new HashSet<>();
        visited.add(location);

        for (int i = 0; i < input.length(); i++) {
            location = location.next(input.charAt(i));
            System.out.println(location.x + ", " + location.y);
            visited.add(location);
        }
        return visited.size();
    }

    public static void main(String[] args) throws IOException {

        String input = FileReader.getString("day3.txt");

        System.out.println("Santa visited " + countVisitedHouses(input) + " at least once");
    }

    public static class Point {
        public final int x;
        public final int y;

        public Point() {
            this.x = 0;
            this.y = 0;
        }

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point next(char input) {
            switch (input) {
                case '>':
                    return new Point(x + 1, y);
                case '<':
                    return new Point(x - 1, y);
                case '^':
                    return new Point(x, y - 1);
                case 'v':
                    return new Point(x, y + 1);
                default:
                    throw new IllegalArgumentException("Invalid direction: " + input);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}

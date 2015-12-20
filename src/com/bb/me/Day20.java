package com.bb.me;

/**
 * --- Day 20: Infinite Elves and Infinite Houses ---
 * <p>
 * To keep the Elves busy, Santa has them deliver some presents by hand, door-to-door. He sends them down a street with infinite houses numbered sequentially: 1, 2, 3, 4, 5, and so on.
 * <p>
 * Each Elf is assigned a number, too, and delivers presents to houses based on that number:
 * <p>
 * The first Elf (number 1) delivers presents to every house: 1, 2, 3, 4, 5, ....
 * The second Elf (number 2) delivers presents to every second house: 2, 4, 6, 8, 10, ....
 * Elf number 3 delivers presents to every third house: 3, 6, 9, 12, 15, ....
 * There are infinitely many Elves, numbered starting with 1. Each Elf delivers presents equal to ten times his or her number at each house.
 * <p>
 * So, the first nine houses on the street end up like this:
 * <p>
 * House 1 got 10 presents.
 * House 2 got 30 presents.
 * House 3 got 40 presents.
 * House 4 got 70 presents.
 * House 5 got 60 presents.
 * House 6 got 120 presents.
 * House 7 got 80 presents.
 * House 8 got 150 presents.
 * House 9 got 130 presents.
 * The first house gets 10 presents: it is visited only by Elf 1, which delivers 1 * 10 = 10 presents. The fourth house gets 70 presents, because it is visited by Elves 1, 2, and 4, for a total of 10 + 20 + 40 = 70 presents.
 * <p>
 * What is the lowest house number of the house to get at least as many presents as the number in your puzzle input?
 * <p>
 * Your puzzle input is 36000000.
 */

import java.util.ArrayList;

interface Visitable {
    void accept(Visitor visitor);
}


interface Visitor {
    void visit(House house);
}

public class Day20 {

    public static void main(String[] args) {

        int noOfPresents = 36000000;
        int limit = 1000000;

        //procedural
        Long start = System.currentTimeMillis();
        int i = 0;
        while (true) {
            int sum = 0;
            i++;

            for (int j = i; j > 0; j--)
                if (i % j == 0)
                    sum += j * 10;

            if (sum >= 36000000) {
                System.out.println(String.format("Found house %d with %d visits", i, sum));
                break;
            }
        }
        Long end = System.currentTimeMillis();

        System.out.println("Algorithmic took " + (end - start) / 1000 + " seconds to complete");

        //fancy, object oriented with Visitor Pattern

        start = System.currentTimeMillis();
        VisitableStreet street = new VisitableStreet(new ArrayList<>());
        boolean found = false;


        for (i = 1; i <= limit; i++) {
            House house = new House(i);
            street.addHouse(house);
        }


        ArrayList<House> housesByNo = new ArrayList<>();

        int elfNumber = 1;
        while (!found && elfNumber <= limit) {
            street.visitHouseByElfNumber(elfNumber);
            housesByNo = street.getHouseNoByVisits(noOfPresents);
            found = (housesByNo.size() > 0);
            elfNumber++;
        }

        end = System.currentTimeMillis();
        System.out.println("Object oriented took " + (end - start) / 1000 + " seconds to complete");
        System.out.println(String.format("Found house %d with %d presents", housesByNo.get(0).getNumber(), housesByNo.get(0).getNoOfPresents()));

    }
}

class House implements Visitable {
    private int number;
    private int noOfPresents;

    public House(int number) {
        this.number = number;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getNoOfPresents() {
        return noOfPresents;
    }

    public void setNoOfPresents(int noOfPresents) {
        this.noOfPresents = noOfPresents;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        House house = (House) o;

        return number == house.number;

    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        return "House{" +
                "number=" + number +
                ", noOfPresents=" + noOfPresents +
                '}';
    }
}

class Elf implements Visitor {

    private int number;

    public Elf(int number) {
        this.number = number;
    }

    @Override
    public void visit(House house) {
        int noOfPresents = house.getNoOfPresents();
        noOfPresents += number * 10;
        house.setNoOfPresents(noOfPresents);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

class VisitableStreet {
    private ArrayList<House> houses;

    public VisitableStreet(ArrayList<House> items) {
        this.houses = items;
    }

    public void visitHouseByElfNumber(int number) {
        Elf elfVisitor = new Elf(number);

        for (House house : houses) {
            if (house.getNumber() % elfVisitor.getNumber() == 0) {
                house.accept(elfVisitor);
            }
        }
    }

    public ArrayList<House> getHouseNoByVisits(int noOfPresents) {
        ArrayList<House> housesByNoOfPresents = new ArrayList<>();
        for (House house : houses) {
            if (house.getNoOfPresents() == noOfPresents) {
                housesByNoOfPresents.add(house);
            }
        }

        return housesByNoOfPresents;
    }

    public void addHouse(House e) {
        this.houses.add(e);
    }
}

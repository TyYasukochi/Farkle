package edu.gonzaga.Farkle;

import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();

        // Adding numbers to the ArrayList
        numbers.add(5);
        numbers.add(1);
        numbers.add(1);
        numbers.add(4);
        numbers.add(2);

        // Sorting the ArrayList in ascending order
        Collections.sort(numbers);

        // Printing the sorted ArrayList
        System.out.println("Sorted ArrayList: " + numbers);
    }
}

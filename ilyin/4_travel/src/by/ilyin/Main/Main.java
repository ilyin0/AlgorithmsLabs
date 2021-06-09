package by.ilyin.Main;

import by.ilyin.Algorithms.Algorithms;

public class Main {
    public static void main(String[] args) {
        Integer[][] matrix = {
                {Integer.MAX_VALUE, 15, 2, 3, 10, 2},
                {15, Integer.MAX_VALUE, Integer.MAX_VALUE, 2, 8, 5},
                {2, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 6, 4},
                {3, 2, 10, Integer.MAX_VALUE, 15, 8},
                {10, 8, 6, 15, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {2, 5, 4, 8, 2, Integer.MAX_VALUE}};


        matrix = Algorithms.floydAlgorithm(matrix);

        System.out.println("Little's algorithm");
        Algorithms.littleAlgorithm(matrix);
    }
}

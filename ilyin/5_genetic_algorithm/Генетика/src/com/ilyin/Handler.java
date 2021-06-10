package com.ilyin;

public class Handler {
    public static void printAllGenomes(Population population) {
        System.out.println("Текущее состояние всех геномов:");

        for (int i = 0; i < 5; ++i) {
            System.out.println("Геном " + i + ": " + population.getPopulation()[i].toString());
        }
    }

    public static float getRandomFloatInRange(float min, float max) {
        return (float) (Math.random() * max + min);
    }
}

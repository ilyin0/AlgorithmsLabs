package com.ilyin;

import java.util.*;

public class Population {
    public static final int VALUE_ACHIEVED = -5;
    public static final int VALUE_NOT_ACHIEVED = -6;

    private Genome[] population = new Genome[5];

    public void createInitialPopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new Genome();
            createGenomeWithRandomValues(population[i]);
        }
    }

    private void createGenomeWithRandomValues(Genome genome) {
        System.out.println("Создание генома со случайными значениями");

        for (int i = 0; i < 5; i++) {
            genome.getGenome()[i] = createGenomeWithRandom();
            System.out.println("Ген " + i + " = " + genome.getGenome()[i]);
        }

        System.out.println();
    }

    public static int createGenomeWithRandom() {
        return getRandomNumberInRange(-300, 300);
    }

    private static int getRandomNumberInRange(int min, int max) {
        return min + (int) ((1 + max - min) * Math.random());
    }

    public int fillGenomesWithFitness() {
        System.out.println("Вычисление приспособленности для всех геномов популяции");

        for (int i = 0; i < 5; i++) {
            float currentFitness = population[i].calculateFitness();
            population[i].setFitness(currentFitness);
            System.out.println("Приспособленность генома " + i + "= " + population[i].getFitness());

            if (currentFitness == VALUE_ACHIEVED) return i;
        }

        System.out.println();
        return VALUE_NOT_ACHIEVED;
    }

    public Genome[][] selection(){
        System.out.println("Селекция");
        Genome[][] pairs = new Genome[5][2];

        for (int i = 0; i < 5; i++){
            pairs[i] = randomSelection(population);
        }

        System.out.println();
        return pairs;
    }

    private Genome[] randomSelection(Genome[] population) {
        List<Genome> pair = new ArrayList<>();
        pair.add(population[getRandomNumberInRange(0, 4)]);
        pair.add(population[getRandomNumberInRange(0, 4)]);

        while(pair.get(0)==pair.get(1)){
            pair.set(1, population[getRandomNumberInRange(0, 4)]);
        }

        System.out.println("Полученная пара родителей:" + Arrays.toString(findBest(pair)));
        return findBest(pair);
    }

    private Genome[] findBest(List<Genome> tournament){
        Genome[] pair = new Genome[2];
        pair[0] = tournament.get(0);
        pair[1] = tournament.get(1);
        return pair;
    }

    private Genome[] proportionalCrossover(Genome parent1, Genome parent2) {
        System.out.println("Пропорциональное вероятностное скрещивание");

        System.out.println("Геном 1-ого родителя: " + parent1);
        System.out.println("Геном 2-ого родителя: " + parent2);

        Genome[] result = new Genome[2];
        result[0] = new Genome();
        result[1] = new Genome();

        for (int i = 0; i < 5; i++) {
            if (Math.random() < parent1.getFitness()/(parent1.getFitness()+parent2.getFitness())) {
                result[0].getGenome()[i] = parent1.getGenome()[i];
                result[1].getGenome()[i] = parent2.getGenome()[i];
            } else {
                result[0].getGenome()[i] = parent2.getGenome()[i];
                result[1].getGenome()[i] = parent1.getGenome()[i];
            }
        }

        return result;
    }

    private Genome[] crossover(Genome[][] pairs) {
        Genome[] children = new Genome[10];
        Genome[] result;

        for (int i = 0; i < 10; i+=2) {
            Genome firstParent = pairs[i/2][0];
            Genome secondParent = pairs[i/2][1];

            result = proportionalCrossover(firstParent, secondParent);

            children[i] = result[0];
            children[i+1] = result[1];

            children[i] = children[i].mutationWithP12(0);
            children[i+1] = children[i+1].mutationWithP12(1);
        }

        System.out.println();

        return children;
    }

    public Genome[] getNewPopulation(Genome[][] pairs){
        Genome[] children  = crossover(pairs);
        Genome[] newPopulation = new Genome[5];
        int index = 0;

        for (int i =0; i < children.length; i++){
            float currentFitness = children[i].calculateFitness();
            children[i].setFitness(currentFitness);

            if (currentFitness == VALUE_ACHIEVED){
                newPopulation[index] = children[i];
                index++;
            }
        }

        float totalFitness = 0;
        index = 0;

        for (int i =0; i < children.length; i+=2) {
            totalFitness = children[i].getFitness() + children[i+1].getFitness() + population[index].getFitness();

            if (Math.random() < children[i].getFitness()/totalFitness)
                newPopulation[index] = children[i];
            else if (Math.random() < (children[i].getFitness() + children[i+1].getFitness())/totalFitness)
                newPopulation[index] = children[i+1];
            else if (Math.random() < 1)
                newPopulation[index] = population[index];
            index++;
        }
        
        for (int i = 0; i < newPopulation.length; i++){
            System.out.println(newPopulation[i].toString());
        }
        System.out.println("\n");

        return newPopulation;
    }

    public Genome[] getPopulation() {
        return population;
    }

    public void setPopulation(Genome[] population) {
        this.population = population;
    }
}

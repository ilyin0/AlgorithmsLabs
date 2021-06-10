package com.ilyin;

public class Genome {
    private int[] genome = new int[5];
    private float fitness;

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int[] getGenome() {
        return genome;
    }

    public void setGenome(int[] genome) {
        this.genome = genome;
    }

    public static int getFunctionValue(int [] roots) {
        int[][]degrees = {
                {2,2,1,1,1},
                {0,0,0,0,1},
                {0,0,1,2,0},
                {0,1,1,1,2},
                {0,2,1,1,0}
        };

        int result = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                result+=degrees[i][j]*roots[j];
            }
        }
        return result;
    }

    public float calculateFitness() {
        int functionValue = 13;
        int fitness = Math.abs(functionValue- getFunctionValue(genome));
        System.out.println("Значение фитнесс функции: " + fitness);
        return 0 != fitness ? 1 / (float) fitness : Population.VALUE_ACHIEVED;
    }

    public Genome mutationWithP12(int childNumber) {
        System.out.println("Мутация генов в геноме:");
        Genome result = (Genome) this.cloneGenome();

        for (int i = 0; i < 5; i++) {
            float randomPercent = Handler.getRandomFloatInRange(0, 300);
            float mutableChance = 0;

            if(childNumber <1)
                mutableChance = 5F;
            else
                mutableChance = 10F;

            if (randomPercent < mutableChance) {
                int oldValue = result.getGenome()[i];
                int newValue = Population.createGenomeWithRandom();
                System.out.println("Мутация произошла в гене №" + i + ", старое значение = " + oldValue + ", новое значение = " + newValue);
                result.getGenome()[i] = newValue;
            }
        }

        System.out.println();
        return result;
    }

    private Object cloneGenome() {
        Genome resultGenome = new Genome();

        resultGenome.setFitness(this.getFitness());
        resultGenome.setGenome(this.genome.clone());

        return resultGenome;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");

        for (int i = 0; i < 5; ++i) {
            result.append(genome[i]);
            result.append(i < 4 ? ", " : "");
        }

        result.append(")");
        return result.toString();
    }
}

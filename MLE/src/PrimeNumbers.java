import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by LindaStemmle on 24.11.2016.
 */
public class PrimeNumbers {

    public static int populationSize = 1000;
    public static int mutationRate = 100;
    public static VM[] population = new VM[populationSize];
    public static VM[] newPopulation = new VM[populationSize];
    public static int populationFitness;
    public static ArrayList<Short> allPrimeNumbers = new ArrayList<Short>();


    public static void createPopulation() {
        for (int i = 0; i < populationSize; i++) {
            population[i] = new VM();
        }
    }

    public static void simulatePopulation() {
        for (int i = 0; i < populationSize; i++) {
            population[i].simulate();
        }
    }

    public static int selectHypothesis() {
        double randNum = Math.random();
        double summe = 0;
        int index = (int) (Math.random() * populationSize + 1);
        do {
            index = index + 1;
            index = index % populationSize;
            double probability = (double) population[index].primeNumbers.size()
                    / (double) populationFitness;
            summe = summe + probability;

        } while (summe < randNum);
        return index;
    }

    public static void calculatePopulationFitness() {
        int maxFitness = 0;
        int individualCount = 0;
        populationFitness = 0;
        for (int i = 0; i < populationSize; i++) {

            int fitness = population[i].getFitness();
            for (short index : population[i].primeNumbers) {
                if (!allPrimeNumbers.contains(index)) {
                    allPrimeNumbers.add(index);
                }
            }

            if (fitness > maxFitness) {
                maxFitness = fitness;
                individualCount = i;
            }
        }
        populationFitness = allPrimeNumbers.size();
        newPopulation[0] = population[individualCount].deepCopy();
    }

    public static void mutation() {
        for (int i = 0; i < mutationRate; i++) {
            int VMindex = (int) (Math.random() * (populationSize - 1));
            while (VMindex == 0) { // in the first field of the new population
                // is the best VM do not overwrite it
                VMindex = (int) (Math.random() * (populationSize - 1));
            }
            short MemIndex = (short) (Math.random() * (newPopulation[VMindex].mem.length - 1));
            byte mem = (byte) (Math.random() * 8);
            newPopulation[VMindex].mem[MemIndex] = mem;
        }
    }

    public static void createNewPopulation() {
        for (int i = 1; i < populationSize; i++) {
            int index = selectHypothesis();
            newPopulation[i] = population[index].deepCopy();
        }
    }

    public static void copyNewIntoOldPopulation() {
        for (int i = 1; i < populationSize; i++) {
            newPopulation[i] = population[i].deepCopy();
        }
    }


    public static void main(String[] args) {

        createPopulation();

        for (int i = 0; i < 100; i++) {
            simulatePopulation();
            calculatePopulationFitness();
            createNewPopulation();
            mutation();
            copyNewIntoOldPopulation();

            System.out.println("populationFitness = " + populationFitness);
            System.out.println("goldenVMFitness = " + population[0].getFitness());
        }

    }
}
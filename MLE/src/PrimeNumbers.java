import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by LindaStemmle on 24.11.2016.
 */
public class PrimeNumbers {

    public static int populationSize = 100;
    public static VM[] population = new VM[populationSize];
    public static VM[] newPopulation = new VM[populationSize];
    public static int populationFitness;
    public static VM goldenVM = new VM();
    public static ArrayList<Short> allPrimeNumbers = new ArrayList<Short>();

    public static int mainCount = 1;

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
            double probability = (double) population[index].primeNumbers.size() / (double) populationFitness;
            summe = summe + probability;

        } while (summe < randNum);
        return index;
    }

    public static void calculatePopulationFitness() {
        int maxFitness = 0;
        int individualCount = 0;
        populationFitness = 0;
        for (int i = 0; i < populationSize; i++) {

            int fitness = population[i].primeNumbers.size();
            //System.out.println("fitness = " + fitness);
            for (short index : population[i].primeNumbers) {
              //  System.out.println("index = " + index);
                if (population[i].isPrime(index)) {
                    if (!allPrimeNumbers.contains(index)) {
                        allPrimeNumbers.add(index);
                    }
                }
            }

           // System.out.println("***************************************************");

            if (fitness > maxFitness) {
                maxFitness = fitness;
                individualCount = i;
            }
        }
        populationFitness = allPrimeNumbers.size();
        goldenVM = population[individualCount];
        newPopulation[0] = goldenVM;
    }


    public static void main(String[] args) {

        //do {
        createPopulation();
        simulatePopulation();
        calculatePopulationFitness();

        population = newPopulation;
        //  } while ();

        System.out.println("populationFitness = " + populationFitness);
        System.out.println("goldenVMFitness = " + newPopulation[0].primeNumbers.size());

    }
}

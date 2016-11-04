import java.util.Arrays;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import sun.net.NetworkServer;

public class Genstrang {

	public static int populationSize = 100;
	public static int geneSize = 20;
	public static int populationFitness;
	public static int[] goldenGenstrang = new int[(geneSize + 1)];
	public static int mutationRate = 10;
	public static double r = 0.25;
	public static double nonCrossoverRate;
	public static double crossoverRate;
	public static int mainCount = 1;

	// geneSize +1 because in the first field of each individual we save its
	// fitness
	public static int[][] population = new int[populationSize][(geneSize + 1)];
	public static int[][] newPopulation = new int[populationSize][(geneSize + 1)];
	public static int[] targetGenstrang = new int[geneSize];

	public static void fillPopulation() {
		for (int i = 0; i < population.length; i++) {
			for (int k = 1; k < population[i].length; k++) {
				population[i][k] = (Math.random() < 0.5) ? 0 : 1;
			}
		}
	}

	public static void fillTargetGenstrang() {
		for (int i = 0; i < targetGenstrang.length; i++) {
			targetGenstrang[i] = (Math.random() < 0.5) ? 0 : 1;
		}
		System.out.print("Target: ");
		for (int j = 0; j < targetGenstrang.length; j++) {
			System.out.print(targetGenstrang[j]);
		}
		System.out.println();

	}

	public static void calculateCrossover() {
		nonCrossoverRate = (Math.round((1 - r) * populationSize));

		if (nonCrossoverRate % 2 != 0) {
			nonCrossoverRate = nonCrossoverRate - 1;
		}
		crossoverRate = (populationSize - nonCrossoverRate) / 2;

		// System.out.println("Crossover:" + crossoverRate + "noCross: " +
		// nonCrossoverRate);
	}

	public static void calculateFitness() {
		int maxFitness = 0;
		int individualCount = 0;
		int fitness = 0;
		populationFitness = 0;

		for (int i = 0; i < population.length; i++) {
			fitness = 0;
			for (int k = 1; k < population[i].length; k++) {
				if (population[i][k] == targetGenstrang[k - 1]) {
					fitness = fitness + 1;
				}
			}
			if (fitness > maxFitness) {
				maxFitness = fitness;
				individualCount = i;
			}
			populationFitness += fitness;
			population[i][0] = fitness;
		}
		goldenGenstrang = Arrays.copyOf(population[individualCount], (geneSize + 1));
		newPopulation[0] = goldenGenstrang;
	}

	public static int selectHypothesis() {
		double randNum = Math.random();
		double summe = 0;
		int index = (int) (Math.random() * populationSize + 1);
		do {
			index = index + 1;
			index = index % populationSize;
			double probability = (double) population[index][0] / (double) populationFitness;
			summe = summe + probability;

		} while (summe < randNum);
		return index;
	}

	public static void noCrossover() {
		// -1 da goldenGenestrag schon kopiert
		for (int i = 0; i < nonCrossoverRate - 1; i++) {
			int index = selectHypothesis();
			newPopulation[mainCount] = Arrays.copyOf(population[index], (geneSize + 1));
			mainCount++;
		}
	}

	public static void crossover() {
		for (int i = 0; i < crossoverRate; i++) {
			int cross1 = selectHypothesis();
			// System.out.println("Cross1: " +
			// Arrays.toString(population[cross1]));

			int cross2 = selectHypothesis();
			// System.out.println("Cross2: " +
			// Arrays.toString(population[cross2]));
			int crossoverPoint = (int) (Math.random() * geneSize + 1);
			// System.out.println("Crossoverpoint:" + crossoverPoint);

			System.arraycopy(population[cross1], 0, newPopulation[mainCount], 0, crossoverPoint);
			System.arraycopy(population[cross2], crossoverPoint, newPopulation[mainCount], crossoverPoint,
					geneSize + 1 - crossoverPoint);
			// System.out.println(Arrays.toString(newPopulation[mainCount]));
			mainCount++;
			System.arraycopy(population[cross2], 0, newPopulation[mainCount], 0, crossoverPoint);
			System.arraycopy(population[cross1], crossoverPoint, newPopulation[mainCount], crossoverPoint,
					geneSize + 1 - crossoverPoint);
			// System.out.println(Arrays.toString(newPopulation[mainCount]));
			mainCount++;

		}
	}

	public static void mutation() {
		for (int i = 0; i < mutationRate; i++) {
			int index = (int) (Math.random() * populationSize);
			while (index == 0) {
				// System.out.println("WHILEWHILEWHILEWHILE: " + index);
				index = (int) (Math.random() * populationSize);
				// System.out.println("NEW INDEX: " + index);

			}
			// System.out.println("Index: " + index);
			int bit = (int) (Math.random() * geneSize + 1);
			// System.out.println("bit: " + bit);
			// System.out.println("Population: " +
			// Arrays.toString(newPopulation[index]));

			if (newPopulation[index][bit] == 0) {
				newPopulation[index][bit] = 1;
			} else {
				newPopulation[index][bit] = 0;
			}
			// System.out.println("Population: " +
			// Arrays.toString(newPopulation[index]));
		}
	}

	public static void main(String[] args) {
		calculateCrossover();
		Genstrang.fillTargetGenstrang();
		Genstrang.fillPopulation();
		int counticount = 0;
		do {
			counticount++;
			calculateFitness();

			// System.out.println("Old Population");
			// HillClimber.print2DArray(population);

			noCrossover();
			crossover();
			mutation();

			// System.out.println("New Population");
			// HillClimber.print2DArray(newPopulation);
			mainCount = 1;
			System.out.println("Fitness: " + goldenGenstrang[0]);
			population = newPopulation;

		} while (goldenGenstrang[0] < geneSize);
		System.out.println("Counticount: " + counticount);

	}
}

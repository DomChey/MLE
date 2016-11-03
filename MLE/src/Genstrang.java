import java.util.Arrays;

public class Genstrang {

	public static int populationSize = 5;
	public static int geneSize = 5;
	public static int populationFitness;
	public static int[] goldenGenstrang = new int[(geneSize + 1)];
	public static int mutationRate = 0;
	public static int crossoverRate = 0;
	public static int nonCrossoverRate = 1 - crossoverRate;

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
	}

	public static int selectHypothesis() {
		double randNum = Math.random();
		double summe = 0;
		int index = (int) (Math.random() * geneSize + 1);
		do {
			index = index + 1;
			index = index % populationSize;
			double probability = (double) population[index][0] / (double) populationFitness;
			summe = summe + probability;

		} while (summe < randNum);
		return index;
	}

	public static void main(String[] args) {

		Genstrang.fillPopulation();
		Genstrang.fillTargetGenstrang();
		calculateFitness();
		HillClimber.print2DArray(population);
		selectHypothesis();

	}

}


public class Genstrang {
	
	public static int populationSize = 5;
	public static int geneSize = 5;
	
	//geneSize +1 because in the first field of each individual we save its fitness
	public static int[][] population = new int[populationSize][geneSize+1];

	public static int[] targetGenstrang = new int[5];

	public static void fillPopulation() {
		for (int i = 0; i < population.length; i++) {
			for(int k = 1; k < population[i].length; k++){
				population[i][k] = (Math.random() < 0.5) ? 0 : 1;
			}
		}
		HillClimber.print2DArray(population);
		
	}
	
	public static void fillTargetGenstrang() {
		for (int i = 0; i < targetGenstrang.length; i++) { 
			targetGenstrang[i] = (Math.random() < 0.5) ? 0 : 1;
		}
		for (int j = 0; j < targetGenstrang.length; j++) {
			System.out.print(targetGenstrang[j]);			
		}
		System.out.println();
		
	}
	

	public static int calculateFitness() {
		int fitness = 0;
		for (int i = 0; i < population.length; i++) {
			fitness = 0;
			for(int k = 1; k < population[i].length; k++){
				if (population[i][k] == targetGenstrang[k-1]){
					fitness += 1;
				}
			}
			population[i][0]= fitness;
			System.out.println("Fitness of individual " +i +": " +fitness);
		}

		return fitness;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Genstrang.fillPopulation();
		Genstrang.fillTargetGenstrang();
		calculateFitness();
	}

}

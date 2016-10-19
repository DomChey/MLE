import java.util.Arrays;

public class SimulatedAnnealing {

	public static double temperatur = 200;
	public static double epsilon = 0.03;
	
	public static void main(String[] args) {
		
		HillClimber.filldistances();
		HillClimber.initalTravel();
		int lastFitness = HillClimber.calculateFitness();
		System.out.println("Erste Fitness: " + lastFitness);
		
		while(temperatur > epsilon){
			int[] copyOldTravel = Arrays.copyOf(HillClimber.towns, HillClimber.towns.length);					
			HillClimber.swapTowns();
			int newFitness = HillClimber.calculateFitness();
			
			System.out.println("Last Fitness: " + lastFitness);
			System.out.println("New Fitness: " + newFitness);
					
			double probability = calcProbability(newFitness, lastFitness);	
			double random = Math.random();
			
			if (newFitness < lastFitness) 
			{
				lastFitness = newFitness;
			} 
			else if (probability > random){

				System.out.println("Random: " + random);
				System.out.println("Probability: " + probability);
				lastFitness = newFitness;
			}
			else 
			{
				HillClimber.towns = Arrays.copyOf(copyOldTravel, copyOldTravel.length);
			}

			
			temperatur = temperatur - epsilon;
		}
	}
	
	public static double calcProbability(int newFitness, int oldFitness) {
		double result;		
		result = Math.exp(((newFitness - oldFitness)* (-1)) / temperatur);		
		return result;
	}

}

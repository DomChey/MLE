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
					
			double probability = calcProbability(newFitness, lastFitness);	
			double random = Math.random();
			
			if (newFitness < lastFitness || probability > random)
			{
				lastFitness = newFitness;
			} 
			else 
			{
				HillClimber.towns = Arrays.copyOf(copyOldTravel, copyOldTravel.length);
			}
			
			System.out.println("Random: " + random);
			System.out.println("New Fitness: " + newFitness);
			System.out.println("Last Fitness: " + lastFitness);
			
			temperatur = temperatur - epsilon;
		}
	}
	
	public static double calcProbability(int newFitness, int oldFitness) {
		double result;		
		result = Math.exp((newFitness - oldFitness) / temperatur);
		System.out.println("Probability: " + result);
		
		return result;
	}

}

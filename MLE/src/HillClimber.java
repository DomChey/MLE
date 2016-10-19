import java.util.Arrays;



public class HillClimber {

	//array in which the towns are stored in some order for the travel
	public static int[] towns = new int[100];

	//array for the distances between towns
	public static int[][] distances = new int[100][100];
	
	// random distances between towns put in a beautiful table aka two-dimensional array
	public static void filldistances()
	{
		for(int zeile=0; zeile<distances.length; zeile++){
			for (int spalte = zeile; spalte < distances.length; spalte++){
				if (spalte == zeile){
					distances[zeile][spalte] = 0;
				} else {
					int randomzahl = (int)(Math.random()*80);
					distances[zeile][spalte] = randomzahl;
					distances[spalte][zeile] = randomzahl;
				}
				
			}
		}
	}
	
	//initial travel is just from town 0 to town 99 in numerical order
	public static void initalTravel(){
		for(int i = 0; i<towns.length; i++){
			towns[i] = i;
		}
	}
	
	//just to pretty print a 2D-Array
	public static void print2DArray(int[][] input){
		for (int i = 0; i<input.length; i++){
			for (int j =0; j<input.length; j++){
				int dingens = input[i][j];
				System.out.printf("%5d ", dingens);
			}
			System.out.println();
		}
	}
	
	//swap two towns by random
	public static void swapTowns(){
		int townOne = (int) (Math.random()*towns.length);
		int townTwo = (int) (Math.random()*towns.length);
		int tmp;
		tmp = towns[townOne];
		towns[townOne]=towns[townTwo];
		towns[townTwo]=tmp;
	}
	
	//calculates the travel distance
	public static int calculateFitness(){
		int result = 0;
		for(int i = 0; i < (towns.length -1); i++){
			int townOne = towns[i];
			int townTwo = towns[i+1];
			result = result + distances[townOne][townTwo];
		}
		return result;
	}

	
	
	public static void main(String[] args) {
		HillClimber.filldistances();
		//print2DArray(HillClimber.distances);
		HillClimber.initalTravel();
		int lastFitness = calculateFitness();
		System.out.println(lastFitness);
		
		for(int i = 0; i < 1000; i++){
			int[] copyOldTravel = Arrays.copyOf(towns, towns.length);
			swapTowns();
			int newFitness = calculateFitness();
			System.out.println("Last Fitness: " + lastFitness);
			System.out.println("New Fitness: " + newFitness);
			if (newFitness < lastFitness){
				lastFitness = newFitness;
				System.out.println("Changed Fitness");
			} else {
				towns = Arrays.copyOf(copyOldTravel, copyOldTravel.length);
			}
		}
	}

}

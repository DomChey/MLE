
public class Genstrang {

	public static int[] genstrang = new int[5];

	public static int[] bestGenstrang = new int[5];

	public static void fillGenstrang() {
		for (int i = 0; i < genstrang.length; i++) {
			genstrang[i] = (Math.random() < 0.5) ? 0 : 1;
		}
		for (int j = 0; j < genstrang.length; j++) {
			System.out.print(genstrang[j]);
		}
		System.out.println();
		
	}
	
	public static void fillBestGenstrang() {
		for (int i = 0; i < bestGenstrang.length; i++) {
			bestGenstrang[i] = (Math.random() < 0.5) ? 0 : 1;
		}
		for (int j = 0; j < bestGenstrang.length; j++) {
			System.out.print(bestGenstrang[j]);			
		}
		System.out.println();
		
	}
	

	public static int calculateFitness(int mutation[]) {

		int fitness = 0;
		for (int i = 0; i < genstrang.length; i++) {
			if (genstrang[i] == mutation[i]) {
				fitness += 1;
			}
		}
		System.out.println(fitness);
		return fitness;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Genstrang.fillGenstrang();
		Genstrang.fillBestGenstrang();
		calculateFitness(bestGenstrang);
	}

}

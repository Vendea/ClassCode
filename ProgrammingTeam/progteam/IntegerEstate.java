package progteam;
import java.util.Scanner;


public class IntegerEstate {
	static Scanner scanner = new Scanner(System.in);
	static int coins;

	public static void main(String[] args) {
		coins = scanner.nextInt();
		while (coins != 0) {
			buyHousesWithCoins();
			coins = scanner.nextInt();
		}
	}

	private static void buyHousesWithCoins() {
		int numPossibilities = (coins == 1) ? 0 : 1;
		
		for (int i = 2; i <= coins/2; i++)
			numPossibilities += canBuyHouse(i) ? 1 : 0;
		
		System.out.println(numPossibilities);
	}

	private static boolean canBuyHouse(int cost) {
		int totalCost = cost;
		int nextHouseCost = cost + 1;
		
		while (totalCost < coins)
			totalCost += nextHouseCost++;

		return totalCost == coins;
	}
}

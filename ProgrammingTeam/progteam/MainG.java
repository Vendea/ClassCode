package progteam;
import java.util.Scanner;
public class MainG {
	static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		while(scanner.hasNextInt()){
			int n = scanner.nextInt();
			if(n != 0)
				System.out.println(doNextProblem(n));
			else
				return;
		}
	}

	public static int doNextProblem(int money){
		int retVal = 0;
		int j = 1;
			for(int l = 2; l <= money/j; l++){
				for(int h = l; h <= money/j; h++){
					int sumHouses = 0;
					for(int i = l; i <= h; i++)
						sumHouses+=i;
						if(h > l)
							j = h-l;
					if(sumHouses == money)
						retVal++;
				}	

			}
		
		return retVal;
	}

}

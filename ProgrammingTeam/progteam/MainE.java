package progteam;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class MainE {
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

	static int doNextProblem(int n){
		int l = 0;
		ArrayList<Integer> intList = new ArrayList<Integer>();
		HashMap<Integer, HashMap<Integer, Integer>> gcdMap = new HashMap<Integer, HashMap<Integer, Integer>>();
		for(int i = 0; i < n; i++){
			l = scanner.nextInt();
			if(!intList.contains(l))
				intList.add(l);
		}


		for(int j = 0; j < intList.size(); j++){
			for(int i = j; i < intList.size(); i++){
				HashMap<Integer, Integer> pairs = new HashMap<Integer, Integer>();
				int a = intList.get(i);
				int b = intList.get(j);
				pairs.put(a, b);
				if(!gcdMap.containsKey(gcd(a,b)))
					gcdMap.put(gcd(a,b), pairs);

			}
		}

		int retVal = gcdMap.size();
		if(!gcdMap.containsKey(1))
			retVal++;
		if(!gcdMap.containsKey(2))
			retVal++;
		if(!gcdMap.containsKey(3))
			retVal++;
		return retVal;
	}

	public static int gcd(int a, int b){
		int r = a % b;

		if(r == 0)
			return b;
		else
			return gcd(b,r);
	}
}
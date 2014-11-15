package progteam;

import java.util.Scanner;
public class Collatz {
	static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		int caseNum = 1;
		while(true){
			int tempA = scanner.nextInt();
			if(tempA < 0)
				return;
			int tempLim = scanner.nextInt();
			doNextProblem(caseNum, tempA, tempLim);
			
			caseNum++;
		}
	}

	static void doNextProblem(int cs, int aVal, int lVal) {
		int numTerms = 1;
		long changingA = aVal;
		while(changingA != 1 && changingA <= lVal){
			if(changingA%2 == 0)
				changingA/=2;
			else
				changingA = changingA*3+1;
			if(changingA<=lVal)
				numTerms++;
		}
		System.out.println("Case "+ cs + ": "+ " A = "+ aVal+ 
				", limit = "+lVal+", number of terms = " + numTerms);
	}
}

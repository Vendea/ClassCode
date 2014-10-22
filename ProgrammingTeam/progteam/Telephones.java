package progteam;

import java.util.ArrayList;
import java.util.Scanner;
public class Telephones{
	static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int M = scanner.nextInt();

		while(N != 0 || M != 0){
			doNextProblem(N, M);
			N = scanner.nextInt();
			M = scanner.nextInt();
		}
	}

	static void doNextProblem(int N, int M){
		ArrayList<Integer> calls = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			scanner.nextInt();
			scanner.nextInt();
			int start = scanner.nextInt();
			calls.add(start);
			calls.add(scanner.nextInt() + start); // end time
		}

		ArrayList<Integer> breaks = new ArrayList<Integer>();
		for (int i = 0; i < M; i++) {
			int start = scanner.nextInt();
			breaks.add(start);
			breaks.add(scanner.nextInt() + start); // end time
		}

		for (int i = 0; i < M; i++) {
			int numTimes = 0;
			for (int j = 0; j < N; j++) {
				if(intervalsOverlap(breaks.get(i*2), breaks.get(i*2+1), calls.get(j*2), calls.get(j*2+1))) {
					numTimes++;
				}
			}
			System.out.println(numTimes);
		}
	}
	
	static boolean intervalsOverlap(int start1, int end1, int start2, int end2) {
		if(start2 <= start1 && end2 >= end1)
			return true;
		if(end2 > start1 && end2 <= end1)
			return true;
		if(start2 < end1 && start2 >= start1)
			return true;
		return false;
	}
}

/*
3 2
3 4 2 5
1 2 0 10
6 5 5 8
0 6
8 2
 */

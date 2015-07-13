package progteamProbs;

import java.util.Scanner;

public class RopeCrisis2 {
public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		int N = scanner.nextInt();

		for(int i = 0; i < N; i++){
			solveNextProblem();
		}

	}

	static void solveNextProblem(){
		double x1 = scanner.nextDouble();
		double y1 = scanner.nextDouble();
		double x2  = scanner.nextDouble();
		double y2 = scanner.nextDouble();
		double R = scanner.nextDouble();

		double area = Math.abs(x1*y2 - x2*y1)/2;
		double base = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		double height = 2* area/base;

		if(height >=R){
			System.out.printf("%.3f\n", base);
		}

		double d1 = Math.sqrt(x1*x1+y1*y1);
		double d2 = Math.sqrt(x2*x2+y2*y2);

		double s1 = Math.sqrt(d1*d2 - R*R);
		double s2 = Math.sqrt(d2*d2 - R*R);

		double bigAngle = Math.acos((x1*x2 + y1*y2) / (d1*d2));
		double p1 = Math.acos(R/d1);
		double p2 = Math.acos(R/d2);
		double angle = bigAngle - p1 - p2;
		double arc = R*angle;

		System.out.printf("%.3f\n", s1+arc+s2);

	}
}

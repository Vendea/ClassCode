package progteamProbs;

import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.Scanner;

public class MyRopePulling {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		while(scanner.hasNext()){
			int N =  scanner.nextInt();
			for(int i = 0; i < N; i++){
				float x1 = scanner.nextFloat();
				float y1 = scanner.nextFloat();
				float x2 = scanner.nextFloat();
				float y2 = scanner.nextFloat();
				float r = scanner.nextFloat();
				solveNextProblem(x1,y1,x2,y2,r);

			}
		}
	}
	static void solveNextProblem(float x1, float y1, float x2,
			float y2, float r) {
		DecimalFormat df = new DecimalFormat("0.000");
		double area = Math.abs(x1*y2 - x2*y1) / 2;
		double base = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		double height = 2 * area / base;

		if(height >= r){
			System.out.println(df.format(base));
			return;
		}
		/**
		 * y = mx + b
		 * y-y1 = m(x-x1)
		 * ay - mx = y1-mx1 --> c and a == 1
		 * y - mx = c
		 * y = (c + mx)
		 * circle: x^2+y^2 = r^2
		 * x^2 + (c+mx)^2 = r^2
		 * x^2 + c^2 + 2*c*mx + mx*mx - r^2 = 0
		 * (m*m+1)*x^2 + (2*c*m)*x + c*c-r*r = 0
		 * x = K
		 *
		 */
		//Line2D lin = new Line2D.Double(x1,y1,x2,y2);
		double m = (y1-y2)/(x1-x2);
		double c = y1-m*x1;
		double ap = m*m+1;
		double bp = 2*c*m;
		double cp = c*c-r*r;
		if(bp*bp-4*ap*cp < 0){
			System.out.println(df.format(base));
			return;
		}
		double root1x;
		double root2x;
		double root1y;
		double root2y;

		if(x1 == x2){
			root1x = x1;
			root2x = x2;
			if((r*r-x1*x1) < 0){
				System.out.println(df.format(base));
				return;
			}
			if((r*r-x2*x2)<0){
				System.out.println(df.format(base));
				return;
			}
			root1y = Math.sqrt(r*r-x1*x1);
			root2y = Math.sqrt(r*r-x2*x2);
		}
		else{
			root1x = (-bp+Math.sqrt(bp*bp-4*ap*cp))/(2*ap);
			root2x = (-bp-Math.sqrt(bp*bp-4*ap*cp))/(2*ap);
			root1y = c+m*root1x;
			root2y = c+m*root2x;
		}
		double CtoA = Math.sqrt((root1x-x1)*(root1x-x1) + (root1y-y1)*(root1y-y1));
		double CtoB = Math.sqrt((root1x-x2)*(root1x-x2) + (root1y-y2)*(root1y-y2));
		double DtoA = Math.sqrt((root2x-x1)*(root2x-x1) + (root2y-y1)*(root2y-y1));
		double DtoB = Math.sqrt((root2x-x2)*(root2x-x2) + (root2y-y2)*(root2y-y2));
		if(!(CtoA+CtoB == base)){
			if(!(DtoA + DtoB == base)){
				System.out.println(df.format(base));
				return;
			}
		}


		double d1 = Math.sqrt(x1*x1 + y1*y1);
		double d2 = Math.sqrt(x2*x2 + y2*y2);

		double s1 = Math.sqrt(d1*d1 - r*r);
		double s2 = Math.sqrt(d2*d2 - r*r);

		double bigAngle = Math.acos((x1*x2 + y1*y2) / (d1*d2));
		double p1 = Math.acos(r/d1);
		double p2 = Math.acos(r/d2);
		double angle = bigAngle - p1 - p2;
		double arc = r * angle;

		System.out.println(df.format(s1 + arc + s2));
	}

}

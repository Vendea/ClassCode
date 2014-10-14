package progteam;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CookiesbyAngle {
	static Scanner scanner = new Scanner (System.in);
	static int DIAMETER = 5;
	public static void main(String[] args){
		int N = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		for(int i = 0; i < N; i++){
			solveOneProblem();
		}
	}


	public static void solveOneProblem(){
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		String s = scanner.nextLine();
		while(s.length()>0){
			String xy[] = s.split(" ");
			double x = Double.valueOf(xy[0]);
			double y = Double.valueOf(xy[1]);
			points.add(new Point2D.Double(x, y));
			s = scanner.nextLine();
		}
		ArrayList<Integer> cookies = findCookies(points);
		Collections.sort(cookies);
		int numChips = cookies.get(cookies.size()-1);
		System.out.println(numChips);
	}


	static ArrayList<Integer> findCookies(ArrayList<Point2D> p) {
		ArrayList<Integer> c = new ArrayList<Integer>();
		for(int i = 0; i < p.size(); i++){
			for(int j = i+1; j < p.size(); j++){
				Point2D a = p.get(i);
				Point2D b = p.get(j);
				if(dist(a,b)<= 5.0){
					int chipcount = 0;
					double inang = Math.asin(dist(a,b)/DIAMETER);
					for(int k = 0; k < p.size();k++){
						Point2D testp = p.get(k);
						Point2D v1 = new Point2D.Double(a.getX()-testp.getX(),a.getY()-testp.getY());
						Point2D v2 = new Point2D.Double(b.getX()-testp.getX(), b.getY()-testp.getY());
						double outang = getAngle(v1,v2);
						if(right(testp, a,b)){
							if (outang >= inang)
								chipcount++;
						}
						else{
							if (outang >= (Math.PI-inang))
								chipcount++;
						}
					}
					chipcount+=2;
					c.add(chipcount);
				}
			}
		}
		return c;
	}

	private static boolean right(Point2D testp, Point2D a, Point2D b) {
		Line2D testl = new Line2D.Double(a,b);
		if(testl.relativeCCW(testp)==-1)
			return true;
		return false;
	}


	static double dist(Point2D s, Point2D e) {
		double retval = 0;
		retval = Math.sqrt((s.getX()-e.getX())*(s.getX()-e.getX())+(s.getY()-e.getY())*(s.getY()-e.getY()));
		return retval;
	}

	static double getAngle(Point2D alpha, Point2D beta){
		double dot = alpha.getX()*beta.getX() + alpha.getY()*beta.getY();
		double maga = Math.sqrt(alpha.getX()*alpha.getX() + alpha.getY()*alpha.getY());
		double magb = Math.sqrt(beta.getX()*beta.getX() + beta.getY()*beta.getY());
		double ang = Math.acos(dot/(maga*magb));
		return ang;
	}
}

package progteamProbs;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Cookies {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		int N = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		for(int i = 0; i < N; i++){
			solveOneProblem();
		}
	}

	public static void solveOneProblem(){
		ArrayList<Point2D> points = new ArrayList<Point2D>();

		// read a line
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

	static ArrayList<Integer> findCookies(ArrayList<Point2D> p){
		ArrayList<Integer> c = new ArrayList<Integer>();
		Point2D.Double center;
		for(int i = 0; i < p.size(); i++){
			for(int j = i+1; j < p.size(); j++){
				if(dist(p.get(i),p.get(j))<= 5.0){
					int chipcount = 0;
					center = new Point2D.Double((p.get(i).getX()+p.get(j).getX())/2, (p.get(i).getY()+p.get(j).getY())/2);
					for(int k = 0; k < p.size();k++){
						if(dist(center, p.get(k))<=2.50)
							chipcount++;
					}
					c.add(chipcount);
				}
			}
		}
		return c;
	}

	static double dist(Point2D s, Point2D e) {
		double retval = 0;
		retval = Math.sqrt((s.getX()-e.getX())*(s.getX()-e.getX())+(s.getY()-e.getY())*(s.getY()-e.getY()));
		return retval;
	}
}

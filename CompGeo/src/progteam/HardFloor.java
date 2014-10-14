package progteam;

import java.awt.geom.Point2D;
import java.util.Scanner;

public class HardFloor {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		while(true){
			int edges = scanner.nextInt();
			if(edges <4)
				return;
			Point2D[] pArr = new Point2D[edges];
			Point2D p;
			for(int i = 0; i < edges; i++){
				if(i == 0)
					p = new Point2D.Double(0.0,0.0);
				else{
					String dir = scanner.next();
					int leng = scanner.nextInt();
					p = new Point2D.Double(getX(dir, leng, pArr[i-1]), getY(dir, leng, pArr[i-1]));
				}
				pArr[i] = p;
			}
			scanner.next();
			scanner.next();
			findArea(pArr);
		}
	}

	static void findArea(Point2D[] points) {
		Point2D p1;
		Point2D p2;
		double totalArea = 0;
		for(int i = 0; i < points.length;i++){
			p1 = points[i];
			p2 = points[(i+1)%points.length];
			totalArea += (p1.getX()*p2.getY() - p1.getY()*p2.getX())/2;
		}
		System.out.println("THE AREA IS "+ Math.abs(Math.round(totalArea)));
	}

	private static int getX(String d, int l, Point2D p) {
		int rval = 0;
		double prevX = p.getX();
		if(d.equals("N") || d.equals("S"))
			rval = (int) prevX;
		else if(d.equals("E"))
			rval = (int)prevX+l;
		else	
			rval = (int)prevX-l;
		return rval;
	}

	private static int getY(String d, int l, Point2D p) {
		int rval = 0;
		double prevY = p.getY();
		if(d.equals("E")||d.equals("W"))
			rval = (int)prevY;
		else if(d.equals("N"))
			rval = (int)prevY+l;
		else
			rval = (int)prevY-l;
		return rval;
	}
}

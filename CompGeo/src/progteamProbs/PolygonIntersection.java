package progteamProbs;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class PolygonIntersection{
	static Scanner scanner;
	static DecimalFormat df = new DecimalFormat("0.00");
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		while(true){
			int pSize = scanner.nextInt();
			if(pSize<3)
				return;
			int[] xpoints = new int[pSize];
			int [] ypoints = new int[pSize];
			for(int i = 0; i < pSize; i++){
				xpoints [i] = scanner.nextInt();
				ypoints [i] = scanner.nextInt();
			}
			Polygon polyOne = new Polygon(xpoints, ypoints, pSize);
			pSize = scanner.nextInt();
			if(pSize<3)
				return;
			xpoints = new int[pSize];
			ypoints = new int[pSize];
			for(int i = 0; i < pSize; i++){
				xpoints [i] = scanner.nextInt();
				ypoints [i] = scanner.nextInt();
			}
			Polygon polyTwo = new Polygon(xpoints, ypoints, pSize);
			findIntersections(polyOne, polyTwo);
		}
	}

	static void findIntersections(Polygon p1, Polygon p2) {

		int numIntersections = 0;
		ArrayList<MyLines> thoseThatX = new ArrayList<MyLines>();
		Line2D[] p1Sides = new Line2D[p1.npoints];
		Line2D[] p2Sides = new Line2D[p2.npoints];
		Point2D[] points1 = new Point2D[p1.npoints];
		Point2D[] points2 = new Point2D[p2.npoints];
		ArrayList<Point2D> pointsInt = new ArrayList<Point2D>();

		for(int i = 0; i < p1.npoints; i++){
			if(i == p1Sides.length-1){
				p1Sides[i] = new Line2D.Double(p1.xpoints[i], p1.ypoints[i], p1.xpoints[0], p1.ypoints[0]);
				points1[i] = new Point2D.Double(p1.xpoints[i], p1.ypoints[i]);
			}
			else{
				p1Sides[i] = new Line2D.Double(p1.xpoints[i], p1.ypoints[i], p1.xpoints[i+1], p1.ypoints[i+1]);
				points1[i] = new Point2D.Double(p1.xpoints[i], p1.ypoints[i]);
			}
		}

		for(int i = 0; i < p2.npoints; i++){
			if(i == p2Sides.length-1){
				p2Sides[i] = new Line2D.Double(p2.xpoints[i], p2.ypoints[i], p2.xpoints[0], p2.ypoints[0]);
				points2[i] = new Point2D.Double(p2.xpoints[i], p2.ypoints[i]);
			}
			else{
				p2Sides[i] = new Line2D.Double(p2.xpoints[i], p2.ypoints[i], p2.xpoints[i+1], p2.ypoints[i+1]);
				points2[i] = new Point2D.Double(p2.xpoints[i], p2.ypoints[i]);
			}
		}

		for(int i = 0; i < p1Sides.length; i++){
			for(int j = 0; j < p2Sides.length; j++){
				if(p2Sides[j].intersectsLine(p1Sides[i])){
					thoseThatX.add(new MyLines(p1Sides[i], p2Sides[j]));
					numIntersections++;
				}
			}
		}
		if(numIntersections == 0){
			System.out.println(0);
			return;
		}
		for(int i = 0; i < numIntersections; i ++){
			MyLines irst = thoseThatX.get(i);
			pointsInt.add(irst.getIntersection());

		}
		Point2D[] print = sortPoints(pointsInt);
		System.out.println(print.length);
		for(int i = 0; i < print.length; i++){
			System.out.println(df.format(print[i].getX()) + " " + df.format(print[i].getY()));
		}
	}

	static Point2D[] sortPoints(ArrayList<Point2D> pI) {
		ArrayList<Point2D> temp = new ArrayList<Point2D>();
		for(int i = 0; i < pI.size();i++){
			if(!(pI.get(i).getX() != pI.get(i).getX())){
				Point2D testPoint = pI.get(i);
				double tempX = makeRound(testPoint.getX());	
				double tempY = makeRound(testPoint.getY());
				testPoint.setLocation(tempX, tempY);
				if(!(temp.contains(testPoint))){
					temp.add(testPoint);
				}
			}
		}

		Point2D[] rval = new Point2D[temp.size()];
		for(int i = 0; i < temp.size(); i++)
			rval[i] = temp.get(i);
		if(rval.length < 2)
			return rval;
		Arrays.sort(rval, new Comparator<Point2D>() {
			public int compare(Point2D a, Point2D b) {
				return (a.getX() < b.getX()) ? -1 : (a.getX() > b.getX()) ?
						1 : (a.getY() < b.getY()) ? -1 : (a.getY() > b.getY()) ? 1 : 0;
			}});
		return rval;
	}
	
	static double makeRound(double d){
		double rval = -99;
		rval = Math.round(d*100.0);
		rval/=100;
		return rval;
		
	}

	static class SortbyXthenY implements Comparator<Point2D> {
		public int compare(Point2D a, Point2D b) {
			return (a.getX() < b.getX()) ? -1 : (a.getX() > b.getX()) ?
					1 : (a.getY() < b.getY()) ? -1 : (a.getY() > b.getY()) ? 1 : 0;
		}
	}
	static class MyLines{
		Line2D lHere1;
		Line2D lHere2;
		double intersectionX;
		double intersectionY;
		Point2D intsx;
		double sl1;
		double sl2;
		double x1;
		double y1;
		double x2;
		double y2;
		double a1;
		double a2;
		double b1;
		double b2;
		double c1;
		double c2;

		public MyLines(Line2D tempL1, Line2D tempL2){

			lHere1 = tempL1;
			lHere2 = tempL2;
			sl1 =(lHere1.getY1()-lHere1.getY2())/(lHere1.getX1()-lHere1.getX2());
			sl2 =(lHere2.getY1()-lHere2.getY2())/(lHere2.getX1()-lHere2.getX2());
			x1 = lHere1.getX1();
			y1 = lHere1.getY1();
			x2 = lHere2.getX1();
			y2 = lHere2.getY1();
			a1 = -sl1;
			a2 = -sl2;
			b1 = 1;
			b2 = 1;
			c1 = y1-sl1*x1;
			c2 = y2-sl2*x2;
			intersectionX = -99;
			intersectionY = -99;
			this.isHorizontal();
			this.isVertical();	
			intsx = new Point2D.Double(intersectionX, intersectionY);
		}
		boolean isVertical(){
			if(lHere1.getX1() == lHere1.getX2()){
				intersectionX = lHere1.getX1();
				return true;
			}
			if(lHere2.getX1() == lHere2.getX2()){
				intersectionX = lHere2.getX1();
				return true;
			}
			return false;
		}
		boolean isHorizontal(){
			if(lHere1.getY1() == lHere1.getY2()){
				intersectionY = lHere1.getY1();
				return true;
			}
			if(lHere2.getY1() == lHere2.getY2()){
				intersectionY = lHere2.getY1();
				return true;
			}
			return false;
		}
		Point2D getIntersection(){
			if(this.isVertical()){
				if(intersectionX==lHere1.getX1()&& intersectionX==lHere1.getX2()){
					intersectionY = sl2*(intersectionX-lHere2.getX1())+lHere2.getY1();
				}
				else{
					intersectionY = sl1*(intersectionX-lHere1.getX1())+lHere1.getY1();
				}
			}
			intsx.setLocation(getX(), getY());
			return intsx;
		}

		public double getX() {
			if(intersectionX != -99)
				return Math.abs(intersectionX);
			double detTop = (c1*b2)-(c2*b1);
			double detBottom = (a1*b2)-(a2*b1);
			intersectionX = detTop/detBottom;
			return Math.abs(detTop/detBottom);
		}
		public double getY() {
			if(intersectionY != -99)
				return Math.abs(intersectionY);
			double detTop = (a1*c2)-(a2*c1);
			double detBottom = (a1*b2)-(a2*b1);
			intersectionY = detTop/detBottom;
			return Math.abs(detTop/detBottom);
		}
	}
}
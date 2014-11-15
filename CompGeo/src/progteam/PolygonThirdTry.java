package progteam;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Scanner;

class PolygonThirdTry {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		while(true){
			int pSize = scanner.nextInt();
			if(pSize == 0)
				return;
			Polygon p = new Polygon();
			Point2D t = new Point2D.Double();
			for(int i = 0; i <= pSize; i++){
				int xpoint = scanner.nextInt();
				int ypoint = scanner.nextInt();
				if(i == pSize)
					t.setLocation(xpoint, ypoint);
				else
					p.addPoint(xpoint, ypoint);
			}
			System.out.println(doNextProb(p,t));
		}	
	}

	private static char doNextProb(Polygon pl, Point2D pt) {
		int numCrosses = 0;
		if(pointOutsideBounds(pl, pt))
			return 'F';
		Rectangle rec = pl.getBounds();
		int deltaX = rec.width+2;
		Point2D testPoint = new Point2D.Double(pt.getX()+deltaX, pt.getY());
		Line2D testLine = new Line2D.Double(pt, testPoint);
		Line2D[] pLines = new Line2D[pl.npoints];
		ArrayList<Line2D> vLines = new ArrayList<Line2D>();
		Line2D tempLine;
		for(int i = 0; i < pl.npoints; i++){
			if(i == pl.npoints-1)
				tempLine = new Line2D.Double(pl.xpoints[i], pl.ypoints[i], pl.xpoints[0], pl.ypoints[0]);
			else
				tempLine = new Line2D.Double(pl.xpoints[i], pl.ypoints[i], pl.xpoints[i+1], pl.ypoints[i+1]);
			pLines[i] = tempLine;
		}
		for(int i = 0; i < pLines.length; i ++)
			if(isVertical(pLines[i]))
				if(pLines[i].getX1()>=pt.getX())
					vLines.add(pLines[i]);
		for(int i = 0; i < vLines.size();i++)
			if(testLine.intersectsLine(vLines.get(i)))
				numCrosses++;
		if(numCrosses%2 == 1)
			return 'T';
		return 'F';
	}

	static boolean isVertical(Line2D lin) {
		if(lin.getX1() == lin.getX2())
			return true;
		return false;
	}

	static boolean pointOutsideBounds(Polygon pl, Point2D pt) {
		Rectangle rec = pl.getBounds();
		if(pt.getX() > rec.getMaxX())
			return true;
		if(pt.getX() < rec.getMinX())
			return true;
		if(pt.getY()<rec.getMinY())
			return true;
		if(pt.getY()>rec.getMaxY())
			return true;
		return false;

	}
}


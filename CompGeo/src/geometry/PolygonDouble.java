package geometry;

import java.awt.Polygon;


public class PolygonDouble extends Polygon {
	double[] xpoints;
	double[] ypoints;	
	int npoints;
	
	public PolygonDouble(double[] arg0, double[] arg1) {
		xpoints = arg0;
		ypoints = arg1;
		npoints = arg0.length;
	}

}

package geometry3D;

public class LineSeg3D {
	public double x1, x2, y1, y2, z1, z2;
	public Point3D vecequiv;
	public LineSeg3D(Point3D beg, Point3D end){
		x1 = beg.x;
		x2 = end.x;
		y1 = beg.y;
		y2 = end.y;
		z1 = beg.z;
		z2 = end.z;
		vecequiv = new Point3D(x2-x1, y2-y1, z2-z1);
	}
	/*public Line3D(double ex1, double ex2, double why1, double why2, double zee1, double zee2){
		Point3D point1 = new Point3D(ex1, why1, zee1);
		Point3D point2 = new Point3D(ex2, why2, zee2);
		new Line3D(point1, point2);
		vecequiv = new Point3D(x2-x1, y2-y1, z2-z1);
	}*/
}

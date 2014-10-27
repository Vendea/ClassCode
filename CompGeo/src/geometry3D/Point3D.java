package geometry3D;

public class Point3D {
	public double x;
	public double y;
	public double z;
	
	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y; 
		this.z = z;
	}
	
	public double distToPlane(Plane3D plan){
		Vector pNorm = plan.normal;
		return Math.abs(this.x*pNorm.x + this.y*pNorm.y + this.z*pNorm.z)/pNorm.magnitude();
	}
	
	public Vector toVector(){
		return new Vector(this);
	}
	public static double area(
			double Ax, double Ay, double Az,
			double Bx, double By, double Bz,
			double Cx, double Cy, double Cz,
			double Dx, double Dy, double Dz){
		double retval = -99;
		Bx-=Ax; By-=Ay; Bz-=Az;
		Cx-=Ax; Cy-=Ay; Cz-=Az;
		Dx-=Ax; Dy-=Ay; Dz-=Az;
		double det1 = Bx*(Cy*Dz - Dy*Cz);
		double det2 = -By*(Cx*Dz - Dx*Cz);
		double det3 = Bz*(Cx*Dy - Dx*Cy);
		retval = det1+det2+det3;
		return retval;
	}
	public static double area(Point3D p1, Point3D p2, Point3D p3, Point3D p4){
		return area(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z, p3.x, p3.y, p3.z, p4.x, p4.y, p4.z);
	}
	public static int sigma(double Ax, double Ay, double Az,
			double Bx, double By, double Bz,
			double Cx, double Cy, double Cz,
			double Dx, double Dy, double Dz){
		double test = area(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Dx, Dy, Dz);
		return test == 0 ?0 : (test < 0 ? -1 : 1);
	}
	public static int sigma(Point3D p1, Point3D p2, Point3D p3, Point3D p4){
		return sigma(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z, p3.x, p3.y, p3.z, p4.x, p4.y, p4.z);
	}
	public boolean equals(Point3D p){
		return this.x == p.x && this.y == p.y && this.z == p.z;
	}
}

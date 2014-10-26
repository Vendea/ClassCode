package geometry3D;

public class LineInt3D {
	public static void main(String[] args){
	}
	
	public static double sigma(
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
	public static String Intersects(Triangle3D abc, LineSeg3D de){
		String retval = "";
		return retval;
	}
}

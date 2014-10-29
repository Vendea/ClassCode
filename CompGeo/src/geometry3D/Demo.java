package geometry3D;

public class Demo {

	public static void main(String[] args) {
		Vector C = new Vector(new Point3D(1,2,3), new Point3D(2,1,4));
		Vector D = new Vector(new Point3D(1,2,3), new Point3D(5,-1,3));
		Vector A = D.cross(C);
		System.out.println(""+A.x+","+A.y+","+A.z);
		System.out.println(A.theta(C));
		System.out.println(A.theta(D));
	}

}

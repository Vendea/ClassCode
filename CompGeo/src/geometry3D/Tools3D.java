package geometry3D;

import java.util.Scanner;

public class Tools3D {
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		Triangle3D t = readTriangle(scanner);
		int N     = scanner.nextInt();
		for(int j = 0; j < N; j++){
			Point3D p1 = readPoint(scanner);
			Point3D p2 = readPoint(scanner);
			System.out.println(Intersects(t, new LineSeg3D(p1, p2)));
		}
	}

	public static String Intersects(Triangle3D abc, LineSeg3D de){
		Point3D a = abc.a;
		Point3D b = abc.b;
		Point3D c = abc.c;
		Point3D d = de.beg;
		Point3D e = de.end;
		if(Point3D.sigma(a, b, c, d)==Point3D.sigma(a, b, c, e)){
			//if the segment and triangle are in the same plane
			if(Point3D.sigma(a, b, c, d) == 0){
				if(de.intersects(abc.A) || de.intersects(abc.B) || de.intersects(abc.C)){
					return "Pierces";
				}
				else
					return "Misses";
			}
			else{
				return "Misses";
			}
		}
		else{
			return resultsOfThisOtherFunction(abc,de);
		}
	}

	private static String resultsOfThisOtherFunction(Triangle3D abc, LineSeg3D de) {
		Plane3D A = new Plane3D(abc.A.vecequiv, de.vecequiv, abc.b);
		Plane3D B = new Plane3D(abc.B.vecequiv, de.vecequiv, abc.c);
		Plane3D C = new Plane3D(abc.C.vecequiv, de.vecequiv, abc.a);
		Point3D a = abc.a;
		Point3D b = abc.b;
		Point3D c = abc.c;
		double das = a.distToPlane(A);
		double dbs = b.distToPlane(B);
		double dcs = c.distToPlane(C);
		Point3D test = de.beg;
		double deA = test.distToPlane(A);
		double deB = test.distToPlane(B);
		double deC = test.distToPlane(C);
		
		if(A.contains(de) || B.contains(de) || C.contains(de)){
			if((A.contains(de)&&B.contains(de)) || (A.contains(de)&&C.contains(de)) || (C.contains(de)&&B.contains(de)) ){
				return "Nicks";
			}
			else{
				return "Shaves";
			}
		}
		else if(deA < das && deB < dbs && deC < dcs){
			return "Pierces";
		}
		else
			return "Misses";
	}
	static Triangle3D readTriangle(Object in){
		Triangle3D retVal = null;
		if(in instanceof Scanner){
			Scanner s = (Scanner)in;
			Point3D p1 = readPoint(s);
			Point3D p2 = readPoint(s);
			Point3D p3 = readPoint(s);
			retVal = new Triangle3D(p1, p2, p3);
		}
		return retVal;

	}
	static Point3D readPoint(Object in){
		Point3D retVal = null;
		if(in instanceof Scanner){
			Scanner s = (Scanner)in;
			double a = s.nextDouble();
			double b = s.nextDouble();
			double c = s.nextDouble();
			retVal = new Point3D(a, b, c);
		}
		return retVal;   
	}
}

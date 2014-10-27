package geometry3D;

import java.util.Arrays;
import java.util.Comparator;

public class Triangle3D {
	LineSeg3D A;
	LineSeg3D B;
	LineSeg3D C;
	Point3D a;
	Point3D b;
	Point3D c;
	//Plane3D infinity;
	
	public Triangle3D(LineSeg3D l1, LineSeg3D l2, LineSeg3D l3){
		this.A = l1;
		this.B = l2;
		this.C = l3;
		
		this.a = l1.beg.equals(l3.end) ? l3.beg : (l1.end.equals(l3.end) ? l3.beg : l3.end);
		this.b = l2.beg.equals(l3.end) ? l3.beg : (l2.end.equals(l3.end) ? l3.beg : l3.end);
		this.c = l3.beg.equals(l1.end) ? l1.beg : (l3.end.equals(l1.end) ? l1.beg : l1.end);
		//infinity = new Plane3D(l1, l2);
	}
	
	public Triangle3D(Point3D p1, Point3D p2, Point3D p3){
		this.A = new LineSeg3D(p1,p2);
		this.B = new LineSeg3D(p2,p3);
		this.C = new LineSeg3D(p3,p1);
		this.a = p3;
		this.b = p1;
		this.c = p2;
	}
	
	public Plane3D toPlane(){
		return new Plane3D(this.a, this.b, this.c);
	}
}

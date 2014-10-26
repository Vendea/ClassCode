package geometry3D;

public class Triangle3D {
	LineSeg3D l1;
	LineSeg3D l2;
	LineSeg3D l3;
	Plane3D infinity;
	public Triangle3D(LineSeg3D l1, LineSeg3D l2, LineSeg3D l3){
		this.l1 = l1;
		this.l2 = l2;
		this.l3 = l3;
		infinity = new Plane3D(l1, l2);
	}
}

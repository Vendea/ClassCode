package geometry3D;

public class Plane3D {
	Vector normal;
	Vector v1, v2;
	Point3D inP;
	
	public Plane3D(LineSeg3D l1, LineSeg3D l2){
		v1 = new Vector(l1);
		v2 = new Vector(l2);
		normal = v1.cross(v2);
		
		if(v1.equals(v2)){
			throw new Error("cannot create a plane from two equivalent vectors");
		}
		inP = l1.beg;
	}
	
	public Plane3D(Vector v1, Vector v2){
		if(v1.equals(v2)){
			throw new Error("cannot create a plane from two equivalent vectors");
		}
		this.v1 = v1;
		this.v2 = v2;
		normal = v1.cross(v2);
		this.inP = v1.toPoint3D();
	}
	
	public double distPoint(Point3D p){
		return Math.abs(v1.cross(v2).dot(p.toVector()))/v1.cross(v2).magnitude();
	}
	
	public Plane3D(Point3D point1, Point3D point2, Point3D point3){
		this.inP = point1;
		v1 = new Vector(point1, point2);
		v2 = new Vector( point2, point3);
		normal = v1.cross(v2);
	}
	
	public Plane3D(Vector v1, Vector v2, Point3D inP){
		if(v1.equals(v2)){
			throw new Error("cannot create a plane from two equivalent vectors");
		}
		this.v1 = v1;
		this.v2 = v2;
		normal = v1.cross(v2);
		this.inP = inP;
		
	}
	
	public Plane3D(LineSeg3D l1, LineSeg3D l2, Point3D... point3ds){
		
	}
	public boolean contains(Point3D p){
			return 0 == this.normal.z*(p.z - this.inP.z)+this.normal.y*(p.y - this.inP.y)+this.normal.x*(p.x - this.inP.x);
	}
	public boolean contains(LineSeg3D l){
		return this.contains(l.beg)&& this.contains(l.end);
	}
}

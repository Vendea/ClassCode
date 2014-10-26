package geometry3D;

public class Vector {
	double x,y,z;
	public Vector(Point3D one, Point3D two){
		this.x = two.x - one.x;
		this.y = two.y - one.y;
		this.z = two.z - one.z;
	}
	public Vector(double x,double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector cross(Vector v){
		double i = this.y*v.z - this.z - v.y;
		double j = -(this.x*v.z - this.z*v.x);
		double k = this.x*v.y - this.y*v.x;
		return new Vector(i,j,k);
	}
	public double dot(Vector v){
		return this.x*v.x + this.y*v.y + this.z*v.z;
	}
	public double magnitude(){
		return Math.sqrt(x*x+y*y+z*z);
	}
	public double theta(Vector v){
		return Math.acos(this.dot(v)/(this.magnitude()*v.magnitude()));
	}
}

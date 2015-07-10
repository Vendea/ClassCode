import java.awt.geom.Point2D;
import java.util.Scanner;


public class Vectors {
static Scanner sc = new Scanner(System.in); 
static Point2D rV = new Point2D.Double(0,0);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while(true)
			addVectors();
		
		
	}
	static void addVectors(){
		System.out.println("magnitude: ");
		double mag = sc.nextInt();
		System.out.println("angle: ");
		double ang = sc.nextInt()*(Math.PI/180);
		double xv = mag*Math.cos(ang);
		double yv = mag*Math.sin(ang);
		System.out.println("negative? 1 for yes");
		if(sc.nextInt() == 1){
			xv = -xv;
			yv = -yv;
		}
			
		System.out.println("x = "+xv+" y = "+yv);
		rV.setLocation(rV.getX()+xv, rV.getY()+yv);
		System.out.println("x = "+rV.getX()+" y = "+rV.getY());
		double magrv = Math.sqrt(rV.getX()*rV.getX()+rV.getY()*rV.getY());
		System.out.println("mag2 = " + magrv*magrv);
		System.out.println("magnitude = " + magrv);
		double angrv = (180/Math.PI)*Math.atan(rV.getY()/rV.getX());
		if(rV.getX() <0 && rV.getY() < 0)
			angrv = -angrv-90;
		if(rV.getX() < 0 && rV.getY() > 0)
			angrv +=90;
		if(rV.getX()>0 && rV.getY()<0)
			angrv = -angrv;
		System.out.println("angle to pos x-axis = "+angrv);
		return;
	}

}

package geo;

import java.util.Scanner;

/**
 * This class contains static methods that can be useful
 * when doing 3D geometry.
 *
 */
public class ClassTools3D {
    
    /**
     * You can use "main" for testing your code.
     */
    public static void main(String[] args){
        // This test reads:
        // *  Nine ints giving the vertices (a,b,c),(d,e,f),(g,h,i) of a triangle
        // *  A number N giving the number of segments that follow
        // *  N lines of six ints each, the endpoints (p,q,r),(s,t,u) of the segments
        // For each segment it says whether it pierces, shaves, nicks or misses the triangle
        Scanner scanner = new Scanner(System.in);
        ClassTriangle3D t = readTriangle(scanner);
        int N     = scanner.nextInt();
        for(int j = 0; j < N; j++){
            ClassPoint3D p1 = readPoint(scanner);
            ClassPoint3D p2 = readPoint(scanner);
            System.out.println(segTriIntersection(t, p1, p2));
        }
    }

    /**
     * Solves the 3D triangle-segment intersection problem
     * @param t        A Triangle3D
     * @param A     A Point3D
     * @param B     A Point3D
     * @return     Checks whether segment AB intersects triangle t.
     *                 "Pierces" means that the segment intersects the interior of the triangle
     *                 "Shaves" means that the segment intersects an edge of the triangle
     *                 "Nicks" means that the segment intersects a vertex of the triangle
     *                 "Misses" means it misses.
     */
    public static String segTriIntersection(ClassTriangle3D t, ClassPoint3D A, ClassPoint3D B){
        // Your code here
        return "Your code here";        
    }
    
    /**
     * Reads one Point3D object from the given Scanner object
     *
     * @param in The input source. Right now, needs to be a Scanner
     * @return   A point read from s
     */
    static ClassPoint3D readPoint(Object in){
        ClassPoint3D retVal = null;
        if(in instanceof Scanner){
            Scanner s = (Scanner)in;
            double a = s.nextDouble();
            double b = s.nextDouble();
            double c = s.nextDouble();
            retVal = new ClassPoint3D(a, b, c);
        }
        return retVal;
        
    }
    
    /**
     *
     * @param   in The input source. Right now, needs to be a Scanner
     * @return     A triangle read from in.
     */
    static ClassTriangle3D readTriangle(Object in){
        ClassTriangle3D retVal = null;
        if(in instanceof Scanner){
            Scanner s = (Scanner)in;
            ClassPoint3D p1 = readPoint(s);
            ClassPoint3D p2 = readPoint(s);
            ClassPoint3D p3 = readPoint(s);
            retVal = new ClassTriangle3D(p1, p2, p3);
        }
        return retVal;
        
    }
    

    /**
     * Gives the value of the determinant obtained from four points. From this value,
     * one could obtain the orientation of the four points in space, and the volume
     * of their tetrahedron.
     * @param A A Point3D
     * @param B A Point3D
     * @param C A Point3D
     * @param D A Point3D
     * @return The determinant of the 3x3 matrix whose rows are B-A, C-A, D-A.
     *
     *  The return value will be six times the volume of the tetrahedron.
     *  If the return value is 0, then the points are co-planar.
     *  A positive return value indicates that vertices of the triangle ABC
     *  will appear in counter-clockwise order, as seen from point D.
     */
    public static double sigmaVal(ClassPoint3D A, ClassPoint3D B, ClassPoint3D C, ClassPoint3D D){
        ClassPoint3D b = B.multAndAdd(-1, A);
        ClassPoint3D c = C.multAndAdd(-1, A);
        ClassPoint3D d = D.multAndAdd(-1, A);
        
        return b.x*c.y*d.z + b.y*c.z*d.x + b.z*c.x*d.y -
                b.z*c.y*d.x - b.y*c.x*d.z - b.x*c.z*d.y;
    }
}

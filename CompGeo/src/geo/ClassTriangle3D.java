package geo;

import java.awt.Polygon;


/**
 * Holds points of a triangle in 3D.
 * The points are always in counter-clockwise order
 * as seen from the "outside" of the triangle's object,
 * if it has an outside, and an object.
 */

public class ClassTriangle3D {
    ClassPoint3D[] points;        // Vertices of the triangle
    ClassTriangle3D[] triangles; // Neighbor triangles. triangles[0] is on edge 0-1.
    
    /**
     * Constructor
     * Constructs a Triangle3D having the three given points as vertices
     * @param p0  first vertex
     * @param p1  second vertex
     * @param p2  third vertex, going counter-clockwise around the triangle as seen from the "outside"
     */
    public ClassTriangle3D(ClassPoint3D p0, ClassPoint3D p1, ClassPoint3D p2){
        points = new ClassPoint3D[3];
        points[0] = p0;
        points[1] = p1;
        points[2] = p2;
    }
    
    /**
     * Prints a triple of the triangle's points.
     */
    public String toString(){
        return "Triangle: (" + points[0].toString() + ", " +
                points[1].toString() + ", " + points[2].toString() + ")";
    }
    
    /**
     * Translates the triangle by adding point d to all three vertices
     * @param d  the displacement vector
     * @return
     */
    public ClassTriangle3D add(ClassPoint3D d){
        return new ClassTriangle3D(points[0].add(d), points[1].add(d), points[2].add(d));
    }
}
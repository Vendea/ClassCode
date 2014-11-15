package geo;


public class ClassPoint3D {
   double x, y, z;        
   
   public ClassPoint3D(double xx, double yy, double zz){
       x = xx;
       y = yy;
       z = zz;
   }
   
   public ClassPoint3D(double[] coords){
       x = coords[0];
       y = coords[1];
       z = coords[2];
   }
   
   double[] getCoords(){
       double[] rv = new double[4];
       rv[0] = x;
       rv[1] = y;
       rv[2] = z;
       rv[3] = 1;
       return rv;
   }
   
   public ClassPoint3D add(ClassPoint3D p){
       return new ClassPoint3D(x + p.x, y + p.y, z + p.z);
   }
   
   public ClassPoint3D multAndAdd(double factor, ClassPoint3D p){
       return new ClassPoint3D(x + factor*p.x, y + factor*p.y, z + factor*p.z);
   }
   
   public String toString(){
       return String.format("(%.3f,  %.3f, %.3f, 1)", x, y, z);
   }
}

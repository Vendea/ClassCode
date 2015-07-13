package ds1;
public class HW2q3{

// compile-time error fixed: capitalized "string"
    public static void main(String[] args){
      double a = 5.0;
      double b = 12.0;

// compile-time error fixed: int required, double found
      double c = 0.0;
      System.out.println("I have a right-triangular garden.");

// compile-time error fixed: added ";" to the end of line
      System.out.println("The west wall has length " + a + " yards.");
      System.out.println("The south wall has length " + b + " yards.");

// run-time error fixed: method does not give correct value for c
      c = Math.sqrt(a*a + b*b);
      System.out.println("The northeast wall will have length " + c + " yards.");
    }
}
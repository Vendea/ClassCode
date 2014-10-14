package ds1;

import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class HashMapDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HashMap<String, Integer> x = new HashMap<String, Integer>();
        Scanner scanner = new Scanner(System.in);
        String name;
        int grade;
        do{
            do{
                System.out.print("Name: ");
                name = scanner.next(); // read a name, no spaces allowed
                // check to see if name is already in the hashmap
                if(x.containsKey(name)){
                    System.out.println("Already in there");
                }
            } while(x.containsKey(name));
            
            System.out.print("Grade: ");
            grade = scanner.nextInt();
            //x[name] = grade; // Not that simple in Java. (Works in Python!)
            x.put(name, grade);
        } while(!name.equals("done"));
    }
}
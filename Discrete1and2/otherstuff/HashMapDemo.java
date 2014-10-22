package otherstuff;

import java.util.HashMap;
import java.util.Scanner;

public class HashMapDemo {

	public static void main(String[] args) {
		HashMap<String, Integer> x = new HashMap<String, Integer>();
		Scanner scanner = new Scanner(System.in);
		String name;
		int grade;
		do{
			do{
				System.out.print("Name: ");
				name = scanner.next();
				if(x.containsKey(name)){
					System.out.println("Already in there");
				}
				
			}while(x.containsKey(name));
			System.out.print("Grade: ");
			grade = scanner.nextInt();
			x.put(name,  grade);
		}while(!name.equals("done"));
	}
}

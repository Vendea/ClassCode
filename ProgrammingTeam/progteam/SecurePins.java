package progteam;
import java.util.Scanner;
//methods return true from for loops, but if not, then return false

public class SecurePins{

	static String stringPin;
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args){
	
		int N = scanner.nextInt();
		for(int i = 0; i < N; i++){
			solveNextProblem();			
		}
		
	}
	
	public static void solveNextProblem(){
		stringPin = scanner.next();
		if(areSame() == true || areConsecutive() == true)
			System.out.println("WEAK");
		else
			System.out.println("ACCEPTABLE");
	}
	
	public static boolean areConsecutive(){											
		for(int i = 1; i <8; i++){					
			int compare = i*100 + (i+1)*10 + (i+2); 
			if(stringPin.contains(Integer.toString(compare)) || stringPin.contains("012"))
				return true;
		}
		for(int i = 9; i >1; i--){
			int compare = i*100 + (i-1)*10 + (i-2);
			if(stringPin.contains(Integer.toString(compare)))
				return true;
		}
		//takes care of exception for pins starting with "012"
		//if(stringPin.length() <6 && stringPin.charAt(0) == '1' && stringPin.charAt(1) == '2')
			//return true;
		
		return false;
	}
	
	public static boolean areSame(){
		for(int i = 0; i < 10; i++){
		//reset numTimes to zero before testing each int
			int numTimes = 0;
			for(int j = 0; j < stringPin.length(); j++){
				if(stringPin.charAt(j) == (char) i+48)
					numTimes++;
				if(numTimes > 2)
					return true;
			}
		}
		return false;
	}
	
}
//I don't know which column number we wanted to use, so
//I was thinking of using a recursive method to find 
//the col number nearest the number we're trying
//to find the binary representation of.  
package progteam;
import java.util.Scanner;

public class Binary{
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args){

		while(scanner.hasNextLong()){
			solveNextProblem();
		}
	}
	static long sum = 0;
	public static void solveNextProblem(){

		long floor = scanner.nextLong();
		long ceiling = scanner.nextLong();
		
		//in case the numbers are negative, exit method
		if(floor < 0 || ceiling < 0){
			return;
		}
		
		int ceilColNum;
		int floorColNum;
		if(ceiling < 9){
			ceilColNum = 2;
		}
		else{
			ceilColNum = 8;
		}
		
		if(floor < 9){
			floorColNum = 1;
		}
		else{
			floorColNum = 8;
		}
		
		/*public long findColNum(){
			
			long newLong = 1;
			if(newLong/2 > 1){
				
			}
			return colNum;
		}*/
		long sum = 	
		(ceiling / (ceilColNum * 2) * ceilColNum + (ceiling % (ceilColNum * 2) - ceilColNum) +1) -
		(floor / (floorColNum * 2) * floorColNum + (floor % (floorColNum * 2) - floorColNum) +1);
		
		System.out.println(sum);
	}
}

/*n/(col*2)*col+g(n%(col*2)-col)+1
g(x) = {x if x>0 }
	   {0 if x<=0}*/

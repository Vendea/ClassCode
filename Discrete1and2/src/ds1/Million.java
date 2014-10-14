package ds1;

public class Million {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int x = 3;
		String print = "";
		int y = 1;
		while(x <= 1000000){
			print += x +",";
			x*=2;
			y++;
			
		}
		System.out.println(y);
		System.out.println(print);
		System.out.println("loop two");
		print = "";
		x = 3;
		for(int i = 0; i < 1000000; i +=(x/2)){
			print += x+",";
			x*=2;
		}
		System.out.println(print);
	}

}

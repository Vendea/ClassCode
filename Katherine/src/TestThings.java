
public class TestThings {
	
	public static void main(String[] args) {
		char[] nums = "0 1 2 3 4".toCharArray();
		System.out.println(nums);
		System.out.println(nums[0] == '0');
		for(int i = 0;2*i < nums.length; i++){
			System.out.println((int)nums[2*i] - 48);
		}
		System.out.println(gcd(75, 25));
		System.out.println(5%10);
		System.out.println(-4);
		char[] a = new char[10];
		a[1] = '4';
		a[9] = '2';
		//System.out.println(a[0].isNull());
		System.out.println(a);
	}
	
	private static int gcd(int a, int b){
		if(a == 0){
			return b;
		}
		if(b == 0){
			return a;
		}
		return gcd(b, a % b);
	}

}

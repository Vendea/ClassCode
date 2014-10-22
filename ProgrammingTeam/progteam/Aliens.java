package progteam;

import java.util.HashMap;
import java.util.Scanner;

public class Aliens {
	static Scanner scanner;
	
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		while(doNextProblem());
	}
	
	static boolean doNextProblem(){
		boolean f;
		int mTimes = scanner.nextInt();
		if (mTimes == 0)
			return false;
		
		String sThing = scanner.next();
		
		//ArrayList<String> matches = findIt(mTimes, sThing);
		int upper = sThing.length();
		int lower = 0;
		int longest = 0;
		
		while(upper > lower+1){
			int midpoint = (upper+lower)/2;
			f = check(mTimes, sThing, midpoint);
			if(f)
				lower = midpoint;
			else
				upper = midpoint;
		}
		//now upper = lower+1 = length of the longest string
		f = check(mTimes, sThing, upper);
		if(f)
			longest = upper;
		else
			longest = lower;
		
		
		if(longest == 0)
			System.out.println("none");
		else{
			String s = findStringLongestTimes(mTimes, sThing, longest);
			System.out.println("" + longest + " " + sThing.lastIndexOf(s));
		}
		return true;
	}
	
	static boolean check(int mTimes, String sThing, int length){
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		for(int i = 0; i + length <= sThing.length(); i++){
			String key = sThing.substring(i, i+length);
			//if the key already has an entry in the map, add 1
			//otherwise, create new entry with value 1
			if(m.containsKey(key)){
				int oldValue = m.get(key);
				m.put(key, oldValue+1);
			}
			else{
				m.put(key, 1);
			}
		}
		for(Integer i : m.values()){
			if(i >= mTimes)
				return true;
		}
		return false;
	}

	
	static String findStringLongestTimes(int mTimes, String sThing, int longest){
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		for(int i = 0; i + longest <= sThing.length(); i++){
			String key = sThing.substring(i, i+longest);
			//if the key already has an entry in the map, add 1
			//otherwise, create new entry with value 1
			if(m.containsKey(key)){
				int oldValue = m.get(key);
				m.put(key, oldValue+1);
			}
			else{
				m.put(key, 1);
			}
		}
		for(String s : m.keySet()){
			if(m.get(s) >= mTimes)
				return s;
		}
		return null;
	}
}

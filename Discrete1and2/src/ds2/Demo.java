package ds2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//import ds1.Game.DSGameNode;

@SuppressWarnings("unused")
public class Demo{

	public static void main(String[] args){
		DSHashMap l = new DSHashMap();
		l.put("Hi", "Bye");
		System.out.println(l.numAdds);
		l.put("try", "fail");
		System.out.println(l.numAdds);
		l.put("one", "two");
		System.out.println(l.numAdds);
		l.put("four", "five");
		System.out.println(l.numAdds);
		l.put("a", "b");
		System.out.println(l.numAdds);
		System.out.println(l.arraySize);
		//System.out.println(""+l.get("Hi"));
		l.delete("a");
		System.out.println(l.numAdds);
		System.out.println();
	}
}
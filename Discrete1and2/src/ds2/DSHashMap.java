package ds2;

import ds1.DSArrayList;

public class DSHashMap {
	static int arraySize;
	static DSArrayList<DSArrayList<Pair>> theArray;
	static int maxChainSize;
	static int numAdds = 0;

	public DSHashMap(){
		arraySize = 7001;
		theArray = new DSArrayList<DSArrayList<Pair>>(arraySize);
		maxChainSize = 0; // For Testing
	}

	private int hashFunction(String s){
		int hv = 0;
		for(int i = 0; i < s.length(); i++){
			hv = (hv * 128 + (int)(s.charAt(i))) % arraySize;
		}
		return hv;
	}

	private DSArrayList<DSArrayList<Pair>> rehash() {
		int nP = nextPrime(arraySize);
		DSArrayList<DSArrayList<Pair>> temparr = new DSArrayList<DSArrayList<Pair>>(nP);
		for(int i = 0; i < theArray.size();i++){
			DSArrayList<Pair> ta = theArray.get(i);
			if(temparr.get(i)==null){
				temparr.put(i, ta);
			}
			else{
				temparr.get(i).append(ta);
			}			
		}
		arraySize = nP;
		return temparr;
	}

	int nextPrime(int min){
		for(int i = min+2; true; i+=2){
			if(isPrime(i))
				return i;
		}
	}


	boolean isPrime(int n) {
		for(int i = 2; i*i<=n; i++){
			if(n%i == 0)
				return false;
		}
		return true;
	}



	void put(String key, String value){
		int hashVal = hashFunction(key);
		if(numAdds>(theArray.size()/2)){
			theArray = rehash();
		}
		if(theArray.get(hashVal) == null){
			DSArrayList<Pair> DSAL = new DSArrayList<Pair>();
			DSAL.add(new Pair(key, value));
			theArray.put(hashVal, DSAL);
		} else {
			theArray.get(hashVal).add(new Pair(key, value));
		}

		// For Testing
		if(theArray.get(hashVal).size() > maxChainSize){
			maxChainSize = theArray.get(hashVal).size();
			System.out.println("We have a chain of size " + maxChainSize);
		}
		numAdds++;
	}

	String delete(String key){
		int hashVal = hashFunction(key);
		theArray.quickDelete(hashVal);
		numAdds--;
		return key;
	}
	
	Object get(String key){
		int hashVal = hashFunction(key);
		DSArrayList<Pair> DSAL = theArray.get(hashVal);
		for(Pair p : DSAL){
			if(p.key.equals(key)){
				return p.value;
			}
		}
		return null;
	}

	class Pair{
		public Pair(String key, String value){
			this.key = key;
			this.value = value;
		}

		String key;
		String value;
	}
} 
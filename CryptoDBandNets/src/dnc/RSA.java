package dnc;

import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {

    static BigInteger TWO = new BigInteger("2");
    public static void main(String[] args) {
    	//System.out.println(factor1(new BigInteger("5612592691460157028488501867482051458587407740355339131559497670427820545466264932268319464911726122036348856881906213034456112079790167")));
    	//System.out.println(factor2(new BigInteger("4203186347211155545407759403310287944739745105712280161537630386886194466890027616072541031930080350590569461440145611070067668431339809")));
    	//System.out.println(factor2(new BigInteger("12393740938979106085270960952777498451581397764755964574512615956658333154690831168855730082381367054663701968030817652770409915261551093300252139673702939609824212197857780837597650223306004227756966151861628501")));
    	//BigInteger temp = factor2(new BigInteger("6138471350288287595828596354472336799310944949"));
    	//System.out.println(temp);
    	//BigInteger n = new BigInteger("6138471350288287595828596354472336799310944949");
    	BigInteger en1 = new BigInteger("3198146490948181");
    	BigInteger en2 = new BigInteger("5318431920345616794413444545267");
    	BigInteger en3 = new BigInteger("6138471350288287595828596354472336799310944949");
    	BigInteger en4 = new BigInteger("7339520327860714514013398644733827882176683970668868528312313");
    	BigInteger en5 = new BigInteger("5437601359923935907138990951748213431162192715637897279001353486200853304833");
    	BigInteger p = new BigInteger("2148485760663500847015661664707");
    	BigInteger q = new BigInteger("3416136360891731088336715347859");	
    	BigInteger phi = phi(p,q);
    	System.out.println(phi);
    	BigInteger e = new BigInteger("5");
    	System.out.println(e.modInverse(phi));
    	//for this number 3367524723136520599574313651967471262880889111291270088046119
    	// factor a 1307632717511110048622297958011
    	// factor b 2575283317739339966230262805829
    }
    
    static BigInteger phi(BigInteger ...args){
    	BigInteger product = BigInteger.ONE;
    	for(int i = 0; i < args.length; i++){
    		product = product.multiply(args[i].subtract(BigInteger.ONE));
    	}
    	return product;
    }

    /**
     * Find the floor of the square root of a BigInteger
     * @param n The number we wish to find the
     * @return
     */
    static BigInteger BIsqrt(BigInteger n){
        BigInteger low = BigInteger.ZERO;
        BigInteger high = n.add(BigInteger.ONE);

        while(high.subtract(low).compareTo(new BigInteger("1")) > 0){
            BigInteger mid = low.add(high).divide(TWO);
            BigInteger sqr = mid.multiply(mid);
            if(sqr.compareTo(n) <= 0)
                low = mid;
            else
                high = mid;
        }
        return low;
    }
    
    /**
     * factors a large pseudo-prime
     * finds the sqrt and then searches all odd numbers > sqrt(n)
     * @param n the pseudo-prime to factor
     * @return one of the factors of n
     */
    static BigInteger factor1(BigInteger n){
    	BigInteger sqrt = BIsqrt(n);
    	if(sqrt.mod(TWO).compareTo(BigInteger.ZERO) == 0){
    		sqrt = sqrt.add(BigInteger.ONE);
    	}
    	while(n.mod(sqrt).compareTo(BigInteger.ZERO) != 0 ){
    		sqrt = sqrt.add(TWO);
    	}
    	return sqrt;    	
    }
    
    /**
     * factors a large pseudo-prime
     * using the expression of n as difference of two squares
     * @param n the pseudo-prime to factor
     * @return one of the factors of n
     */
    static BigInteger factor2(BigInteger n){
    	BigInteger sqrt = BIsqrt(n);
    	while(!isBISquare(sqrt.multiply(sqrt).subtract(n))){
    		sqrt = sqrt.add(BigInteger.ONE);
    	}
    	BigInteger r = BIsqrt(sqrt.multiply(sqrt).subtract(n));
    	return sqrt.subtract(r);
    }
    
    /**
     * factors a large pseudo-prime
     * using the expression of n as difference of two squares
     * @param n the pseudo-prime to factor
     * @return one of the factors of n
     */
    static BigInteger factor2_test(BigInteger n){
    	BigInteger sqrt = BIsqrt(n);
    	if(sqrt.mod(TWO).compareTo(BigInteger.ONE) == 0)
    		sqrt = sqrt.add(BigInteger.ONE);
    	while(!isBISquare(sqrt.multiply(sqrt).subtract(n))){
    		sqrt = sqrt.add(TWO);
    	}
    	BigInteger r = BIsqrt(sqrt.multiply(sqrt).subtract(n));
    	return sqrt.subtract(r);
    }

    /**
     * Determines if the given integer is a perfect square
     * @param n The number to test
     * @return true if the number is a perfect square
     */
    static boolean isBISquare(BigInteger n){
        BigInteger sqrt = BIsqrt(n);
        return (sqrt.multiply(sqrt).compareTo(n) == 0);
    }
    /**
     * Creates a number that is the product of two primes
     * @param numBits The desired number of bits in the product
     * @param logDifference The desired number of bits in the difference between the primes
     * @return A BigInteger that is the product of two primes
     */
    static BigInteger makeProduct(int numBits, int logDifference){
        // Build the first factor
        BigInteger f1 = BigInteger.ZERO.setBit(numBits / 2);
        for(int i = 0; i < numBits/2; i++)
            if(Math.random() < 0.5)
                f1 = f1.setBit(i);
        f1 = f1.nextProbablePrime();

        // Build the difference
        BigInteger diff = BigInteger.ZERO.setBit(logDifference);
        for(int i = 0; i < logDifference/2; i++)
            if(Math.random() < 0.5)
                diff = diff.setBit(i);
        BigInteger f2 = f1.add(diff).nextProbablePrime();
        System.out.println(f1);
        System.out.println(f2);

        // Create and return their product
        return f1.multiply(f2);
    }

    static ArrayList<Integer> factorSmooth(BigInteger n, long limit){
        ArrayList<Integer> factors = new ArrayList<Integer>();

        BigInteger bilimit = BigInteger.valueOf(limit);
        BigInteger div = new BigInteger("3");
        while(div.compareTo(bilimit) < 0){
            if(n.mod(div).compareTo(BigInteger.ZERO) == 0){
                factors.add(div.intValue());
                n = n.divide(div);
            } else {
                div = div.add(TWO);
            }
        }
        factors.add(n.intValue());
        return factors;
    }

    static void fermatFactor(BigInteger n){
        long limit = 2000;
        BigInteger sq = BIsqrt(n);

        for(int i = 1; i < 1000000; i++){
            BigInteger target = sq.add(BigInteger.valueOf(i)).modPow(TWO, n);
            ArrayList<Integer> facs = factorSmooth(target, limit);
            if(facs.get(facs.size()-1) == 1){
                System.out.println(i);
                for(int j : facs) System.out.print(j + " ");
                System.out.println("");
            }
        }
    }
}
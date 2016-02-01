import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Comap {
	static double TOKELVIN = 273.15;
	static double EMISSIVITY = 0.998;
	static double STEFANC = 5.6703 * Math.pow(10, -8);
	static double HEATEVAP = 2257000; // J / kg
	static double COEVAP = 25/3600; //evaporation coefficient kg / m*m*s
	static double MAXHUMID = .065; //kg / kg 
	static double HUMIDRATIO = 0.0098; // kg / kg from Mollier diagram
	static double AVGHEIGHT = 1.65; // meters average human height
	static double AVGLEG = AVGHEIGHT * 0.75; // meters average leg length 
	static double AVGTORSO = AVGHEIGHT * 0.25; // meters average torso length
	static double AVGSA = 1.75; // square meters avg human surface area (includes head & neck)
	static double AVGVOL = 0.00771; // 	cubic meters avg volume of a human
	static double HUMWIDTH = 0.3; //meters body average width (across legs & torso)
	static double HUMDEPTH = 0.0625; //meters body depth (through torso, leg thickness)
	static double HUMTEMP = 37; // deg C
	static double SPECHEATWAT = 4186000; //Joules per gram deg C times a million for unit conversions
	static double TNOUGHT = 40;
	static double width = 0.6; // width of tub also x
	static double length = 1.5; // length of tub also y
	static double height = AVGTORSO; // height of tub also z maximum depth of tub, aka height of overflow drain
	static double thickness = 0.1; //meters thickness of tub
	static double deltaK = 0.58; // Watts / meter Kelvin - for water
	static double kTub = 0.19; // Watts / meter Kelvins - for tub
	static double tubLength = 0.10;
	static double timeStep = 0.1;
	static double ambientTemp = 25;
	static double startingWheight = 0.25;// meters starting water height
	static double timeAddWater = 0;
	static double inflowRate = 0; //cubic meters per second
	static double inflowTemp = 70;
	static HashMap<String, Double> interactions = new HashMap<String, Double>();
	static double[][][] temps;
	static Scanner sc;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		sc = new Scanner(System.in);
		System.out.println("number of cubes to use");
		int numCubes = sc.nextInt();
		double cS = cSide(numCubes);
		int x = (int) Math.ceil(width/cS);
		int y = (int) Math.ceil(length/cS);
		int z = (int) Math.ceil(height/cS);
		int leg = (int) Math.ceil(AVGLEG/cS); 
		int w = (int) Math.ceil(HUMWIDTH/cS);
		int d = (int) Math.ceil(HUMDEPTH/cS);
		temps = new double[x][y][z];
		//fill array with initial temps
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				for (int k = 0; k < z; k++){

					if(i > (x-w)/2 && i < x-((x-w)/2)){
						if(j > (y-leg)/2){
							if(j < y-((y-leg)/2)){
								if(j > y-((y-leg)/2)-d){
									temps[i][j][k] = HUMTEMP;
								}
								else if(k < d){
									temps[i][j][k] = HUMTEMP;
								}
								else {
									temps[i][j][k] = TNOUGHT;
								}
							}
						}
						else {
							temps[i][j][k] = TNOUGHT;
						}
					}

					else{
						temps[i][j][k] = TNOUGHT;
					}
				}
			}
		}
		double time;
		System.out.println("enter time to run simulation");
		time = sc.nextDouble();
		runSimforTimeLength(time, cS);
		System.out.println("simulation done.  Saving data");
		generateCSV(temps);
	}

	private static void generateCSV(double[][][] t) throws FileNotFoundException, IOException {
		StringBuilder sb = new StringBuilder();
		File file = new File("C:\\Users\\KatherineMJB\\Documents\\GitHub\\ClassCode\\Katherine\\src","watersim.csv");
		FileWriter writer = new FileWriter(file);
		for (int i = 0; i < t.length; i++){
			for (int j = 0; j < t[0].length; j++){
				for (int k = 0; k < t[0][0].length; k++){
					sb.append(i);sb.append('\t');sb.append(j);sb.append('\t');sb.append(k);sb.append('\t');
					sb.append(t[i][j][k]);sb.append('\n');
				}
			}
		}
		writer.write(sb.toString());
		System.out.println("csv saved");
	}

	static void resetHuman(double cS){
		int x = (int) Math.ceil(width/cS);
		int y = (int) Math.ceil(length/cS);
		int z = (int) Math.ceil(height/cS);
		int leg = (int) Math.ceil(AVGLEG/cS); 
		int w = (int) Math.ceil(HUMWIDTH/cS);
		int d = (int) Math.ceil(HUMDEPTH/cS);

		for(int i = (x-w)/2; i < x-((x-w)/2); i++){
			for(int j = (y-leg)/2; j < y-((y-leg)/2); j++){
				while(j < y-((y-leg)/2)-d){
					for(int k = 0; k < d; k++){
						temps[i][j][k] = HUMTEMP;
					}
					for(int k = 0; k < z; k++){
						temps[i][j][k] = HUMTEMP;
					}
					j++;
				}
			}
		}			
	}

	static void resetInflow(int x, int y, int z, double cS){
		int mid = x / 2;
		int len = y / 4;
		int hei = z / 2;
		for(int k = hei; k < z; k++){
			temps[mid][len][k] = inflowTemp;
		}
	}


	static void runSimforTimeLength(double t, double cS){
		double ticker = 0;
		resetHuman(cS);
		int x = (int) Math.ceil(width/cS);
		int y = (int) Math.ceil(length/cS);
		int z = (int) Math.ceil(height/cS);
		while(ticker < t){
			if(ticker >= timeAddWater){
				resetInflow(x, y, z, cS);
			}
			getInteractions(x, y, z, cS);
			changeTemps(x, y, z, cS);
			System.out.println("" + ticker);
			ticker+=timeStep;
		}
	}

	static void changeTemps(int x, int y, int z, double cS) {
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				for (int k = 0; k < z; k++){
					//System.out.println((i-1)+"."+j+"."+k+"."+i+"."+j+"."+k);
					double delq = 
							interactions.get((i-1)+"."+j+"."+k+"."+i+"."+j+"."+k)+
							interactions.get(i+"."+(j-1)+"."+k+"."+i+"."+j+"."+k)+
							interactions.get(i+"."+j+"."+(k-1)+"."+i+"."+j+"."+k)-
							interactions.get(i+"."+j+"."+k+"."+(i+1)+"."+j+"."+k)-
							interactions.get(i+"."+j+"."+k+"."+i+"."+(j+1)+"."+k)-
							interactions.get(i+"."+j+"."+k+"."+i+"."+j+"."+(k+1));
					double delt = delq / (SPECHEATWAT * cS*cS*cS);
					temps[i][j][k] = temps[i][j][k] + delt;
				}
			}
		}
	}

	static void getInteractions(int x, int y, int z, double cS) {
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				for (int k = 0; k < z; k++){
					if(i == 0){
						interactions.put
						((i-1)+"."+j+"."+k+"."+i+"."+j+"."+k, 
								-1*cubeisTouchingTub(i, j, k, cS));
					}
					if(j == 0){
						interactions.put
						(i+"."+(j-1)+"."+k+"."+i+"."+j+"."+k, 
								-1*cubeisTouchingTub(i, j, k, cS));
					}
					if(k == 0){
						interactions.put
						(i+"."+j+"."+(k-1)+"."+i+"."+j+"."+k, 
								-1*cubeisTouchingTub(i, j, k, cS));
					}
					if(i+1 >= x){
						interactions.put
						(i+"."+j+"."+k+"."+(i+1)+"."+j+"."+k, 
								cubeisTouchingTub(i, j, k, cS));
					}else{	
						interactions.put
						(i+"."+j+"."+k+"."+(i+1)+"."+j+"."+k,
								deltaQ(i, j, k, i+1, j, k, cS));
					}
					if(j+1 >= y){
						interactions.put
						(i+"."+j+"."+k+"."+i+"."+(j+1)+"."+k, 
								cubeisTouchingTub(i, j, k, cS));
					} else {	
						interactions.put
						(i+"."+j+"."+k+"."+i+"."+(j+1)+"."+k,
								deltaQ(i, j, k, i, j+1, k, cS));
					}
					if(k+1 >= z){
						interactions.put
						(i+"."+j+"."+k+"."+i+"."+j+"."+(k+1),
								cubeisTouchingAir(i, j, k, cS));
					}else{
						interactions.put
						(i+"."+j+"."+k+"."+i+"."+j+"."+(k+1),
								deltaQ(i, j, k, i, j, k+1, cS));
					}
				}
			}
		}
	}
	static double cubeisTouchingTub(int xi, int yi, int zi, double cS){
		double surfaceArea = cS*cS;
		double deltaT = temps[xi][yi][zi] - ambientTemp;
		if(deltaT < 0.001) return 0;
		return kTub * surfaceArea * deltaT * timeStep / tubLength;
	}

	static double cubeisTouchingHuman(int x, int y, int z, double cS){
		return 0;
	}

	static double cubeisTouchingAir(int x, int y, int z, double cS){
		double radiation = 
				EMISSIVITY * STEFANC * cS*cS *
				(Math.pow(temps[x][y][z] + TOKELVIN, 4) - Math.pow(ambientTemp, 4));
		double evaporation = COEVAP * cS * cS * (MAXHUMID - HUMIDRATIO) * timeStep * HEATEVAP;
		return radiation + evaporation;
	}

	static double deltaQ(int c1x, int c1y, int c1z, int c2x, int c2y, int c2z, double cS){
		double t1 = temps[c1x][c1y][c1z];
		double t2 = temps[c2x][c2y][c2z];

		double deltaT = Math.abs(t1 - t2);
		if(deltaT < .001) return 0;
		return deltaK * cS * deltaT * timeStep;
	}

	static double getCubesPerSide(double sL, double cS){
		return sL / cS;
	}

	static double cSide(double numC){
		return Math.cbrt((height * width * length) / numC); 
	}	
}
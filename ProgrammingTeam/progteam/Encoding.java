package progteam;

import java.util.Scanner;

public class Encoding {
	static Scanner scanner = new Scanner(System.in);
	static int[][] matrix;
	static int dir;
	static int x=0;
	static int y=0;
	static int R;
	static int C;

	public static void main(String[] args){

		int probs = scanner.nextInt();

		for(int i = 0; i < probs; i++){
			x=0;
			y=0;
			dir=0;
			solveNextProblem(i+1);
		}

	}

	public static void solveNextProblem(int num){
		R = scanner.nextInt();
		C = scanner.nextInt();
		System.out.println(R);
		System.out.println(C);
		matrix = new int[R][C];
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				matrix[i][j] = -1;
			}
		}
		String msg = scanner.nextLine();
		msg=msg.trim();
		int l;
		for(int i = 0; i < msg.length(); i++){
			if(msg.charAt(i)==' '){
				l=0;
			}
			else{
				l=(int)(msg.charAt(i)-'A')+1;
			}
			insert((l & 16) >> 4);
			insert((l & 8) >> 3);
			insert((l & 4) >> 2);
			insert((l & 2) >> 1);
			insert((l & 1) >> 0);
		}
		System.out.print(num + " ");
		for(int j = 0; j < R; j++){
			for(int i = 0; i < C; i++){
				if(matrix[i][j]==-1){
					matrix[i][j]=0;
				}
				System.out.print(matrix[i][j]);
			}
		}
		System.out.println();
	}

	public static void insert(int letter){
		matrix[x][y] = letter;
		if(dir==0){
			if(x < R-1 && matrix[x+1][y]==-1){
				x++;
			} else {
				y++;
				dir = 1;
			}
		}
		else if(dir==1){
			if(y < C-1 && matrix[x][y+1]==-1){
				y++;
			} else {
				x--;
				dir = 2;
			}
		}
		else if(dir==2){
			if(x!=0 && matrix[x-1][y]==-1){
				x--;
			} else {
				y--;
				dir = 3;
			}
		} else {
			if(matrix[x][y-1]==-1){
				y--;
			} else {
				x++;
				dir = 0;
			}
		}
		
	}
}

package ds1;
public class Experiment1{

	public static void main(String[] args){
		
			//TwoPlayer.computerPlaySelf();
		
		int num1Wins = 0;
		int num2Wins = 0;
		int numDraws = 0;
		int numGames = 100;

		for(int i = 0; i < numGames; i++){
			Nim playSelf = new Nim(9,10,7);
			if(playSelf.endstate == TwoPlayer.PLAYER1WIN)
				num1Wins++;
			if(playSelf.endstate == TwoPlayer.PLAYER2WIN)
				num2Wins++;
			//if(playSelf.endstate == TwoPlayer.DRAW)
				//numDraws++;
		}
		System.out.println("Player one won " + num1Wins + " out of " + numGames);
		System.out.println("Player two won " + num2Wins + " out of " + numGames);
		System.out.println("Game was a draw " + numDraws + " out of " + numGames);
	//System.out.println(" ");
	}
}

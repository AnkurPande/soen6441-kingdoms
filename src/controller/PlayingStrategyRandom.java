package controller;

import java.util.ArrayList;
import java.util.Random;

import components.Castle;
/**
 * this class defines the game play strategy randomly
 * @author TEAM B
 *
 */
public class PlayingStrategyRandom implements PlayingStrategy {
	
	@Override
	/**
	 * using functions from game controller select and make move randomly 
	 */
	public void selectAndMakeMove(GameController gc) {
		/**
		 * creating array list to store possible moves
		 */
		int randomInt = new Random().nextInt(3);
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		possibleMoves.add(0);
		possibleMoves.add(1);
		possibleMoves.add(2);
		
		boolean moveAttempt = false;
		boolean loopCondition = true;
		/**
		 * this condition will make the possible move in the loop
		 */
		while(loopCondition){
			
			while(!possibleMoves.contains(randomInt)){
				randomInt = new Random().nextInt(3);
			}
			
			moveAttempt = makeMove(randomInt, gc);
			/**
			 * if condition attempts possible move in loop condition
			 */
			if(moveAttempt){
				loopCondition = false;
			}
			
			possibleMoves.remove((Integer)randomInt);
			/**
			 * if possible moves of size is greater or equal to 0 than loop condition will false
			 */
			if(possibleMoves.size() <= 0){
				loopCondition = false;
			}
		}
		/**
		 * if there will be no move attempt than it will display could not make any moves
		 */
		if(!moveAttempt){
			gc.getGame().gameActionLog += "Player " + gc.getGame().getCurrentPlayerIndex() + " could not make any moves - passing on to the next player.";
		}

	}
	/**
	 * this will make the move in the possible moves in vacant place in the game board
	 * @param moveNo number of move by players
	 * @param gc game controller object that instantiate the game controller class
	 * @return return the attempted move
	 */
	private boolean makeMove(int moveNo, GameController gc){
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean moveAttempt = false;
		switch(moveNo){
			case 0: moveAttempt = gc.drawAndPlaceTile(row, col);
					break;
			case 1: Castle.CastleRank nextAvailableCastleRank = gc.nextAvailableCastleRank(currentPlayerIndex);
					moveAttempt = gc.placeCastle(currentPlayerIndex, nextAvailableCastleRank, row, col);
					break;
			case 2: moveAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
					break;
			default:break;
		}
		
		return moveAttempt;
	}

}

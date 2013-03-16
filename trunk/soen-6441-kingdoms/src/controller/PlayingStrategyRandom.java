package controller;

import java.util.ArrayList;
import java.util.Random;

import components.Castle;

/**
 * The strategy makes the player choose random moves.
 * 
 * @author Team B
 *
 */
public class PlayingStrategyRandom implements PlayingStrategy {
	
	/**
	 * This method determines the move a player will make next.
	 */
	@Override
	public void selectAndMakeMove(GameController gc) {
		
		int randomInt = new Random().nextInt(3);
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		possibleMoves.add(0);
		possibleMoves.add(1);
		possibleMoves.add(2);
		
		boolean moveAttempt = false;
		boolean loopCondition = true;
		
		while(loopCondition){
			
			while(!possibleMoves.contains(randomInt)){
				randomInt = new Random().nextInt(3);
			}
			
			moveAttempt = makeMove(randomInt, gc);
			
			if(moveAttempt){
				loopCondition = false;
			}
			
			possibleMoves.remove((Integer)randomInt);
			
			if(possibleMoves.size() <= 0){
				loopCondition = false;
			}
		}
		
		if(!moveAttempt){
			gc.getGame().gameActionLog += "Player " + gc.getGame().getCurrentPlayerIndex() + " could not make any moves - passing on to the next player.";
		}

	}
	
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

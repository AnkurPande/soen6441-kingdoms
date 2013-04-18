package controller;

import java.util.Scanner;

import components.Castle;
/**
 * The strategy makes the human player choose the move and  place the tiles and castles on the game board.
 *  
 * @author Team B
 *
 */

public class PlayingStrategyHuman implements PlayingStrategy {
	
	private int selectedRowIndex = -1, selectedColIndex = -1;
	private int selectedMoveIndex = -1;
	private boolean selectedLocationValidAndVacant = false;

	/**
	 * This method determines the move a player will make next.
	 */
	@Override
	public void selectAndMakeMove(GameController gc) {
		
		boolean moveAttempt = false;
		
		do{
			selectLocation(gc);
			selectedLocationValidAndVacant = gc.isGameBoardPlaceValidAndVacant(this.selectedRowIndex, this.selectedColIndex);
			if(!selectedLocationValidAndVacant){
				System.out.println("The selected location is either not valid or not vacant! Please try again.");
			}
		}while(!selectedLocationValidAndVacant);
		
		do{
			selectMove();
			moveAttempt = makeMove(this.selectedMoveIndex, this.selectedRowIndex, this.selectedColIndex, gc);
			if(!moveAttempt){
				System.out.println("Move failed. Please try again!");
			}
		}while(!moveAttempt);
		
	}
	
	/**
	 * Select location on game board to where a move is to be made by user input.
	 * @param gc The game controller
	 */
	private void selectLocation(GameController gc){
		
		int maxColIndex = gc.getGame().gameBoard.length - 1;
		int maxRowIndex = gc.getGame().gameBoard[0].length - 1;
		int input;
						
	    Scanner sc = new Scanner(System.in);
	    
		do {
			System.out.print("Please enter the column index number (whole no between 0 and " + maxRowIndex + ") :");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please try again:");
				sc.next();
				System.out.print("Please enter the column index number (whole no between 0 and " + maxRowIndex + ") :");
			}
			
			input = sc.nextInt();
			
			if(input < 0 || input > maxRowIndex){
				System.out.println("Input must be within the specified range! Please try again:");
			}
			
		} while (input < 0 || input > maxRowIndex);
		
		this.selectedRowIndex = input;
		
		do {
			System.out.print("Please enter the row index number (whole no between 0 and " + maxColIndex + ") :");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please try again:");
				sc.next();
				System.out.print("Please enter the row index number (whole no between 0 and " + maxColIndex + ") :");
			}
			
			input = sc.nextInt();
			
			if(input < 0 || input > maxColIndex){
				System.out.println("Input must be within the specified range! Please try again:");
			}
			
		} while (input < 0 || input > maxColIndex);
		
		this.selectedColIndex = input;
	}
	
	/**
	 * Select move based on user input.
	 */
	private void selectMove(){
	
		int maxMoveIndex = 1;
		int input;
						
	    Scanner sc = new Scanner(System.in);
	    
		do {
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("Move 0 : Place Tile and Draw New. Move 1: Place Castle.");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.print("Please enter the move index number (whole no between 0 and " + maxMoveIndex + ") :");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please try again:");
				sc.next();
				System.out.print("Please enter the move index number (whole no between 0 and " + maxMoveIndex + ") :");
			}
			
			input = sc.nextInt();
			
			if(input < 0 || input > maxMoveIndex){
				System.out.println("Input must be within the specified range! Please try again:");
			}
			
		} while (input < 0 || input > maxMoveIndex);
		
		this.selectedMoveIndex = input;
	}
	
	/**
	 * 
	 * @param moveNo The index of the move to make.
	 * @param gc The GameController that is triggering the move.
	 * @return Returns true if the move was successful - otherwise returns false.
	 */
	private boolean makeMove(int moveNo, int rowIndex, int colIndex, GameController gc){
	
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean moveAttempt = false;
		switch(moveNo){
			case 0: displayPlayerTiles(gc,currentPlayerIndex);
					int selectedTileIndex = selectTileIndex(gc, currentPlayerIndex);
					moveAttempt = gc.placeTileAndDraw(rowIndex, colIndex, selectedTileIndex);
					break;
			case 1: displayPlayerCastles(gc, currentPlayerIndex);
					Castle.CastleRank selectedCastleRank = selectCastleRank();
					moveAttempt = gc.placeCastle(currentPlayerIndex, selectedCastleRank, rowIndex, colIndex);
					break;
			case 2: moveAttempt = gc.placeFirstTile(currentPlayerIndex, rowIndex, colIndex);
					break;
			default:break;
		}
		
		return moveAttempt;
	}
	
	/**
	 * Select the rank of the castle to place.
	 * @return Return the selected castle rank
	 */
	private Castle.CastleRank selectCastleRank(){
		
		Castle.CastleRank selectedCastleRank = null;
		int maxCastleRank = 4;
		int input;
		
		Scanner sc = new Scanner(System.in);

		do {
			System.out.print("Please enter the rank of the castle (whole no between 1 and " + maxCastleRank + ") :");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please try again:");
				sc.next();
				System.out.print("Please enter the rank of the castle (whole no between 1 and " + maxCastleRank + ") :");
			}

			input = sc.nextInt();

			if(input < 0 || input > maxCastleRank){
				System.out.println("Input must be within the specified range! Please try again:");
			}

		} while (input < 0 || input > maxCastleRank);
		
		selectedCastleRank = determineCastleRank(input);

		return 	selectedCastleRank;
	}
	
	/**
	 * Determine castle rank from the specified numeric input.
	 * @param castleRankNo The numeric castle rank no.
	 * @return The castle rank resolved from the numeric input.
	 */
	private Castle.CastleRank determineCastleRank(int castleRankNo){
		
		Castle.CastleRank selectedCastleRank = null;
		
		switch(castleRankNo){
			case 1: selectedCastleRank = Castle.CastleRank.ONE;
					break;
			case 2: selectedCastleRank = Castle.CastleRank.TWO;
					break;
			case 3: selectedCastleRank = Castle.CastleRank.THREE;
					break;
			case 4: selectedCastleRank = Castle.CastleRank.FOUR;
					break;
			default: break;
		}
		
		return 	selectedCastleRank;
	}
	
	/**
	 * Displays the players castles of the specified index.
	 * @param gc The Game Controller
	 * @param playerIndex The player index
	 */
	private void displayPlayerCastles(GameController gc, int playerIndex){
		
		System.out.println("Rank 1 castles: " + gc.getGame().players[playerIndex].rank1Castles.size());
		System.out.println("Rank 2 castles: " + gc.getGame().players[playerIndex].rank2Castles.size());
		System.out.println("Rank 3 castles: " + gc.getGame().players[playerIndex].rank3Castles.size());
		System.out.println("Rank 4 castles: " + gc.getGame().players[playerIndex].rank4Castles.size());
		
	}
	
	private void displayPlayerTiles(GameController gc, int playerIndex){
		int noOfTiles = gc.getGame().players[playerIndex].playerTiles.size();
		
		for(int i = 0 ; i < noOfTiles ; i++){
			System.out.println("Tile " + (i) + ": " + gc.getGame().players[playerIndex].playerTiles.elementAt(i).getType().toString() + "(" + gc.getGame().players[playerIndex].playerTiles.elementAt(i).getValue() + ")");
		}
	}
	
	private int selectTileIndex(GameController gc, int playerIndex){
		int selectedTileIndex = -1;
		int maxTileIndex = gc.getGame().players[playerIndex].playerTiles.size() - 1;
		
		int input;

		Scanner sc = new Scanner(System.in);

		do {
			System.out.print("Please enter the index of the tile to place (whole no between 0 and " + maxTileIndex + ") :");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please try again:");
				sc.next();
				System.out.print("Please enter the index of the tile to place (whole no between 0 and " + maxTileIndex + ") :");
			}

			input = sc.nextInt();

			if(input < 0 || input > maxTileIndex){
				System.out.println("Input must be within the specified range! Please try again:");
			}

		} while (input < 0 || input > maxTileIndex);
				
		return input;
	}

}

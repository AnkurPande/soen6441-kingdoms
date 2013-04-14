package controller;

import java.util.Random;
import java.util.Scanner;

import components.Castle;

public class PlayingStrategyHuman implements PlayingStrategy {
	
	private int selectedRowIndex = -1, selectedColIndex = -1;
	private int selectedMoveIndex = -1;
	private boolean selectedLocationValidAndVacant = false;

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
			case 0: //moveAttempt = gc.placeFirstTile(currentPlayerIndex, rowIndex, colIndex);
					moveAttempt = gc.placeTileAndDraw(rowIndex, colIndex);
					break;
			case 1: Castle.CastleRank selectedCastleRank = selectCastleRank();
					moveAttempt = gc.placeCastle(currentPlayerIndex, selectedCastleRank, rowIndex, colIndex);
					break;
			case 2: moveAttempt = gc.placeFirstTile(currentPlayerIndex, rowIndex, colIndex);
					break;
			default:break;
		}
		
		return moveAttempt;
	}
	
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

}

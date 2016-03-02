// Team 8
package com.team8.game;
public class Board {

	private int length;
	private int height;
	Block [][] board;
	
	
	public Board (int length, int height) { // most likely 6x14
		// Advice from a web site, the board will be 6x14, the first two rows are "ghost" rows containing the piece 
		// as it spawns
				
		this.board = new Block[height][length];
		this.length = length;
		this.height = height;
	}

	
	public boolean isGameOver() { // checks if the starting block position is blocked
		 return (board[2][2] != null);
	}
	
	
	public void print() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < length; ++j) {
				if (board[i][j] == null) {
					System.out.print("D ");
				}
				else {
					System.out.print(board[i][j].getColor() + " ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public void moveBlock(int currentLR, int currentUD, int newLR, int newUD) {
		if (currentLR == newLR && currentUD == newUD) {
			return;
		}
		Block temp = board[currentUD][currentLR];
		board[newUD][newLR] = temp;
		temp.setLR(newLR);
		temp.setUD(newUD);
		board[currentUD][currentLR] = null;
		
	}
	
	public void removeTopRow() {   // removes blocks on the top row. may allow for vanishing trick)
		for (int i = 0; i < 6; ++i) {
			board[0][i] = null;
		}
	} 
	
	public void destroyGroups() { // go through the block, destroy groups
		
	}
	
	public void allDrop() {
		int temp;
		for (int i = 0; i < length -1 ; ++i) {
			temp = height - 1;
			for (int j = height - 1; j > 0; --j) {
				if (board[j][i] != null) {
					moveBlock(i, j, i, temp);
					temp --;
				}
			}
		}
	}
	
	public int getLength() {
		return this.length;
	}
	public int getHeight() {
		return this.height;
	}

}

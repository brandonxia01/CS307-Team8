// Team 8

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
		if (board[2][2] == null) {
			return true;
		}
		return false;
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
		Block temp = board[currentUD][currentLR];
		board[newUD][newLR] = temp;
		temp.setLR(newLR);
		temp.setUD(newUD);
		board[currentUD][currentLR] = null;
		
	}
	
	public int getLength() {
		return this.length;
	}
	public int getHeight() {
		return this.height;
	}

}

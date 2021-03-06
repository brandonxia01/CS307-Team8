// Team 8
import java.util.*;

public class Board {

	private int length;
	private int height;
	Block [][] board;

	ArrayList <Integer[]> groups = new ArrayList<>();

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
	
	public void removeTopRow() {   // removes blocks on the top row. may allow for vanishing trick
		for (int i = 0; i < 6; ++i) {
			board[0][i] = null;
		}
	} 
	
	public boolean findGroups() { // go through the block, destroy groups
		boolean destroyed = false;
	
		
		for (int i = 0; i < 6; ++i) {
			for (int j = 1; j < 14; ++j) {
				if (board[j][i] != null) {
					searchGroup(i, j, board[j][i].getColor());
					if (groups.size() >= 4) {
						destroyed = true;
						for (int k = 0; k < groups.size(); ++k) {
							board[groups.get(k)[1]][groups.get(k)[0]] = null;
						}
					}
					groups.clear();
				}
			}
		}
		
		return destroyed;
	}

	public void searchGroup(int lr, int ud, int color) {
		if (board[ud][lr] == null) { return; }
		if (board[ud][lr].getColor() != color) { return; }
	
		for (int i = 0; i < groups.size(); ++i) {
			if (groups.get(i)[0] == lr && groups.get(i)[1] == ud) {
				return;
			}
		}
		groups.add(new Integer[] {lr,ud});
	
		if (lr > 0) { searchGroup(lr - 1, ud, color); }
		if (lr < length - 1) { searchGroup(lr + 1, ud, color); }
		if (ud > 0) { searchGroup(lr, ud - 1, color); }
		if (ud < height - 1) { searchGroup(lr, ud + 1, color); }
	
		
	}
	
	
	
	public void allDrop() {
		int temp;
		for (int i = 0; i < length -1 ; ++i) {
			temp = height - 1;
			for (int j = height - 1; j > 0; --j) {
				if (board[j][i] != null) {
					moveBlock(i, j, i, temp-- );
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

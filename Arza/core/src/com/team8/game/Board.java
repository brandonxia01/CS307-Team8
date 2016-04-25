// Team 8
package com.team8.game;
import java.util.*;

public class Board {

	private int length;
	private int height;
	public Block [][] board;
	public int score;
	public long elapsed;
	public long min;
	public long sec;
	public long startTime;
	public int offset;


	ArrayList <Integer[]> groups = new ArrayList<Integer[]>();

	public Board (int length, int height) { // most likely 6x14
		// Advice from a web site, the board will be 6x14, the first two rows are "ghost" rows containing the piece
		// as it spawns
		startTime = System.nanoTime();
		elapsed = 0;
		min = 0;
		sec = 0;
		score = 0;
		this.board = new Block[height][length];
		this.length = length;
		this.height = height;
	}

	public int getBottom(int col) {
		int i = 13;
		while (board[i][col] != null) {
			i--;
		}
		return i;
	}

	public boolean isGameOver() { // checks if the starting block position is blocked
		if (board[2][2] != null)
			startTime = System.nanoTime();
		return (board[2][2] != null);

	}

	public void clear() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < height; j++) {
				this.board[j][i] = null;
			}
		}
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
						score += 10;
						for (int k = 0; k < groups.size(); ++k) {
							score += 1;
							if (k > 4) {
								score += k;
							}
							board[groups.get(k)[1]][groups.get(k)[0]] = null;
							removeGarbage(groups.get(k)[1], groups.get(k)[0]);
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

	public void removeGarbage(int ud, int lr) {
		if (lr > 0 && board[ud][lr - 1] != null && board[ud][lr - 1].getColor() == 5)
		{board[ud][lr - 1] = null; }
		if (lr < length - 1 && board[ud][lr + 1] != null && board[ud][lr + 1].getColor() == 5)
		{ board[ud][lr + 1] = null; }
		if (ud > 0 && board[ud - 1][lr] != null && board[ud - 1][lr].getColor() == 5)
		{board[ud - 1][lr] = null; }
		if (ud < height - 1 && board[ud + 1][lr]!= null && board[ud + 1][lr].getColor() == 5)
		{ board[ud + 1][lr] = null; }

	}

	public void takeGarbage(int num) {
		for (int i = 0; i < num; ++i) {
			for (int j = 0; j < 6; ++j) {
				Block a = new Block (5);
				if(this.board[1][j] == null) {
					this.board[1][j] = a;
				}
			}

			//this.print();
		}
		this.allDrop();
		//System.out.println("after---");
		//this.print();
	}

	public void allDrop() {
		int temp;
		for (int i = 0; i < length ; ++i) {
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
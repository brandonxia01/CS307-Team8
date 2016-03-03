// Team 8 CS307
package com.team8.game;


public class Piece {
	
	boolean control;
	Block blockA, blockB;
	public Piece() {
		blockA = new Block();
		blockB = new Block();
	
		control = true;
		
		// starting placement for any piece. will probably need to change later since its hard coded
		blockA.setLR(2);
		blockA.setUD(1);
		blockB.setLR(2);
		blockB.setUD(0);
		
		
		
	}
	public void putPieceInto(Board board) {
		board.board[1][2] = blockA;
		board.board[0][2] = blockB;
	}
	
	
	
	public void singleDrop(Board board) { // drops each block by one, checks if its valid.
		// the piece will lose control when it tries to fall and there is something underneath

		
		
		int h = board.getHeight() - 1;
		if (blockA.getUD() > blockB.getUD()) {// X X B X X X    // must move A first
			//								     X X A X X X
			if ((blockA.getUD() != h && board.board[blockA.getUD() + 1][blockA.getLR()] == null)) {
				board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR(), blockA.getUD() + 1);
				board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR(), blockB.getUD() + 1);
			}
			else { control = false; }
		}
		else if (blockA.getUD() < blockB.getUD()) { // X X A X X X     // must move B first
			//										   X X B X X X
			if ((blockB.getUD() != h && board.board[blockB.getUD() + 1][blockB.getLR()] == null)) {   
				board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR(), blockB.getUD() + 1);
				board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR(), blockA.getUD() + 1);
			}
			else { control = false; }
		}
		else if (blockA.getUD() == blockB.getUD()) { // X X A B X X    or    X X B A X X	  
			// they can move in either order
			if ((blockB.getUD() != h && board.board[blockB.getUD() + 1][blockB.getLR()] == null)) {
				if (blockA.getUD() != h && board.board[blockA.getUD() + 1][blockA.getLR()] == null) {
					board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR(), blockA.getUD() + 1); 
					board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR(), blockB.getUD() + 1);
				}
				else { control = false; }
			}
			else { control = false; }
		}
		
		
	
		if (!control) {
		 	// send a message to the game telling it to move on?
			//drop both to the bottom quickly
			dropBoth(board);
		}
	}
	
	public void dropBoth(Board board) {
	
		for (int i = 0; i < board.getHeight(); ++i) {
			if (isDownEmpty(board)) {
				board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR(), blockA.getUD() + 1); 
			}
			if (isDownEmpty(board, 1)) {
				board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR(), blockB.getUD() + 1);
			}
			if (!isDownEmpty(board) && !isDownEmpty(board, 1)) {
				return;
			}
		}
	}
	
	public void rotateCounter(Board board) { 
		// if both blocks are still falling, rotate one of the blocks around the other. otherwise does nothing.
		if (!control) { return; }
		
		int lr1 = blockA.getLR();
		int ud1 = blockA.getUD();
		int lr2 = blockB.getLR();
		int ud2 = blockB.getUD();
		
		int position = checkBPosition();

			//	  X 1 X
			//    2 A 4
			//    X 3 X
		switch (position) {
		case 1: 
			if (isLeftEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2-1, ud2+1);	
			}
			else if (isRightEmpty(board)) { // wallkick
				board.moveBlock(lr1, ud1, lr1+1, ud1);
				board.moveBlock(lr2,ud2, lr2, ud2 +1);
			}
			else if (isDownEmpty(board)) {
				board.moveBlock(lr2, ud2,  lr2, ud2+2);
			}
			else {
				swap(board);
			}
		break;
		case 2:
			if (isDownEmpty(board)) {
				board.moveBlock(lr2,ud2, lr2+1, ud2+1);
			}
			else if (isUpEmpty(board)) { //floor kick
				board.moveBlock(lr1, ud2, lr1, ud1-1);
				board.moveBlock(lr2, ud2, lr2+1, ud2);
			}
			else if (isRightEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2+2, ud2);
			}
			else {
				swap(board);
			}
		break;
		case 3:
			//	  X 1 X
			//    2 A 4
			//    X 3 X
			if (isRightEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2+1, ud2-1);
			}
			else if (isLeftEmpty(board)) {
				board.moveBlock(lr1, ud1, lr1 - 1, ud1);
				board.moveBlock(lr2, ud2, lr2, ud2 -1);
			}
			else if (isUpEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2, ud2 - 2);
			}
			else {
				swap(board);
			}
		break;
		case 4: 
			board.moveBlock(lr2, ud2, lr2-1, ud2-1); // up will always be empty (HOPEFULLY)
		break;
		}	
		
		//   X X X       X B X         X X X      X A X         X X X     
		//   X A B   ->  X A X    ->   B A X  ->  X B X   ->    X A B           
		//   X X X       X X X         X O X      X O X         X X X       
        //                               ^			
					
	}
	
	public void rotateClockwise(Board board) {
		if (!control) { return; }
//	  X 1 X
//    2 A 4   //cycle goes from 1-4-3-2-1
//    X 3 X  
		int lr1 = blockA.getLR();
		int ud1 = blockA.getUD();
		int lr2 = blockB.getLR();
		int ud2 = blockB.getUD();
		//System.out.println("" + lr1 + " " + ud1 + " " + lr2 + " " + ud2);
		int position = checkBPosition();
		//System.out.println(position + "P");
			//	  X 1 X
			//    2 A 4
			//    X 3 X
		switch (position) {
		case 1: 
			if (isRightEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2+1, ud2+1);
			}
			else if (isLeftEmpty(board)) { // wall kick
				board.moveBlock(lr1, ud1, lr1-1, ud1);
				board.moveBlock(lr2, ud2, lr2, ud2+1);
			}
			else if (isDownEmpty(board)) {
				board.moveBlock(lr2,ud2, lr2, ud2+2);
			}
			else {
				swap(board);
			}
		break;
		case 2: 
			board.moveBlock(lr2,ud2, lr2 +1, ud2 -1); // UP is always available HOPEFULLY
		break;
		case 3:
			if (isLeftEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2 -1, ud2 -1);
			}
			else if (isRightEmpty(board)) {
				board.moveBlock(lr1, ud1, lr1+1, ud1);
				board.moveBlock(lr2, ud2, lr2, ud2-1);				
			}
			else if (isUpEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2, ud2-2);
			}
			else {
				swap(board);
			}
		break;
		//	  X 1 X
		//    2 A 4
		//    X 3 X
		case 4: 
			if (isDownEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2 - 1, ud2 + 1);
			}
			else if (isUpEmpty(board)) {
				board.moveBlock(lr1, ud1, lr1, ud1-1);
				board.moveBlock(lr2, ud2, lr2-1, ud2);
			}
			else if (isLeftEmpty(board)) {
				board.moveBlock(lr2, ud2, lr2-2, ud2);
			}
			else {
				swap(board);
			}
		break;
			
		}
		
		
	}
	
	public void swap(Board board) {
		int lr = blockA.getLR();
		int ud = blockA.getUD();
		int lr2= blockB.getLR();
		int ud2= blockB.getUD();
		board.moveBlock(blockA.getLR(), blockA.getUD(), 0, 0);
		board.moveBlock(blockB.getLR(), blockB.getUD(), lr, ud);
		board.moveBlock(0, 0, lr2, ud2);
		
	}
	public boolean isRightEmpty(Board board) {
		return (blockA.getLR() < board.getLength() && board.board[blockA.getUD()][blockA.getLR() + 1] == null);
	}
	public boolean isLeftEmpty(Board board) {
		return (blockA.getLR() > 0 && board.board[blockA.getUD()][blockA.getLR() - 1] == null);
	}
	public boolean isUpEmpty(Board board) {  // might be unnecessary 
		return (blockA.getUD() > 0 && board.board[blockA.getUD() - 1][blockA.getLR()] == null);
	}
	public boolean isDownEmpty(Board board) {
		return (blockA.getUD() < board.getHeight() - 1 && board.board[blockA.getUD()+1][blockA.getLR()] == null);
	}
	public boolean isDownEmpty(Board board, int a) {
		return (blockB.getUD() < board.getHeight() - 1 && board.board[blockB.getUD()+1][blockB.getLR()] == null);

	}
	
	
	public int checkBPosition() {
		if (blockA.getLR() == blockB.getLR() - 1) {
			return 4;
		}
		if (blockA.getLR() == blockB.getLR() + 1) {
			return 2;
		}
		if (blockA.getUD() == blockB.getUD() + 1) {
			return 1;
		}
		if (blockA.getUD() == blockB.getUD() - 1) {
			return 3;
		}
		return 0;
	}
	
	public void moveRight(Board board) {  // self explanatory title, checks for validity.
		if (!control) { return; }
		
		if (blockA.getLR() > blockB.getLR()) {// X X B A X X    // must move A first
			if ((blockA.getLR() != 5 && board.board[blockA.getUD()][blockA.getLR() + 1] == null)) {
				board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR() + 1, blockA.getUD());
				board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR() + 1, blockB.getUD());
			}
		}
		else if (blockA.getLR() < blockB.getLR()) { // X X A B X X     // must move B first
			if ((blockB.getLR() != 5 && board.board[blockB.getUD()][blockB.getLR() + 1] == null)) {   
				board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR() + 1, blockB.getUD());
				board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR() + 1, blockA.getUD());
			}
		}
		else if (blockA.getLR() == blockB.getLR()) { // X X A X X X          X X B X X X	
			//											X X B X X X    or 	 X X A X X X    
			// the piece will only move if both can move, and they can move in either order
			if ((blockB.getLR() != 5 && board.board[blockB.getUD()][blockB.getLR() + 1] == null)) {
				if (blockA.getLR() != 5 && board.board[blockA.getUD()][blockA.getLR() + 1] == null) {
					board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR() + 1, blockA.getUD()); 
					board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR() + 1, blockB.getUD());
				}
			}
		}
	}
	public void moveLeft(Board board) { // self explanatory title, checks for validity.
		if (!control) { return; }
		
		if (blockA.getLR() < blockB.getLR()) {// X X A B X X    // must move A first
			if ((blockA.getLR() != 0 && board.board[blockA.getUD()][blockA.getLR() - 1] == null)) {
				board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR() - 1, blockA.getUD());
				board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR() - 1, blockB.getUD());
			}
		}
		else if (blockA.getLR() > blockB.getLR()) { // X X B A X X     // must move B first
			if ((blockB.getLR() != 0 && board.board[blockB.getUD()][blockB.getLR() - 1] == null)) {   
				board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR() - 1, blockB.getUD());
				board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR() - 1, blockA.getUD());
			}
		}
		else if (blockA.getLR() == blockB.getLR()) { // X X A X X X          X X B X X X	
			//											X X B X X X    or 	 X X A X X X    
			// the piece will only move if both can move, and they can move in either order
			if ((blockB.getLR() != 0 && board.board[blockB.getUD()][blockB.getLR() + 1] == null)) {
				if (blockA.getLR() != 0 && board.board[blockA.getUD()][blockA.getLR() + 1] == null) {
					board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR() - 1, blockA.getUD()); 
					board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR() - 1, blockB.getUD());
				}
			}
		}	
	}
	
	
	public Block getA() {
		return blockA;
	}
	public Block getB() {
		return blockB;
	}
	
}

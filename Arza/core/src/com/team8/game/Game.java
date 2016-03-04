package com.team8.game;
public class Game {

	public static void main(String[] args) {
		
		//Create a board, maybe two if vs cpu or other person

		
		Board board = new Board(6,14); // 6x14 is the generic measurements
									   // only the rows 2-13 are visible to the user
									   // rows 0, 1 are ghostly
		
		//while (!board.isGameOver()) {
		
		Piece p = new Piece();
		// arraylist of pieces maybe to get the same order
		
		p.putPieceInto(board);
		
		//on move Right command 
		p.moveRight(board);
		
		// on move left command
		p.moveLeft(board);
		
		//on rotate counterclockwise
		p.rotateCounter(board);
		
		// on rotate clockwise
		p.rotateClockwise(board);
		
		//wait 1 second or something in between single drops 
		p.singleDrop(board); 
		// singleDrop will drop the piece by one
		// when the piece can no longer be moved, it will automatically drop both blocks
		
		
		
		
		//}

	}

}


package com.team8.game;

public class Game {

	public Board board;
	int framectr;
	public Piece p;
	public Piece nextp;
	public boolean isover;

	public Game() {
		//Create a board, maybe two if vs cpu or other person
		board = new Board(6,14); // 6x14 is the generic measurements
		// only the rows 2-13 are visible to the user
		// rows 0, 1 are ghostly

		//one thread that draws the board and polls input every 60ms
		//another thread that reads input and changes board
		isover = false;

		framectr = 0;
		p = new Piece();
		nextp = new Piece();
		p.putPieceInto(board);
	}

	public Board update() {
		this.framectr++;
		if (board.offset < 42) board.offset++;
		if (framectr == 42) {
			p.singleDrop(board);
			board.offset = 0;
			framectr = 0;
		}

		if (!p.control) {
			if (board.isGameOver()) {
				board.score = 0;
				board.clear();
				isover=true;
			}
			while (this.board.findGroups()) {
				//wait
				this.board.allDrop();
			}
			p = nextp;
			nextp= new Piece();
			p.putPieceInto(board);
			framectr = 0;
		}

		return this.board;
	}

	public static void main(String[] args) {
		/*Piece p = new Piece();
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
			*/


	}

}

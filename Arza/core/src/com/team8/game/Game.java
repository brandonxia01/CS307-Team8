
package com.team8.game;
import java.util.Random;

public class Game {

	public Board board;
	int framectr;
	public Piece p;
	public Piece nextp;
	public boolean isover;
	public Piece p2;
	public Piece nextp2;
	public int framectr2;
	public Board board2;
	boolean drop=false;

	public Game() {
		//Create a board, maybe two if vs cpu or other person
		board = new Board(6,14); // 6x14 is the generic measurements
		board2 = new Board(6,14);
		// only the rows 2-13 are visible to the user
		// rows 0, 1 are ghostly

		//one thread that draws the board and polls input every 60ms
		//another thread that reads input and changes board
		isover = false;

		framectr = 0;
		p = new Piece();
		p2 = new Piece();
		nextp2 = new Piece();
		nextp = new Piece();
		p.putPieceInto(board);
		p2.putPieceInto(board2);
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
			boolean found;
			while (found = board.findGroups() || this.drop == true) {
				//wait
				if (found) {
					try{ Thread.sleep(500);}
					catch (InterruptedException e) {}
				}
				//call all drop on next call
				if (this.drop) {
					//System.out.println("REACHEDAAAAAAAAAAAAAAAA");
					board.allDrop();
					try{ Thread.sleep(500);}
					catch (InterruptedException e) {}
					this.drop = false;
				}
				else {
					this.drop = true;
					return this.board;
				}
			}
			p = nextp;
			nextp= new Piece();
			p.putPieceInto(board);
			framectr = 0;
		}

		return this.board;
	}

	public Board updateMini() {
		this.framectr2++;
		if (board2.offset < 42) board2.offset++;
		if (framectr2 == 42) {
			p2.singleDrop(board2);
			board2.offset = 0;
			framectr2 = 0;
		}

		if (!p2.control) {
			if (board2.isGameOver()) {
				board2.score = 0;
				board2.clear();
				isover=true;
			}
			while (this.board2.findGroups()) {
				//wait
				this.board2.allDrop();
			}
			p2 = nextp2;
			nextp2 = new Piece();
			p2.putPieceInto(board2);
			framectr2 = 0;
		}
		Random r = new Random();
		int x = r.nextInt(200);
		switch(x) {
			case 0: p2.moveLeft(board2);
				break;
			case 1: p2.moveRight(board2);
				break;
			case 2: p2.rotateCounter(board2);
				break;
			case 3: p2.singleDrop(board2);
				break;

		}


		return this.board2;
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

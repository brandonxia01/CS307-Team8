
package com.team8.game;
import java.util.Random;

public class Game {

	public Board board;
	public int framectr;
	public Piece p;
	public Piece nextp;
	public boolean isover;
	public Piece p2;
	public Piece nextp2;
	public int framectr2;
	public Board board2;
	boolean drop=false;
	int garbcount1=0;
	int garbcount2=0;
	int turncount1=1;
	int turncount2=1;
	int endlessgarbctr=5;
	boolean endless = false;
	public float speed = 42;
	public float currentspeed=42;
	boolean found = false;

	public Game(int state) {
		//Create a board, maybe two if vs cpu or other person
		board = new Board(6,14); // 6x14 is the generic measurements
		board2 = new Board(6,14);
		// only the rows 2-13 are visible to the user
		// rows 0, 1 are ghostly

		if (state==1) endless=true;
		System.out.printf("Begin game state %b\n", endless);

		isover = false;

		framectr = 0;
		p = new Piece();
		p2 = new Piece();
		nextp2 = new Piece();
		nextp = nextp2;
		nextp = new Piece();
		p.putPieceInto(board);
		p2.putPieceInto(board2);
	}

	public Board update() {
		this.framectr++;
		/*
		if (fastfall && board.offset<42) board.offset+=(42/4);
		else if (board.offset < 42) board.offset++;

		if ((fastfall && framectr==4) || framectr == 42) {
			p.singleDrop(board);
			board.offset = 0;
			framectr = 0;
		}
		 */


		if (!p.control) {
			if (board.isGameOver()) {
				board.score = 0;
				board.clear();
				isover=true;
			}
			try{ Thread.sleep(300);}
			catch (InterruptedException e) {}
			found = board.findGroups();
			if (found || this.drop == true) {
				System.out.println("While loop");
				try{ Thread.sleep(300);}
				catch (InterruptedException e) {}

				if (found) {
					System.out.println("Wait: found group = true");
					try{ Thread.sleep(300);}
					catch (InterruptedException e) {}
					garbcount1++;
				}
				//If combo is found, blocks have been broken. Draw board in current state and drop on next call
				if (this.drop) {
					board.allDrop();
					System.out.println("Wait: drop = true");
					try{ Thread.sleep(300);}
					catch (InterruptedException e) {}
					this.drop = false;
					return this.board;
				}
				else {
					this.drop = true;
					return this.board;
				}
			}
			if (garbcount1 > 0) {
				try{ Thread.sleep(300);}
				catch (InterruptedException e) {}
				board2.takeGarbage(garbcount1);
				garbcount1=0;
			}
			//Every 10 turns receive garbage
			if (endless && turncount1%10==0) {
				board.takeGarbage(1);
			}
			//Every 15 turns increase speed
			if (endless && turncount1%15==0) {
				if (currentspeed > 21) currentspeed-=8;
			}
			p = nextp;
			nextp= new Piece();
			p.putPieceInto(board);
			framectr = 0;
			turncount1++;
			speed=currentspeed;
		}
		if (board.offset<42) board.offset+=(42/speed);

		if (framectr>=speed ) {
			p.singleDrop(board);
			board.offset = 0;
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
				garbcount1++;
				this.board2.allDrop();
			}
			if (garbcount2 > 0) {
				board.takeGarbage(garbcount2);
				garbcount2=0;
			}
			this.board2.allDrop();
			p2 = nextp2;
			nextp2 = new Piece();
			p2.putPieceInto(board2);
			framectr2 = 0;
			turncount2++;
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

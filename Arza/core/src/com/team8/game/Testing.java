package com.team8.game;
public class Testing {

	public static void main(String[] args) {

		// how to use some of the methods written in Block, Board and Piece
		// Piece class initiates two Block objects automatically
		
		Board board = new Board(6,14);
		board.print();
		Piece newPiece = new Piece();
		newPiece.putPieceInto(board);
		board.print();
		
		/*
		  
		 
		0 1 2 3 4 5    LRPosition ( left right position)  
0		G G 3 G G G         GHOST ROW
1		G G 2 G G G         GHOST ROW      the 3 and 2 are where pieces originate
2		G G G G G G 		
3		G G G G G G 
4		G G G G G G 
5		G G G G G G 
6		G G G G G G 
7		G G G G G G 		rows 2-13 are the 12 rows visible to the player
8		G G G G G G 
9		G G G G G G 
10		G G G G G G 
11		G G G G G G 
12		G G G G G G 
13		G G G G G G 

UDPosition
(up down position)
		 
		 
		 */
		newPiece.singleDrop(board);
		newPiece.singleDrop(board);
		newPiece.singleDrop(board);
		board.print();
		
		Piece p2 = new Piece();
		p2.putPieceInto(board);
		
		newPiece.moveLeft(board);
		board.print();
		
		/*System.out.println(newPiece.getA().getLR());
		System.out.println(newPiece.getA().getUD());
		System.out.println(newPiece.getB().getLR());
		System.out.println(newPiece.getB().getUD());
		*/
		board.print();
		p2.rotateCounter(board);
		board.print();
		p2.rotateCounter(board);
		board.print();
		p2.rotateCounter(board);
		board.print();
		p2.rotateCounter(board);
		board.print();
		p2.rotateCounter(board);
		board.print();
		System.out.println("time");
		p2.singleDrop(board);
		board.print();
		newPiece.singleDrop(board);
		board.print();
		p2.singleDrop(board);
		
		board.print();
		p2.singleDrop(board);
		board.print();
		p2.singleDrop(board);
		board.print();
		board.allDrop();
		board.print();
	
	}

}

// Team 8 CS307



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
					board.moveBlock(blockA.getLR(), blockA.getUD(), blockA.getLR() + 1, blockA.getUD()); 
					board.moveBlock(blockB.getLR(), blockB.getUD(), blockB.getLR() + 1, blockB.getUD());
				}
				else { control = false; }
			}
			else { control = false; }
		}
		
		
		/*
		 if (!control) {
		 	// send a message to the game telling it to move on?
		 	
		 }
		 
		 
		 */
		
	}
	
	public void rotatePiece(Board board, boolean clockwise) { 
		// if both blocks are still falling, rotate one of the blocks around the other. otherwise does nothing.
		if (!control) { return; }
		
			int lr = blockA.getLR();
			int ud = blockA.getUD();
			
			
			
   			//   X X X       X B X         X X X      X X X         X X X     
			//   X A B   ->  X A X    ->   B A X  ->  X A X   ->    X A B           
			//   X X X       X X X         X O X      X B X         X X X       
            //                               ^			
			
			
			/*
			int tempLR = blockA.getLR();
			int tempUD = blockA.getUD();
			
			blockA.setUD(blockB.getUD());
			blockA.setLR(blockB.getLR());
			
			blockB.setUD(tempUD);
			blockB.setLR(tempLR);
			
			board.board[blockA.getUD()][blockA.getLR()] = blockB;
			board.board[blockB.getUD()][blockB.getLR()] = blockA;
			
			*/
		
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
	public Block blockB() {
		return blockB;
	}
	
}

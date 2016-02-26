package chess;

/*********************************************************************
Defines the Queen ChessPiece. Includes unique move set.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class Queen extends ChessPiece {

/*********************************************************************
Constructor method for Queen. Requires an associated owner of type
Player.

@param player the player who owns this piece
@return none
*********************************************************************/
	public Queen(Player player) {
		
		// calls ChessPiece constructor
		super(player);
	}

/*********************************************************************
Returns the designated name of this piece.

@param none
@return String the name of the piece
*********************************************************************/
	public String type() {
		return "Queen";
	}
	
/*********************************************************************
Move validity test for Queen. Queen can be moved horizontally,
vertically, or diagonally so long as it has a clear path to the
destination.

@param move the move being examined
@param board the board on which the move is being made
@return boolean true for valid move, false for invalid move
*********************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		
		// creates a new bishop and rook at from position
		Bishop move1 = new Bishop(
				board[move.fromRow][move.fromColumn].player());
		Rook move2 = new Rook(
				board[move.fromRow][move.fromColumn].player());
		
		// returns true if either rook or bishop move set is valid
		return (move1.isValidMove(move, board) || 
				move2.isValidMove(move, board));
	}
}

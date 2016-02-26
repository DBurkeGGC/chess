package chess;

/*********************************************************************
Defines the King ChessPiece. Includes unique move set.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class King extends ChessPiece {

/*********************************************************************
Constructor method for King. Requires an associated owner of type
Player.

@param player the player who owns this piece
@return none
*********************************************************************/
	public King(Player player) {
		
		// calls ChessPiece constructor
		super(player);
	}

/*********************************************************************
Returns the designated name of this piece.

@param none
@return String the name of the piece
*********************************************************************/
	public String type() {
		return "King";
	}
	
/*********************************************************************
Move validity test for King. King can move one space in any direction.

@param move the move being examined
@param board the board on which the move is being made
@return boolean true for valid move, false for invalid move
*********************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		
		// the boolean value that is returned
		boolean valid = false;

		// checks to see that the general move set is valid
		if (super.isValidMove(move, board)) {
			
			// if the piece is moved one space in any direction
			if ((Math.abs(move.toRow - move.fromRow) == 1 &&
					move.toColumn == move.fromColumn) ||
					(Math.abs(move.toColumn - move.fromColumn) == 
					1 && move.toRow == move.fromRow) ||
					(Math.abs(move.toRow - move.fromRow) == 1 &&
					Math.abs(move.toColumn - move.fromColumn) == 1)) {
				valid = true;
			}
		}
		return valid;
	}
}

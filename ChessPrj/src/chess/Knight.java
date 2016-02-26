package chess;

/*********************************************************************
Defines the Knight ChessPiece. Includes unique move set.

@author Gregorio De Leon
@author Dale Burke
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class Knight extends ChessPiece {

/*********************************************************************
Constructor method for Knight. Requires an associated owner of type
Player.

@param player the player who owns this piece
@return none
*********************************************************************/
	public Knight(Player player) {
		
		// calls ChessPiece constructor
		super(player);
	}

/*********************************************************************
Returns the designated name of this piece.

@param none
@return String the name of the piece
*********************************************************************/	
	public String type() {
		return "Knight";
	}

/*********************************************************************
Move validity test for Knight. Knight can move two spaces vertically
or horizontally, and one space to either side after that (in a "L"
shape). Does not require a clear path.

@param move the move being examined
@param board the board on which the move is being made
@return boolean true for valid move, false for invalid move
*********************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		// the boolean value that is returned  
		boolean valid = false;

		// checks to see that the general move set is valid 
		if(super.isValidMove(move, board)) {

			// checks if knight is moving vertically (L-shape)
			if((Math.abs(move.toRow - move.fromRow) == 2) && 
				(Math.abs(move.toColumn - move.fromColumn) == 1)) {
					valid = true;
			}

			// checks if knight is moving horizontally (L-shape)
			if((Math.abs(move.toRow - move.fromRow) == 1) && 
				(Math.abs(move.toColumn - move.fromColumn) == 2)) {
					valid = true;
			}
		}
		return valid;
	}
}

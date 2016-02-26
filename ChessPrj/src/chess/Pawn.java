package chess;

/*********************************************************************
Defines the Pawn ChessPiece. Includes unique move set.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class Pawn extends ChessPiece {

/*********************************************************************
Constructor method for Pawn. Requires an associated owner of type
Player.

@param player the player who owns this piece
@return none
*********************************************************************/
	public Pawn(Player player) {
		
		// calls ChessPiece constructor
		super(player);
	}

/*********************************************************************
Returns the designated name of this piece.

@param none
@return String the name of the piece
*********************************************************************/
	public String type() {
		return "Pawn";
	}
	
/*********************************************************************
Move validity test for Pawn. Pawn can move forward two spaces from
initial position (provided nothing blocks the path to its destination)
and only one space forward if not in initial position. Pawn cannot 
take pieces directly in front of it, only diagonally, to either side 
of its front face. 

@param move the move being examined
@param board the board on which the move is being made
@return boolean true for valid move, false for invalid move
*********************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		
		// the boolean value that is returned
		boolean valid = false;
		
		// checks to see that the general move set is valid
		if (super.isValidMove(move, board)) {
			
			// checks to see if it is black's turn
			if (board[move.fromRow][move.fromColumn].player() == 
					Player.BLACK) {
				
				/* checks to see if the piece is being moved 
				 * diagonally down and over one space
				 */
				if (((move.toRow - 1 == move.fromRow) && 
						(move.toColumn + 1 == move.fromColumn)) ||
						((move.toRow - 1 == move.fromRow) && 
						(move.toColumn - 1 == move.fromColumn))) {
					
					/* checks to see if this piece is taking another 
					 * player's piece
					 */
					if (board[move.toRow][move.toColumn] != null) {
						valid = true;
					}
				}
				
				/* checks to see if this piece is a white pawn 
				 * in row 2
				 */
				if (move.fromRow == 1) {
					
					/* checks to see if this piece is being 
					 * moved 2 spaces forward
					 */
					if ((move.toColumn == move.fromColumn) && 
							(move.toRow == 3)) {
						
						/* checks to see if this piece has a 
						 * clear path
						 */
						if ((board[2][move.fromColumn]) == null && 
								(board[3][move.fromColumn]) == null) {
							valid = true;
						}
					}
				}
				
				/* checks to see if this piece is being moved down 
				 * one row
				 */
				if ((move.toColumn == move.fromColumn) && 
						(move.toRow - 1 == move.fromRow)) {
					
					// checks to see if this piece has a clear path
					if ((board[move.toRow][move.toColumn]) == null) {
						valid = true;
					}
				}	
			}
			
			// checks to see if it is black's turn
			else if (board[move.fromRow][move.fromColumn].player() == 
					Player.WHITE) {
				
				/* checks to see if the piece is being moved 
				 * diagonally up and over one space
				 */
				if (((move.toRow + 1 == move.fromRow) && 
						(move.toColumn + 1 == move.fromColumn)) ||
						((move.toRow + 1 == move.fromRow) && 
						(move.toColumn - 1 == move.fromColumn))) {
					
					/* checks to see if this piece is taking another 
					 * player's piece
					 */
					if (board[move.toRow][move.toColumn] != null) {
						valid = true;
					}
				}
				
				// checks to see if this piece is a black pawn in row 7
				if (move.fromRow == 6) {
					
					/* checks to see if this piece is being moved 2 
					 * spaces forward
					 */
					if ((move.toColumn == move.fromColumn) && 
							(move.toRow == 4)) {
						
						/* checks to see if this piece has a 
						 * clear path
						 */
						if ((board[5][move.fromColumn]) == null && 
								(board[4][move.fromColumn]) == null) {
							valid = true;
						}
					}
				}
				
				/* checks to see if this piece is being moved 
				 * up one row
				 */
				if ((move.toColumn == move.fromColumn) && 
						(move.toRow + 1 == move.fromRow)) {
					
					// checks to see if this piece has a clear path
					if ((board[move.toRow][move.toColumn]) == null ) {
						valid = true;
					}
				}	
			}
		}
		return valid;
	}
}

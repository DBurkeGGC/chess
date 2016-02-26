package chess;

/*********************************************************************
Defines the Rook ChessPiece. Includes unique move set.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class Rook extends ChessPiece {

/*********************************************************************
Constructor method for Rook. Requires an associated owner of type
Player.

@param player the player who owns this piece
@return none
*********************************************************************/
	public Rook(Player player) {
		
		// calls ChessPiece constructor
		super(player);
	}

/*********************************************************************
Returns the designated name of this piece.

@param none
@return String the name of the piece
*********************************************************************/
	public String type() {
		return "Rook";
	}
	
/*********************************************************************
Move validity test for Rook. Rook can move horizontally or vertically
so long as no pieces block the path to its destination.

@param move the move being examined
@param board the board on which the move is being made
@return boolean true for valid move, false for invalid move
*********************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		
		// the boolean value that is returned
		boolean valid = false;

		// where to start for loops
		int startRow;
		int startColumn;
		
		// checks to see that the general move set is valid
		if (super.isValidMove(move, board)) {

			// checks to see if the columns are the same for the move
			if (move.toColumn == move.fromColumn) {
				
				// sets the lower row value as the starting point
				if (move.fromRow > move.toRow) {
					startRow = move.toRow;
				}
				else {
					startRow = move.fromRow;
				}
				
				/* returns true if there is a clear path 
				 * between points
				 */
				valid = true;
				
				// iterates through the pieces between the two points
				for (int i = 0; i <= Math.abs(
						move.toRow - move.fromRow); i++) {
					
					/* evaluates if there is a piece in the line, 
					 * excluding end points
					 */
					if ((board[startRow + i][move.fromColumn] !=
							null) && ((startRow + i) != 
							move.fromRow) && ((startRow + i) != 
							move.toRow)) {
						
						/* returns false if pieces exist 
						 * between points
						 */
						valid = false;
					}
				}
			}
			
			// checks to see if the rows are the same for the move
			else if ((move.toRow == move.fromRow)) {
				
				// sets the lower column value as the starting point
				if (move.fromColumn > move.toColumn) {
					startColumn = move.toColumn;
				}
				else {	
					startColumn = move.fromColumn;
				}

				/* returns true if there is a clear
				 * path between points
				 */
				valid = true;
				
				// iterates through the pieces between the two points
				for (int i = 0; i <= Math.abs(
						move.toColumn - move.fromColumn); i++) {
					
					/* evaluates if there is a piece in the line, 
					 * excluding end points
					 */
					if ((board[move.fromRow][startColumn + i] != 
							null) && ((startColumn + i) !=
							move.fromColumn) && ((startColumn + i) !=
							move.toColumn)) {
						
						// returns false if pieces exist between points
						valid = false;
					}
				}
			}
		}
		return valid;
	}
}

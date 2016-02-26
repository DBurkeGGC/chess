package chess;

/*********************************************************************
Defines the Bishop ChessPiece. Includes unique move set.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class Bishop extends ChessPiece {

/*********************************************************************
Constructor method for Bishop. Requires an associated owner of type
Player.

@param player the player who owns this piece
@return none
*********************************************************************/
	public Bishop(Player player) {
		
		// calls ChessPiece constructor
		super(player);
	}

/*********************************************************************
Returns the designated name of this piece.

@param none
@return String the name of the piece
*********************************************************************/
	public String type() {
		return "Bishop";
	}
	
/*********************************************************************
Move validity test for Bishop. Bishop can be moved diagonally, so long
as no pieces block the path to its destination.

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

			// checks for a diagonal move
			if (Math.abs(move.toColumn - move.fromColumn) ==
					Math.abs(move.toRow - move.fromRow)) {
				
				// checks for direction of move
				if ((((move.toRow - move.fromRow) < 0) &&
						((move.toColumn - move.fromColumn) < 0)) || 
						(((move.toRow - move.fromRow) > 0) &&
						((move.toColumn - move.fromColumn) > 0))) {
					
					// sets lower column value as the starting point
					if (move.fromColumn > move.toColumn) {	
						startColumn = move.toColumn;
					}
					else {
						startColumn = move.fromColumn;					
					}
					
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
					
					/* iterates through the pieces between the 
					 * two points
					 */
					for (int i = 0; i <= Math.abs(
							move.toRow - move.fromRow); i++) {
						
						/* evaluates if there is a piece in 
						 * the line, excluding end points
						 */
						if ((board[startRow + i][startColumn + i] != 
								null) && 
								((startRow + i) != 
								move.fromRow) && 
								((startRow + i) != 
								move.toRow)) {
							
							/* returns false if pieces exist 
							 * between points
							 */
							valid = false;
						}
					}
				}
				
				// checks for direction of move
				else if ((((move.toRow - move.fromRow) > 0) &&
						((move.toColumn - move.fromColumn) < 0)) || 
						(((move.toRow - move.fromRow) < 0) &&
						((move.toColumn - move.fromColumn) > 0))) {
					
					/* sets the lower column value as the 
					 * starting point
					 */
					if (move.fromColumn > move.toColumn) {
						startColumn = move.toColumn;
					}
					else {
						startColumn = move.fromColumn;
					}
					
					/* sets the higher row value as the 
					 * starting point
					 */
					if (move.fromRow > move.toRow) {	
						startRow = move.fromRow;
					}
					else {
						startRow = move.toRow;
					}

					/* returns true if there is a clear path 
					 * between points
					 */
					valid = true;
					
					/* iterates through the pieces between the 
					 * two points
					 */
					for (int i = 0; i <= Math.abs(
							move.toRow - move.fromRow); i++) {
						
						/* evaluates if there is a piece in the 
						 * line, excluding end points
						 */
						if ((board[startRow - i][startColumn + i] !=
								null) && ((startRow - i) != 
								move.fromRow) && ((startRow - i) != 
								move.toRow)) {
							
							/* returns false if pieces exist between 
							 * points
							 */
							valid = false;	
						}
					}
				}
			}
		}
		return valid;
	}
}

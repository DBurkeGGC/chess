package chess;

/*********************************************************************
Defines general chess piece attributes, such as owner and basic move
set.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public abstract class ChessPiece implements IChessPiece {

	/** who owns the chess piece */
	private Player owner;
	
/*********************************************************************
Constructor method for ChessPiece object. Requires a player of type
Player to be associated with the ChessPiece.

@param player the player who owns the particular piece
@return none
*********************************************************************/
	protected ChessPiece(Player player) {
		this.owner = player;
	}
	
/**
 * Returns the type of the piece as a String value.
 *
 * @return String the name of the piece
 */
	public abstract String type();

/*********************************************************************
Method to return who owns a particular ChessPiece.

@param none
@return Player the owner of the piece
*********************************************************************/
	public Player player() {
		return owner;
	}

/*********************************************************************
General move validity test. The ChessPiece cannot be moved onto
itself, cannot be moved onto a piece owned by the same player,
and must not be null.

@param move the move being examined
@param board the board on which the move is being made
@return boolean true for meets general requirements, 
		false for invalid move
*********************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// stores the boolean value to be returned
		boolean valid = false;
		
		// checks if the start and end positions are the same
		if (((move.fromRow == move.toRow) && 
				(move.fromColumn == move.toColumn)) == false) {
			
			// checks that the start position is not null
			if (board[move.fromRow][move.fromColumn] != null) {
				
				// if the end position is not null
				if (board[move.toRow][move.toColumn] != null) {
					
					// check if the players are different
					if (board[move.fromRow][move.fromColumn].player() 
						!= board[move.toRow][move.toColumn].player()) {
						valid = true;
					}
				}
				
				// if moved to null space, return true
				else {
					valid = true;
				}
			}
		}
		return valid;
	}
}

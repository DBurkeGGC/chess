package chess;

/*********************************************************************
Defines the game and game logic used by ChessPanel.
Game consists of a 2-dimensional array of IChessPieces, player turn,
	and current error message if applicable.
Defines valid moves for IChessPieces, inCheck status, board size.
Contains various methods for viewing and altering the board data.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class ChessModel implements IChessModel {	
	
	/** array where chess piece data is stored */
	private IChessPiece[][] board;
	
	/** the current player */
	private Player player;
	
	/** variable used in ChessPanel for displaying correct game 
	 * message; 0 = not in check, 1 = moving into check, 2 = 
	 * currently in check, 3 = invalid regardless
	 */
	private int messageCode;

/*********************************************************************
Constructor method, creates the chess game.
Defines the board size, initializes board to starting positions, 
sets current player to white.

@param none
@return none
*********************************************************************/
	public ChessModel() {
		
		// creates a new (8 by 8) board of chess pieces
		board = new IChessPiece[numRows()][numColumns()];
		
		// sets the starting player as white
		player = Player.WHITE;

		// populates the board with black pieces
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop (Player.BLACK);
		board[0][6] = new Knight (Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);

		for (int c = 0; c < 8; c++) {
			board[1][c] = new Pawn(Player.BLACK);
		}

		// populates the board with white pieces
		for (int c = 0; c < 8; c++) {
			board[6][c] = new Pawn(Player.WHITE);
		}

		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop (Player.WHITE);
		board[7][6] = new Knight (Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
	}

/*********************************************************************
Method to determine if the game is over. When current player is in
check, this method searches through all of that player's pieces for
valid moves. If none are found, the method returns true and the game
is over.

@param none
@return boolean true for game over, false for available moves left
*********************************************************************/
	public boolean isComplete() {
		// value to be returned
		boolean valid = false;
		
		// temporary move to be constructed
		Move move;
		
		// if current player is in check
		if (inCheck(player)) {
			
			// temporarily set valid to true
			valid = true;
			
			// look for all that player's pieces on the board
			for (int r1 = 0; r1 < numRows(); r1++) {
				for (int c1 = 0; c1 < numColumns(); c1++) {
					
					// if the piece is not null, get player
					if (board[r1][c1] != null) {
						
						// found a piece of the current player
						if (board[r1][c1].player() == player) {

							// search for a spot to move to
							for (int r2 = 0; r2 < numRows(); r2++) {
								for (int c2 = 0; c2 < numColumns();
										c2++) {

									/* construct a temporary move to 
									 * check validity
									 */
									move = new Move(r1, c1, r2, c2);

									/* if a valid move exists, the 
									 * game is not over
									 */
									if (isValidMove(move) == true) {
										valid = false;
									}
								}
							}	
						}
					}
				}
			}
		}
		return valid;
	}

/*********************************************************************
Verifies that the move pattern is valid for a particular piece, and
that the king is not being placed in or left in check once the move
has been made.

@param move the move being examined
@return boolean true for valid move, false for invalid
*********************************************************************/
	public boolean isValidMove(Move move) {
		
		// value to be returned
		boolean valid = false;

		// if the space being examined isn't null
		if (board[move.fromRow][move.fromColumn] != null) { 
			
			// if the move pattern is valid
			if (board[move.fromRow][move.fromColumn].isValidMove(
					move, board) == true) {

				// copy the current pieces in from / to positions
				IChessPiece fromPiece = 
						board[move.fromRow][move.fromColumn];
				IChessPiece toPiece = 
						board[move.toRow][move.toColumn];

				// if the current player is not in check
				if (inCheck(player) == false) {

					// attempt the move
					move(move);

					// if it didn't put the player in check
					if (inCheck(player) == false) {

						// no error message
						messageCode = 0;
						
						// it is a valid move
						valid = true;
					}

					// if it put the player in check
					else if (inCheck(player) == true) {

						// error, king is placed in check
						messageCode = 1;
						
						// invalid move
						valid = false;
					}
					
					// replace the original pieces
					board[move.fromRow][move.fromColumn] = fromPiece;
					board[move.toRow][move.toColumn] = toPiece;
				}

				// if the player is already in check
				else if (inCheck(player) == true) {

					// attempt the move
					move(move);

					// if player is no longer in check
					if (inCheck(player) == false) {
						
						// no error message
						messageCode = 0;
						
						// it is a valid move
						valid = true;
					}

					// else the player is still in check
					else {

						// king is left in check message
						messageCode = 2;
						
						// invalid move
						valid = false;
					}

					// return the pieces to their original positions
					board[move.fromRow][move.fromColumn] = fromPiece;
					board[move.toRow][move.toColumn] = toPiece;
				}
			}
			
			// if it was not valid to begin with 
			else {
				// invalid move message
				messageCode = 3;
			}
		}
		return valid;
	}

/*********************************************************************
Moves whatever is located in the "from" cell on the board to the "to"
cell, irrespective of validity.

@param move where to draw the "from" and "to" coordinates from
@return none
*********************************************************************/
	public void move(Move move) {
		
		// copy the "from" piece to the "to" piece
		board[move.toRow][move.toColumn] = 
				board[move.fromRow][move.fromColumn];

		// set the "from" piece to null
		board[move.fromRow][move.fromColumn] = null;
	}

/*********************************************************************
Method to determine if player p's king is currently in check.

@param p player being examined
@return boolean true for in check, false for not currently in check
*********************************************************************/
	public boolean inCheck(Player p) {
		
		// value to be returned
		boolean valid = false;
		
		// temporary move to be constructed
		Move move;
		
		// from row of move
		int fromRow;
		
		// from column of move
		int fromColumn;
		
		// to row of move
		int toRow = -1;
		
		// to column of move
		int toColumn = -1;
		
		// iterate through the positions on the board
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numColumns(); c++) {
				
				// if a piece exists in specified location
				if (board[r][c] != null) {
					
					// check if it is player p's king
					if (board[r][c].player().equals(p) && 
							board[r][c].type().equals("King")) {
						
						/* set "to" location of the move to that
						 * of player p's king
						 */
						toRow = r;
						toColumn = c;
					}
				}
			}
		}
		
		// iterate through positions on the board again
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numColumns(); c++) {
				
				// if a piece exists at specified location
				if (board[r][c] != null) {
					
					// check if it is the opposite player's piece
					if (board[r][c].player().equals(p.next())) {
						
						/* if so, set the "from" coordinate of the
						 * move to the location of opposing player's
						 * piece
						 */
						fromRow = r;
						fromColumn = c;
						
						// construct the move
						move = new Move(fromRow, fromColumn, 
								toRow, toColumn);
						
						/* if a piece of the opposite player can
						 * make a valid move to player p's king,
						 * player p's king is in check
						 */
						if (board[r][c].isValidMove(move, board)) {
							valid = true;
						}
					}
				}
			}
		}
		return valid;
	}

/*********************************************************************
This method returns the current player

@param none
@return Player the current player
*********************************************************************/
	public Player currentPlayer() {
		return player;
	}

/*********************************************************************
This method returns the number of rows on the board.

@param none
@return int number of rows
*********************************************************************/
	public int numRows() {
		
		// number of rows on the board is always 8
		return 8;
	}

/*********************************************************************
This method returns the number of columns on the board.

@param none
@return int number of columns
*********************************************************************/
	public int numColumns() {
		
		// number of columns on the board is always 8
		return 8;
	}

/*********************************************************************
This method returns the IChessPiece at the given coordinates.

@param row specified row
@param column specified column	
@return IChessPiece which piece it is
*********************************************************************/
	public IChessPiece pieceAt(int row, int column) {		
		return board[row][column];
	}

/*********************************************************************
This method switches the players.

@param none
@return none
*********************************************************************/
	public void setNextPlayer() {
		player = player.next();
	}
	
/*********************************************************************
This method sets the specified location on the board to the specified
IChessPiece.

@param row specified row
@param column specified column
@return none
*********************************************************************/
	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}
	
/*********************************************************************
This method gets the message code for displaying messages to the user.

@param none
@return int which message number to display
*********************************************************************/
	public int getMessage() {
		return messageCode;
	}
}

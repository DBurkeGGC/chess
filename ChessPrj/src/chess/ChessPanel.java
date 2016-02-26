package chess;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/*********************************************************************
Panel for use with ChessModel and ChessGUI classes. 
Chess game designed for two players.
The chess board can be reset.
Moves can be undone.
Player turn is indicated.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
@SuppressWarnings("serial")
public class ChessPanel extends JPanel {

	/** array of JButtons used for displaying the board */
	private JButton[][] board;   
	
	/** ChessModel that stores the current game's IChessPieces */
	private ChessModel model;
	
	/** image icon for rook, player black */
	private ImageIcon bRook;
	
	/** image icon for bishop, player black */
	private ImageIcon bBishop;

	/** image icon for queen, player black */
	private ImageIcon bQueen;

	/** image icon for king, player black */
	private ImageIcon bKing;

	/** image icon for pawn, player black */
	private ImageIcon bPawn;

	/** image icon for knight, player black */
	private ImageIcon bKnight;

	/** image icon for rook, player white */
	private ImageIcon wRook;

	/** image icon for bishop, player white */
	private ImageIcon wBishop;

	/** image icon for queen, player white */
	private ImageIcon wQueen;

	/** image icon for king, player white */
	private ImageIcon wKing;

	/** image icon for pawn, player white */
	private ImageIcon wPawn;

	/** image icon for knight, player white */
	private ImageIcon wKnight;

	/** button for use with reset method */
	private JButton reset;

	/** button for use with undo method */
	private JButton undo;

	/** label to display the current player's turn */
	private JLabel turnLabel;

	/** panel for use with the chess board JButton array */
	private JPanel boardPanel;

	/** panel for use with turnLabel, undo & reset buttons */
	private JPanel buttonPanel;

	/** stores the current player's turn as a message string */
	private String playerTurn;

	/** stores the current game message code */
	private int messageCode;

	/** turn counter to help index move history */
	private int turn;

	/** ArrayList that stores the move history of current game */
	private ArrayList<Move> moveHistory;

	/** ArrayList that stores the "from" piece history of moves */
	private ArrayList<IChessPiece> fromHistory;

	/** ArrayList that stores the "to" piece history of moves */
	private ArrayList<IChessPiece> toHistory;

	/** action listener for use with the chess panel */
	private ButtonListener listener;
	
/*********************************************************************
Constructor method, creates the chess game panel.
Default values are 8 x 8 squares.
Includes buttons for undo, reset game.
Includes turnLabel for displaying which player's turn it is.
Relevant components are initialized.
Actions are directed to ButtonListener.

@param none
@return none	
*********************************************************************/
	public ChessPanel() {

		//instantiates ArrayLists to record game history
		moveHistory = new ArrayList<Move>();
		fromHistory = new ArrayList<IChessPiece>();
		toHistory = new ArrayList<IChessPiece>();

		// sets the turn history to 0
		turn = 0;

		// creates a new game of chess
		model = new ChessModel();
		
		// sets the current player label to player white
		turnLabel = new JLabel("<HTML><CENTER>Player's turn:" +
				"<BR>WHITE</CENTER></HTML>", JLabel.CENTER);
		
		// instantiates the JButton array for the board
		board = new JButton[model.numRows()][model.numColumns()];
		
		// instantiates the listener
		listener = new ButtonListener();

		// sets the icons for the black player pieces
		bRook = new ImageIcon("bRook.png");
		bBishop = new ImageIcon("bBishop.png");
		bQueen = new ImageIcon("bQueen.png");
		bKing = new ImageIcon("bKing.png");
		bPawn = new ImageIcon("bPawn.png");
		bKnight = new ImageIcon("bKnight.png");

		// sets the icons for the white player pieces
		wRook = new ImageIcon("wRook.png");
		wBishop = new ImageIcon("wBishop.png");
		wQueen = new ImageIcon("wQueen.png");
		wKing = new ImageIcon("wKing.png");
		wPawn = new ImageIcon("wPawn.png");
		wKnight = new ImageIcon("wKnight.png");

		// sets up the reset button, adds listener
		reset = new JButton("Reset Game");
		reset.setPreferredSize(new Dimension(120, 120));
		reset.setBackground(Color.WHITE);
		reset.addActionListener(listener);
		
		// sets up the undo button, adds listener
		undo = new JButton("Undo Move");
		undo.setPreferredSize(new Dimension(120, 120));
		undo.setBackground(Color.WHITE);
		undo.addActionListener(listener);

		// instantiates the panels to be placed on the JFrame
		buttonPanel = new JPanel();

		// create the JButton array from model data
		createBoard(model);
		
		// set grid layout for button panel, 3 row, 1 col
		buttonPanel.setLayout(new GridLayout(3, 1, 1, 7));
		
		// add the turnLabel, reset and undo items to the button panel
		buttonPanel.add(turnLabel);
		buttonPanel.add(reset);
		buttonPanel.add(undo);

		//add the board and button panels to the JFrame
		add(boardPanel);
		add(buttonPanel);
	}

/*********************************************************************
Draws the board based on current ChessModel data.
	
@param none
@return none	
*********************************************************************/
	private void displayBoard() {

		// iterate through all positions on the board
		for(int r = 0; r < model.numRows(); r++) {
			for(int c = 0; c < model.numColumns(); c++) {
				setPieceIcon(r, c);
			}
		}
		
		// sets the player turn label appropriately
		turnLabel.setText(playerTurn);
	}
	
/*********************************************************************
ActionListener for event handling in ChessPanel.
Evaluates button clicks and assigns appropriate actions.
	
@param none
@return none	
*********************************************************************/
	private class ButtonListener implements ActionListener {

		// stores false for click 1, true for click 2
		boolean fromTo = false;

		// from row coordinate used for constructing a move
		int fromRow = -1;

		// from column coordinate used for constructing a move
		int fromColumn = -1;

		// to row coordinate used for constructing a move
		int toRow = -1;

		// to column coordinate used for constructing a move
		int toColumn = -1;

		// the move being constructed
		Move tempMove;

		// stores background color of first click's space
		Color tempColor;

/*********************************************************************
Evaluates which button was clicked and performs the appropriate action.
		
@param event denotes which button is clicked
@return none	
*********************************************************************/
		public void actionPerformed(ActionEvent event) {

			// if undo button is clicked
			if (undo == event.getSource()) {

				// deselects board selections, sets to initial state
				deselectSquare();

				// undo the last move
				undoMove();

				// displays board
				displayBoard();
			}

			// if reset button is clicked
			if (reset == event.getSource()) {	

				// deselects board selections, sets to initial state
				deselectSquare();

				// show reset message box
				int confirm = JOptionPane.showOptionDialog(null,
						"Are you sure you want to start a new game?",
						"Reset Confirmation", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, 
						null, null, null);

				// if user selects yes
				if (confirm == JOptionPane.YES_OPTION) {

					// reset board to initial state
					resetBoard();
				}
			}

			// cycles through to see if the board was clicked
			for(int r = 0; r < model.numRows(); r++) {
				for(int c = 0; c < model.numColumns(); c++) {

					// if the button at r, c was clicked
					if (board[r][c] == event.getSource()) {
						
						// handle click appropriately
						boardSelected(r, c);
					}
				}
			}
		}

/*********************************************************************
Private method for board selection event handling.

@param r the row position that was selected
@param c the column position that was selected
@return none		
*********************************************************************/
		private void boardSelected(int r, int c) {
			
			// for the first click
			if (fromTo == false) {

				// if there is a piece at that location
				if (model.pieceAt(r, c) != null) {
					
					// if it belongs to current player
					if (model.pieceAt(r, c).player() == 
							model.currentPlayer()) {
						
						// get the background color
						tempColor = board[r][c].getBackground();
						
						// highlight the piece
						board[r][c].setBackground(Color.CYAN);
						
						// set the "from" location
						fromRow = r;
						fromColumn = c;

						// set message code appropriately
						messageCode = 0;
						
						// indicate second click for next pass
						fromTo = true;
					}
					
					// if it doesn't belong to current player
					else {
						
						// set message code appropriately
						messageCode = 1;

						// display error message
						displayMessage();
					}
				}
			}
			
			// for the second click
			else if (fromTo == true) {

				// un-highlight the from location
				board[fromRow][fromColumn].setBackground(tempColor);
				
				// set the "to" location
				toRow = r;
				toColumn = c;
				
				// construct the move
				tempMove = new Move(fromRow, fromColumn, toRow, 
						toColumn);
				
				// if the move is valid
				if (model.isValidMove(tempMove)) {
					
					// record it
					recordMove(tempMove, model);
					
					// finalize the move
					model.move(tempMove);
					
					// promote pawns appropriately
					promotion(toRow, toColumn);
					
					// next player's turn
					model.setNextPlayer();
					
					// set player turn message
					setTurnMessage(model);
					
					// add 1 to turn count
					turn++;
					
					// update board
					displayBoard();
					
					// if next player was put in check
					if (model.inCheck(
							model.currentPlayer())) {
						
						// set method appropriately
						messageCode = 2;
						
						// if checkmate
						if (model.isComplete()) {
							
							// show new game dialog
							messageCode = 3;
						}
					}
				}
				
				// set to initial click
				fromTo = false;
				
				// display appropriate message
				displayMessage();
			}
		}
				
/*********************************************************************
Private helper method that deselects a highlighted square
messages.

@param none
@return none		
*********************************************************************/
		private void deselectSquare() {
			
			// if a square has been selected
			if (fromTo == true) {
				
				// reset it to initial state
				fromTo = false;
				board[fromRow][fromColumn].setBackground(tempColor);
			}
		}
		
/*********************************************************************
Private method that displays all of the various game status and error 
messages.

@param none
@return none		
*********************************************************************/
		private void displayMessage() {
			
			// if piece's player didn't match current player
			if (messageCode == 1) {
				JOptionPane.showMessageDialog(null, 
					"It is not your turn.");
			}
			
			// if a king was placed in check
			else if (messageCode == 2) {
				
				// if white was put in check
				if (model.currentPlayer() == Player.WHITE) {
					JOptionPane.showMessageDialog(null, 
						"WHITE is in check.");
				}
				
				// if black was put in check
				else if (model.currentPlayer() == Player.BLACK) {
					JOptionPane.showMessageDialog(null, 
						"BLACK is in check.");
				}
			}
			
			// if checkmate was achieved
			else if (messageCode == 3) {
				
				// if black checkmates white
				if (model.currentPlayer() == Player.WHITE) {
					JOptionPane.showMessageDialog(null, 
						"Checkmate! BLACK wins.");
				}
				
				// if white checkmates black
				else if (model.currentPlayer() == Player.BLACK) {
					JOptionPane.showMessageDialog(null,
						"Checkmate! WHITE wins.");
				}
				
				// show game over dialog
				gameOverDialog();
			}
			
			// if king is moved into check
			else if (model.getMessage() == 1) {
				JOptionPane.showMessageDialog(null, 
					"Invalid move :  the King is placed in check.");
			}
			
			// if king is not moved out of check
			else if (model.getMessage() == 2) {
				JOptionPane.showMessageDialog(null, 
					"Invalid move :  the King remains in check.");
			}
			
			// if move is generally invalid
			else if (model.getMessage() == 3) {
				JOptionPane.showMessageDialog(null, 
					"" + model.pieceAt(fromRow, fromColumn).type() +
					" :  invalid move.");
			}
		}

/*********************************************************************
Undoes previous player's move, setting cell to empty and swapping 
players. Does not undo other actions.
			
@param none
@return none		
*********************************************************************/
		private void undoMove() {
			
			// if it isn't the first turn of game
			if (turn > 0) {
				
				// move the array indexes back one
				int index = turn - 1;
				
				// reset the "from" piece
				model.setPiece(moveHistory.get(index).fromRow, 
						moveHistory.get(index).fromColumn,
						fromHistory.get(index));
				
				// reset the "to" piece
				model.setPiece(moveHistory.get(index).toRow, 
						moveHistory.get(index).toColumn,
						toHistory.get(index));
				
				// set current player to previous
				model.setNextPlayer();
				
				// set the current player message
				setTurnMessage(model);
				
				// subtract one from turn counter
				turn--;
				
				// remove last entry in game history
				moveHistory.remove(index);
				fromHistory.remove(index);
				toHistory.remove(index);
			}
		}
		
/*********************************************************************
This method is called whenever a player makes a move on the board.
It records the move of and IChessPiece and creates a new 
IChessPiece. The IChessPieces involved are added to appropriate 
ArrayLists of IChessPieces and the move is added to an ArrayList of
moves.   

@param move the move of the IChessPiece
@param model which game the move is taking place in
@return none
*********************************************************************/
		private void recordMove(Move move, ChessModel model) {
			
			// copies piece in "from" location
			IChessPiece fromPiece = model.pieceAt(
					move.fromRow, move.fromColumn);
			
			// copies piece in "to" location
			IChessPiece toPiece = model.pieceAt(
					move.toRow, move.toColumn);
			
			// add move and associated pieces to history
			moveHistory.add(tempMove);
			fromHistory.add(fromPiece);
			toHistory.add(toPiece);
		}
		
/*********************************************************************
This method is called when a player wins the game. It asks the user if 
they would like to start a new game. If yes, it creates a new board,
new array List of moves and IChessPieces.

@param none
@return none		
*********************************************************************/
		private void gameOverDialog() {
			
			// show reset dialog
			int confirm = JOptionPane.showOptionDialog(null,
					"Would you like to play again?",
					"Game Over", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			
			// if user selects yes
			if (confirm == JOptionPane.YES_OPTION) {
				
				// reset board to initial state
				resetBoard();
			}
			
			// if no option or closed
			if (confirm == JOptionPane.NO_OPTION || confirm ==
					JOptionPane.CLOSED_OPTION) {
				
				// exit game
				System.exit(1);
			}
		}
		
/*********************************************************************
This method replaces a pawn with a queen when it reaches the opposite 
side of the board.	

@param row position of piece being examined
@param col position of piece being examined
@return none		
*********************************************************************/	
		private void promotion(int row, int col) {
			
			// if piece is a pawn
			if (model.pieceAt(row, col).type().equals("Pawn")) {
				
				// if it's a white pawn in row 0
				if (model.pieceAt(row, col).player().equals(
						Player.WHITE) && row == 0) {
					
					// promote to a queen
					Queen tempQueen = new Queen(Player.WHITE);
					model.setPiece(row, col, tempQueen);
				}
				
				// if it's a black pawn in row 7
				else if (model.pieceAt(row, col).player().equals(
						Player.BLACK) && row == 7) {
					
					// promote to a queen
					Queen tempQueen = new Queen(Player.BLACK);
					model.setPiece(row, col, tempQueen);
				}
			}
		}
	}
	
/*********************************************************************
This method sets the message string based on the current player.

@param model which game to get the player from
@return none	
*********************************************************************/
	private void setTurnMessage(ChessModel model) {
		
		// if current player is white
		if (model.currentPlayer().equals(Player.WHITE)) {
			
			// set message appropriately
			playerTurn = "<HTML><CENTER>Player's turn:" +
					"<BR>WHITE</CENTER></HTML>";
		}
		
		// if current player is black
		else if (model.currentPlayer().equals(Player.BLACK)) {
			
			// set message appropriately
			playerTurn = "<HTML><CENTER>Player's turn:" + 
					"<BR>BLACK</CENTER></HTML>";
		}
	}

/*********************************************************************
Private helper method used to reset the board.

@param none
@return none	
*********************************************************************/
	private void resetBoard() {

		// reset the board data to initial state
		model = new ChessModel();
		moveHistory = new ArrayList<Move>();
		fromHistory = new ArrayList<IChessPiece>();
		toHistory = new ArrayList<IChessPiece>();
		turn = 0;
		setTurnMessage(model);

		// display the board
		displayBoard();
	}
	
/*********************************************************************
Private helper method used create the JButton board based off
ChessModel data.

@param model what model to get IChessPiece data from
@return none	
*********************************************************************/
	private void createBoard(ChessModel model) {

		// instantiates the chess board panel
		boardPanel = new JPanel();
		
		// sets the layout of the panels
		boardPanel.setLayout(new GridLayout(model.numRows(), 
				model.numColumns(), 1, 1));

		// sets square dimensions for the board panel
		boardPanel.setPreferredSize(new Dimension(600, 600));	
		
		// iterates through the board piece data
		for(int r = 0; r < model.numRows(); r++) {
			for(int c = 0; c < model.numColumns(); c++) {

				// if the piece is null, instantiate as null button
				if (model.pieceAt(r, c) == null) {
					board[r][c] = new JButton(null, null);
				}

				// instantiate player white's pieces
				else if(model.pieceAt(r, c).player() == 
						Player.WHITE) {

					// find the matching icon for the piece
					if(model.pieceAt(r, c).type().equals(
							"Pawn")) {
						board[r][c] = new JButton(null, wPawn);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Rook")) {
						board[r][c] = new JButton(null, wRook);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Knight")) {
						board[r][c] = new JButton(null, wKnight);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Bishop")) {
						board[r][c] = new JButton(null, wBishop);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Queen")) {
						board[r][c] = new JButton(null, wQueen);
					}
					else if(model.pieceAt(r, c).type().equals(
							"King")) {
						board[r][c] = new JButton(null, wKing);
					}
				}

				// instantiates player black's pieces
				else if(model.pieceAt(r, c).player() == 
						Player.BLACK) {

					// find the matching icon for the piece
					if(model.pieceAt(r, c).type().equals(
							"Pawn")) {
						board[r][c] = new JButton(null, bPawn);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Rook")) {
						board[r][c] = new JButton(null, bRook);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Knight")) {
						board[r][c] = new JButton(null, bKnight);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Bishop")) {
						board[r][c] = new JButton(null, bBishop);
					}
					else if(model.pieceAt(r, c).type().equals(
							"Queen")) {
						board[r][c] = new JButton(null, bQueen);
					}
					else if(model.pieceAt(r, c).type().equals(
							"King")) {
						board[r][c] = new JButton(null, bKing);
					}
				}

				// assign a color to every other square (black)
				if ((c % 2 == 1 && r % 2 == 0) || 
						(c % 2 == 0 && r % 2 == 1)) {
					board[r][c].setBackground(Color.GRAY);
				}

				// assign a color to every other square (white)
				else if ((c % 2 == 0 && r % 2 == 0) || 
						(c % 2 == 1 && r % 2 == 1)) {
					board[r][c].setBackground(Color.WHITE);
				}

				// attach a listener to the button
				board[r][c].addActionListener(listener);

				// add the button on the panel
				boardPanel.add(board[r][c]);
			}
		}
	}
	
/*********************************************************************
Private helper method used set JButton board icons based off current 
model, board, and specified piece coordinates.

@param r piece at row r
@param c piece at column c
@return none	
*********************************************************************/
	private void setPieceIcon(int r, int c) {

		// if there isn't an existing piece, remove icon
		if (model.pieceAt(r, c) == null) {
			board[r][c].setIcon(null);
		}
		
		// if there is a piece of player white, assign icon
		else if(model.pieceAt(r, c).player() == 
				Player.WHITE) {

			// find the matching icon for the piece
			if(model.pieceAt(r, c).type().equals("Pawn")) {
				board[r][c].setIcon(wPawn);
			}
			else if(model.pieceAt(r, c).type().equals("Rook")) {
				board[r][c].setIcon(wRook);
			}
			else if(model.pieceAt(r, c).type().equals("Knight")) {
				board[r][c].setIcon(wKnight);
			}
			else if(model.pieceAt(r, c).type().equals("Bishop")) {
				board[r][c].setIcon(wBishop);
			}
			else if(model.pieceAt(r, c).type().equals("Queen")) {
				board[r][c].setIcon(wQueen);
			}
			else if(model.pieceAt(r, c).type().equals("King")) {
				board[r][c].setIcon(wKing);
			}
		}
		
		// if there is a piece of player black, assign icon
		else if(model.pieceAt(r, c).player() == 
				Player.BLACK) {

			// find the matching icon for the piece
			if(model.pieceAt(r, c).type().equals("Pawn")) {
				board[r][c].setIcon(bPawn);
			}
			else if(model.pieceAt(r, c).type().equals("Rook")) {
				board[r][c].setIcon(bRook);
			}
			else if(model.pieceAt(r, c).type().equals("Knight")) {
				board[r][c].setIcon(bKnight);
			}
			else if(model.pieceAt(r, c).type().equals("Bishop")) {
				board[r][c].setIcon(bBishop);
			}
			else if(model.pieceAt(r, c).type().equals("Queen")) {
				board[r][c].setIcon(bQueen);
			}
			else if(model.pieceAt(r, c).type().equals("King")) {
				board[r][c].setIcon(bKing);
			}
		}
	}
}
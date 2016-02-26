package chess;

import java.awt.Dimension;

import javax.swing.JFrame;
/*********************************************************************
For use with the ChessPanel class. Calls the appropriate constructors
to start a new game.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class ChessGUI {

/*********************************************************************
Main method, sets up the GUI for a new chess game.

@param none
@return none
*********************************************************************/
	public static void main(String[] args) {
		
		// creates a new JFrame for the chess game
		JFrame frame = new JFrame("Chess Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// creates a new ChessPanel and places it on the frame
		ChessPanel panel = new ChessPanel();
		frame.getContentPane().add(panel);
		
		// sets and locks the size of the frame
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(742, 638));
		
		frame.pack();
		frame.setVisible(true);
	}
}

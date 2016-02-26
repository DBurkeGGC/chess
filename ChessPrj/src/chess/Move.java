package chess;

/*********************************************************************
Defines the Move object. Consists of four integers: a "from" 
row and column value, and a "to" row and column value. These are
appropriately paired into coordinates that define a move.

@author Dale Burke
@author Gregorio De Leon
@author Marcos Diaz
@version March 2013
*********************************************************************/
public class Move {

	/** from row coordinate of the move */
	public int fromRow;
	
	/** from column coordinate of the move */
	public int fromColumn;
	
	/** to row coordinate of the move */
	public int toRow;
	
	/** to column coordinate of the move */
	public int toColumn;

/*********************************************************************
Blank constructor method. Creates a Move object with no values.

@param none
@return none
*********************************************************************/
	public Move() 
	{
		
	}

/*********************************************************************
Move constructor method. Creates a Move object with the specified
values.

@param fromRow from row value of the move
@param fromColumn from column value of the move
@param toRow to row value of the move
@param toColumn to column value of the move
@return none
*********************************************************************/
	public Move(int fromRow, int fromColumn, int toRow, int toColumn) 
	{
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}
}

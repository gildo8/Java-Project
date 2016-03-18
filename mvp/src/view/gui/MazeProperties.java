/**
 * @Project: mvp
 * @Class : MazeProperties.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;


/**
 * The Class MazeProperties.
 */
public class MazeProperties {
	
	/** The Maze name. */
	private String MazeName;
	
	/** The Rows. */
	private int Rows;
	
	/** The Columns. */
	private int Columns;
	
	/** The Floors. */
	private int Floors;
	
	
	/**
	 * Instantiates a new maze properties.
	 *
	 * @param mazeName the maze name
	 * @param rows the rows
	 * @param columns the columns
	 * @param floors the floors
	 */
	public MazeProperties(String mazeName, int rows, int columns, int floors) {
		super();
		MazeName = mazeName;
		Rows = rows;
		Columns = columns;
		Floors = floors;
	}
	
	/**
	 * Instantiates a new maze properties.
	 */
	public MazeProperties() {
		MazeName = "Deafult";
		Rows = 5;
		Columns = 5;
		Floors = 5;
	}

	/**
	 * Gets the maze name.
	 *
	 * @return the maze name
	 */
	public String getMazeName() {
		return MazeName;
	}

	/**
	 * Sets the maze name.
	 *
	 * @param mazeName the new maze name
	 */
	public void setMazeName(String mazeName) {
		MazeName = mazeName;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public int getRows() {
		return Rows;
	}

	/**
	 * Sets the rows.
	 *
	 * @param rows the new rows
	 */
	public void setRows(int rows) {
		Rows = rows;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public int getColumns() {
		return Columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @param columns the new columns
	 */
	public void setColumns(int columns) {
		Columns = columns;
	}

	/**
	 * Gets the floors.
	 *
	 * @return the floors
	 */
	public int getFloors() {
		return Floors;
	}

	/**
	 * Sets the floors.
	 *
	 * @param floors the new floors
	 */
	public void setFloors(int floors) {
		Floors = floors;
	}
	
	
}

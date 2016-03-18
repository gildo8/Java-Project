/**
 * @Project: mvp
 * @Class : Maze2dDraw.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package model;

import algorithms.demo.Maze2dAdapter;
import view.DrawMaze;


/**
 * The Class Maze2dDraw represents 2 dimentions maze to converting data for 2 dimentions method , like cross section.
 * Uses the Object Adapter.
 */
public class Maze2dDraw implements DrawMaze<int [][]> {

	/** The maze2d. */
	Maze2dAdapter maze2d;
	
	/**
	 * Instantiates a new maze2d draw.
	 *
	 * @param maze2d the maze2d
	 */
	public Maze2dDraw(Maze2dAdapter maze2d){
		this.maze2d = maze2d;
	}
	
	/* (non-Javadoc)
	 * @see view.DrawMaze#getMazeData()
	 */
	@Override
	public int[][] getMazeData() {
		return maze2d.getMyMaze();
	}

}

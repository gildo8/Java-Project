/**
 * @Project: mvp
 * @Class : Maze3dDraw.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package model;

import algorithms.mazeGenerators.Maze3d;
import view.DrawMaze;


/**
 * The Class Maze3dDraw.
 * Object Adapter for Maze3d
 */
public class Maze3dDraw implements DrawMaze<Maze3d> {

	/** The maze3d. */
	Maze3d maze3d;
	
	/**
	 * Instantiates a new maze3d draw.
	 *
	 * @param maze3d the maze3d
	 */
	public Maze3dDraw(Maze3d maze3d) {
		this.maze3d = maze3d;
	}

	/* (non-Javadoc)
	 * @see view.DrawMaze#getMazeData()
	 */
	@Override
	public Maze3d getMazeData() {
		return this.maze3d;
	}
}

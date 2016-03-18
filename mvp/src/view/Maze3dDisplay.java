/**
 * @Project: mvp
 * @Class : Maze3dDisplay.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;


/**
 * The Class Maze3dDisplay, implements DisplayMaze Interface for display 3 dimentation Maze.
 *
 */
public class Maze3dDisplay implements DisplayMaze<Maze3d> {

	/** The draw maze. */
	Maze3d drawMaze;
	
	/** The out. */
	PrintWriter out;
	
	/**
	 * Instantiates a new maze3d display.
	 *
	 * @param out the out
	 */
	public Maze3dDisplay(PrintWriter out) {
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.DisplayMaze#getDisplayerMaze(view.DrawMaze)
	 */
	@Override
	public void getDisplayerMaze(DrawMaze<Maze3d> dMaze) {
		this.drawMaze = dMaze.getMazeData();
	}
	
	/* (non-Javadoc)
	 * @see view.DisplayMaze#displayMaze()
	 */
//	public void getDisplayerMaze(DrawMaze<int[][][]> dMaze , Maze3d maze) {
//		this.drawMaze = dMaze.getMazeData();	
//	}

	@Override
	public void displayMaze() {
		drawMaze.printMaze();
	}
}

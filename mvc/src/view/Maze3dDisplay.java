/**
 * @Project: mvc
 * @Class : Maze3dDisplay.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;

/**
 * The Class Maze3dDisplay.
 */
public class Maze3dDisplay implements DisplayMaze<int[][][]> {

	/** The draw maze. */
	int[][][] drawMaze;
	
	/** The out. */
	PrintWriter out;
	
	/**
	 * Instantiates a new maze3d display.
	 *
	 * @param out
	 *            the out
	 */
	public Maze3dDisplay(PrintWriter out) {
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.DisplayMaze#getDisplayerMaze(view.DrawMaze)
	 */
	@Override
	public void getDisplayerMaze(DrawMaze<int[][][]> dMaze) {
		this.drawMaze = dMaze.getMazeData();
	}
	
	/**
	 * Gets the displayer maze.
	 *
	 * @param dMaze
	 *            the d maze
	 * @param maze
	 *            the maze
	 */
	public void getDisplayerMaze(DrawMaze<int[][][]> dMaze , Maze3d maze) {
		this.drawMaze = dMaze.getMazeData();	
	}

	/* (non-Javadoc)
	 * @see view.DisplayMaze#displayMaze()
	 */
	@Override
	public void displayMaze() {
		for (int i = 0; i < drawMaze.length; i++) {
			for (int j = 0; j < drawMaze[i].length; j++) {
				for (int w = 0; w < drawMaze[i][j].length; w++) {
					out.flush();
					out.print(drawMaze[i][j][w] + " ");
					out.flush();
				}
				out.println("");
			}
			out.println("\n");
		}
	}
}

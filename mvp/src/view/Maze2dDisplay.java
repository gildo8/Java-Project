/**
 * @Project: mvp
 * @Class : Maze2dDisplay.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.io.PrintWriter;


/**
 * The Class Maze2dDisplay , implements DisplayMaze Interface for display 2 dimentation Maze.
 */
public class Maze2dDisplay implements DisplayMaze<int [][]> {

	/** The draw maze. */
	private int [][] drawMaze;
	
	/** The out. */
	PrintWriter out;
	
	/**
	 * Instantiates a new maze2d display.
	 *
	 * @param out the out
	 */
	public Maze2dDisplay(PrintWriter out) {
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.DisplayMaze#getDisplayerMaze(view.DrawMaze)
	 */
	@Override
	public void getDisplayerMaze(DrawMaze<int[][]> dMaze) {
		this.drawMaze = dMaze.getMazeData();
	}

	/* (non-Javadoc)
	 * @see view.DisplayMaze#displayMaze()
	 */
	@Override
	public void displayMaze() {
		for (int i = 0; i < drawMaze.length; i++) {
			for (int j = 0; j < drawMaze[i].length; j++) {
				out.flush();
				out.print(drawMaze[i][j] + " ");	
			}
			out.println("");
		}
		out.println("\n");
	}
}

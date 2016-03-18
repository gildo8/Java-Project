/**
 * @Project: mvc
 * @Class : MyView.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.io.BufferedReader;
import java.io.File;

import java.io.PrintWriter;
import java.util.HashMap;
import algorithms.search.Searchable;
import algorithm.generic.Solution;
import controller.Command;


/**
 * The Class MyView.
 */
public class MyView implements View {

	/** The v cli. */
	private CLI vCli;
	
	/** The maze3d displayer. */
	private Maze3dDisplay maze3dDisplayer;
	
	/** The maze2d displayer. */
	private Maze2dDisplay maze2dDisplayer;
	
	/** The out. */
	PrintWriter out;
	
	/**
	 * Instantiates a new my view.
	 *
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 */
	public MyView(BufferedReader in , PrintWriter out) {
		this.vCli = new CLI(in, out);
		this.maze2dDisplayer = new Maze2dDisplay(out);
		this.maze3dDisplayer = new Maze3dDisplay(out);
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.View#dir(java.lang.String)
	 */
	@Override
	public void dir(String fName) {
		File f = new File(fName);
		File[] listFiles = f.listFiles();
			for (File file : listFiles) {
				if( (file.isFile()) || (file.isDirectory()) ){
					this.out.println(file.getName());
				}
				else{
					this.out.println("Error with path");
				}
			}		
	}

	/* (non-Javadoc)
	 * @see view.View#displayMazeSolution(algorithm.generic.Solution)
	 */
	@Override
	public <T> void displayMazeSolution(Solution<T> sol) {
		sol.print();
		this.displayMsg("");
	}

	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> command) {
		this.vCli.setCommands(command);
	}

	/* (non-Javadoc)
	 * @see view.View#errorMsg(java.lang.String)
	 */
	@Override
	public void errorMsg(String msg) {
		System.out.println("--> " + msg);
	}

	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		vCli.run();
	}

	/*@Override
	public void displayMaze(Maze3d maze) {
		maze.printMaze();
		displayStartGoalPoints(maze);
	}*/

	/* (non-Javadoc)
	 * @see view.View#displayMsg(java.lang.String)
	 */
	@Override
	public void displayMsg(String msg) {
		System.out.println(msg);
	}

	/* (non-Javadoc)
	 * @see view.View#displayMaze(view.DrawMaze)
	 */
	/*
	@Override
	public void displayCrossSection(int[][] maze2d) {
		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[i].length; j++) {	
				System.out.print(maze2d[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
	}

	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayMaze(DrawMaze<T> dMaze) {
		maze3dDisplayer.getDisplayerMaze((DrawMaze<int[][][]>) dMaze);
		maze3dDisplayer.displayMaze();
	}
	
	
	/* (non-Javadoc)
	 * @see view.View#displayCrossSection(view.DrawMaze)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayCrossSection(DrawMaze<T> dMaze) {
		maze2dDisplayer.getDisplayerMaze((DrawMaze<int[][]>) dMaze);
		maze2dDisplayer.displayMaze();
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayStartGoalPoints(algorithms.search.Searchable)
	 */
	@Override
	public <T> void displayStartGoalPoints(Searchable<T> maze) {
		this.out.println("Start Point: " + maze.getStartPosition().toString());
		this.out.println("Goal Point: " + maze.getGoalPosition().toString());
	}

}

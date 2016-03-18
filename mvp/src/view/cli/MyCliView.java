/**
 * @Project: mvp
 * @Class : MyCliView.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Searchable;
import algorithm.generic.Solution;
import presenter.Command;
import view.CLI;
import view.DrawMaze;
import view.Maze2dDisplay;
import view.Maze3dDisplay;
import view.View;



/**
 * The Class MyCliView , an implementation of View , Defining the CLI View.
 */
public class MyCliView extends Observable implements View {

	/** The v cli. */
	private CLI vCli;
	
	/** The maze3d displayer. */
	private Maze3dDisplay maze3dDisplayer;
	
	/** The maze2d displayer. */
	private Maze2dDisplay maze2dDisplayer;
	
	/** The out. */
	public PrintWriter out;
	
	/** The v command. */
	private Command vCommand;
	
	/**
	 * Instantiates a new my cli view.
	 *
	 * @param out the out
	 * @param in the in
	 */
	public MyCliView(PrintWriter out , BufferedReader in) {
		vCli = new CLI(out, in , this);
		this.out = out;
		maze2dDisplayer = new Maze2dDisplay(out);
		maze3dDisplayer = new Maze3dDisplay(out);
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
	 * @see view.View#displayMazeSolution(algorithms.search.Solution)
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
		vCli.setCommands(command);
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
		Thread myThread = new Thread(vCli);
		myThread.start();
	}

	/* (non-Javadoc)
	 * @see view.View#displayMsg(java.lang.String)
	 */
	@Override
	public void displayMsg(String msg) {
		out.println(msg);
		out.flush();
	}
		
	/* (non-Javadoc)
	 * @see view.View#displayMaze(view.DrawMaze)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayMaze(DrawMaze<T> dMaze) {
		maze3dDisplayer.getDisplayerMaze((DrawMaze<Maze3d>) dMaze);
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

	/* (non-Javadoc)
	 * @see view.View#exit()
	 */
	@Override
	public void exit() {
		vCli.setWorking(false);
	}

	/* (non-Javadoc)
	 * @see view.View#getUserCommand()
	 */
	@Override
	public Command getUserCommand() {
		return vCommand;
	}

	/* (non-Javadoc)
	 * @see view.View#setUserCommand(presenter.Command)
	 */
	@Override
	public void setUserCommand(Command command){
		this.vCommand = command;
		setChanged();
		notifyObservers("New command");
	}
}

/**
 * @Project: mvp
 * @Class : View.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.util.HashMap;
import algorithms.search.Searchable;
import algorithm.generic.Solution;
import presenter.Command;


/**
 * The Interface View.
 */
public interface View {

	/**
	 * Dir.
	 *
	 * @param fName the f name
	 */
	public void dir(String fName);
	
	/**
	 * Display maze solution.
	 *
	 * @param <T> the generic type
	 * @param sol the sol
	 */
	public <T> void displayMazeSolution(Solution<T> sol);
	
	/**
	 * Sets the commands.
	 *
	 * @param command the command
	 */
	public void setCommands(HashMap<String,Command> command);
	
	/**
	 * Error msg.
	 *
	 * @param msg the msg
	 */
	public void errorMsg(String msg);
	
	/**
	 * Start.
	 */
	public void start();
	
	/**
	 * Display maze.
	 *
	 * @param <T> the generic type
	 * @param dMaze the d maze
	 */
	public <T> void displayMaze (DrawMaze<T> dMaze);
	
	/**
	 * Display msg.
	 *
	 * @param msg the msg
	 */
	public void displayMsg(String msg);
	
	/**
	 * Display cross section.
	 *
	 * @param <T> the generic type
	 * @param dMaze the d maze
	 */
	public <T> void displayCrossSection (DrawMaze<T> dMaze);
	
	/**
	 * Display start goal points.
	 *
	 * @param <T> the generic type
	 * @param maze the maze
	 */
	public <T> void displayStartGoalPoints(Searchable<T> maze);
	
	/**
	 * Gets the user command.
	 *
	 * @return the user command
	 */
	public Command getUserCommand();
	
	/**
	 * Sets the user command.
	 *
	 * @param command the new user command
	 */
	public void setUserCommand(Command command);
	
	/**
	 * Exit.
	 */
	public void exit();
}

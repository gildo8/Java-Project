package algorithms.search;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.ArrayList;

import algorithm.generic.State;

/**
 * <h1>Searchable Maze3D</h1>
 * 
 * SearchableMaze3D is a specific problem that implement Searchable with position parameter.<br>
 * Using Object Adapter.
 *
 * @author Gil Doron
 * @since 21-08-2015  
 * 
 */

public class SearchableMaze3D implements Searchable<Position>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The maze. */
	private Maze3d maze;

	/**
	 * Instantiates a new maze3d searchable adapter.
	 *
	 * @param z
	 *            -height
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 */
	public SearchableMaze3D(int x, int y, int z) {

		MyMaze3dGenerator mg = new MyMaze3dGenerator();
		maze = mg.generate(x, y, z);
	}

	/**
	 * Instantiates a new maze3d searchable adapter.
	 *
	 * @param maze
	 *            the maze
	 */
	public SearchableMaze3D(Maze3d maze) {
		super();
		this.maze = maze;
	}

	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze
	 *            the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * gets the start position
	 * 
	 * @return the StartPosition
	 */
	@Override
	public State<Position> getStartPosition() {

		Position start = maze.getStartPosition();
		State<Position> pos = new State<Position>(start);
		return pos;
	}

	/**
	 * gets the goal position
	 * 
	 * @return the GoalPosition
	 */
	@Override
	public State<Position> getGoalPosition() {
		Position goal = maze.getGoalPosition();
		State<Position> pos = new State<Position>(goal);
		return pos;
	}

	/**
	 * gets the All the Possible Moves from current state
	 * 
	 * @return the AllPossibleMoves
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleMoves(State<Position> state) {
		String[] possibleMovesArray = maze.getPossibleMoves(state.getPosition());
		ArrayList<State<Position>> possibleMovesList = new ArrayList<State<Position>>();
		Position p = new Position();
		
		for (int i = 0; i < possibleMovesArray.length; i++) {
			Position possibleMove = p.toPosition(possibleMovesArray[i]);
			State<Position> possibleMoveState = new State<Position> (possibleMove);
			possibleMoveState.setCost(1);
			
			possibleMovesList.add(possibleMoveState);
		}

		return possibleMovesList;
	}

	/**
	 * Prints the maze.
	 */
	public void print() {

		maze.printMaze();
	}

}

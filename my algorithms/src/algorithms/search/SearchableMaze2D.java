package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

import algorithm.generic.State;
import algorithms.demo.Maze2dAdapter;
import algorithms.mazeGenerators.Position;


public class SearchableMaze2D implements Searchable<Position>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Maze2dAdapter myMaze;
	
	public SearchableMaze2D(Maze2dAdapter myMaze) {
		this.myMaze = myMaze;
	}
	
	public Maze2dAdapter getMyMaze() {
		return myMaze;
	}

	public void setMyMaze(Maze2dAdapter myMaze) {
		this.myMaze = myMaze;
	}

	@Override
	public State<Position> getStartPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State<Position> getGoalPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<State<Position>> getAllPossibleMoves(State<Position> current) {
		// TODO Auto-generated method stub
		return null;
	}

}

/*
 * 
 */
package algorithms.search;
import java.util.ArrayList;

import algorithm.generic.State;

/**
 * <h1>Searchable</h1>
 * Each class implementing this interface will allowe to be searched by some type of searcher.
 *
 * @author Gil Doron
 * @param <T> the generic type
 * @since 21-08-2015
 */
public interface Searchable<T> {
	/**
	 * The beginning state
	 * 
	 * @return Start state.
	 */
	public State<T> getStartPosition();

	/**
	 * The goal state
	 * 
	 * @return Goal state.
	 */
	public State<T> getGoalPosition();

	/**
	 * Gets an arraylist of all the possible moves from current state
	 * 
	 * @param current the current
	 * @return the possible moves from current state
	 */
	public ArrayList<State<T>> getAllPossibleMoves(State<T> current);

}

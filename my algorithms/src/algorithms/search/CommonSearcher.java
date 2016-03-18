/*
 * 
 */
package algorithms.search;
import java.util.Comparator;
import java.util.PriorityQueue;

import algorithm.generic.Solution;
import algorithm.generic.State;

/**
 * <h1>Common Searcher</h1>
 * This class implement all the method and data member from Searcher.<br>
 * Each search Algorithm need to extands from this class.
 *
 * @author Gil Doron
 * @param <T> the generic type
 * @since 21-08-2015
 */

public abstract class CommonSearcher<T> implements Searcher<T> {

	/**
	 * The open states.
	 */
	protected PriorityQueue<State<T>> openStates;
	
	/**
	 * The evaluated nodes.
	 */
	protected int evaluatedNodes;
	
	/**
	 * Instantiates a new common searcher.
	 */
	public CommonSearcher(){
		evaluatedNodes = 0;
		openStates = new PriorityQueue<State <T>>(
				new Comparator<State <T>>(){
					
					@Override
					public int compare(State<T> sT1, State<T> sT2){
						return (int) (sT1.getCost() - sT2.getCost());
					}
				}
				);
	}
	
	public abstract Solution<T> search (Searchable <T> searchable);
	
	/**
	 * Pop from open list.
	 *
	 * @return the state
	 */
	protected State<T> popFromOpenList(){
		evaluatedNodes++;
		
		return openStates.poll();
	}
	
	/**
	 * Adds the to open list.
	 *
	 * @param state the state
	 */
	protected void addToOpenList(State<T> state){
		openStates.add(state);
	}
	
	/**
	 * Gets the number of nodes evaluated.
	 *
	 * @return the number of nodes evaluated
	 */
	public int getNumberOfNodesEvaluated(){
		return evaluatedNodes;
	}
	
	/**
	 * <h1>Back Trace Method</h1>
	 * BackTrace Method return a backtrace from the GoalState to the parents.
	 *  
	 *
	 * @param startState the start state
	 * @param goalState the goal state
	 * @return path by array for parametric type T[]
	 */

	protected Solution<T> backTrace(State<T> startState , State<T> goalState){
		Solution<T> solution = new Solution<T>();
		solution.addState(goalState);
		do {
			solution.addState(goalState.getCameFrom());
			goalState = goalState.getCameFrom();
		} while (goalState.getCameFrom() != null);
		return solution;
	}
}

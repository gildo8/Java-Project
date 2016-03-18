/*
 * 
 */
package algorithms.search;
import java.util.ArrayList;
import java.util.HashSet;

import algorithm.generic.Solution;
import algorithm.generic.State;

/**
 * <h1>BFS Algorithm Class </h1>
 * BFS class gives implement of BFS algorithm and used for Searchable specific problem to solve.
 *
 * @author Gil Doron
 * @param <T> the generic type
 * @since 21-08-2015
 */
public class BFS<T> extends CommonSearcher<T> {

	/**
	 * Instantiates a new bfs.
	 */
	public BFS(){
		super();
	}
	
	/**
	 * <h1>BFS - Search Method </h1>
	 * Search method calculate the shortest path from start point to the goal point.<br>
	 * Using BFS Algrithm.
	 * 
	 * @param searchable specific problem
	 * @return Shortest path by array for parametric type T[]
	 * 
	 */
	@Override
	public Solution<T> search(Searchable<T> searchable) {
	
		//addToOpenList(searchable.getStartState());	//Priority queue of states to be evaluated
		openStates.add(searchable.getStartPosition());
		
		HashSet<State<T>> closedSet = new HashSet<State<T>>();	//Set of states already evaluated
		
		while( !(openStates.isEmpty()) ){
			
			//State<T> n = popFromOpenList();	//Remove the best node from openStatesList
			
			evaluatedNodes++;
			State<T> n = openStates.poll();
			
			closedSet.add(n);
			
			if(n.equals(searchable.getGoalPosition())){	
				return backTrace(searchable.getStartPosition(), n);	//back traces through the parents
			}
			
			ArrayList<State<T>> Successors = searchable.getAllPossibleMoves(n);
			
			for (State<T> state : Successors) {
				
				if( !closedSet.contains(state) && !openStates.contains(state) ){
					state.setCameFrom(n);
					state.setCost(n.getCost() + state.getCost());
					//addToOpenList(state);
					openStates.add(state);
				}
				
				//If this new path is better than previous one
				else if( state.getCost() < n.getCost()){
					
					if( !(openStates.contains(state)) ){
						//addToOpenList(state);
						openStates.add(state);
					}	
					else{
						openStates.remove(state);
						//addToOpenList(state);
						openStates.add(state);
					}
				}
			}
		}
		return null;
	}
}

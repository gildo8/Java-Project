/*
 * 
 */
package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import algorithm.generic.Solution;
import algorithm.generic.State;


/**
 * <h1>A* Algorithm Class </h1>
 * ASTAR class gives implement of A* algorithm and used for Searchable specific problem to solve.
 *
 * @author Gil Doron
 * @param <T> the generic type
 * @since 21-08-2015
 */
public class Astar<T> extends CommonSearcher<T> {
	
	/**
	 * The h.
	 */
	private Heuristic<T> h;
	
	/**
	 * Instantiates a new astar.
	 *
	 * @param h the h
	 */
	public Astar(Heuristic<T> h) {
		super();
		this.h = h;
	}
	
	/**<h1>A* - Search Method </h1>
	 * Search method calculate the shortest path from start point to the goal point.<br>
	 * Using A* Algorithm.
	 * 
	 * @param searchable specific problem
	 * @return Shortest path by array for parametric type Solution T
	 * 
	 */
	@Override
	public Solution<T> search(Searchable<T> searchable) {
		HashMap<State<T>, Double> gScore = new HashMap<State<T>,Double>();
		HashSet<State<T>> closedSet = new HashSet<State<T>>();
		State<T> startState = searchable.getStartPosition();
		
		gScore.put(startState, 0.0);
		startState.setCost(gScore.get(startState) + h.evaluate(startState, searchable.getGoalPosition()));
		addToOpenList(startState);
		
		while(openStates.size() > 0){
			State<T> current = popFromOpenList();
			
			if (current.equals(searchable.getGoalPosition())) {
				return backTrace(searchable.getStartPosition(), current);
			}
			//openStates.remove(current);
			closedSet.add(current);
			
			ArrayList<State<T>> nSuccessors = searchable.getAllPossibleMoves(current);
			
			for ( State<T> s : nSuccessors ) {
				
				if (closedSet.contains(s)) {
					continue;
				}
				
				
				double tempGscore = ( gScore.get(current) + s.getCost() );
				
				if ( (!openStates.contains(s)) || tempGscore < gScore.get(s) ) {
					s.setCameFrom(current);
					gScore.put(s, tempGscore);
					s.setCost(gScore.get(s) + h.evaluate(s, searchable.getGoalPosition()));
					
					if ( (!openStates.contains(s) )) {
						addToOpenList(s);
					}
				}
			}
		}
		return null;
	}

}

/*
 * 
 */
package algorithms.search;

import algorithm.generic.Solution;

/**
 * <h1>Searcher</h1>
 * Each class implementing this interface could using search method to search in searchable problems.
 *
 * @author Gil Doron
 * @param <T> the generic type
 * @since 21-08-2015
 */
public interface Searcher<T> {
	
	/**
	 * <h1>Searcher - Search Method</h1>
	 * Search method used for each searchable problem to calculate the path from start point to the goal.
	 *  
	 *
	 * @param searchable the searchable
	 * @return the t[]
	 */
	public Solution<T> search (Searchable <T> searchable);
	
}

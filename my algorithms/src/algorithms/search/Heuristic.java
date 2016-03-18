/*
 * 
 */
package algorithms.search;

import algorithm.generic.State;

/**
 * <h1>Heuristic</h1>
 * Each class implementing this interface could using evaluate method to improve his Algorithm.
 *
 * @author Gil Doron
 * @param <T> the generic type
 * @since 21-08-2015
 */
public interface Heuristic<T> {

	/**
	 * <h1>Evaluate Method </h1>
	 * Evaluate method used for each search algorithm ,each algorithm that implements must Override.
	 *  
	 *
	 * @param sT1 the s t1
	 * @param sT2 the s t2
	 * @return A double number as a result of calculating using a specific formula.
	 */
	public double evaluate (State<T> sT1 , State <T> sT2);
	
}

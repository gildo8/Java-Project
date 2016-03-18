/*
 * 
 */
package algorithms.search;
import algorithm.generic.State;
import algorithms.mazeGenerators.Position;

/**
 * <h1>Maze Manhattan Distance </h1>
 * MazeManhattanDistance implements Heuristic interface.<br>
 * Calculates the "h" in (f=g+h) A* algorithm using air distance 
 *
 * @author Gil Doron
 * @since 21-08-2015  
 * 
 */
public class MazeManhattanDistance implements Heuristic<Position> {

	/**
	 * <h1>Maze Manhattan Distance - Evaluate Method </h1>
	 * This Evaluate method calculating the Distance between two getting State's Position in X , Y , Z
	 * @return The sum of Distance in X , Y , Z
	 * 
	 */
	@Override
	public double evaluate(State<Position> sT1, State<Position> sT2) {
		double evaX;
		double evaY;
		double evaZ;
		double eva;
		
		evaX = Math.abs( sT2.getPosition().getX() - sT1.getPosition().getX() );
		evaY = Math.abs( sT2.getPosition().getY() - sT1.getPosition().getY() );
		evaZ = Math.abs( sT2.getPosition().getZ() - sT1.getPosition().getZ() );
		eva =  ( evaX + evaY + evaZ );
		
		return eva;
	}

}

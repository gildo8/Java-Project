/*
 * 
 */
package algorithms.search;
import algorithm.generic.State;
import algorithms.mazeGenerators.Position;

/**
 * <h1>Maze Air Distance</h1>
 * MazeAirDistance implements Heuristic interface.<br>
 * Calculates the "h" in (f=g+h) A* algorithm using air distance 
 *
 * @author Gil Doron
 * @since 21-08-2015  
 * 
 */
public class MazeAirDistance implements Heuristic<Position> {

	
	/**
	 * <h1>Maze Air Distance - Evaluate Method </h1>
	 * This Evaluate method calculating the Distance between two getting State's Position in X , Y , Z
	 * 
	 */
	@Override
	public double evaluate(State<Position> sT1, State<Position> sT2) {
		double evaX;
		double evaY;
		double evaZ;
		double eva;
		
		evaX = Math.pow(sT2.getPosition().getX() - sT1.getPosition().getX(), 2);
		evaY = Math.pow(sT2.getPosition().getY() - sT1.getPosition().getY(), 2);
		evaZ = Math.pow(sT2.getPosition().getZ() - sT1.getPosition().getZ(), 2);
	 
		eva = Math.sqrt( (evaX + evaY + evaZ) );
		
		return eva;
	}

}

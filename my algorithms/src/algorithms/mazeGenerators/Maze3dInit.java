/*
 * 
 */
package algorithms.mazeGenerators;

/**
 * <h1>Maze3dInit</h1>
 * An Abstract class that Contains everything that in common to all the Mazes.<br>
 * Each specific maze have to implements the generate's method 
 *
 * @author Gil Doron
 * @since 21-08-2015  
 * 
 */
public abstract class Maze3dInit implements Maze3dGenerator {
	
	protected Maze3d maze;
	
	@Override
	public abstract Maze3d generate(int x, int y, int z);

	@Override
	public String measureAlgorithmTime(int x, int y, int z) {
		
		long startTime = System.currentTimeMillis();
		this.generate(x, y, z);
		long endTime = System.currentTimeMillis();
		
		double measureTime = ( (double)(endTime - startTime) / 1000 );
		
		return "Time: "+measureTime;
	}

}

/*
 * 
 */
package algorithms.mazeGenerators;

/**
 * <h1>Maze3dGenerator</h1>
 * Interface that define methods which every class have to implement.
 *
 * @author Gil Doron
 * @since 21-08-2015
 */
public interface Maze3dGenerator {
	
	/**
	 * Generate.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the maze3d
	 */
	Maze3d generate(int x,int y, int z);
	
	/**
	 * Measure algorithm time.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the string
	 */
	String measureAlgorithmTime(int x, int y, int z);
	
}

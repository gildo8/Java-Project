/**
 * @Project: mvc
 * @Class : DrawMaze.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

/**
 * The Interface DrawMaze.
 *
 * @param <T>
 *            the generic type
 */
public interface DrawMaze<T> {

	/**
	 * Gets the maze data.
	 *
	 * @return the maze data
	 */
	T getMazeData();
}

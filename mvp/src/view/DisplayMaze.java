/**
 * @Project: mvp
 * @Class : DisplayMaze.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;


/**
 * The Interface DisplayMaze , gets a drawable object data and it's implementation display maze.
 *
 * @param <T> the generic type
 */
public interface DisplayMaze<T> {

	/**
	 * Gets the displayer maze.
	 *
	 * @param dMaze the d maze
	 */
	void getDisplayerMaze(DrawMaze<T> dMaze);
	
	/**
	 * Display maze.
	 */
	void displayMaze();
}

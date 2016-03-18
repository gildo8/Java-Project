/**
 * @Project: mvc
 * @Class : Controller.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package controller;

import java.util.HashMap;

/**
 * The Interface Controller.
 */
public interface Controller {

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	HashMap<String, Command> getCommand();
	
	/**
	 * Start.
	 */
	public void start();
	
}

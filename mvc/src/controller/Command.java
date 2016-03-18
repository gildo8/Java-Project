/**
 * @Project: mvc
 * @Class : Command.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package controller;

/**
 * The Interface Command.
 */
public interface Command {

	/**
	 * Do command.
	 *
	 * @param args
	 *            the args
	 */
	public void doCommand(String[] args);
}

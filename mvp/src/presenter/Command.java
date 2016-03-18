/**
 * @Project: mvp
 * @Class : Command.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package presenter;


/**
 * The Interface Command , each class that implements that methods responsible for requesting a specific command from model.
 * It sends that to the View , it's define  the behavior of Commands.
 */
public interface Command {

	/**
	 * Do command.
	 */
	public void doCommand();

	/**
	 * Sets the command arguments.
	 *
	 * @param args the new command arguments
	 */
	public void setCommandArguments(String[] args);
	
}

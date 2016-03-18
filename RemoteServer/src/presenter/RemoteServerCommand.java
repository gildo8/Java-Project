/**
 * @Project: RemoteServer
 * @Class : RemoteServerCommand.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package presenter;

/**
 * The Interface RemoteServerCommand.
 */
public interface RemoteServerCommand {
	/**
	 * Do command.
	 */
	void doCommand();
	
	/**
	 * Sets the arguments.
	 *
	 * @param args
	 *            the new arguments
	 */
	public void setArguments(String args);

}


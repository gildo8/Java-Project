/**
 * @Project: RemoteServer
 * @Class : View.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.util.concurrent.ConcurrentHashMap;

import presenter.RemoteServerCommand;


/**
 * The Interface View.
 */
public interface View {
	
	
	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public RemoteServerCommand getCommand();
	
	/**
	 * Sets the commands.
	 *
	 * @param commandMap
	 *            the command map
	 */
	public void setCommands(ConcurrentHashMap<String, RemoteServerCommand> commandMap);
	
	/**
	 * Display Message by Message Box.
	 *
	 * @param msg
	 *            the msg
	 */
	public void Display(String msg);
	
	/**
	 * Save data.
	 *
	 * @param data
	 *            the data
	 */
	public void saveData(String data);
	
	/**
	 * Adds the client.
	 *
	 * @param Client
	 *            the client
	 */
	public void addClient(String Client);
	
	/**
	 * Removes the client.
	 *
	 * @param Client
	 *            the client
	 */
	public void removeClient(String Client);
	
	/**
	 * Sets the user command.
	 *
	 * @param userCommand
	 *            the new user command
	 */
	public void setUserCommand(RemoteServerCommand userCommand);

	/**
	 * Display status.
	 *
	 * @param msg
	 *            the msg
	 */
	void DisplayStatus(String msg);

}

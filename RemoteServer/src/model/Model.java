/**
 * @Project: RemoteServer
 * @Class : Model.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package model;

/**
 * The Interface Model.
 */
public interface Model {

	/**
	 * Gets the status of client.
	 *
	 * @param client
	 *            the client
	 */
	public void getStatusClient(String client);
	
	/**
	 * Disconnect client.
	 *
	 * @param client
	 *            the client
	 */
	public void DisconnectClient(String client);
	
	/**
	 * Start Remote Server to Getting Clients
	 */
	public void StartServer();
	
	/**
	 * Disconnect server.
	 */
	public void DisconnectServer();
	
	/**
	 * Exit.
	 */
	public void exit();
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String[] getData();

}

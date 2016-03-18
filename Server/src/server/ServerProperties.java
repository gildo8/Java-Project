/**
 * @Project: Server
 * @Class : ServerProperties.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package server;

import java.io.Serializable;


/**
 * The Class ServerProperties.
 */
public class ServerProperties implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The port. */
	private int port;
	
	/** The num of clients. */
	private int numOfClients;
	
	/**
	 * Instantiates a new server properties.
	 */
	public ServerProperties() {
		port = 5400;
		numOfClients = 20;
	}
	
	/**
	 * Instantiates a new server properties.
	 *
	 * @param port
	 *            the port
	 * @param numOfClients
	 *            the num of clients
	 */
	public ServerProperties(int port,int numOfClients) {
		this.port=port;
		this.numOfClients=numOfClients;
	}
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Sets the port.
	 *
	 * @param port
	 *            the new port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * Gets the num of clients.
	 *
	 * @return the num of clients
	 */
	public int getNumOfClients() {
		return numOfClients;
	}
	
	/**
	 * Sets the num of clients.
	 *
	 * @param numOfClients
	 *            the new num of clients
	 */
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
}

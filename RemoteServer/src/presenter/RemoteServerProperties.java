/**
 * @Project: RemoteServer
 * @Class : RemoteServerProperties.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package presenter;

import java.io.Serializable;


/**
 * The Class Remote Server Properties represents all the necessaries
 * properties to connect to the server
 */
public class RemoteServerProperties implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Port_ for_ clients. */
	private int Port_For_Clients;
	
	/** The Port_ for_ server. */
	private int Port_For_Server;
	
	/** The Number_ of_ clients. */
	private int Number_Of_Clients;
	
	/** The Port_ for_ remote_ control. */
	private int Port_For_Remote_Control;
	
	/** The ip. */
	private String IP;
	
	/**
	 * Gets the port on which server listens.
	 *
	 * @return the port on which server listens
	 */
	public int getPortOnWhichServerListens() {
		return Port_For_Server;
	}
	
	/**
	 * Sets the port on which server listens.
	 *
	 * @param Port_For_Server
	 *            the new port on which server listens
	 */
	public void setPortOnWhichServerListens(int Port_For_Server) {
		this.Port_For_Server = Port_For_Server;
	}
	
	
	/**
	 * Instantiates a new remote server properties.
	 */
	public RemoteServerProperties() {
		Port_For_Clients=5400;
		Number_Of_Clients=32;
		Port_For_Remote_Control = 1234;
	}
	
	/**
	 * Instantiates a new remote server properties.
	 *
	 * @param Port_For_Clients
	 *            the port_ for_ clients
	 * @param Number_Of_Clients
	 *            the number_ of_ clients
	 * @param Port_For_Remote_Control
	 *            the port_ for_ remote_ control
	 */
	public RemoteServerProperties(int Port_For_Clients,int Number_Of_Clients,int Port_For_Remote_Control) {
		this.Port_For_Clients = Port_For_Clients;
		this.Number_Of_Clients = Number_Of_Clients;
		this.Port_For_Remote_Control = Port_For_Remote_Control;
	}
	
	/**
	 * Gets the remote control port listener.
	 *
	 * @return the remote control port listener
	 */
	public int getRemoteControlPortListener() {
		return Port_For_Remote_Control;
	}
	
	/**
	 * Sets the remote control port listener.
	 *
	 * @param Port_For_Remote_Control
	 *            the new remote control port listener
	 */
	public void setRemoteControlPortListener(int Port_For_Remote_Control) {
		this.Port_For_Remote_Control = Port_For_Remote_Control;
	}
	
	/**
	 * Gets the port server clients.
	 *
	 * @return the port server clients
	 */
	public int getPortServerClients() {
		return Port_For_Clients;
	}
	
	/**
	 * Sets the port server clients.
	 *
	 * @param port
	 *            the new port server clients
	 */
	public void setPortServerClients(int port) {
		this.Port_For_Clients = port;
	}
	
	/**
	 * Gets the num of clients.
	 *
	 * @return the num of clients
	 */
	public int getNumOfClients() {
		return Number_Of_Clients;
	}
	
	/**
	 * Sets the num of clients.
	 *
	 * @param Number_Of_Clients
	 *            the new num of clients
	 */
	public void setNumOfClients(int Number_Of_Clients) {
		this.Number_Of_Clients = Number_Of_Clients;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * Sets the ip.
	 *
	 * @param iP
	 *            the new ip
	 */
	public void setIP(String iP) {
		IP = iP;
	}
}


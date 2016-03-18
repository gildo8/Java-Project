/**
 * @Project: Server
 * @Class : ClientHandler.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package server;

import java.net.Socket;

/**
 * The Interface ClientHandler.
 */
public interface ClientHandler {
	
	/**
	 * Handle Maze's client to get commands from the client and to management all active connections.
	 *
	 * @param client
	 *            the client
	 */
	void handleClient(Socket client);
}

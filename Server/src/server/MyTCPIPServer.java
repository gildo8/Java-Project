/**
 * @Project: Server
 * @Class : MyTCPIPServer.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;


import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;


/**
 * The Class MyTCPIPServer.
 */
public class MyTCPIPServer {
	
	/** The server properties. */
	ServerProperties serverProperties;
	
	/** The client handler. */
	ClientHandler clientHandler;
	
	private volatile boolean stopped;

	/**
	 * Instantiates a new my tcpip server.
	 *
	 * @param serverProperties
	 *            the server properties
	 * @param clientHandler
	 *            the client handler
	 */
	public MyTCPIPServer(ServerProperties serverProperties,ClientHandler clientHandler) {
		this.serverProperties=serverProperties;
		stopped = false;
		this.clientHandler=clientHandler;
	}
	
	/**
	 * Start The Server and set it to listen on a getting port and wait for Clients
	 */
	public void startServer()
	{
		ServerSocket server;
		try {
			server = new ServerSocket(serverProperties.getPort());
			System.out.println("The Server is now Listening on Port: " + serverProperties.getPort() + " For Clients.");
			ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(serverProperties.getNumOfClients()));
			server.setSoTimeout(500);// changed from 500 to 0 which is infinite timoute
			while(!stopped)
			{
				try {
					final Socket someClient=server.accept();
					System.out.println("New client" + " Port: " + someClient.getPort() + " Ip: " + someClient.getInetAddress().getHostAddress());
					threadPool.execute(new Runnable() {
						
						@Override
						public void run() {
							try {
								clientHandler.handleClient(someClient);
								someClient.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					
				} catch (SocketTimeoutException e) {
				}
			}
			threadPool.shutdownNow();
			server.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Stopped server.
	 */
	public void stoppedServer()
	{
		stopped = true;
	}
}

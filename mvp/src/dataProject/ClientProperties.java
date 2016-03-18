/**
 * @Project: mvp
 * @Class : ClientProperties.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package dataProject;

import java.io.Serializable;


/**
 * The Class ClientProperties.
 */
public class ClientProperties implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Enum MazeGenerator.
	 */
	public enum MazeGenerator{
		/** The dfs. */
		DFS,
		
		/** The random. */
		RANDOM
	}

	/**
	 * The Enum UI.
	 */
	public enum UI{
		/** The cli. */
		CLI,
		
		/** The gui. */
		GUI
	}

	/**
	 * The Enum MazeSolver.
	 */
	public enum MazeSolver{
		/** The manhattan astar. */
		MANHATTAN_ASTAR,
		
		/** The euclidian astar. */
		EUCLIDIAN_ASTAR,	
		
		/** The bfs. */
		BFS
	}
	
	/**
	 * The Enum Access.
	 */
	public enum Access{
	 /** The remote server. */
	 REMOTE_SERVER, 
 
	 /** The local. */
	 LOCAL
	}

	/** The Number of clients. */
	public int NumberOfClients;

	/** The Server ip. */
	public String ServerIP;

	/** The Server port. */
	public int ServerPort;

	/** The Generator. */
	public MazeGenerator Generator;

	/** The Ui. */
	public UI Ui;

	/** The Solver. */
	public MazeSolver Solver;
	
	/** The Access. */
	public Access Access;
	
	/**
	 * Instantiates a new client properties.
	 */
	@SuppressWarnings("static-access")
	public ClientProperties() {
		this.NumberOfClients = VariablesData.DEFAULT_POOL_SIZE;
		this.Ui = UI.CLI;
		this.Generator = MazeGenerator.DFS;
		this.Solver = MazeSolver.BFS;
		this.Access = Access.LOCAL;
	}
	
	/**
	 * Instantiates a new client properties.
	 *
	 * @param poolSize the pool size
	 * @param serverIP the server ip
	 * @param serverPort the server port
	 * @param generator the generator
	 * @param ui the ui
	 * @param solver the solver
	 */
	public ClientProperties(int poolSize, String serverIP, int serverPort,
			MazeGenerator generator, UI ui, MazeSolver solver) {
		 
		this.NumberOfClients = poolSize;
		this.ServerIP = serverIP;
		this.ServerPort = serverPort;
		this.Generator = generator;
		this.Ui = ui;
		this.Solver = solver;
	}

	/**
	 * Gets the server ip.
	 *
	 * @return the server ip
	 */
	public String getServerIP() {
		return ServerIP;
	}

	/**
	 * Sets the server ip.
	 *
	 * @param serverIP the new server ip
	 */
	public void setServerIP(String serverIP) {
		this.ServerIP = serverIP;
	}

	/**
	 * Gets the server port.
	 *
	 * @return the server port
	 */
	public int getServerPort() {
		return ServerPort;
	}

	/**
	 * Sets the server port.
	 *
	 * @param serverPort the new server port
	 */
	public void setServerPort(int serverPort) {
		this.ServerPort = serverPort;
	}

	/**
	 * Gets the generator.
	 *
	 * @return the generator
	 */
	public MazeGenerator getGenerator() {
		return Generator;
	}

	/**
	 * Sets the generator.
	 *
	 * @param generator the new generator
	 */
	public void setGenerator(MazeGenerator generator) {
		this.Generator = generator;
	}

	/**
	 * Gets the ui.
	 *
	 * @return the ui
	 */
	public UI getUi() {
		return Ui;
	}

	/**
	 * Sets the ui.
	 *
	 * @param ui the new ui
	 */
	public void setUi(UI ui) {
		this.Ui = ui;
	}

	/**
	 * Gets the solver.
	 *
	 * @return the solver
	 */
	public MazeSolver getSolver() {
		return Solver;
	}

	/**
	 * Sets the solver.
	 *
	 * @param solver the new solver
	 */
	public void setSolver(MazeSolver solver) {
		this.Solver = solver;
	}

	/**
	 * Gets the pool size.
	 *
	 * @return the pool size
	 */
	public int getPoolSize() {
		return NumberOfClients;
	}

	/**
	 * Sets the pool size.
	 *
	 * @param poolSize the new pool size
	 */
	public void setPoolSize(int poolSize) {
		this.NumberOfClients = poolSize;
	}



	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the access.
	 *
	 * @return the access
	 */
	public Access getAccess() {
		return Access;
	}

	/**
	 * Sets the access.
	 *
	 * @param access the new access
	 */
	public void setAccess(Access access) {
		this.Access = access;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "***** Properties: *****\nAccess : " + Access + "\nUi : " + Ui
				+ "\nServer IP : " + ServerIP + "\nServer Port : " + ServerPort
				+ "\nNumber Of Clients : " + NumberOfClients + "\nMaze Generator : " + Generator
				+ "\nMaze Solver : " + Solver + "\n***********************\n";
	}

}

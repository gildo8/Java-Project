/**
 * @Project: mvp
 * @Class : ClientModel.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.SearchableMaze2D;
import algorithms.search.SearchableMaze3D;
import algorithm.generic.Solution;
import dataProject.ClientProperties;
import dataProject.ServerConstant;
import dataProject.VariablesData;

/**
 * The Class ClientModel , this class is kind of implementation of 	Model for the server's side.
 * When request come from client it's go direct to the Remote Server by query
 * All the proccess of Functunallity happend in the server.
 */
public class ClientModel extends Observable implements Model {

	/** The my maze. */
	Maze3d myMaze;
	
	/** The solution. */
	Solution<Position> solution;
	
	/** The variables args. */
	String [] variablesArgs;
	
	/** The Client properties. */
	private ClientProperties ClientProperties;	

	/**
	 * Instantiates a new client model.
	 *
	 * @param ClientProperties the client properties
	 */
	public ClientModel(ClientProperties ClientProperties) {
		this.ClientProperties = ClientProperties;
		this.variablesArgs = new String[2];
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInMemory(java.lang.String)
	 */
	@Override
	public int getModelSizeInMemory(String name) throws IOException {

		String data = name;
		return (int)queryServer(ClientProperties.ServerIP, ClientProperties.ServerPort, ServerConstant.GET_MODEL_SIZE_IN_MEMORY, data, "");
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInFile(java.lang.String)
	 */
	@Override
	public long getModelSizeInFile(String name) {

		String data = name;
		return (int)queryServer(ClientProperties.ServerIP, ClientProperties.ServerPort, ServerConstant.GET_MODEL_SIZE_IN_FILE, data, "");
	}


	/* (non-Javadoc)
	 * @see model.Model#saveModel(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveModel(String name, String fileName) {
		String data = name + " " + fileName;
		String [] valid = (String[])queryServer(ClientProperties.ServerIP, ClientProperties.ServerPort, ServerConstant.SAVE_MAZE, data, "");
		variablesArgs[0] = valid[0];
		variablesArgs[1] = fileName;
		setChanged();
		notifyObservers(variablesArgs);
	}


	/* (non-Javadoc)
	 * @see model.Model#loadModel(java.lang.String, java.lang.String)
	 */
	@Override
	public Maze3d loadModel(String fileName, String name) throws IOException, FileNotFoundException {
		this.myMaze = null;
		String data = name + " " + fileName;
		this.myMaze = (Maze3d)queryServer(ClientProperties.ServerIP, ClientProperties.ServerPort, ServerConstant.LOAD_MAZE, data, "");
		variablesArgs[0] = VariablesData.MODEL_LOADED;
		variablesArgs[1]= name;
		setChanged();
		notifyObservers(variablesArgs);
		return this.myMaze;
	}



	/* (non-Javadoc)
	 * @see model.Model#solveModel(java.lang.String)
	 */
	@Override
	public void solveModel(String name) {

		String property=null;
		switch(ClientProperties.getSolver())
		{
		case BFS:
			property="BFS";
			break;
		case MANHATTAN_ASTAR:
			property="MANHATTAN_ASTAR";
			break;
		case EUCLIDIAN_ASTAR:
			property="EUCLIDIAN_ASTAR";
			break;
		default:
			return;
		}
		

		@SuppressWarnings("unchecked")
		Solution<Position> solution = (Solution<Position>) queryServer(ClientProperties.getServerIP(),ClientProperties.getServerPort(),ServerConstant.SOLVE_MAZE,name,property);
		
		if(solution==null)
		{
			variablesArgs[0] = ServerConstant.DISCONNECT;
			variablesArgs[1] = name;
			setChanged();
			notifyObservers(variablesArgs);
			return;
		}

		this.solution = solution;

		variablesArgs[0] = VariablesData.MODEL_SOLVED;
		variablesArgs[1] = name;
		setChanged();
		notifyObservers(variablesArgs);

	}

	/* (non-Javadoc)
	 * @see model.Model#generateModel(java.lang.String, java.lang.String[])
	 */
	@Override
	public void generateModel(String name, String[] params) {

		String property=null;
		switch(ClientProperties.getGenerator())
		{
		case DFS:
			property="DFS";
			break;
		case RANDOM:
			property="RANDOM";
			break;
		default:
			return;
		}

		String x = params[0];
		String y = params[1];
		String z = params[2];


		Maze3d myMaze=(Maze3d)queryServer(ClientProperties.getServerIP(),ClientProperties.getServerPort(),ServerConstant.GENERATE_MAZE,name+","+x+","+y+","+z ,property);
		if(myMaze==null)
		{
			variablesArgs[0] = ServerConstant.DISCONNECT;
			variablesArgs[1] = name;
			setChanged();
			notifyObservers(variablesArgs);
			return;
		}
		this.myMaze = myMaze;

		variablesArgs[0] = VariablesData.MODEL_GENERATED;
		variablesArgs[1] = name;
		setChanged();
		notifyObservers(variablesArgs);
	}

	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Solution<Position> getSolution(String name) {

		return (Solution<Position>)this.solution;
	}

	/* (non-Javadoc)
	 * @see model.Model#getNameToModel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Searchable<Position> getNameToModel(String name) {

		Maze3d myMaze = (Maze3d)queryServer(ClientProperties.ServerIP, ClientProperties.ServerPort, ServerConstant.MAZE_EXISTS, name, "");
		SearchableMaze3D myMazeAdapter = new SearchableMaze3D(myMaze);
		return myMazeAdapter;
	}





	/* (non-Javadoc)
	 * @see model.Model#CrossSectionBy(java.lang.String, java.lang.String, int)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Searchable<Position> CrossSectionBy(String name, String section, int index) {

		SearchableMaze2D myMazeAdapter = (SearchableMaze2D)queryServer(ClientProperties.ServerIP, ClientProperties.ServerPort, ServerConstant.GET_CROSS_SECTION, name, index + " "  + section);
		variablesArgs[0] = VariablesData.MODEL_GENERATED;
		variablesArgs[1] = name;
		setChanged();
		notifyObservers(variablesArgs);

		return myMazeAdapter;
	}
	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() throws IOException {}
	
	/**
	 * Query server , trying to connect to the getting IP and PORT and sends query to do.
	 *
	 * @param serverIP the server ip
	 * @param serverPort the server port
	 * @param command the command
	 * @param data the data
	 * @param property the property
	 * @return the object
	 */
	private Object queryServer(String serverIP,int serverPort,String command,String data,String property)
	{
		Object result=null;
		Socket server;			
		try {
			System.out.println("Connecting to Server...");
			server = new Socket(serverIP,serverPort);
			PrintWriter writerToServer=new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
			writerToServer.println(command);
			writerToServer.flush();
			writerToServer.println(property);
			writerToServer.flush();
			writerToServer.println(data);
			writerToServer.flush();
			ObjectInputStream inputDecompressed;
			inputDecompressed = new ObjectInputStream((server.getInputStream()));
			result=inputDecompressed.readObject();
			if(result.toString().contains("disconnect"))
			{
				setChanged();
				notifyObservers(ServerConstant.DISCONNECT);
			}
			writerToServer.close();
			inputDecompressed.close();
			server.close();
		} catch (ClassNotFoundException | IOException  e) {
			e.printStackTrace();
		}
		return result;
	}
}

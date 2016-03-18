/**
 * @Project: mvp
 * @Class : MyObservableModel.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package model;


import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import algorithms.demo.Maze2dAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Astar;
import algorithms.search.BFS;
import algorithms.search.Heuristic;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searchable;
import algorithms.search.SearchableMaze2D;
import algorithms.search.SearchableMaze3D;
import algorithm.generic.Solution;
import dataProject.ClientProperties;
import dataProject.VariablesData;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;


/**
 * The Class MyObservableModel , this class is kind of implementation of Model for Local uses.
 * This class is Observable and all the requests from the user happening on local.
 */
public class MyObservableModel extends Observable implements Model  { 

	/** The name to maze. */
	private HashMap<String, Maze3d> nameToMaze;

	/** The name to file name. */
	private HashMap<String, String> nameToFileName;

	/** The name to solution. */
	private HashMap<String, Solution<Position>>nameToSolution;

	/** The maze to solution. */
	private HashMap<Maze3d, Solution<Position>> mazeToSolution;

	/** The my compressor. */
	MyCompressorOutputStream myCompressor;

	/** The my decompressor. */
	MyDecompressorInputStream myDecompressor;

	/** The Variables data args. */
	String [] VariablesDataArgs;

	/** The my xml encoder. */
	XMLEncoder myXMLEncoder;

	/** The preferences. */
	private ClientProperties preferences;


	/** The executor. */
	private ListeningExecutorService executor;

	/**
	 * Instantiates a new my observable model.
	 *
	 * @param preferences the preferences
	 */
	public MyObservableModel(ClientProperties preferences) {

		this.nameToMaze = new HashMap<String, Maze3d>();
		this.nameToFileName = new HashMap<String, String>();
		this.nameToSolution = new HashMap<String, Solution<Position>>();
		this.mazeToSolution = new HashMap<Maze3d, Solution<Position>>();
		this.myCompressor = null;
		this.myDecompressor = null;
		this.VariablesDataArgs = new String[2];
		loadSolution();
		this.preferences = preferences;
		executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(preferences.NumberOfClients));
	}


	/**
	 * Sets the name to solution.
	 *
	 * @param nameToSolution the name to solution
	 */
	public void setNameToSolution(HashMap<String, Solution<Position>> nameToSolution) {
		this.nameToSolution = nameToSolution;
	}



	/**
	 * Gets the name to maze.
	 *
	 * @return the name to maze
	 */
	public HashMap<String, Maze3d> getNameToMaze() {
		return nameToMaze;
	}


	/**
	 * Sets the name to maze.
	 *
	 * @param nameToMaze the name to maze
	 */
	public void setNameToMaze(HashMap<String, Maze3d> nameToMaze) {
		this.nameToMaze = nameToMaze;
	}


	/* (non-Javadoc)
	 * @see model.Model#saveModel(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveModel(String name, String fileName) {

		String path = ".\\resources\\mazes\\";
		try {
			this.myCompressor = new MyCompressorOutputStream(new FileOutputStream(path+fileName));
			nameToFileName.put(name, fileName);
			this.myCompressor.write(nameToMaze.get(name).toByteArray());
			myCompressor.close();
		} catch (FileNotFoundException e) {
			VariablesDataArgs[0] = VariablesData.FILE_NOT_FOUND; 
			setChanged();
			notifyObservers(VariablesDataArgs);

		} catch (IOException e) {
			VariablesDataArgs[0] = VariablesData.NO_MODEL_FOUND;

			setChanged();
			notifyObservers(VariablesDataArgs);

		} finally {
			try {
				myCompressor.close();
				VariablesDataArgs[0] = VariablesData.MODEL_SAVED;
				VariablesDataArgs[1] = fileName;
				setChanged();
				notifyObservers(VariablesDataArgs);
			} catch (IOException e) {
				VariablesDataArgs[0] = VariablesData.ERROR_CLOSING_FILE;
				setChanged();
				notifyObservers(VariablesDataArgs);
			}
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public  Solution<Position> getSolution(String name){

		if(nameToSolution.get(name) != null){
			return nameToSolution.get(name);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInMemory(java.lang.String)
	 */
	@Override
	public int getModelSizeInMemory(String name) throws IOException {

		int size;
		size = nameToMaze.get(name).toByteArray().length;
		return size;

	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInFile(java.lang.String)
	 */
	@Override
	public long getModelSizeInFile(String name) {

		if(name != null){
			File myFile = new File(name);
			return myFile.length();
		}
		else
			return 0;
	}

	/* (non-Javadoc)
	 * @see model.Model#solveModel(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void solveModel(String name) {

		if(this.preferences==null)
		{
			VariablesDataArgs[0] = VariablesData.PROPERTIES_ARE_NO_SET;
			setChanged();
			notifyObservers(VariablesDataArgs);
			return;
		}

		ListenableFuture<Solution<Position>> futureSolution = null;

		Maze3d myMaze = nameToMaze.get(name);

		if(myMaze != null){
			Solution<Position> solution = new Solution<Position>();

			if((solution = mazeToSolution.get(myMaze)) != null){
				nameToSolution.put(name, solution);
				VariablesDataArgs[0] = VariablesData.MODEL_SOLVED;
				VariablesDataArgs[1] = name;
				setChanged();
				notifyObservers(VariablesDataArgs);
			}
		}
		switch(preferences.getSolver()){

		case BFS:
			SearchableMaze3D myAdapter = new SearchableMaze3D(myMaze);

			futureSolution=executor.submit(new Callable<Solution<Position>>() {

				@Override
				public Solution<Position> call() throws Exception {
					BFS <Position> myBfs = new BFS<Position>();

					return myBfs.search(myAdapter);
				}
			});

			break;
		case MANHATTAN_ASTAR:

			SearchableMaze3D myAdapter1 = new SearchableMaze3D(myMaze);
			Heuristic<Position> myHeuristic = new MazeManhattanDistance();
			futureSolution=executor.submit(new Callable<Solution<Position>>() {

				@Override
				public Solution<Position> call() throws Exception {
					Astar<Position> myAstar = new Astar<Position>(myHeuristic);
					return myAstar.search(myAdapter1);
				}

			});

			break;
			
		case EUCLIDIAN_ASTAR:

			SearchableMaze3D myAdapter2 = new SearchableMaze3D(myMaze);
			Heuristic<Position> myHeuristic1 = new MazeAirDistance();
			futureSolution=executor.submit(new Callable<Solution<Position>>() {

				@Override
				public Solution<Position> call() throws Exception {
					Astar<Position> myAstar = new Astar<Position>(myHeuristic1);
					return myAstar.search(myAdapter2);
				}

			});

			break;
		default:
			VariablesDataArgs[0] = VariablesData.NO_MODEL_FOUND; 
			setChanged();
			notifyObservers(VariablesDataArgs);
			break;
		}
		 if(futureSolution!=null){
				
						 Futures.addCallback(futureSolution, new FutureCallback<Solution<Position>>() {
				
							 @Override
							 public void onFailure(Throwable arg0) {
								 VariablesDataArgs[0] = VariablesData.MODEL_ERROR; 
								 setChanged();
								 notifyObservers(VariablesDataArgs);
							 }
				
				
							 @Override
							 public void onSuccess(Solution<Position> arg0) {
								
								 mazeToSolution.put(myMaze,arg0);
								 nameToSolution.put(name, arg0);
							 VariablesDataArgs[0] = VariablesData.MODEL_SOLVED;
								 VariablesDataArgs[1] = name;
								 setChanged();
								 notifyObservers(VariablesDataArgs);
							 }		
						 });
					 }

	}
	

	/* (non-Javadoc)
	 * @see model.Model#loadModel(java.lang.String, java.lang.String)
	 */
	@Override
	public Maze3d loadModel(String fileName, String name) throws IOException, FileNotFoundException{

		String path = ".\\resources\\mazes\\";
		ArrayList<Byte> myStream = new ArrayList<Byte>();
		byte [] byteArray = new byte[1024];

		MyDecompressorInputStream myDecompressor = new MyDecompressorInputStream(new FileInputStream(path+fileName));
		while(myDecompressor.read(byteArray) > 0){

			for (byte b : byteArray) {
				myStream.add(b);
			}
		}
		myDecompressor.close();
		byte[] data = new byte[myStream.size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) myStream.get(i);
		}

		Maze3d myMaze = new Maze3d(data);
		nameToMaze.put(name, myMaze);
		VariablesDataArgs[0] = VariablesData.MODEL_LOADED; 
		VariablesDataArgs[1]= name;
		setChanged();
		notifyObservers(VariablesDataArgs);

		return myMaze;
	}



	/* (non-Javadoc)
	 * @see model.Model#CrossSectionBy(java.lang.String, java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Searchable<Position> CrossSectionBy(String name, String section, int index) {

		Maze3d maze3d=nameToMaze.get(name);

		if(maze3d == null){

			return null;
		}
		try{
			Maze2dAdapter maze2d = null;

			switch (section) {

			case "z":
				if(index > 0 && index > maze3d.getz())
					maze2d= new Maze2dAdapter(maze3d.getCrossSectionByZ(index));
				break;

			case "x": if(index > 0 && index < maze3d.getx()){
				maze2d = new Maze2dAdapter(maze3d.getCrossSectionByX(index));
			  }break;

			case "y": if(index > 0 && index > maze3d.gety()){
				maze2d = new Maze2dAdapter(maze3d.getCrossSectionByY(index));
		      }break;

			default:

				return null;

			}

			SearchableMaze2D myMazeAdapter = new SearchableMaze2D(maze2d);
			return myMazeAdapter;

		}catch (ArrayIndexOutOfBoundsException | NullPointerException a){

			return null;
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getNameToModel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Searchable<Position> getNameToModel(String name) {

		Maze3d maze = nameToMaze.get(name);
		if(maze != null){
			SearchableMaze3D myMaze = new SearchableMaze3D(maze);
			return  myMaze;
		}

		return null;
	}



	/* (non-Javadoc)
	 * @see model.Model#generateModel(java.lang.String, java.lang.String[])
	 */
	@SuppressWarnings("static-access")
	@Override
	public void generateModel(String name, String [] params) {

		if(this.preferences==null)
		{
			VariablesDataArgs[0] = VariablesData.PROPERTIES_ARE_NO_SET;
			setChanged();
			notifyObservers(VariablesDataArgs);
			return;
		}

		ListenableFuture<Maze3d> futureMaze=null;

		switch(preferences.getGenerator()){

		case DFS:

			futureMaze = executor.submit(new Callable<Maze3d>() {

				@Override
				public Maze3d call() throws Exception {

					int x = Integer.parseInt(params[0]) ;
					int y = Integer.parseInt(params[1]) ;
					int z = Integer.parseInt(params[2]) ;

					MyMaze3dGenerator myGenerator = new MyMaze3dGenerator();
					SearchableMaze3D myAdapter = new SearchableMaze3D(myGenerator.generate(x, y, z));
					return myAdapter.getMaze();
				}	

			});
			break;
		case RANDOM:

			futureMaze = executor.submit(new Callable<Maze3d>() {

				@Override
				public Maze3d call() throws Exception {

					int x = Integer.parseInt(params[0]) ;
					int y = Integer.parseInt(params[1]) ;
					int z = Integer.parseInt(params[2]) ;

					SimpleMaze3dGenerator myGenerator = new SimpleMaze3dGenerator();
					SearchableMaze3D myAdapter = new SearchableMaze3D(myGenerator.generate(x, y, z));
					return myAdapter.getMaze();
				}	

			});

			break;

		default:
			break;
		}


		if(futureMaze!=null)
		{		
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			Futures.addCallback(futureMaze, new FutureCallback<Maze3d>() {

				@Override
				public void onFailure(Throwable arg0) {
					
					VariablesDataArgs[0] = VariablesData.MODEL_ERROR;
					setChanged();
					notifyObservers(VariablesDataArgs);
				}

				@Override
				public void onSuccess(Maze3d arg0) {
					
					nameToMaze.put(name, arg0);
					VariablesDataArgs[0] = VariablesData.MODEL_GENERATED;
					VariablesDataArgs[1] = name;
					setChanged();
					notifyObservers(VariablesDataArgs);
				}

			});
		}
	}

	/**
	 * Gets the name to solution.
	 *
	 * @return the name to solution
	 */
	public HashMap<String, Solution<Position>> getNameToSolution() {
		return nameToSolution;
	}


	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() throws IOException {

		if(myCompressor!=null)
			myCompressor.close();
		if(myDecompressor!=null)
			myDecompressor.close();

		saveSolution();
		savePreferences();
		executor.shutdownNow();
		VariablesDataArgs[0]= VariablesData.MODEL_EXIT;
		setChanged();
		notifyObservers(VariablesDataArgs);
		
		//Close the program
		System.out.println("Bye Bye!");
		System.exit(0);
	}

	/**
	 * Save solution.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void saveSolution() throws FileNotFoundException, IOException{
		try {
			FileOutputStream fos=new FileOutputStream(VariablesData.FILE_PATH);
			GZIPOutputStream gzos=new GZIPOutputStream(fos);
			ObjectOutputStream out=new ObjectOutputStream(gzos);
			out.writeObject(mazeToSolution);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.getStackTrace();
		}

	}

	/**
	 * Load solution.
	 */
	@SuppressWarnings("unchecked")
	private void loadSolution() {

		try {
			FileInputStream fos=new FileInputStream(VariablesData.FILE_PATH);
			GZIPInputStream gzos=new GZIPInputStream(fos);
			ObjectInputStream out=new ObjectInputStream(gzos);
			mazeToSolution = (HashMap<Maze3d, Solution<Position>>) out.readObject();
			out.close();
		}
		catch (  IOException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save preferences.
	 */
	public void savePreferences(){

		try {

			myXMLEncoder = new XMLEncoder(
					new BufferedOutputStream(
							new FileOutputStream(VariablesData.FILE_PATH + "properties.xml")));
			myXMLEncoder.flush();
			myXMLEncoder.writeObject(preferences);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			myXMLEncoder.close();
		}
	}

}



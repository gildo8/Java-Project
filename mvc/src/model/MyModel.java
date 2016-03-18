/**
 * @Project: mvc
 * @Class : MyModel.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.demo.Maze2dAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.BFS;
import algorithms.search.Heuristic;
import algorithms.search.MazeAirDistance;
import algorithms.search.Searchable;
import algorithms.search.SearchableMaze2D;
import algorithms.search.SearchableMaze3D;
import algorithm.generic.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * The Class MyModel.
 */
public class MyModel implements Model {

	/** The name files list. */
	private HashMap<String, String> nameFilesList;
	
	/** The name solution. */
	private HashMap<String, Solution<Position>> nameSolution;
	
	/** The name maze list. */
	private HashMap<String, Maze3d> nameMazeList;

	/** The out. */
	private MyCompressorOutputStream out;
	
	/** The in. */
	@SuppressWarnings("unused")
	private MyDecompressorInputStream in;

	/**
	 * Instantiates a new my model.
	 */
	public MyModel(){
		this.nameFilesList = new HashMap<>();
		this.nameSolution = new HashMap<>();
		this.nameMazeList = new HashMap<>();
		this.out = null;
		this.in = null;
	}

	/* (non-Javadoc)
	 * @see model.Model#saveMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveMaze(String mName, String fName) {
		
		try {
			this.out = new MyCompressorOutputStream(new FileOutputStream(fName));
			nameFilesList.put(mName, fName);
			this.out.write(nameMazeList.get(mName).toByteArray());
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				this.out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
	}

	/* (non-Javadoc)
	 * @see model.Model#loadMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void loadMaze(String fName, String mName) throws IOException,FileNotFoundException {
		
		try{
			ArrayList<Byte> arrBytes = new ArrayList<Byte>();
			byte[] b = new byte[9000];
			MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(fName));
			
			while( in.read(b) > 0 ){
				for (byte it : b) {
					arrBytes.add(it);
				}
			}
			
			in.close();
			//in.read(b);
			
			byte[] newArrByte = new byte[arrBytes.size()];
			for (int i = 0; i < newArrByte.length; i++) {
				newArrByte[i] = (byte) arrBytes.get(i);
			}
			
			Maze3d loadFile = new Maze3d(newArrByte);
			nameMazeList.put(mName, loadFile);	
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#solveMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void solveMaze(String mName, String algo) {
		Maze3d maze = nameMazeList.get(mName);
		SearchableMaze3D mazeAdapter = new SearchableMaze3D(maze);
		
		@SuppressWarnings("rawtypes")
		Heuristic h;
		
		if( algo.toLowerCase().equals("astar") ){
			h = new MazeAirDistance();
			@SuppressWarnings("unchecked")
			Astar<Position> astar = new Astar<Position>(h);
			nameSolution.put(mName, astar.search(mazeAdapter));
		}
		
		else if( algo.toLowerCase().equals("bfs") ){
			BFS<Position> bfs = new BFS<Position>();
			nameSolution.put(mName, bfs.search(mazeAdapter));
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getMazeName(java.lang.String)
	 */
	@Override
	public Searchable<Position> getMazeName(String mName){
		Maze3d maze = nameMazeList.get(mName);
		if(maze != null){
			SearchableMaze3D mazeAdapter = new SearchableMaze3D(maze);
			return mazeAdapter;
		}
		else{
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see model.Model#findMazeName(java.lang.String)
	 */
	public boolean findMazeName(String mName){
		if(nameMazeList.containsKey(mName)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see model.Model#getSizeInMemo(java.lang.String)
	 */
	@Override
	public int getSizeInMemo(String mName) {
		int s;
		try {
			s = nameMazeList.get(mName).toByteArray().length;
			return s;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	/* (non-Javadoc)
	 * @see model.Model#getFileSize(java.lang.String)
	 */
	@Override
	public long getFileSize(String mName) {
		String fName = nameFilesList.get(mName);
		if(fName != null){
			File f = new File(fName);
			return f.length();
		}
			return 0;
	}

	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Solution<Position> getSolution (String sol){
	
		if( nameSolution.get(sol) != null ){
			return nameSolution.get(sol);
		}
		else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() {
		//Closing all Opening files
		if(out != null){
			try {
				this.out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Close the program
		System.out.println("Bye Bye!");
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see model.Model#generateModel(java.lang.String[])
	 */
	@Override
	public void generateModel(String[] mazeSet) {

		String name;
		int x = Integer.parseInt(mazeSet[1]);
		int y = Integer.parseInt(mazeSet[2]);
		int z = Integer.parseInt(mazeSet[3]);
		name = mazeSet[0];
		
		/*
		MyMaze3dGenerator mazeDfs = new MyMaze3dGenerator();
		Maze3d maze = mazeDfs.generate(x, y, z);
		*/
		SearchableMaze3D newMaze = new SearchableMaze3D(x, y, z);
		
		try {
			
			Maze3d maze = new Maze3d(newMaze.getMaze().toByteArray());
			if(maze != null){
				this.nameMazeList.put(name, maze);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getMazeSection(java.lang.String, char, int)
	 */
	@Override
	public Searchable<Position> getMazeSection(String name, char section, int index) {
		Maze3d maze3d = nameMazeList.get(name);
		Maze2dAdapter maze2d = null;
		
		if( (maze3d) != null){
		
			switch (section) {
			case 'x': if(index > 0 && index < maze3d.getx()){
				maze2d = new Maze2dAdapter(maze3d.getCrossSectionByX(index));
			  }break;

			case 'y': if(index > 0 && index > maze3d.gety()){
				maze2d = new Maze2dAdapter(maze3d.getCrossSectionByY(index));
		      }break;
		
			case 'z': if(index > 0 && index > maze3d.getz()){
				maze2d = new Maze2dAdapter(maze3d.getCrossSectionByZ(index));
	          }break;
		
				default:  return null;
			}
			
			SearchableMaze2D maze2dAdapter = new SearchableMaze2D(maze2d);
			return maze2dAdapter;
		}
		else{
			return null;
		}
	}
}

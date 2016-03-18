/**
 * @Project: mvc
 * @Class : Model.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithm.generic.Solution;

/**
 * The Interface Model.
 */
public interface Model {

	/**
	 * Save maze.
	 *
	 * @param mName
	 *            the m name
	 * @param fName
	 *            the f name
	 */
	public void saveMaze(String mName, String fName);
	
	/**
	 * Load maze.
	 *
	 * @param fName
	 *            the f name
	 * @param mName
	 *            the m name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public void loadMaze(String fName, String mName) throws IOException, FileNotFoundException;
	
	/**
	 * Solve maze.
	 *
	 * @param mName
	 *            the m name
	 * @param algo
	 *            the algo
	 */
	public void solveMaze(String mName, String algo);
	
	/**
	 * Gets the size in memo.
	 *
	 * @param mName
	 *            the m name
	 * @return the size in memo
	 */
	public int getSizeInMemo(String mName);
	
	/**
	 * Gets the file size.
	 *
	 * @param mName
	 *            the m name
	 * @return the file size
	 */
	public long getFileSize(String mName);
	
	/**
	 * Gets the maze name.
	 *
	 * @param mName
	 *            the m name
	 * @return the maze name
	 */
	public Searchable<Position> getMazeName (String mName);
	
	/**
	 * Find maze name.
	 *
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	public boolean findMazeName (String name);
	
	/**
	 * Gets the solution.
	 *
	 * @param <T>
	 *            the generic type
	 * @param sol
	 *            the sol
	 * @return the solution
	 */
	public <T> Solution<T> getSolution(String sol);
	
	/**
	 * Exit.
	 */
	public void exit();
	
	/**
	 * Generate model.
	 *
	 * @param mazeSet
	 *            the maze set
	 */
	public void generateModel(String[] mazeSet);
	
	/**
	 * Gets the maze section.
	 *
	 * @param name
	 *            the name
	 * @param section
	 *            the section
	 * @param index
	 *            the index
	 * @return the maze section
	 */
	public Searchable<Position> getMazeSection (String name , char section , int index);
	
}

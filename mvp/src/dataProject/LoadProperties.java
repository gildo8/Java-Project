/**
 * @Project: mvp
 * @Class : LoadPrefernces.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package dataProject;

import java.io.Serializable;

/**
 * The Class LoadPrefernces used for load's method.
 */
public class LoadProperties implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name of file. */
	public String nameOfFile;

	/** The name of maze. */
	public String nameOfMaze;

	/**
	 * Instantiates a new load prefernces.
	 */
	public LoadProperties() {
		this.nameOfMaze = "default";
	}

	/**
	 * Instantiates a new load prefernces.
	 *
	 * @param mazeString the maze string
	 * @param fileName the file name
	 */
	public LoadProperties(String mazeString,String fileName) {
		this.nameOfMaze = mazeString;
		this.nameOfFile = fileName;
	}

	/**
	 * Gets the maze name.
	 *
	 * @return the maze name
	 */
	public String getMazeName() {
		return nameOfMaze;
	}

	/**
	 * Sets the maze name.
	 *
	 * @param mazeName the new maze name
	 */
	public void setMazeName(String mazeName) {
		this.nameOfMaze = mazeName;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return nameOfFile;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.nameOfFile = fileName;
	}


}
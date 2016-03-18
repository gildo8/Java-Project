/*
 * 
 */
package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * <h1>Position</h1>
 * This class represents a cell in the maze .
 *
 * @author Gil Doron
 * @since 21-08-2015
 */
public class Position implements Serializable{
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The x.
	 */
	int x;
	
	/**
	 * The y.
	 */
	int y;
	
	/**
	 * The z.
	 */
	int z;
	
	/**
	 * Instantiates a new position.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Position(int x , int y , int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Instantiates a new position.
	 *
	 * @param p the p
	 */
	public Position(Position p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}
	
	/**
	 * Instantiates a new position.
	 */
	public Position() {
		
	}
	
	public String toString(){
		return "{" + this.getX() + "," + this.getY() + "," + this.getZ() + "}";
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * Split.
	 *
	 * @return the int[]
	 */
	public int[] split ()
	{
		String s = this.toString().replace("{", "");
		s = s.replace("}", "");
		String[] stringPosition = (s.split(","));
		int[] intPosition = new int[stringPosition.length];
		for (int i = 0; i < intPosition.length; i++) {
			intPosition[i] = Integer.valueOf(stringPosition[i]);
		}
		return intPosition;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object o){
		if (((Position)o).x==this.x && ((Position)o).y==this.y && ((Position)o).z==this.z) {
			return true;
		}
		return false;
	}
	
	/**
	 * To position.
	 *
	 * @param s the s
	 * @return the position
	 */
	public Position toPosition(String s) {
		try {
			String[] stringPos = s.substring(1, s.length() - 1).split(",");

			return new Position(Integer.parseInt(stringPos[0]), Integer.parseInt(stringPos[1]),
					Integer.parseInt(stringPos[2]));
		} catch (Exception e) {
			return null;
		}
	}
}


package algorithms.mazeGenerators;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Class Maze3d.
 */
public class Maze3d implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Length = X , Width = Y , Height = Z
	/** The maze. */
	private int[][][] maze;

	/** The Length. */
	private int x;

	/** The Width. */
	private int y;

	/** The Height. */
	private int z;

	/** The Start position. */
	private Position StartPosition;

	/** The Goal position. */
	private Position GoalPosition;
	
	
	public Maze3d(int x, int y, int z) {
		this.maze = new int[x][y][z];
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Instantiates a new maze3d.
	 */
	public Maze3d(){}
	
	public Maze3d (byte[] byteArr) throws IOException{
		//Length = X , Width = Y , Height = Z
		int [] start = new int[3];
		int [] goal = new int[3];
		
		int currentSize = start.length ;
		for (int i = 0; i < currentSize; i++) {
			start[i] = (int)byteArr[i];
		}
		currentSize = start.length + goal.length;
		
		int j = 0;
		for (int i = 3; i < currentSize; i++) {
			goal[j] = (int) byteArr[i];
			j++;
		}
		currentSize = start.length + goal.length;

		this.GoalPosition = new Position(goal[0], goal[1], goal[2]);
		this.StartPosition = new Position(start[0], start[1], start[2]);

		this.z = (int) byteArr[currentSize++];
		this.x = (int) byteArr[currentSize++];
		this.y = (int) byteArr[currentSize++];

		this.maze = new int[this.x][this.y][this.z];

		for (int i = 0; i < this.x; i++) {
			for (int w = 0; w < this.y; w++) {
				for (int k = 0; k < this.z; k++) {
					this.maze[i][w][k] = byteArr[currentSize++];
				}
			}
		}
	}
	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public int[][][] getMaze() {
		return maze;
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(int[][][] maze) {
		this.maze = maze;
	}

	/**
	 * Gets the start position.
	 *
	 * @return the start position
	 */
	public Position getStartPosition() {
		return StartPosition;
	}

	/**
	 * Sets the start position.
	 *
	 * @param startPosition the new start position
	 */
	public void setStartPosition(Position startPosition) {
		this.StartPosition = startPosition;
	}

	/**
	 * Gets the goal position.
	 *
	 * @return the goal position
	 */
	public Position getGoalPosition() {
		return GoalPosition;
	}

	/**
	 * Sets the goal position.
	 *
	 * @param goalPosition the new goal position
	 */
	public void setGoalPosition(Position goalPosition) {
		this.GoalPosition = goalPosition;
	}

	public void setGoalPosition(int x , int y, int z){
		this.GoalPosition = new Position(x, y, z);
	}
	
	public void setStartPosition(int x , int y , int z){
		this.StartPosition = new Position(x, y, z);
	}
	/**
	 * Gets the cell.
	 *
	 * @param p the p
	 * @return the cell
	 */
	public int getCell(Position p) {
		if (checkPositionBounds(p))
			return maze[p.getX()][p.getY()][p.getZ()];
		throw new IndexOutOfBoundsException(p.toString());
	}

	/**
	 * Sets the maze wall.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void setMazeWall(int x, int y, int z) {
		if (checkPositionBounds(x, y, z))
			maze[x][y][z] = 1;
		else
			throw new IndexOutOfBoundsException((new Position(x, y, z).toString()));
	}

	/**
	 * Sets the maze empty.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void setMazeEmpty(int x, int y, int z) {
		if (checkPositionBounds(x, y, z))
			maze[x][y][z] = 0;
		else
			throw new IndexOutOfBoundsException((new Position(x, y, z).toString()));
	}

	/**
	 * Sets the maze empty position.
	 *
	 * @param p the new maze empty position
	 */
	public void setMazeEmptyPosition(Position p) {
		if (checkPositionBounds(p))
			maze[p.getX()][p.getY()][p.getZ()] = 0;
		else
			throw new IndexOutOfBoundsException(p.toString());
	}

	
	/**
	 * Removes the maze wall.
	 *
	 * @param source the source
	 * @param destination the destination
	 */
	public void removeMazeWall(Position source, Position destination) {
		
		if (source.getX() != destination.getX() && source.getY() == destination.getY()
				&& source.getZ() == destination.getZ()) {
			if (source.getX() + 2 == destination.getX()) {
				this.setMazeEmpty(source.getX() + 1, source.getY(), source.getZ());

			} else if (source.getX() - 2 == destination.getX()) {
				this.setMazeEmpty(source.getX() - 1, source.getY(), source.getZ());
			} else {
				throw new IndexOutOfBoundsException(
						"route: " + source.toString() + " to " + destination.toString() + " is too big to handle");
			}
		}

		else if (source.getX() == destination.getX() && source.getY() != destination.getY()
				&& source.getZ() == destination.getZ()) {
			if (source.getY() + 2 == destination.getY()) {
				this.setMazeEmpty(source.getX(), source.getY() + 1, source.getZ());

			} else if (source.getY() - 2 == destination.getY()) {
				this.setMazeEmpty(source.getX(), source.getY() - 1, source.getZ());
			} else {
				throw new IndexOutOfBoundsException(
						"route: " + source.toString() + " to " + destination.toString() + " is too big to handle");
			}
		}
		
		else if (source.getX() == destination.getX() && source.getY() == destination.getY()
				&& source.getZ() != destination.getZ()) {
			if (source.getZ() + 2 == destination.getZ()) {
				this.setMazeEmpty(source.getX(), source.getY(), source.getZ() + 1);

			} else if (source.getZ() - 2 == destination.getZ()) {
				this.setMazeEmpty(source.getX(), source.getY(), source.getZ() - 1);
			} else {
				throw new IndexOutOfBoundsException(
						"route: " + source.toString() + " to " + destination.toString() + " is too big to handle");
			}
		}

		else {
			throw new IndexOutOfBoundsException(
					"route: " + source.toString() + " to " + destination.toString() + " is not possible");
		}
	}

	/**
	 * Prints the maze.
	 */
	public void printMaze() {
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				for (int z = 0; z < maze[0][0].length; z++) {
					System.out.print(maze[x][y][z] + " ");
				}
				System.out.print("\n");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Gets the cross section by x.
	 * @param index the index
	 * @return int[][] SectionBy x
	 */
	public int[][] getCrossSectionByX(int index)
			throws IndexOutOfBoundsException {
		if (index < 0 || index > this.getx())
			throw new IndexOutOfBoundsException("Error cross x");
		int[][] maze2d = new int[this.getx()][this.gety()];

		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[0].length; j++) {
				maze2d[i][j] = this.maze[i][index][j];
			}

		}
		return maze2d;
	}

	/**
	 * Gets the cross section by y.
	 * @param index the index
	 * @return int[][] SectionBy y
	 */
	public int[][] getCrossSectionByY(int index)
			throws IndexOutOfBoundsException {
		if (index < 0 || index > this.gety())
			throw new IndexOutOfBoundsException("Error by section y");
		int[][] maze2d = new int[this.gety()][this.getz()];

		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[0].length; j++) {
				maze2d[i][j] = this.maze[i][j][index];
			}

		}
		return maze2d;
	}

	/**
	 * Gets the cross section by z.
	 * @param index the index
	 * @return int[][] SectionBy z
	 */
	public int[][] getCrossSectionByZ(int index)
			throws IndexOutOfBoundsException {
		if (index < 0 || index > this.getz())
			throw new IndexOutOfBoundsException("Error by section z");
		int[][] maze2d = new int[this.getz()][this.gety()];

		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[0].length; j++) {
				maze2d[i][j] = this.maze[index][i][j];
			}

		}
		return maze2d;
	}

	
	/**
	 * Prints the maze.
	 *
	 * @param arr the arr
	 */
	public void printMaze(int[][] arr) {
		for (int x = 0; x < arr.length; x++) {
			for (int y = 0; y < arr[0].length; y++) {
					System.out.print(arr[x][y] + ", ");
				
			}System.out.print("\n");
		}
	}

	/**
	 * Check position bounds.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return true, if successful
	 */
	public boolean checkPositionBounds(int x, int y, int z) {
		return ((x >= 0 && x < getx()) && (y >= 0 && y < gety()) && (z >= 0 && z < getz()));
	}

	/**
	 * Check position bounds.
	 *
	 * @param p the p
	 * @return true, if successful
	 */
	public boolean checkPositionBounds(Position p) {
		return checkPositionBounds(p.getX(), p.getY(), p.getZ());
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getx() {
		return this.x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int gety() {
		return this.y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getz() {
		return this.z;
	}

	

	/**
	 * Gets the possible moves.
	 *
	 * @param p the p
	 * @return the possible moves
	 */
	public String[] getPossibleMoves(Position p) {

		ArrayList<Position> pMovesList = new ArrayList<Position>();
		// push all moves to pMovesList
		pMovesList.add(new Position(p.getX() + 1, p.getY(), p.getZ()));
		pMovesList.add(new Position(p.getX() - 1, p.getY(), p.getZ()));
		pMovesList.add(new Position(p.getX(), p.getY() + 1, p.getZ()));
		pMovesList.add(new Position(p.getX(), p.getY() - 1, p.getZ()));
		pMovesList.add(new Position(p.getX(), p.getY(), p.getZ() + 1));
		pMovesList.add(new Position(p.getX(), p.getY(), p.getZ() - 1));

		// check conditions for each position is in bounds
		Iterator<Position> it = pMovesList.iterator();
		while (it.hasNext()) {
			Position pos = it.next();
			if (!checkPositionBounds(pos) || getCell(pos) != 0) {
				it.remove();
			}
		}

		// allocate the String array
		String[] possibleMovesArray = new String[pMovesList.size()];
		// copy from list to array
		for (int i = 0; i < possibleMovesArray.length; i++) {
			possibleMovesArray[i] = pMovesList.get(i).toString();
		}

		return possibleMovesArray;
	}
	
	public byte[] toByteArray()	throws IOException{
		//Length = X , Width = Y , Height = Z
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		int [] array1= this.getStartPosition().split();
		for (int i = 0; i < array1.length; i++) {
			dos.write(array1[i]);	
		}

		int []array2= this.getGoalPosition().split();
		for (int j = 0; j < array2.length; j++) {
			dos.write(array2[j]);
		}
		dos.write(z);
		dos.write(x);
		dos.write(y);

		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				for (int k = 0; k < this.z; k++) {
					dos.write(maze[i][j][k]);
				}
			}
		}
		byte [] retVal = baos.toByteArray();
		return retVal;
	}
	
	public boolean equals(Maze3d maze) {
		if( (this.getStartPosition().equals(maze.getStartPosition())) && (this.getGoalPosition().equals(maze.getGoalPosition())) ){
			if( (this.getx() == maze.getx()) && (this.gety() == maze.gety()) && (this.getz() == maze.getz()) ){
				for (int i = 0; i < this.maze.length; i++) {
					for (int j = 0; j < this.maze.length; j++) {
						for (int k = 0; k < this.maze.length; k++) {
							if(this.maze[i][j][k] == maze.maze[i][j][k]){
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;

	}
	
	public Boolean availableMove(int x, int y, int z) {
		int count = 0;
		
		//height = z , length = x , width = y

		if (z + 1 < getz()) {
			if (this.maze[x][y][z + 1] == 0)
				count++;
		}
		if (z - 1 > 0) {
			if (this.maze[x][y][z - 1] == 0)
				count++;
		}
		if (x + 1 < getx()) {
			if (this.maze[x + 1][y][z] == 0)
				count++;
		}
		if (x - 1 > 0) {
			if (this.maze[x - 1][y][z] == 0)
				count++;
		}
		if (y + 1 < gety()) {
			if (this.maze[x][y + 1][z] == 0)
				count++;
		}
		if (y - 1 > 0) {
			if (this.maze[x][y - 1][z] == 0)
				count++;
		}
		
//		if (z + 1 < getz()) {
//			if (this.maze[x + 1][y][z] == 0)
//				count++;
//		}
//		if (z - 1 > 0) {
//			if (this.maze[x - 1][y][z] == 0)
//				count++;
//		}
//		if (x + 1 < getx()) {
//			if (this.maze[x][y + 1][z] == 0)
//				count++;
//		}
//		if (x - 1 > 0) {
//			if (this.maze[x][y - 1][z] == 0)
//				count++;
//		}
//		if (y + 1 < gety()) {
//			if (this.maze[x][y][z + 1] == 0)
//				count++;
//		}
//		if (y - 1 > 0) {
//			if (this.maze[x][y][z - 1] == 0)
//				count++;
//		}
		return count <= 1;
	}
	
	public boolean mazeOutRange(int x , int y , int z){
		if( (x >= 0 && y >= 0 && z >= 0) && (x < this.maze.length && y < this.maze[0].length && z < this.maze[0][0].length) ){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getCellValue(int x, int y, int z) {
		if(mazeOutRange(x, y, z)){
			return this.maze[x][y][z];
		}
		else
			return 1;
	}
	
	public void setCellValue(int x, int y, int z, int num) {
		if(mazeOutRange(x, y, z)){
			this.maze[x][y][z] = num;
		}
	}
	
	
}
	
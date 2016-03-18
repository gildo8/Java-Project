package algorithms.demo;

public class Maze3dAdapter {

	private int[][][] maze3d;
	private int x;
	private int y;
	private int z;
	
	public Maze3dAdapter(int[][][] maze3d, int x, int y, int z) {
		this.maze3d = new int[x][y][z];
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Maze3dAdapter(int x, int y, int z) {
		this.maze3d = new int[x][y][z];
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int[][][] getMaze3d() {
		return maze3d;
	}
	public void setMaze3d(int[][][] maze3d) {
		this.maze3d = maze3d;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
	
	
	
}

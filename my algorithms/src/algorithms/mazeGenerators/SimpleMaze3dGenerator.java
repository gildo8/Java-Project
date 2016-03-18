/*
 * 
 */
package algorithms.mazeGenerators;

import java.util.Random;

/**
 * <h1>SimpleMaze3DGenerator</h1>
 * This class Extends from Maze3dInit and generate a maze.<br>
 * Using a simple maze algorithm that building an random maze.
 *
 * @author Gil Doron
 * @since 21-08-2015  
 * 
 */
public class SimpleMaze3dGenerator extends Maze3dInit {

	/**
	 * <h1>SimpleMaze3dGenerato - Generate maze </h1>
	 * Method that generating maze.
	 * 
	 */
	@Override
	public Maze3d generate(int x1, int y1, int z1) {
		Random rnd = new Random();
		Maze3d myMaze = new Maze3d(x1, y1, z1);

		for (int x = 0; x < x1; x++) {
			for (int y = 0; y < y1; y++) {
				for (int z = 0; z < z1; z++) {
					myMaze.setMazeWall(x, y, z);
				}
			}
		}
		
		Position start = new Position(0, 0, rnd.nextInt(z1 - 2) + 1);
		myMaze.setStartPosition(start);
		myMaze.setMazeEmptyPosition(start);

		// build the route until it reaches some exit in the right plain
		Position p = new Position(start);
		while (p.getX() != x1 - 1) {

			int move = rnd.nextInt(6);
			switch (move) {
			// move right
			case 0:
				if (p.getX() + 1 <= x1 - 1) {
					p.setX(p.getX() + 1);
					myMaze.setMazeEmptyPosition(p);
				}
				break;
			// move up
			case 1:
				if (p.getY() + 1 <= y1 - 1) {
					p.setY(p.getY() + 1);
					myMaze.setMazeEmptyPosition(p);
				}
				break;
			// move front
			case 2:
				if (p.getZ() + 1 <= z1 - 1) {
					p.setZ(p.getZ() + 1);
					myMaze.setMazeEmptyPosition(p);
				}
				break;
			}
		}
		myMaze.setGoalPosition(p);

		return myMaze;
	}
}

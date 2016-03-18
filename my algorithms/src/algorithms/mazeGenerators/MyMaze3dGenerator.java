
package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * <h1>MyMaze3dGenerator</h1>
 * This class Extends from Maze3dInit and generate a maze by using DFS algorithm.
 *
 * @author Gil Doron
 * @since 21-08-2015
 */
public class MyMaze3dGenerator extends Maze3dInit {
	
	/**
	 * The visited.
	 */
	private HashSet<Position> visited;

	/**
	 * <h1>DFS - Generate maze </h1>
	 * Method that generating maze by using DFS algorithm.
	 * 
	 */
	@Override
	public Maze3d generate(int x, int y, int z) {
		Maze3d myMaze = new Maze3d(x, y, z);

		// initialize walls and return HashSet marking outer walls as already visited
		visited = initHash(myMaze);

		// generate start and goal position
		Position p1 = new Position();
		Position p2 = new Position();
		do {
			p1 = this.generateRandomPosition(myMaze);
			p2 = this.generateRandomPosition(myMaze);
		} while (!checkEvenOdd(p1, p2) || (p1.equals(p2)));

		myMaze.setStartPosition(p1);
		myMaze.setGoalPosition(p2);

		myMaze.setMazeEmptyPosition(p1);
		myMaze.setMazeEmptyPosition(p2);

		visited.remove(myMaze.getGoalPosition());

		DFS(myMaze, myMaze.getStartPosition());

		return myMaze;
	}

	/**
	 * Dfs.
	 *
	 * @param myMaze the my maze
	 * @param curPos the cur pos
	 */
	private void DFS(Maze3d myMaze, Position curPos) {

		// if p(one of the neighbors) reached the end -> backtrack
		if (curPos.equals(myMaze.getGoalPosition()))
			return;
		
		// adds current position to visited HashSet
		visited.add(curPos);

		// get current position's possible neighbors list
		ArrayList<Position> curPosNeighbors = getPositionNeighbors(myMaze, curPos);

		// randomizes the neighbors list
		Collections.shuffle(curPosNeighbors);

		for (Position p : curPosNeighbors) {
			if (!visited.contains(p)) {
				// p is a wall and hadn't been visited yet -> break the wall and recurse on it
				myMaze.removeMazeWall(curPos, p);
				myMaze.setMazeEmptyPosition(p);

				DFS(myMaze, p);
			}
		}
	}

	/**
	 * Inits the hash.
	 *
	 * @param myMaze the my maze
	 * @return the hash set
	 */
	private HashSet<Position> initHash(Maze3d myMaze) {
		HashSet<Position> visited = new HashSet<Position>();

		for (int x = 0; x < myMaze.getx(); x++) {
			for (int y = 0; y < myMaze.gety(); y++) {
				for (int z = 0; z < myMaze.getz(); z++) {
					myMaze.setMazeWall(x, y, z);
					if (x == 0 || x == myMaze.getx() - 1 || y == 0 || y == myMaze.gety() - 1 || z == 0
							|| z == myMaze.getz() - 1)
						visited.add(new Position(x, y, z));
				}
			}
		}
		return visited;
	}

	/**
	 * Gets the position neighbors.
	 *
	 * @param myMaze the my maze
	 * @param p the p
	 * @return the position neighbors
	 */
	private ArrayList<Position> getPositionNeighbors(Maze3d myMaze, Position p) {

		ArrayList<Position> possibleMoves = new ArrayList<Position>();
		
		possibleMoves.add(new Position(p.getX() + 2, p.getY(), p.getZ()));
		possibleMoves.add(new Position(p.getX() - 2, p.getY(), p.getZ()));
		possibleMoves.add(new Position(p.getX(), p.getY() + 2, p.getZ()));
		possibleMoves.add(new Position(p.getX(), p.getY() - 2, p.getZ()));
		possibleMoves.add(new Position(p.getX(), p.getY(), p.getZ() + 2));
		possibleMoves.add(new Position(p.getX(), p.getY(), p.getZ() - 2));

		Iterator<Position> it = possibleMoves.iterator();
		while (it.hasNext()) {
			if (!myMaze.checkPositionBounds(it.next()))
				it.remove();
		}

		return possibleMoves;
	}

	/**
	 * Check even odd.
	 *
	 * @param pos1 the pos1
	 * @param pos2 the pos2
	 * @return true, if successful
	 */
	// checks if 2 positions are of the same evenness\oddness
	private boolean checkEvenOdd(Position pos1, Position pos2) {
		return ((pos1.getX() % 2 == pos2.getX() % 2) && (pos1.getY() % 2 == pos2.getY() % 2)
				&& (pos1.getZ() % 2 == pos2.getZ() % 2));
	}

	/**
	 * Generate random position.
	 *
	 * @param myMaze the my maze
	 * @return the position
	 */
	private Position generateRandomPosition(Maze3d myMaze) {

		Position p = new Position();
		Random rnd = new Random();

		switch (rnd.nextInt(6)) {
		case 0:
			// left plain
			p.setX(0);
			p.setY(rnd.nextInt(myMaze.gety() - 2) + 1);
			p.setZ(rnd.nextInt(myMaze.getz() - 2) + 1);
			break;
		case 1:
			// right plain
			p.setX(myMaze.getx() - 1);
			p.setY(rnd.nextInt(myMaze.gety() - 2) + 1);
			p.setZ(rnd.nextInt(myMaze.getz() - 2) + 1);
			break;
		case 2:
			// bottom plain
			p.setX(rnd.nextInt(myMaze.getx() - 2) + 1);
			p.setY(0);
			p.setZ(rnd.nextInt(myMaze.getz() - 2) + 1);
			break;
		case 3:
			// upper plain
			p.setX(rnd.nextInt(myMaze.getx() - 2) + 1);
			p.setY(myMaze.gety() - 1);
			p.setZ(rnd.nextInt(myMaze.getz() - 2) + 1);
			break;
		case 4:
			// front plain
			p.setX(rnd.nextInt(myMaze.getx() - 2) + 1);
			p.setY(rnd.nextInt(myMaze.gety() - 2) + 1);
			p.setZ(0);
			break;
		case 5:
			// back plain
			p.setX(rnd.nextInt(myMaze.getx() - 2) + 1);
			p.setY(rnd.nextInt(myMaze.gety() - 2) + 1);
			p.setZ(myMaze.getz() - 1);
			break;
		}
		return p;
	}
}

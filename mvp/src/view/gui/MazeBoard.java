/**
 * @Project: mvp
 * @Class : MazeBoard.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import jaco.mp3.player.MP3Player;
import algorithm.generic.Solution;
import algorithm.generic.State;
import model.Maze3dDraw;


/**
 * The Class MazeBoard this class defining the operation of the Maze Board.
 * Extends from CommonBoard.
 * 
 */
public class MazeBoard extends CommonBoard {

	/** The gifs. */
	ImageLoader gifs=new ImageLoader();
	
	/** The my maze. */
	Maze3dDraw myMaze;
	
	/** The images. */
	ImageData[] images;
	
	/** The frame index. */
	int frameIndex = 0;
	
	/** The start x. */
	int startX;
	
	/** The start y. */
	int startY;
	
	/** The goal x. */
	int goalX;
	
	/** The goal y. */
	int goalY;
	
	/** The floor z. */
	int floorZ;
	
	/** The goal z. */
	int goalZ;
	
	/** The maze position. */
	Position mazePosition;
	
	/** The flag. */
	public boolean flag = true;
	
	/** The hints. */
	public boolean[][][] hints;
	
	/** The is hint. */
	volatile boolean isHint;
	
	/** The floor cell img. */
	boolean floorCellImg = false;	//img of up or down floor

	/** The player. */
	MP3Player player;
	
	/** The asked for solution. */
	volatile boolean askedForSolution;
	
	/**
	 * Instantiates a new maze board.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public MazeBoard(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		images = gifs.load(".\\resources\\images\\diamond.gif"); //goal's pic
		player = new MP3Player();
	}

	/**
	 * Sets the board data.
	 *
	 * @param maze the new board data
	 */
	private void setBoardData(Maze3dDraw maze)
	{
		this.myMaze = maze;
		MazeBoard a = this;
		
		getDisplay().syncExec(new Runnable() {
			public void run() {
				if(board!=null)
					a.destructBoard(); // destory previous maze if exists 
				Maze3d myMaze = maze.getMazeData();
				
				boardRowsX = myMaze.getx(); //gets maze rows and cols
				boardColsY = myMaze.gety();
				
				if(flag){
					hints = new boolean[myMaze.getx()][myMaze.gety()][myMaze.getz()];
					startX = myMaze.getStartPosition().getX();
					startY = myMaze.getStartPosition().getY();
					floorZ = myMaze.getStartPosition().getZ();
					goalX = myMaze.getGoalPosition().getX();
					goalY = myMaze.getGoalPosition().getY();
					flag = false;
					mazePosition = new Position(startX, startY, floorZ);
					goalZ = myMaze.getGoalPosition().getZ();
				}
				
				GridLayout layout=new GridLayout(boardColsY, true); //defines the grid layout
				layout.horizontalSpacing = 0;
				layout.verticalSpacing = 0;
				setLayout(layout); //sets the layout
				
				board = new MazeTile[boardRowsX][boardColsY]; //creates a new maze array
				
				for(int i = 0 ; i < boardRowsX ; i++)
					for(int j = 0 ; j < boardColsY ; j++)
					{
						board[i][j] = new MazeTile(a,SWT.NONE);
						board[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
						board[i][j].setCellImage(cellImage(maze,i,j)); //creates a cell and sets the correct image
						if(floorCellImg){
							//if there is an img of up or down floor set this position to true
							board[i][j].setFloorCellImage(true);
							floorCellImg = false;
						}
						
						if (askedForSolution && hints[i][j][floorZ]) {
							
							//for hint display - if there is an img of up or down floor so dont remove it. 
							if(!board[i][j].isFloorCellImage()){
								Image img = new Image(getDisplay(), ".\\resources\\images\\Coin.png");
								(board[i][j]).setCellImage(img);
								(board[i][j]).setHint(img);
								(board[i][j]).redraw();
							}
						}
					}
				getShell().layout();
			}
		}); 

	}

	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#destructBoard()
	 */
	public void destructBoard()
	{
		if(board != null){
			
			for(int i=0;i<board.length;i++)
			{
				for(int j=0;j<board[0].length;j++)
				{	
					if( board[i][j].getCellImage()!=null)
						( board[i][j]).getCellImage().dispose();
					if(( board[i][j]).getGoal()!=null)
						( board[i][j]).getGoal().dispose();
					if(( board[i][j]).getCharacter()!=null)
						( board[i][j]).getCharacter().dispose();
					if((board[i][j]).getHint() != null)
						(board[i][j]).getHint().dispose();

					if(character!=null)
						character.dispose();

					( board[i][j]).dispose();
				}
			}
		}
		if(timer!=null)
			timer.cancel();

	}
	
	/**
	 * Cell image.
	 *
	 * @param maze the maze
	 * @param x the x
	 * @param y the y
	 * @return the image
	 */
	private Image cellImage(Maze3dDraw maze, int x, int y)
	{
		Maze3d myMaze = maze.getMazeData();
		int available = myMaze.getCellValue(x, y, floorZ);
		String temp=".\\resources\\images\\";
		String str="";

		if (available == 1){
			str = "wall.png";
		}
		
		else if(myMaze.getCellValue(x , y, floorZ+1) == 0 && myMaze.getCellValue(x , y, floorZ-1) == 1){
			str = "up.jpg";
			this.floorCellImg = true;	//means there is an img of up or down floor
		}

		else if(myMaze.getCellValue(x, y , floorZ+1) == 1 && myMaze.getCellValue(x , y, floorZ-1) == 0){
			str = "down.jpg";
			this.floorCellImg = true;	//means there is an img of up or down floor
		}

		else if(myMaze.getCellValue(x, y , floorZ+1) == 0 && myMaze.getCellValue(x , y, floorZ-1) == 0){
			str = "up_down.jpg";
			this.floorCellImg = true;	//means there is an img of up or down floor
		}

		else if(myMaze.getCellValue(x, y , floorZ+1) == 1 && myMaze.getCellValue(x , y, floorZ-1) == 1){
			str = "road.jpg";
		}
		
		board[x][y].setImageName(str); //sets the image name
		return new Image(getDisplay(),new ImageData(temp+str)); //returns the image
	}

	/* (non-Javadoc)
	 * @see view.gui.GUIBoard#drawBoard(org.eclipse.swt.events.PaintEvent)
	 */
	@Override
	public void drawBoard(PaintEvent arg0) {
		if( board == null && won == false ){ //displays the photo in the begining of the program as an intro
			int width = getParent().getSize().x;
			int height = getParent().getSize().y;
			ImageData data = new ImageData(".\\resources\\images\\wallpaper.png");
			arg0.gc.drawImage(new Image(getDisplay(),data),0,0,data.width,data.height,0,0,width,height);
		}
		else if(won==true)
		{
			flag = true;
			setVisible(false);
		}
		else if(board!=null)
			for(int i = 0 ; i < board.length ; i++)
				for(int j = 0 ; j < board[0].length ; j++)
					board[i][j].redraw();

	}
	
	
	/* (non-Javadoc)
	 * @see view.gui.CommonBoard#applyInputDirection(view.gui.Direction)
	 */
	@Override
	public void applyInputDirection(Direction direction) {
		int dirX = 0;
		int dirY = 0;
		String path = null;
		switch (direction){
		
		case FORWARD:
			dirX = 1; //for example if we go up we need to take 1 from the row 
			mazePosition.setX(mazePosition.getX() + 1);
			character.setImagePath("sonic_forward.gif");
			path = character.getImagePath();
			break;
			
		case RIGHT:
			dirY = 1;
			mazePosition.setY(mazePosition.getY() + 1);
			character.setImagePath("sonic_right.gif");
			path = character.getImagePath();
			break;
			
		case LEFT:
			dirY = -1;
			mazePosition.setY(mazePosition.getY() - 1);
			character.setImagePath("sonic_left.gif");
			path = character.getImagePath();
			break;
			
		case BACKWARD: 
			dirX = -1;
			mazePosition.setX(mazePosition.getX() - 1);
			character.setImagePath("sonic_backward.gif");
			path = character.getImagePath();
			break;
			
		case DOWN:
			mazePosition.setZ(floorZ - 1);
			setFloor(getFloor()-1, this.myMaze);
			character.setImagePath("sonic_forward.gif");
			path = character.getImagePath();
			player.addToPlayList(new File(".\\resources\\sounds\\ring.mp3"));
			player.play();
			break;
			
		case UP:
			mazePosition.setZ(floorZ + 1);
			setFloor(getFloor()+1, this.myMaze);
			character.setImagePath("sonic_forward.gif");
			path = character.getImagePath();
			player.addToPlayList(new File(".\\resources\\sounds\\ring.mp3"));
			player.play();
			
			break;

		default:
			break;
		}	
		
		try{
			
			int row = character.getCellX();  //this function redraws the character
			int col = character.getCellY(); //in the new place as needed
			
			board[row][col].setCharacter(null);
			character.setVisible(false);
			character = new MazeCharacter( board[row+dirX][col+dirY],SWT.FILL , path); //change the character direction
			character.cellX = row + dirX;
			character.cellY = col + dirY;
			character.setCharacterImageIndex(0);
			board[row+dirX][col+dirY].setCharacter(character);
			board[character.cellX-dirX][character.cellY-dirY].redraw();
			board[character.cellX][character.cellY].redraw(); //redrawing the character
		}catch (ArrayIndexOutOfBoundsException e){ 
			//if the user is on the edge and he push further.
		}

		if(character.cellX == this.goalX && character.cellY == this.goalY && board!=null && this.floorZ == goalZ){
			//if we have reacharactered the destination
			won = true; 
			player.addToPlayList(new File(".\\resources\\sounds\\win.mp3"));
			player.play();
			getShell().setBackgroundImage(new Image(getDisplay(),".\\resources\\images\\win.jpg"));	//Win's Pic
			getShell().setFullScreen(true);
			drawBoard(null);
		}
	}

	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#displayProblem(java.lang.Object)
	 */
	@Override
	public void displayProblem(Object o) {
		Maze3dDraw maze = (Maze3dDraw)o;

		getDisplay().syncExec(new Runnable() {
			public void run() {
				setBoardData(maze);
				
				character = new MazeCharacter(board[mazePosition.getX()][mazePosition.getY()],SWT.FILL);
				character.setCellX(mazePosition.getX());
				character.setCellY(mazePosition.getY());
				character.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,true,2,2));

				//set character to the begining of the maze
				(board[mazePosition.getX()][mazePosition.getY()]).setCharacter(character); 
				board[mazePosition.getX()][mazePosition.getY()].redraw();
				
				layout(); //draw all the things needed
				forceFocus();
			}
		});
		scheduleTimer(maze);
	}
	
	
	/**
	 * Schedule timer.
	 *
	 * @param maze the maze
	 */
	private void scheduleTimer(Maze3dDraw maze)
	{
		Maze3d myMaze = maze.getMazeData();
		TimerTask  timerTask = new TimerTask() {

			@Override
			public void run() {
				if(!isDisposed()){
					
					getDisplay().syncExec(new Runnable() {
						//this is the timer task allowing us to redraw the goal target gif and sonic's gif
						
						@Override
						public void run() { 
							if( character != null && !isDisposed() && maze != null){
								if(myMaze.getGoalPosition().getX() < board.length && myMaze.getGoalPosition().getY() < board[0].length){
									character.setCharacterImageIndex((character.getCharacterImageIndex() + 1) % character.getCharacterImagesArray().length); //next frame in gifs
									frameIndex = (frameIndex+1) % images.length; //next frame in gifs
									(board[goalX][goalY]).setGoal(new Image(getDisplay(),images[frameIndex]));
									board[character.cellX][character.cellY].redraw(); //redraw cell in which character now stays
									board[goalX][goalY].redraw();
									//redraw the goal cell
								}
							}
						}
					});
				}
			}
		};

		this.timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 50); //ever 0.05 seconds render display
	}


	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#hasPathUp(int, int)
	 */
	@Override
	public boolean hasPathUp(int characterX, int characterY) {// add adapter and check with the maze func
		Maze3d maze = this.myMaze.getMazeData();
		int av = maze.getCellValue(characterX, characterY, floorZ + 1);
		
		return av == 0;
	}
	
	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#hasPathRight(int, int)
	 */
	@Override
	public boolean hasPathRight(int characterX, int characterY) {
		Maze3d maze = this.myMaze.getMazeData();
		int av = maze.getCellValue(characterX, characterY + 1, floorZ);
		
		return av == 0;
	}
	
	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#hasPathDown(int, int)
	 */
	@Override
	public boolean hasPathDown(int characterX, int characterY) {
		Maze3d maze = this.myMaze.getMazeData();
		int av = maze.getCellValue(characterX, characterY, floorZ - 1);
		
		return av == 0;
	}
	
	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#hasPathLeft(int, int)
	 */
	@Override
	public boolean hasPathLeft(int characterX, int characterY) {
		Maze3d maze = this.myMaze.getMazeData();
		int av = maze.getCellValue(characterX, characterY - 1, floorZ);
		
		return av == 0;
	}
	
	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#hasPathForward(int, int)
	 */
	@Override
	public boolean hasPathForward(int characterX, int characterY) {
		Maze3d maze = this.myMaze.getMazeData();
		int av = maze.getCellValue(characterX + 1, characterY, floorZ);
		
		return av == 0;
	}
	
	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#hasPathBackward(int, int)
	 */
	@Override
	public boolean hasPathBackward(int characterX, int characterY) {
		Maze3d maze = this.myMaze.getMazeData();
		int av = maze.getCellValue(characterX - 1, characterY, floorZ);
		
		return av == 0;
	}
	
	/* (non-Javadoc)
	 * @see view.gui.CommonMazeBoard#displaySolution(algorithms.search.Solution)
	 */
	@Override
	public <T> void displaySolution(Solution<T> s) {
		ArrayList<State<T>> solList = s.getSolution();
		
		for (State<T> state : solList) {
			Position pos = (Position) state.getPosition();
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			hints[x][y][z] = true;
			setHint(true);
		}
		
		setAskedForSolution(true);
		player.addToPlayList(new File(".\\resources\\sounds\\solve.mp3"));
		player.play();
		for (State<T> state : solList) {
			System.out.println(state.getPosition().toString());
		}
//		drawBoard(null);
//		forceFocus();
	}

	/**
	 * Gets the floor.
	 *
	 * @return the floor
	 */
	public int getFloor() {
		return floorZ;
	}

	/**
	 * Sets the floor.
	 *
	 * @param floor the floor
	 * @param maze the maze
	 */
	public void setFloor(int floor, Maze3dDraw maze) {
		this.floorZ = floor;
		displayProblem(maze);
	}

	/**
	 * Checks if is hint.
	 *
	 * @return true, if is hint
	 */
	public boolean isHint() {
		return isHint;
	}

	/**
	 * Sets the hint.
	 *
	 * @param isHint the new hint
	 */
	public void setHint(boolean isHint) {
		this.isHint = true;
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[i].length; j++) {
						if( hints[i][j][floorZ]){
							if(floorCellImg){
								board[i][j].setFloorCellImage(true);
								floorCellImg = false;
							}
							
							if (isHint && hints[i][j][floorZ]) {
								if(!board[i][j].isFloorCellImage()){	//if there is'nt floor img
									Image img = new Image(getDisplay(), ".\\resources\\images\\Coin.png");
									(board[i][j]).setCellImage(img);
									(board[i][j]).setHint(img);
									(board[i][j]).redraw();
								}
							}
						}
					}
				}
				layout();
				forceFocus();	
			}
		});
	}

	/**
	 * Checks if is asked for solution.
	 *
	 * @return true, if is asked for solution
	 */
	public boolean isAskedForSolution() {
		return askedForSolution;
	}

	/* (non-Javadoc)
	 * @see view.gui.CommonBoard#setAskedForSolution(boolean)
	 */
	public void setAskedForSolution(boolean askedForSolution) {
		if(askedForSolution){
			this.askedForSolution = true;
			getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {


					for (int  i= 0; i < board.length;i++) {
						for (int j = 0; j < board[i].length; j++) {
							if(askedForSolution && hints[i][j][floorZ]){
								Image img = new Image(getDisplay(),".\\resources\\images\\Coin.png"); //hint image
								(board[i][j]).setCellImage(img);
								(board[i][j]).setHint(img);
								(board[i][j]).redraw();
							}
						}
					}
					layout();
					forceFocus();
				}

			});

		}

		else{
			this.askedForSolution = false;
			if(hints !=null)
			{
				for (int i = 0; i < hints.length; i++) {
					for (int j = 0; j < hints[i].length; j++) {
						for (int w = 0; w < hints[i][j].length; w++){
							hints[i][j][w] = false;

						}


					}

				}
			}
		}

	}
}

/**
 * @Project: mvc
 * @Class : MyController.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package controller;

import java.io.IOException;
import java.util.HashMap;

import algorithms.demo.Maze2dAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.SearchableMaze2D;
import algorithms.search.SearchableMaze3D;
import algorithm.generic.Solution;
import model.Maze2dDraw;
import model.Maze3dDraw;
import model.Model;
import view.View;


/**
 * The Class MyController.
 */
public class MyController implements Controller {

	/** The c view. */
	private View cView;
	
	/** The c model. */
	private Model cModel;
	
	/** The commands list. */
	protected HashMap<String, Command> commandsList;
	
	/**
	 * Instantiates a new my controller.
	 *
	 * @param cView
	 *            the c view
	 * @param cModel
	 *            the c model
	 */
	public MyController(View cView, Model cModel) {
		super();
		this.cView = cView;
		this.cModel = cModel;
		cView.setCommands(getCommand());
	}

	/* (non-Javadoc)
	 * @see controller.Controller#getCommand()
	 */

	@Override
	public HashMap<String, Command> getCommand() {
		this.commandsList = new HashMap<String,Command>();
		
		commandsList.put("dir", new dirCommand());	
		commandsList.put("generate", new generateCommand());   
		commandsList.put("display", new displayCommand()); 
		commandsList.put("save", new saveMazeCommand());	
		commandsList.put("load", new loadMazeCommand());
		commandsList.put("maze", new sizeInMemoCommand());
		commandsList.put("file", new fileSizeCommand()); 
		commandsList.put("solve", new solveCommand()); 
		commandsList.put("exit", new exitCommand());
		
		return commandsList;
	}

	/* (non-Javadoc)
	 * @see controller.Controller#start()
	 */
	@Override
	public void start() {
		this.cView.start();
	}
	
	/**
	 * The Class dirCommand.
	 */
	public class dirCommand implements Command{

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {

			try {
				if(args[1] != null){
					String fName = args[1];
					cView.dir(fName);
				}
				
			} catch (ArrayIndexOutOfBoundsException e) {
				cView.errorMsg("Invalid Args");
			}
			catch(NullPointerException e1){
				cView.errorMsg("Invalid Args");
			}
		}
	}

	/**
	 * The Class generateCommand.
	 */
	public class generateCommand implements Command,Runnable{

		/** The args. */
		String args[];		
		
		/** The maze set. */
		String[] mazeSet = new String[4];
		
		/** The gen thread. */
		Thread genThread;

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {

			try {
					mazeSet[0] = args[3]; //name
					mazeSet[1] = args[4]; //X
					mazeSet[2] = args[5]; //Y
					mazeSet[3] = args[6]; //Z
				
				cModel.generateModel(mazeSet);
				cView.displayMsg("Maze " + args[3] + " is ready!");
			} catch (ArrayIndexOutOfBoundsException e) {
				cView.errorMsg("Index Out Of Bounds Error!");
			}
		}

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
				this.args = args;
				genThread = new Thread(this);
				genThread.start();
		}

	}
	
	/**
	 * The Class displayCommand.
	 */
	public class displayCommand implements Command{

		/** The maze3d searchable. */
		Searchable<Position> maze3dSearchable;
		
		/** The maze2d searchable. */
		Searchable<Position> maze2dSearchable;
		
		/** The maze3d draw. */
		Maze3dDraw maze3dDraw;
		
		/** The maze2d draw. */
		Maze2dDraw maze2dDraw;	
		
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {

			if(args[1].equals("maze")){
				
				maze3dSearchable = cModel.getMazeName(args[2]);
				if(maze3dSearchable != null){
					Maze3d maze = ((SearchableMaze3D)maze3dSearchable).getMaze();
					maze3dDraw = new Maze3dDraw(maze);
					cView.displayMaze(maze3dDraw);
					cView.displayStartGoalPoints(maze3dSearchable);
				}
				else{
					cView.errorMsg("Error with display maze.");
				}
			}

			else if(args[1].equals("cross")){
				
				int index = Integer.parseInt(args[5]);
				char section = args[4].toLowerCase().charAt(0);
				String name = args[7].toLowerCase();
				
				maze2dSearchable = cModel.getMazeSection(name, section, index);
				if(maze2dSearchable != null){
					Maze2dAdapter maze2d = ((SearchableMaze2D)maze2dSearchable).getMyMaze();
					maze2dDraw = new Maze2dDraw(maze2d);
					cView.displayCrossSection(maze2dDraw);
				}
				else{
					cView.errorMsg("Error with display cross section maze.");
				}
			}
			
			else if(args[1].equals("solution")){
				if(cModel.findMazeName(args[2])){
					Solution<Position> solution = cModel.getSolution(args[2]);
					if(solution != null){
						cView.displayMazeSolution(solution);
					}else{
						cView.errorMsg("There is no Solution for " + args[2] + " to display.");
					}
				}else{
					cView.errorMsg("Cant find the maze " + args[2] +" for Solution.");
				}
			}
			else{
				cView.errorMsg("Error with display solution.");
			}
		}
	}

	/**
	 * The Class saveMazeCommand.
	 */
	public class saveMazeCommand implements Command{

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			cModel.saveMaze(args[2] , args[3] );
			cView.displayMsg("file saved! ("+ args[3] +")");
		}
	}

	/**
	 * The Class loadMazeCommand.
	 */
	public class loadMazeCommand implements Command {

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			try{
				cModel.loadMaze(args[2] , args[3]);
				cView.displayMsg(args[2] + " Loaded!");
			}catch(IOException e){
				cView.errorMsg("Error with Loading a File.");
			}
		}
		
	}

	/**
	 * The Class sizeInMemoCommand.
	 */
	public class sizeInMemoCommand implements Command{

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			int size = cModel.getSizeInMemo(args[2]);
			if(size > 0){
				cView.displayMsg(args[2] + " size in memory: "+ size);
			}
			else{
				cView.errorMsg("Maze not found in the maze list("+args[2]+")");
			}
		}
	}

	/**
	 * The Class fileSizeCommand.
	 */
	public class fileSizeCommand implements Command{

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			
			long size = cModel.getFileSize(args[2]);
			if(size > 0){
				cView.displayMsg("File:"+args[2]+" ,Size:" + size);
			}
			else{
				cView.errorMsg("File not found in the files list");
			}
		}
		
	}

	/**
	 * The Class solveCommand.
	 */
	public class solveCommand implements Command,Runnable{

		/** The args. */
		String[] args;
		
		/** The solve thread. */
		Thread solveThread;
		
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			if( (cModel.getMazeName(args[1]) != null) && (args[2].equals("bfs") || args[2].equals("astar")) ){
				this.args = args;
				solveThread = new Thread(this);
				solveThread.start();
			}
			else{
				cView.errorMsg("Invalid Args");
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			String mazeName , algo;
			
			mazeName = args[1];
			algo = args[2];
			
			cModel.solveMaze(mazeName , algo);
			cView.displayMsg("Solution for -" + args[1] + "- is ready!");	
		}
	}
	
	/**
	 * The Class exitCommand.
	 */
	public class exitCommand implements Command{

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
	public void doCommand(String[] args) {
			cModel.exit();
		}
	}
}

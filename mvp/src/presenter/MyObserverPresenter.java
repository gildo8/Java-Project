/**
 * @Project: mvp
 * @Class : MyObserverPresenter.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package presenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

import algorithms.demo.Maze2dAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.SearchableMaze2D;
import algorithms.search.SearchableMaze3D;
import algorithm.generic.Solution;
import dataProject.ServerConstant;
import dataProject.VariablesData;
import model.Maze2dDraw;
import model.Maze3dDraw;
import model.Model;
import view.View;


/**
 * The Class MyObserverPresenter , Observer and implements Presenter @see Presenter
 */
public class MyObserverPresenter implements Presenter {

	/** The model. */
	private Model model;

	/** The view. */
	private View  view;

	/** The commands. */
	HashMap<String, Command> commands;

	/**
	 * Instantiates a new my observer presenter.
	 *
	 * @param myView the my view
	 * @param myModel the my model
	 */
	public MyObserverPresenter(View myView, Model myModel) {

		this.view = myView;
		this.model = myModel;
		view.setCommands(getCommands()); 
	}

	/* (non-Javadoc)
	 * @see controller.Controller#getCommands()
	 */
	public HashMap<String, Command> getCommands() {

		this.commands = new HashMap<String, Command>();

		commands.put("dir", new DirCommand());
		commands.put("solve", new SolveModelCommand());
		commands.put("generate", new GenerateModelCommand());
		commands.put("display", new DisplayModelCommand());
		commands.put("save", new SaveModelCommand());
		commands.put("load", new LoadModelCommand());
		commands.put("file", new ModelSizeInFileCommand());//size
		commands.put("maze", new ModelSizeInMemoryCommand());//size
		commands.put("exit", new ExitCommand());

		return commands;
	}


	/**
	 * The Class SolveModelCommand.
	 */
	public class SolveModelCommand implements Command{

		/** The args. */
		String [] args;

		/** The my thread. */
		Thread myThread;



		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {


			String name = args[1];


			try {


				model.solveModel(name);

			}catch (ArrayIndexOutOfBoundsException e){

				model.solveModel(name);
			}

		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class GenerateModelCommand.
	 */
	public class GenerateModelCommand implements Command{

		/** The args. */
		String [] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {
			//Length = X , Width = Y , Height = Z
			
			try{
				int x = Integer.parseInt(args[4]);
				int y = Integer.parseInt(args[5]);
				int z = Integer.parseInt(args[6]);

				if(x > 0 && y > 0 && z > 0){

					try {

						String name = args[3];
						String [] params = new String[3];
						params[0] = args[4];
						params[1] = args[5];
						params[2] = args[6];

						model.generateModel(name,params);

					} catch (ArrayIndexOutOfBoundsException e) {
						view.displayMsg("Invalid arguments");
					}


				}

			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

				view.displayMsg("invalid parameters");
			}
		}

		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class DirCommand.
	 */
	public class DirCommand implements Command{

		/** The args. */
		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try{
				if(args[1] != null){
					String fileName= args[1];
					view.dir(fileName);
				}
			}
			catch (ArrayIndexOutOfBoundsException e){
				view.displayMsg("Error, no arguments");

			}
		}

		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class DisplayModelCommand.
	 */
	public class DisplayModelCommand implements Command{

		/** The args. */
		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {
			Searchable<Position> myMaze3dSearchableAdapter; 
			Searchable<Position> myMaze2dSearchableAdapter; 
			Maze3dDraw myMaze3dDrawAdapter;
			Maze2dDraw myMaze2dDrawAdapter;

			try{
				if(args[1] != null){
					switch (args[1]) {
					case "maze":

						myMaze3dSearchableAdapter = model.getNameToModel(args[2]);
						if(myMaze3dSearchableAdapter != null){
							Maze3d myMaze = ((SearchableMaze3D) myMaze3dSearchableAdapter).getMaze();
							myMaze3dDrawAdapter = new Maze3dDraw(myMaze); 
							view.displayMaze(myMaze3dDrawAdapter);
						}
						else{
							view.displayMsg("Invalid values");	
						}

						break;

					case "solution":

						if(model.getNameToModel(args[2]) == null){
							view.displayMsg("No record of " + args[2]+ ". Try to create it first");
						}
						else{
							Solution<Position> solution= model.getSolution(args[2]);
							if (solution!=null){
								view.displayMazeSolution(solution);
							}
							else{
								view.displayMsg("No solution for " + args[2]+ ". Try to create it first");
							}

						}

						break;

					case "cross":

						String name = args[7];
						String dimention = args[4];
						int section = Integer.parseInt(args[5]);

						myMaze2dSearchableAdapter = model.CrossSectionBy(name, dimention, section);
						if(myMaze2dSearchableAdapter != null){
							Maze2dAdapter myMaze2d = ((SearchableMaze2D) myMaze2dSearchableAdapter).getMyMaze();
							myMaze2dDrawAdapter = new Maze2dDraw(myMaze2d); 
							view.displayCrossSection(myMaze2dDrawAdapter);
						}
						else{
							view.displayMsg("Invalid values");	
						}
						break;

					default:
						view.displayMsg(" invaild args.");
						break;
					}
				}
			}catch (ArrayIndexOutOfBoundsException | NullPointerException e){
				view.displayMsg("Invalid values");
			}
		}

		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class SaveModelCommand.
	 */
	public class SaveModelCommand implements Command{

		/** The args. */
		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try{
				model.saveModel(args[2], args[3]);}
			catch (ArrayIndexOutOfBoundsException e){
				view.displayMsg("Invalid input");
			}
		}

		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class LoadModelCommand.
	 */
	public class LoadModelCommand implements Command{

		/** The args. */
		String[] args;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try {
				model.loadModel(args[2], args[3]);
			} catch (IOException e) {
				view.displayMsg("File not found.");

			}catch(ArrayIndexOutOfBoundsException c){
				view.displayMsg("Invalid input");
			}
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class ModelSizeInMemoryCommand.
	 */
	public class ModelSizeInMemoryCommand implements Command{

		/** The args. */
		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			if(model.getNameToModel(args[2]) != null){

				int size;
				try {
					size = model.getModelSizeInMemory(args[2]);
					view.displayMsg("Maze size in memory: " + size + " bytes");
				} catch (IOException e) {

					view.displayMsg("Invalid arguments");
				}
			}
			else{
				view.displayMsg("No such name exist");
			}
		}

		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class ModelSizeInFileCommand.
	 */
	public class ModelSizeInFileCommand implements Command{

		/** The args. */
		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try{
				long size = model.getModelSizeInFile(args[2]);
				view.displayMsg("Maze size in file: " + size + " bytes");
			}catch(ArrayIndexOutOfBoundsException e){
				view.displayMsg("Invalid args");
			}
		}

		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class ExitCommand.
	 */
	public class ExitCommand implements Command{

		/** The args. */
		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try {
				view.exit();
				model.exit();
			} catch (IOException e) {
				view.displayMsg("Can't close thread");
			}
		}

		/* (non-Javadoc)
		 * @see presenter.Command#setCommandArguments(java.lang.String[])
		 */
		@Override
		public void setCommandArguments(String[] args) {
			this.args = args;

		}
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof View)
		{
			
			if(arg!=null && !arg.equals(VariablesData.MODEL_ERROR))
			{

				Command command= view.getUserCommand();
				command.doCommand();
			}

		}
		else if(o instanceof Model){

			
			String [] args = (String[])arg;
			switch (args[0]){

			case VariablesData.MODEL_SAVED:

				view.displayMsg(args[1]+" was saved");

				break;

			case VariablesData.FILE_NOT_FOUND:

				view.displayMsg(VariablesData.FILE_NOT_FOUND);

				break;
			case VariablesData.NO_MODEL_FOUND:

				view.displayMsg(VariablesData.NO_MODEL_FOUND);

				break;
			case VariablesData.ERROR_CLOSING_FILE:

				view.displayMsg(VariablesData.ERROR_CLOSING_FILE);

				break;

			case VariablesData.MODEL_SOLVED:

				//view.displayString(VariablesData.MODEL_SOLVED);
				if(model.getSolution(args[1]) != null){
				view.displayMazeSolution(model.getSolution(args[1]));
				}
				else{
					view.displayMsg("Solution is not ready");
				}

				break;

			case VariablesData.MODEL_LOADED:

				view.displayMsg(VariablesData.MODEL_LOADED + " " +args[1]);
				Searchable<Position> maze3d = model.getNameToModel(args[1]);
				SearchableMaze3D myMaze3d = (SearchableMaze3D) maze3d;
				Maze3dDraw maze3dDrew = new Maze3dDraw(myMaze3d.getMaze());
				view.displayMaze(maze3dDrew);

				break;

			case VariablesData.PROPERTIES_ARE_NO_SET:

				view.displayMsg(VariablesData.PROPERTIES_ARE_NO_SET);

				break;

			case VariablesData.MODEL_GENERATED:
				Searchable<Position> maze = model.getNameToModel(args[1]);
				SearchableMaze3D myMaze = (SearchableMaze3D) maze;
				Maze3dDraw mazeDrew = new Maze3dDraw(myMaze.getMaze());
				view.displayMaze(mazeDrew);

				break;

			case VariablesData.MODEL_EXIT:

				view.displayMsg(VariablesData.MODEL_EXIT);

				break;
			case VariablesData.MODEL_ERROR:

				view.displayMsg(VariablesData.MODEL_ERROR);
				
			case ServerConstant.DISCONNECT:
				view.displayMsg(args[0] + " " + args[1]);

				break;

			}
		}
	}

	/* (non-Javadoc)
	 * @see presenter.Presenter#start()
	 */
	@Override
	public void start() {

		view.start();

	}
}

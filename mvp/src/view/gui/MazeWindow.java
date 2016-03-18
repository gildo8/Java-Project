/**
 * @Project: mvp
 * @Class : MazeWindow.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithm.generic.Solution;
import boot.Run;
import boot.RunCli;
import boot.RunGui;
import boot.WriteProperties;
import dataProject.ClientProperties;
import dataProject.LoadProperties;
import dataProject.MazeProperties;
import dataProject.VariablesData;
import presenter.Command;
import view.DrawMaze;
import view.View;


/**
 * The Class MazeWindow a kind of implementation of View Interface
 * Defining and Designing the Window , extends the InitWidget() From BasicWindow
 * so we can Create and Design the window. 
 */
public class MazeWindow extends BasicWindow implements View {

	/** The commands. */
	protected HashMap<String, Command>  commands;
	
	/** The Last user command. */
	protected Command LastUserCommand =null;
	
	/** The board widget. */
	CommonBoard boardWidget;
	
	/** The preferences. */
	ClientProperties preferences;
	
	/** The maze name. */
	String mazeName = null;
	
	/** The data recieved. */
	Maze3d dataRecieved = null; 
	
	/** The input. */
	MazeProperties input;
	
    /** The bar. */
    ProgressBar bar ;

	/**
	 * Instantiates a new maze window.
	 *
	 * @param display the display
	 * @param shell the shell
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public MazeWindow(Display display,Shell shell,String title, int width, int height) {
		super(display,shell,title,width,height);
	}

	/**
	 * Instantiates a new maze window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);

	}

	/* (non-Javadoc)
	 * @see view.gui.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
	    
		shell.addListener(SWT.Close,new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				exit();
				display.dispose();
				setUserCommand(commands.get("exit"));
			}

		});
		//sets the background image to white
		shell.setBackground(new Color(null,255,255,255));
		shell.setLayout(new GridLayout(3,false));
//		shell.setText("The Maze"); //sets the text of window
		//creates a tool bar
		Menu menuBar = new Menu(shell, SWT.BAR);
		
		//creates a file category in toolbar
		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeFileMenu.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeFileMenu.setMenu(fileMenu);
		
		//creates a Maze category in toolbar
		MenuItem cascadeMazeMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeMazeMenu.setText("&Maze");
		Menu mazeMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeMazeMenu.setMenu(mazeMenu);

		//creates a help category in toolbar
		MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeHelpMenu.setText("&Help");
		Menu HelpMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeHelpMenu.setMenu(HelpMenu);


		/******* Open Properties Button menu *********/
		
		MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Open Properties");
		item.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {


			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN); //opens a dialog box in which we can select a xml file and load it
				fd.setText("open");
				fd.setFilterPath(VariablesData.FILE_PATH);
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename=fd.open(); //choose the file
				if(filename!=null){
					setProperties(filename);
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							switch(preferences.getUi())
							{
							case CLI: //if the properties calls for CLI
								exit(); //dispose all data
								display.dispose(); //dispose display
								setUserCommand(commands.get("exit"));
								RunCli demo=new RunCli(); //call for a function that works with cli
								demo.startCli(getPreferences());
								break;
							case GUI:
								exit();// dispose all and close timer task
								display.dispose();
								setUserCommand(commands.get("exit"));
								RunGui demoG = new RunGui(); //calls for a function that recreates a gui window
								demoG.startGui(getPreferences());
								break;
							default:
								return;	
							}
						}
					});
				}
			}

		});


		/******* Write Properties Button menu *********/
		
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Write Properties");
		item.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.asyncExec(new Runnable() {

					@Override
					public void run() {//this function works on the same basis as open Properties the only difference is the source of the Properties data here we recieve it directly from the user
						WriteProperties guiProp=new WriteProperties();
						if(guiProp.WriteProGui(shell)!=-1)
						{
							preferences=Run.readPropertiesFromXML();
							switch(preferences.getUi())
							{
							case CLI:
								exit();
								display.dispose();
								LastUserCommand= commands.get("exit");
								setChanged();
								notifyObservers();
								RunCli demo=new RunCli();
								demo.startCli(getPreferences());
								break;
							case GUI:
								exit();
								display.dispose();
								LastUserCommand = commands.get("exit");
								setChanged();
								notifyObservers();
								RunGui demoG = new RunGui();
								demoG.startGui(getPreferences());
								break;
							default:
								return;	
							}
						}
					}
				});


			}

		});
		
		/******* Exit Button menu *********/
		
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Exit");
		item.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {


			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				exit();
				display.dispose();
				setUserCommand(commands.get("exit"));	
			}

		});

		
		shell.setMenuBar(menuBar);


		//button that solves the maze
		//creates an instance of boardWidget
		boardWidget = new MazeBoard(shell,SWT.NONE);
		boardWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true,3,5));

		
		Label name1 = new Label(shell, SWT.BORDER);
		name1.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 0, 0));
		name1.setText("Name: ");
		
		Text name = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		name.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false, 0, 0));

		name1.setVisible(false);
		name.setVisible(false);
		
		Label xLabel = new Label(shell, SWT.BORDER);
		xLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 0, 0));
		xLabel.setText("X: ");
		
		Text mazeX = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		mazeX.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false, 0, 0));

		xLabel.setVisible(false);
		mazeX.setVisible(false);
	
		Label yLabel = new Label(shell, SWT.BORDER);
		yLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 0, 0));
		yLabel.setText("Y: ");
		
		Text mazeY = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		mazeY.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false, 0, 0));
		
		yLabel.setVisible(false);
		mazeY.setVisible(false);
		
		Label zLabel = new Label(shell, SWT.BORDER);
		zLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 0, 0));
		zLabel.setText("Z: ");

		Text mazeZ = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		mazeZ.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false, 0, 0));
		
		zLabel.setVisible(false);
		mazeZ.setVisible(false);
		
		/******* Generate Button Menu *********/
		
		MenuItem generate = new MenuItem(mazeMenu, SWT.PUSH);
		generate.setText("Generate Maze");
		generate.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
//			    bar = new ProgressBar(shell, SWT.SMOOTH);
//			    bar.setBounds(10, 10, 200, 32);
			    
				boardWidget.won=false;
				boardWidget.getShell().setBackgroundImage(null);
				//boardWidget.setVisible(true);
				dataRecieved=null;
				ClassInputDialog dlg = new ClassInputDialog(shell,MazeProperties.class);
				MazeProperties tempInput=(MazeProperties)dlg.open();
				
				if(tempInput!=null)
				{
					input=tempInput;
					mazeName=input.getMazeName();
					String x = "" +input.getRows();
					String y = "" + input.getColumns();
					String z = "" + input.getFloors();

					name.setText(mazeName);
					mazeX.setText(x);
					mazeY.setText(y);
					mazeZ.setText(z);

					name.setVisible(true);
					name1.setVisible(true);
					
					mazeX.setVisible(true);
					xLabel.setVisible(true);
					
					mazeY.setVisible(true);
					yLabel.setVisible(true);
					
					mazeZ.setVisible(true);
					zLabel.setVisible(true);
					
					boardWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true,1,6));
					boardWidget.setRedraw(true);
					
					String [] args= {"generate", "maze", "3d",input.getMazeName(), x, y, z};
					Command command = commands.get("generate");
					command.setCommandArguments(args);
					setUserCommand(command);
					
//					openProgressBar();
					shell.setText(args[3] + "'s Maze!");
					
				}
				boardWidget.forceFocus();
			}

		});

		/******* Solve Maze Button menu *********/

		MenuItem solveMaze = new MenuItem(HelpMenu, SWT.PUSH);
		solveMaze.setText("Solve Maze");

		solveMaze.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeName!=null && !boardWidget.won){
					String [] args= {"solve",mazeName};
					Command command= commands.get("solve");
					command.setCommandArguments(args);
					setUserCommand(command);

				}
				else{ //if there is no maze to solve
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					messageBox.setText("Information");
					messageBox.setMessage("No maze to solve");
					messageBox.open();

				}
			}

		});	

		
		/************ Keys Button Menu **************/
		
		
		MenuItem keyMenu = new MenuItem(HelpMenu, SWT.PUSH);
		keyMenu.setText("Keys");

		keyMenu.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//if there is no maze to solve
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					messageBox.setText("Keys");
					messageBox.setMessage("UP: Arrow UP \nDOWN: Arrow DOWN \nLEFT: Arrow LEFT \nRIGHT: Arrow RIGHT \n\nFLOOR UP: PAGE UP \nFLOOR DOWN: PAGE DOWN");
					messageBox.open();
			}
		});	
		
		
		/******* Save Maze Button menu *********/
		
		MenuItem saveMaze = new MenuItem(mazeMenu, SWT.PUSH);
		saveMaze.setText("Save Maze");
		saveMaze.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{

					String [] args= {"save", "maze" ,mazeName,mazeName+".txt"};
					Command command= commands.get("save");
					command.setCommandArguments(args);
					setUserCommand(command);
					boardWidget.forceFocus();
				}
				catch (NullPointerException e) {
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					messageBox.setText("Information");
					messageBox.setMessage("There is no maze named " + mazeName);
					messageBox.open();

					boardWidget.forceFocus();
				}
			}
		});	
		
		
		
		/******* Load Maze Button menu *********/
		
		
		
		MenuItem loadMaze = new MenuItem(mazeMenu, SWT.PUSH);
		loadMaze.setText("Load Maze");
		loadMaze.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					ClassInputDialog dlg = new ClassInputDialog(shell,LoadProperties.class);
					LoadProperties tempInput = (LoadProperties)dlg.open();
					mazeName = tempInput.nameOfFile;
					String [] args= {"load", "maze" ,tempInput.nameOfFile,tempInput.nameOfMaze};
					Command command = commands.get("load");
					command.setCommandArguments(args);
					setUserCommand(command);
					boardWidget.forceFocus();
				}
				catch (NullPointerException e) {
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					messageBox.setText("Information");
					messageBox.setMessage("Can't load maze");
					messageBox.open();
					boardWidget.forceFocus();
				}
			}
		});	

	}

	/**
	 * Sets the properties.
	 *
	 * @param filename the new properties
	 */
	protected void setProperties(String filename) {

		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(VariablesData.FILE_PATH + "properties.xml");
			d=new XMLDecoder(in);
			preferences=(ClientProperties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			displayMsg("Error Loading Properties");
		}
	}

	/* (non-Javadoc)
	 * @see view.View#dir(java.lang.String)
	 */
	@Override
	public void dir(String fileName) {
		// not relevant for this view

	}
	
	/* (non-Javadoc)
	 * @see view.View#displayMaze(view.DrawMaze)
	 */
	@Override
	public <T> void displayMaze(DrawMaze<T> draw) {
		boardWidget.won = false;
		boardWidget.setAskedForSolution(false);
		boardWidget.setVisible(true);
		boardWidget.displayProblem(draw);
	}
	
	/* (non-Javadoc)
	 * @see view.View#displayCrossSection(view.DrawMaze)
	 */
	@Override
	public <T> void displayCrossSection(DrawMaze<T> draw) {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
	 * @see view.View#displayMazeSolution(algorithm.generic.Solution)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayMazeSolution(Solution<T> sol) {
		boardWidget.displaySolution((Solution<Position>)sol);

	}
	
	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;

	}
	
	/* (non-Javadoc)
	 * @see view.View#displayMsg(java.lang.String)
	 */
	@Override
	public void displayMsg(String toPrint) {
		if(!boardWidget.isDisposed()){
			MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			messageBox.setText("Information");
			messageBox.setMessage(toPrint);
			messageBox.open();
		}
	}
	
	/* (non-Javadoc)
	 * @see view.View#getUserCommand()
	 */
	@Override
	public Command getUserCommand() {

		return LastUserCommand;
	}
	
	/* (non-Javadoc)
	 * @see view.View#setUserCommand(presenter.Command)
	 */
	@Override
	public void setUserCommand(Command command) {
		LastUserCommand= command;
		setChanged();
		notifyObservers("New command"); 

	}
	
	/* (non-Javadoc)
	 * @see view.View#exit()
	 */
	@Override
	public void exit() {
		if(boardWidget!=null){
			boardWidget.destructBoard();
			boardWidget.dispose();
		}

	}
	
	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		this.run();

	}

	/**
	 * Gets the preferences.
	 *
	 * @return the preferences
	 */
	public ClientProperties getPreferences() {
		return preferences;
	}

	/**
	 * Sets the preferences.
	 *
	 * @param preferences the new preferences
	 */
	public void setPreferences(ClientProperties preferences) {
		this.preferences = preferences;
	}

	/* (non-Javadoc)
	 * @see view.View#errorMsg(java.lang.String)
	 */
	@Override
	public void errorMsg(String msg) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayStartGoalPoints(algorithms.search.Searchable)
	 */
	@Override
	public <T> void displayStartGoalPoints(Searchable<T> maze) {
		// TODO Auto-generated method stub
		
	}
	
//	public void openProgressBar(){	
//	    for (int i = 0; i <= bar.getMaximum(); i++) {
//	        try {
//	          Thread.sleep(100);
//	        } catch (Throwable th) {
//	        }
//	        bar.setSelection(i);
//	      }
//	    bar.setVisible(false);
//	}
	
}





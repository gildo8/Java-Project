/**
 * @Project: RemoteServer
 * @Class : RemoteServerWindow.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import presenter.RemoteServerCommand;
import presenter.RemoteServerProperties;
import boot.RunGui;
import jaco.mp3.player.MP3Player;
import model.Constants;


/**
 * The Class Remote Server Window builds the Remote Server Window
 */
public class RemoteServerWindow extends BasicWindow implements View {
	
	/** The clients. */
	String [] clients;
	
	/** The status. */
	Text status;
	
	/** The server properties. */
	RemoteServerProperties serverProperties;
	
	/** The command map. */
	ConcurrentHashMap<String, RemoteServerCommand> commandMap = new ConcurrentHashMap<String, RemoteServerCommand>();
	
	/** The last command. */
	RemoteServerCommand lastCommand = null;
	
	/** The Data from model. */
	String DataFromModel= null;
	
	/** The list. */
	List list;
	
	/** The player. */
	public MP3Player player;
	
	/**
	 * Instantiates a new remote server window.
	 *
	 * @param title
	 *            the title
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public RemoteServerWindow(String title, int width, int height) {
		super(title, width, height);
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\wallpaper1.png")); //setting the image and some music:)
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		player = new MP3Player();
		player.addToPlayList(new File(".\\resources\\sounds\\sound.mp3"));
		player.play();
		player.setRepeat(true);
	}
	
	/**
	 * Instantiates a new remote server window.
	 *
	 * @param title
	 *            the title
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param display
	 *            the display
	 * @param shell
	 *            the shell
	 */
	public RemoteServerWindow(String title, int width, int height,Display display,Shell shell) {
		super(display,shell,title, width, height);
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\wallpaper1.png")); //setting the image and some music:)
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		player = new MP3Player();
		player.addToPlayList(new File(".\\resources\\sounds\\sound.mp3"));
		player.play();
		player.setRepeat(true);
	}
	
	
	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		
		shell.addListener(SWT.Close,new Listener(){ 

			@Override
			public void handleEvent(Event arg0) {
				
				setUserCommand(commandMap.get("exit"));
				display.dispose();				 
			}
			
		});
		
		initMenu();
		shell.setLayout(new GridLayout(2,false)); 
		initMenu();
		
		
		/***** Start Server Button *****/
		
		Button startServerButton=new Button(shell,SWT.PUSH);
		startServerButton.setText("Start Server");
		startServerButton.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));
		startServerButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				setUserCommand(commandMap.get("start server"));
				
			}
			
		});
		
		/***** Stop Server Button *****/
		
		Button stopServerButton = new Button(shell,SWT.PUSH);
		stopServerButton.setText("Stop Server");
		stopServerButton.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		stopServerButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				setUserCommand(commandMap.get("stop server"));
				
			}
		});
		
				
		/***** Desgin of Window *****/
		final Label listLabel=new Label(shell,SWT.CENTER);
		
		listLabel.setForeground(listLabel.getDisplay().getSystemColor( SWT.COLOR_DARK_BLUE)); //setting color
		listLabel.setText("Connected Clients :");
		
		FontData fontData = listLabel.getFont().getFontData()[0];
		fontData.setHeight(30);
		Font font = new Font(display, new FontData(fontData.getName(), fontData
		    .getHeight(), SWT.BOLD)); 
		
		listLabel.setFont(font);
		listLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1)); //setting layout
		
		list = new List(shell, SWT.MULTI | SWT.V_SCROLL); //setting the list of connected clients
		list.setBounds(10, 10, 10, 10);
		list.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_CYAN));
		list.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));

		//setting status of selected clients
	    status = new Text(shell, SWT.CENTER | SWT.H_SCROLL | SWT.V_SCROLL);
		FontData fontDataStatus = listLabel.getFont().getFontData()[0];
		fontDataStatus.setHeight(10);
		Font fontStatus = new Font(display, new FontData(fontDataStatus.getName(), fontDataStatus
		    .getHeight(), SWT.BOLD));
	    status.setFont(fontStatus);
	    status.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
	    status.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true,2,1));
	  
	    list.addSelectionListener(new SelectionListener() { //on selection show status
	      public void widgetSelected(SelectionEvent event) {
	        String[] selectedClients = list.getSelection(); //get all clients selcted
	        @SuppressWarnings("unused")
			String outString = "";
	        
	        if(selectedClients.length>1)
	        {// for each selected client
	        	for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++)
	        	{
		        	RemoteServerCommand command = commandMap.get("connection status");
		        	command.setArguments(selectedClients[loopIndex]);
		        	setUserCommand(command);
		        	
	        		if(loopIndex!=selectedClients.length-1)
	        			outString += selectedClients[loopIndex]+ "\n"; 
	        	}
	        }
	        else if(selectedClients.length==1){
	        	RemoteServerCommand command = commandMap.get("connection status");
	        	command.setArguments(selectedClients[0]);
	        	setUserCommand(command);
	        	outString=selectedClients[0] + "\n";
	        }
	        else
	        	status.setText("");
	      }

	      public void widgetDefaultSelected(SelectionEvent event) {
	        int[] selectedClients = list.getSelectionIndices();
	        String outString = "";
	        for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++){
	        	outString += selectedClients[loopIndex] + " ";
	        	
	        	
	        }
	        System.out.println("Selected Clients: " + outString);
	      }
	    });
		
	    /***** Disconnect Client Button *****/
	    
		Button disconnectClientButton=new Button(shell,SWT.PUSH | SWT.CENTER);
		disconnectClientButton.setText("Disconnect Client");
		disconnectClientButton.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,false,false,2,1));
		disconnectClientButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] unparsedClients = list.getSelection(); //getting all selected clients
				for(int i=0;i<unparsedClients.length;i++){
					
					RemoteServerCommand command = commandMap.get("disconnect user");
		        	command.setArguments(unparsedClients[i]);
		        	setUserCommand(command);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		 
		
	}
	
	
	/**
	 * Inits the Menu Bar.
	 */
	private void initMenu() {
		Menu menuBar = new Menu(shell, SWT.BAR);
		/***** Creates File Category *****/
		
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);

        /***** Add Open Properties Item to File's Category *****/
		MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Open Properties");
			item.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN); 
				fd.setText("open");
				fd.setFilterPath(Constants.FILE_PATH);
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename=fd.open();
				if(filename!=null){
					setProperties(filename);
					shell.close();
					new RunGui().loadWindow(serverProperties); //runs a new programm with new properties from user
					
				}
			}
	    	
	    });
			
			/***** Add Write Properties Item to File's Category *****/
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
							RemoteServerWriteProperties guiProp=new RemoteServerWriteProperties();
							if(guiProp.writeProperties(display,shell)!=-1)
							{
								shell.close();
								new RunGui().loadWindow(readProperties()); //runs a new programm with new properties from user
							}
						}
				});
				}
			});

				/***** Add Exit Item to File's Category *****/
			 	item = new MenuItem(fileMenu, SWT.PUSH);
			    item.setText("Exit");
			    item.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						setUserCommand(commandMap.get("exit"));
						display.dispose();		
					}
			    	
			    });
			    
			    
			    
			    /**** Mute + Play Button Menu *****/
			    
				MenuItem cascadeSoundMenu = new MenuItem(menuBar, SWT.CASCADE);
//				cascadeSoundMenu.setText("&Sound");
				
				cascadeSoundMenu.setImage(new Image(display,".\\resources\\images\\menu.png"));
				Menu SoundMenu = new Menu(shell, SWT.ERROR_MENU_NOT_DROP_DOWN);
				cascadeSoundMenu.setMenu(SoundMenu);
				
				MenuItem mute = new MenuItem(SoundMenu, SWT.PUSH);
				mute.setText("Turn OFF");
				MenuItem play = new MenuItem(SoundMenu, SWT.PUSH);
				play.setText("Turn ON");
				play.setEnabled(false);
				
				mute.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						
						if(!player.isStopped()){
							player.stop();
							play.setEnabled(true);
							mute.setEnabled(false);
						}
						
					}
					
				});
				
				play.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						
						if(player.isStopped()){
							player.play();
							mute.setEnabled(true);
							play.setEnabled(false);
						}
					}
				});	    
			shell.setMenuBar(menuBar);
	}
	
	/**
	 * Sets the properties from certain file
	 *
	 * @param filename
	 *            the new properties
	 */
	private void setProperties(String filename) {
		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			serverProperties = (RemoteServerProperties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Read properties from XML file
	 *
	 * @return the remote server properties
	 */
	public static RemoteServerProperties readProperties()
	{
		XMLDecoder d;
		RemoteServerProperties p=null;
		try {
			FileInputStream in=new FileInputStream(Constants.FILE_PATH + "properties.xml");
			d=new XMLDecoder(in);
			p=(RemoteServerProperties)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e) {
			return new RemoteServerProperties();
		}
		return p;
	}
	
	/* (non-Javadoc)
	 * @see view.View#getCommand()
	 */
	@Override
	public RemoteServerCommand getCommand() {
		return this.lastCommand;
	}
	
	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.concurrent.ConcurrentHashMap)
	 */
	@Override
	public void setCommands(ConcurrentHashMap<String, RemoteServerCommand> commandMap) {
		this.commandMap=commandMap;
		
	}
	
	/* (non-Javadoc)
	 * @see view.View#Display(java.lang.String)
	 */
	@Override
	public void Display(String msg) {
		display.asyncExec(new Runnable(){
			@Override
			public void run() {
				if(!shell.isDisposed()){
				MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				messageBox.setText("Remote maze client");
				messageBox.setMessage(msg);
				messageBox.open(); //message to user
				}
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see view.View#DisplayStatus(java.lang.String)
	 */
	@Override
	public void DisplayStatus(String msg) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				if(!shell.isDisposed()){
				status.setText(msg);
				}
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see view.View#saveData(java.lang.String)
	 */
	@Override
	public void saveData(String data) {
		this.DataFromModel=data;
		
	}
	
	/* (non-Javadoc)
	 * @see view.View#addClient(java.lang.String)
	 */
	@Override
	public void addClient(String Client) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				list.add(Client);
				
			}
			
		});
		
		
	}
	
	/* (non-Javadoc)
	 * @see view.View#removeClient(java.lang.String)
	 */
	@Override
	public void removeClient(String Client) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				try{list.remove(Client);}
				catch(Exception e)
				{
					
				}
				status.setText("");
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see view.View#setUserCommand(presenter.RemoteControlCommand)
	 */
	@Override
	public void setUserCommand(RemoteServerCommand userCommand) {
		
		this.lastCommand = userCommand;
		setChanged(); 
		notifyObservers();
		
	}

	/**
	 * Player of the Background's Music
	 *
	 * @return the player
	 */
	public MP3Player getPlayer() {
		return player;
	}
	
}

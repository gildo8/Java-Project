/**
 * @Project: mvp
 * @Class : BasicWindow.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;

import java.util.Observable;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * The Class BasicWindow represents a basic window , each class that implements the
 * InitWidget() will fill the window with there Widget and with these window properties.
 */
public abstract class BasicWindow extends Observable implements Runnable{

	/** The display. */
	Display display; 
	
	/** The shell. */
	Shell shell;
	
	/**
	 * Instantiates a new basic window.
	 *
	 * @param display the display
	 * @param shell the shell
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public BasicWindow(Display display,Shell shell,String title, int width, int height) {
		this.display=display;
		this.shell=shell;
		shell.setText(title); //set window text 
		shell.setSize(width,height); //set window heights
	}
	
	/**
	 * Instantiates a new basic window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public BasicWindow(String title, int width, int height) {
		display=new Display(); // creates display
		shell=new Shell(display); //creates a window inside the display
		shell.setText(title); //set window text 
		shell.setSize(width,height); //set window heights
	}
	
	/**
	 * Inits the widgets.
	 */
	abstract void initWidgets(); // a function that will be later implemented and will have all widgets inside the windows
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
		// System.exit(0);
	}

	/**
	 * Gets the shell.
	 *
	 * @return the shell
	 */
	public Shell getShell() {
		return shell;
	}

}
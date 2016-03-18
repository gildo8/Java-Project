/**
 * @Project: RemoteServer
 * @Class : RunRemote.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package boot;


import model.RemoteServerModel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.RemoteServerPresenter;
import view.RemoteServerWindow;
import view.RemoteServerWriteProperties;

/**
 * The Class RunRemote.
 */
public class RunRemote {

	/**
	 * Loads dialog window for the Remote Server
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		
		RemoteServerWriteProperties sp = new RemoteServerWriteProperties(); 
		Display display= new Display();
		Shell shell=new Shell(display); 
		sp.writeProperties(display, shell); 
		RemoteServerWindow SE = new RemoteServerWindow("Server Controller",1080,600,display,shell);
		RemoteServerModel m = new RemoteServerModel(RemoteServerWindow.readProperties()); 
		RemoteServerPresenter p = new RemoteServerPresenter(m, SE); 
		m.addObserver(p); 
		SE.addObserver(p);
		SE.run();
	}
}


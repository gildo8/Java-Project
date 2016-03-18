/**
 * @Project: RemoteServer
 * @Class : RunGui.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package boot;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import model.RemoteServerModel;
import presenter.RemoteServerPresenter;
import presenter.RemoteServerProperties;
import view.RemoteServerWindow;

/**
 * The Class RunGui.
 */
public class RunGui {

	/**
	 * Loads dialog window for the Remote Server
	 *
	 * @param serverProperties
	 *            the server properties
	 */
	public void  loadWindow(RemoteServerProperties serverProperties){
		Display display= new Display(); 
		Shell shell=new Shell(display);
		RemoteServerWindow SE= new RemoteServerWindow("Server Controller",1100,600,display,shell);
		RemoteServerModel m = new RemoteServerModel(serverProperties); 
		RemoteServerPresenter p = new RemoteServerPresenter(m, SE); 
		m.addObserver(p); 
		SE.addObserver(p);
		SE.run(); 
	}
}

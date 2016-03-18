/**
 * @Project: Server
 * @Class : RunServer.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package boot;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import server.ServerProperties;
import server.UDPMazeServerRemoteControl;
import view.gui.ClassInputDialog;


/**
 * The Class RunServer.
 */
public class RunServer {

	/**
	 * The main method Runs Dialog window that gets properties for the Server
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		
		Display display=new Display();
		Shell shell=new Shell(display);
		ClassInputDialog dlg=new ClassInputDialog(shell, ServerProperties.class);
		ServerProperties properties=(ServerProperties)dlg.open();
		if(properties!=null)
		{
			MessageBox messageBox = new MessageBox(shell,SWT.OK | SWT.Activate);
	        messageBox.setText("Server");
	        messageBox.setMessage("Server is Working \nPlease Run Remote Server \n\nPort on which server listen: "+properties.getPort());
			messageBox.open();
			new UDPMazeServerRemoteControl(properties).run();
		}
		else{
			MessageBox messageBox = new MessageBox(shell,SWT.ERROR | SWT.Activate);
	        messageBox.setText("Error");
	        messageBox.setMessage("Error With Write Properties.");
			messageBox.open();
		}
	}
}

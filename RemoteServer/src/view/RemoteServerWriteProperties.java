/**
 * @Project: RemoteServer
 * @Class : RemoteServerWriteProperties.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import model.Constants;
import presenter.RemoteServerProperties;

/**
 * The Class Remote Server Write Properties.
 */
public class RemoteServerWriteProperties {
	
	/**
	 * Write Remote Server's Properties to XML file.
	 *
	 * @param display
	 *            the display
	 * @param shell
	 *            the shell
	 * @return the int
	 */
	public int writeProperties(Display display, Shell shell)
	{
		XMLEncoder e;
		
	    ClassInputDialog dlg = new ClassInputDialog(shell,RemoteServerProperties.class);
	    RemoteServerProperties input = (RemoteServerProperties) dlg.open();
	    if (input != null) {
	      // User clicked OK; set the text into the label
	    	try {
				e = new XMLEncoder(new FileOutputStream(Constants.FILE_PATH + "properties.xml"));
				e.writeObject(input);
				e.flush();
				e.close();
				return 0;
	    	} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
	    }
	    return -1;
	   
	}
}

/**
 * @Project: mvp
 * @Class : WriteProperties.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package boot;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import org.eclipse.swt.widgets.Shell;

import dataProject.ClientProperties;
import dataProject.VariablesData;
import view.gui.ClassInputDialog;


/**
 * The Class WriteProperties.
 */
public class WriteProperties {

	/**
	 * Write the properties of the client side into xml file.
	 *
	 * @param shell the shell
	 * @return the int
	 */
	public int WriteProGui(Shell shell) {
		XMLEncoder xmlEnc;
		ClassInputDialog dlg = new ClassInputDialog(shell, ClientProperties.class);
		ClientProperties p = (ClientProperties) dlg.open();
		
		if(p!=null){
			try{
				xmlEnc = new XMLEncoder(new FileOutputStream(VariablesData.FILE_PATH + "properties.xml"));
				xmlEnc.writeObject(p);
				xmlEnc.flush();
				xmlEnc.close();
				return 0;
			}
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error With Create Properties File");
			}
		}
		return -1;
	}
}

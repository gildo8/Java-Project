/**
 * @Project: mvp
 * @Class : Run.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import dataProject.ClientProperties;
import dataProject.VariablesData;
import model.ClientModel;
import model.MyObservableModel;
import presenter.MyObserverPresenter;
import view.cli.MyCliView;
import view.gui.MazeWindow;

/**
 * The Class Run.
 */
public class Run {

	/**
	 * The main method , Starts the dialog window of the client side.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		Display display=new Display();
		Shell shell=new Shell(display);
		WriteProperties pro = new WriteProperties();
		pro.WriteProGui(shell);
		
		MyObservableModel localModel;
		ClientModel serverModel;
		
		ClientProperties properties;

		if((properties = readPropertiesFromXML())!=null)
		{
			localModel = new MyObservableModel(properties);
			switch(properties.getAccess()){

			case LOCAL:

				switch(properties.getUi())
				{
					case CLI:
						localModel = new MyObservableModel(properties); 
						MyCliView locaView = new MyCliView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
						MyObserverPresenter localPresenter = new MyObserverPresenter(locaView, localModel);
						
						locaView.addObserver(localPresenter);
						localModel.addObserver(localPresenter);
						
						locaView.start();
						break;
						
					case GUI:
						localModel = new MyObservableModel(properties);	
						MazeWindow localGuiView = new MazeWindow(display, shell, "The Maze - Local", 400, 600);
						MyObserverPresenter localGuiPresenter = new MyObserverPresenter(localGuiView, localModel);
						
						localGuiView.addObserver(localGuiPresenter);
						localModel.addObserver(localGuiPresenter);
						
						localGuiView.start();
						break;
					default:
						return;	
				}
				break;	
			case REMOTE_SERVER:

				switch(properties.getUi())
				{
					case CLI:
						serverModel = new ClientModel(properties);
						MyCliView serverView = new MyCliView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
						MyObserverPresenter serverPresenter = new MyObserverPresenter(serverView, serverModel);
						
						serverView.addObserver(serverPresenter);
						serverModel.addObserver(serverPresenter);
						
						serverView.start();
						break;
					case GUI:
						serverModel = new ClientModel(properties);
						MazeWindow serverGuiView = new MazeWindow(display, shell, "The Maze - Server", 400, 600);
						MyObserverPresenter serverGuiPresenter = new MyObserverPresenter(serverGuiView, serverModel);
						
						serverGuiView.addObserver(serverGuiPresenter);
						serverModel.addObserver(serverGuiPresenter);
						
						serverGuiView.start();
						break;
					default:
						return;	
				}
				break;

			default:
				return;
			}

		}
		else
			return;
		
	}
	
	/**
	 * Read properties from xml.
	 *
	 * @return the client properties
	 */
	public static ClientProperties readPropertiesFromXML(){
		
		XMLDecoder xmlD;
		ClientProperties pro = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(VariablesData.FILE_PATH + "properties.xml"));
			xmlD = new XMLDecoder(in);
			pro = (ClientProperties) xmlD.readObject();
			System.out.println(pro);
			xmlD.close();
		} catch (IOException e) {
			e.printStackTrace();
			return new ClientProperties();
		}
		return pro;
	}
}

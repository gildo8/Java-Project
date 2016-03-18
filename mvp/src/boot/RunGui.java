/**
 * @Project: mvp
 * @Class : RunGui.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package boot;

import dataProject.ClientProperties;
import model.ClientModel;
import model.MyObservableModel;
import presenter.MyObserverPresenter;
import view.gui.MazeWindow;


/**
 * The Class RunGui, starts the GUI UI.
 */
public class RunGui {
	
	/**
	 * Start gui.
	 *
	 * @param properties the properties
	 */
	public void startGui(ClientProperties properties){
		
		switch(properties.getAccess()){

		case LOCAL:
				MyObservableModel model = new MyObservableModel(properties);
				MazeWindow guiView=new MazeWindow("Maze", 1300, 700);
				MyObserverPresenter pMaze=new MyObserverPresenter(guiView,model);
				
				guiView.addObserver(pMaze);
				model.addObserver(pMaze);
				
				guiView.start();
			break;
			
		case REMOTE_SERVER:
			    ClientModel clientModel = new ClientModel(properties);
				guiView = new MazeWindow("Maze", 1300, 700);
				pMaze = new MyObserverPresenter(guiView,clientModel);
				
				guiView.addObserver(pMaze);
				clientModel.addObserver(pMaze);
				
				guiView.start();
				break;
		
		default:
				return;	
		}
	}
}

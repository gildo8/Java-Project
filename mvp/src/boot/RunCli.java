/**
 * @Project: mvp
 * @Class : RunCli.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import dataProject.ClientProperties;
import model.ClientModel;
import model.MyObservableModel;
import presenter.MyObserverPresenter;
import view.cli.MyCliView;

/**
 * The Class RunCli , starts the Cli UI.
 */
public class RunCli {

	/** The in. */
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	/** The out. */
	PrintWriter out = new PrintWriter(System.out);
	
	/**
	 * Start cli.
	 *
	 * @param properties the properties
	 */
	public void startCli(ClientProperties properties){
		
		switch(properties.getAccess()){

		case LOCAL:
			 MyCliView viewCli = new MyCliView(out,in);
			 MyObservableModel modelCli = new MyObservableModel(properties);
			 MyObserverPresenter presenterCli = new MyObserverPresenter(viewCli, modelCli);

			 viewCli.addObserver(presenterCli);
			 modelCli.addObserver(presenterCli);
		
			 viewCli.start();
			break;
			
		case REMOTE_SERVER:
			 ClientModel modelServer = new ClientModel(properties);
			 viewCli = new MyCliView(out,in);
			 presenterCli = new MyObserverPresenter(viewCli, modelServer);
			 
			 viewCli.addObserver(presenterCli);
			 modelServer.addObserver(presenterCli);
		
			 viewCli.start();
		
		default:
				return;	
		}
	}
	
}

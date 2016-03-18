/**
 * @Project: mvp
 * @Class : Presenter.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package presenter;

import java.util.HashMap;
import java.util.Observer;


/**
 * The Interface Presenter , it's located in the middle between View and Model
 * By uses the Command Interface it defining what happend in View and Model.
 */
public interface Presenter extends Observer {

	/**
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	HashMap<String, Command> getCommands();
	
	/**
	 * Start.
	 */
	public void start();
	
}

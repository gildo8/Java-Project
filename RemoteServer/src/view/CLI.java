/**
 * @Project: RemoteServer
 * @Class : CLI.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import presenter.RemoteServerCommand;

/**
 * The Class CLI.
 */
public class CLI implements Runnable {

	/** The runing. */
	boolean runing = true;
	
	/** The out. */
	private PrintWriter out;

	/** The in. */
	private BufferedReader in;

	/** The commands. */
	private HashMap<String,RemoteServerCommand> commands;

	/** The user command. */
	RemoteServerCommand userCommand;

	/** The view. */
	View view;

	/**
	 * Instantiates a new cli.
	 *
	 * @param out
	 *            the out
	 * @param in
	 *            the in
	 * @param view
	 *            the view
	 */
	public CLI(PrintWriter out, BufferedReader in, View view) {

		this.out = out;
		this.in = in;
		this.view = view;
	}

	/**
	 * Gets the out.
	 *
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * Sets the out.
	 *
	 * @param out
	 *            the new out
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	/**
	 * Gets the in.
	 *
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * Sets the in.
	 *
	 * @param in
	 *            the new in
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	/**
	 * Sets the commands.
	 *
	 * @param commands
	 *            the commands
	 */
	public void setCommands(HashMap<String, RemoteServerCommand> commands) {

		this.commands = commands; 
	}


	/**
	 * Gets the user command.
	 *
	 * @return the user command
	 */
	public RemoteServerCommand getUserCommand() {
		return userCommand;
	}

	/**
	 * Sets the user command.
	 *
	 * @param userCommand
	 *            the new user command
	 */
	public void setUserCommand(RemoteServerCommand userCommand) {
		this.userCommand = userCommand;
	}

	/**
	 * Prints the commands.
	 */
	public void printCommands(){
		out.flush();
		out.println();
	}




	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()  {
		out.flush();
		String line=null;
		String [] args=null;
		
		printCommands();
		try {	
			while (runing){
				out.println("Enter command: ");
				out.flush();
				
				line= in.readLine();
				args= line.split(" ");
				if(commands.containsKey(args[0])){
					userCommand= commands.get(args[0]);		
					userCommand.setArguments(line);
					view.setUserCommand(userCommand);
				}
				else{
					out.println("Invalid parameters, retry command.");
					out.println();
					out.flush();
				}
			}
		} catch (IOException e) {
			out.println("Oops something wrong happend");
		}
	}

	/**
	 * Checks if is runing.
	 *
	 * @return true, if is runing
	 */
	public boolean isRuning() {
		return runing;
	}

	/**
	 * Sets the runing.
	 *
	 * @param runing
	 *            the new runing
	 */
	public void setRuning(boolean runing) {
		this.runing = runing;
	}
}
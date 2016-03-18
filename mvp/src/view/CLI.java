/**
 * @Project: mvp
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

import presenter.Command;

/**
 * The Class CLI , represents Command Line Interface.
 */
public class CLI implements Runnable{

	/** The in. */
	private BufferedReader in;
	
	/** The out. */
	private PrintWriter out;

	/** The commands. */
	private HashMap<String, Command> commands;
	
	/** The c view. */
	View cView;
	
	/** The c command. */
	Command cCommand;
	
	/** The is working. */
	boolean isWorking = true;
	
	/**
	 * Instantiates a new cli.
	 *
	 * @param out the out
	 * @param in the in
	 * @param cView the c view
	 */
	public CLI(PrintWriter out, BufferedReader in, View cView) {
		this.out = out;
		this.in = in;
		this.cView = cView;
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
	 * @param in the new in
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
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
	 * @param out the new out
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		//This is the Command Line where we get the Command from the user

		out.flush();
		String line=null;
		String [] args=null;
		
		printInstructions();
		

		try {	
			while (isWorking){
				out.print("-> Enter Command:\n");
				out.flush();
				
				line= in.readLine();
				args= line.split(" ");
				if(commands.containsKey(args[0])){

					cCommand= commands.get(args[0]);		
					cCommand.setCommandArguments(args);
					cView.setUserCommand(cCommand);

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
	 * Checks if is working.
	 *
	 * @return true, if is working
	 */
	public boolean isWorking() {
		return isWorking;
	}

	/**
	 * Sets the working.
	 *
	 * @param isWorking the new working
	 */
	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	/**
	 * Prints the instructions.
	 */
	public void printInstructions(){
		out.println("\n***** Command List: *****");
		out.println("- dir <path> : show the files in the path. ");
		out.println("- generate 3d maze <name> <x> <y> <z> : generate 3D maze");
		out.println("- display maze <name> : display the maze");
		out.println("- display cross section by <X/Y/Z> <number> for <name>");
		out.println("- display solution <name> : disaply the solution");
		out.println("- save maze <name> <file name> : save the maze");
		out.println("- load maze <file name> <name> : load the maze");
		out.println("- maze size <name> : the size of maze in memory");
		out.println("- file size <name> : the size of maze in file");
		out.println("- solve <name> : solving the maze with algorithm");
		out.println("- exit");
		out.flush();
	}
}

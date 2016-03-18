/**
 * @Project: mvc
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

import controller.Command;

/**
 * The Class CLI.
 */
public class CLI extends Thread{

	/** The in. */
	private BufferedReader in;
	
	/** The out. */
	private PrintWriter out;
	
	/** The commands. */
	private HashMap<String, Command> commands;
	
	/**
	 * Instantiates a new cli.
	 *
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
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
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	public HashMap<String, Command> getCommands() {
		return commands;
	}

	/**
	 * Sets the commands.
	 *
	 * @param commands
	 *            the commands
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		//This is the Command Line where we get the Command from the user
		
		String line;
		Command c;
		String[] args;
		printInstructions();
		
		try {
			while( (line = in.readLine()) != "exit" ){

				args = line.split(" ");
				if( commands.containsKey(args[0]) ){
					c = commands.get(args[0]);
					c.doCommand(args);
					out.flush();
					out.print("-> Enter Command: \n");
					out.flush();
				}
				else{
					out.println("\nInvalid Command , Try Again");
					out.flush();
					out.print("-> Enter Command: ");
					out.flush();
					//printInstructions();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints the instructions.
	 */
	public void printInstructions(){
		out.println("***** Command List: *****");
		out.println("- dir <path> : show the files in the path. ");
		out.println("- generate 3d maze <name> <x> <y> <z> : generate 3D maze");
		out.println("- display maze <name> : display the maze");
		out.println("- display cross section by <X/Y/Z> <number> for <name> : display maze2d for the index number");
		out.println("- display solution <name> : disaply the solution");
		out.println("- save maze <name> <file name> : save the maze");
		out.println("- load maze <file name> <name> : load the maze");
		out.println("- maze size <name> : the size of maze in memory");
		out.println("- file size <name> : the size of maze in file");
		out.println("- solve <name> <bfs / astar> : solving the maze with algorithm");
		out.println("- exit");
		out.print("-> Enter Command: ");
		out.flush();
	}
}

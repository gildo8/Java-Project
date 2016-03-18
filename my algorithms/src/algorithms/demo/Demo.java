package algorithms.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.SearchableMaze3D;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * The Class Demo.
 */
public class Demo {
	
	/**
	 * Run.
	 */
	public void Run(){
		
		SearchableMaze3D newMaze = new SearchableMaze3D(10, 15, 15);
		try{
			System.out.println("******************** REGULAR ********************");
			newMaze.print();
			System.out.println("Start: " + newMaze.getStartPosition());
			System.out.println("Goal: " + newMaze.getGoalPosition());
			
			System.out.println("******************** BYTE ********************");
			Maze3d mazeToByte = new Maze3d(newMaze.getMaze().toByteArray());
			mazeToByte.printMaze();
			System.out.println("Start: "+ mazeToByte.getStartPosition());
			System.out.println("Goal: " + mazeToByte.getGoalPosition());
			
			OutputStream out=new MyCompressorOutputStream(new FileOutputStream("1.maz"));
			out.write(mazeToByte.toByteArray());
			out.flush();
			out.close();
			
			InputStream in=new MyDecompressorInputStream(new FileInputStream("1.maz"));
			byte b[]=new byte[mazeToByte.toByteArray().length];
			in.read(b);
			in.close();
			
			Maze3d loaded=new Maze3d(b);
			System.out.println("******************** LOADED FROM FILE ********************");
			System.out.println("Start: " + loaded.getStartPosition());
			System.out.println("Goal: " + loaded.getGoalPosition());
			System.out.println("Equals: " + loaded.equals(mazeToByte));
		}
		catch(IOException ex){
			ex.printStackTrace();	
		}
	}
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Demo d = new Demo();
		d.Run();
	}

}

package io;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

	private InputStream in;
	
	int iter1 = 0;
	int iter2 = 0;
	int lengthArr = 0;

	public MyDecompressorInputStream(InputStream in){
		this.in = in;
	}

	@Override
	public int read(byte b[]) throws IOException{
		int w = 0;
		
		while( (iter1 = in.read()) != -1 && (iter2 = in.read()) != -1 && w < b.length){
			for (int i = 0; i < iter2; i++) {
				b[w++] = (byte)iter1;
			}
		}
	
		if( w > 0 && w < b.length){
			return w;
		}
		else if(w == 0){
			return -1;
		}
		return w;
	}
	
	@Override
	public int read() throws IOException {
		return this.in.read();
	}
}

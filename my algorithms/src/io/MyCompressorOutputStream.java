package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	int count = 0;
	int outByte = 0;
	
	public MyCompressorOutputStream(OutputStream out){
		super();
		this.out = out;
	}

	@Override
	public void write(byte[] b) throws IOException{
		count = 1;
		outByte = b[0];
		
		for (int i = 1; i < b.length; i++) {
			if(b[i] == outByte){
				count++;
			}
			else{
				this.out.write(outByte);
				this.out.write(count);
				outByte = b[i];
				count = 1;
			}
		}
	}
	
	@Override
	public void write(int b) throws IOException {
		this.out.write(b);
	}

	@Override
	public void close() throws IOException{
		this.out.close();
	}
}

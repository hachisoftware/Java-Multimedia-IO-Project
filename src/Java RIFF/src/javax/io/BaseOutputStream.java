package javax.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BaseOutputStream extends OutputStream {

	private final OutputStream out;
	
	public BaseOutputStream(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void write(int b) throws IOException {
		this.out.write(0);
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.out.write(b);
	}
	
	public void writeWORD(String word) throws IOException {
		write(word.getBytes("UTF-16LE"));
	}
	
	public void writeDWORD(String word) throws IOException {
		write(word.getBytes("UTF-32LE"));
	}
	
	public void writeQWORD(String word) throws IOException {
		write(word.getBytes("UTF-64LE"));
	}
	
	public void writeIntLE(int i) throws IOException 
	{
		ByteBuffer buffer = ByteBuffer.allocateDirect(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(i);
		buffer.rewind();
		
		if(!buffer.hasArray())
		{
			byte[] array = new byte[4];
			buffer.get(array);
			write(array);
		}
		else
			write(buffer.array());
	}
	
	public void writeIntBE(int i) throws IOException 
	{
		ByteBuffer buffer = ByteBuffer.allocateDirect(4);
		buffer.order(ByteOrder.BIG_ENDIAN);
		buffer.putInt(i);
		buffer.rewind();
		
		if(!buffer.hasArray())
		{
			byte[] array = new byte[4];
			buffer.get(array);
			write(array);
		}
		else
			write(buffer.array());
	}
	
	@Override
	public void flush() throws IOException {
		this.out.flush();
	}
	
	@Override
	public void close() throws IOException {
		this.out.close();
	}
}

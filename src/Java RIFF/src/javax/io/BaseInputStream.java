package javax.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BaseInputStream extends InputStream {

	private final InputStream in;
	
	public BaseInputStream(InputStream stream) {
		this.in = stream;
	}
	
	@Override
	public int read() throws IOException {
		return this.in.read();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		return this.in.read(b);
	}
	
	public String readWORD() throws IOException {
		byte[] word = new byte[2];
		read(word);
		return new String(word);
	}
	
	public String readDWORD() throws IOException {
		byte[] dword = new byte[4];
		read(dword);
		return new String(dword);
	}
	
	public String readQWORD() throws IOException {
		byte[] qword = new byte[8];
		read(qword);
		return new String(qword);
	}
	
	public int readIntLE() throws IOException {
		byte[] le = new byte[4];
		read(le);
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(le);
		buffer.rewind();
		
		return buffer.asIntBuffer().get(0);
	}
	
	public int readIntBE() throws IOException {
		byte[] le = new byte[4];
		read(le);
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(4);
		buffer.order(ByteOrder.BIG_ENDIAN);
		buffer.put(le);
		buffer.rewind();
		
		return buffer.asIntBuffer().get(0);
	}
	
	@Override
	public void close() throws IOException {
		this.in.close();
	}

}

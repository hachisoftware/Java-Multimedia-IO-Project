package javax.io;

import java.io.IOException;
import java.io.InputStream;

public class RIFFInputStream extends InputStream {

	private final BaseInputStream in;
	
	private int stSize, stRead;
	private String stType;
	
	public RIFFInputStream(InputStream stream) throws IOException {
		this.in = new BaseInputStream(stream);
		
		if(!this.in.readDWORD().equals("RIFF"))
			throw new IOException("Currupt RIFF stream header!");
		
		this.stSize = this.in.readIntLE();
		this.stType = this.in.readDWORD();
	}
	
	@Override
	public int read() throws IOException {
		this.stRead++;
		return this.in.read();
	}

	public String streamType() {
		return this.stType;
	}
	
	public boolean hasNext() {
		return this.stRead < this.stSize;
	}
	
	public RIFFObject next() throws IOException
	{
		if(this.stRead >= this.stSize)
			return null;
		
		String ckId = this.in.readDWORD();
		int ckSize = this.in.readIntLE();
		
		this.stRead += 8;
		
		byte[] ckData = new byte[ckSize];
		int read = this.in.read(ckData);
		
		if(read > ckSize)
			throw new IOException("Data read did not match the chunks quota!");
		else
			this.stRead += ckSize;
		
		
			return new RIFFChunk(ckId, ckData);
	}
	
	@Override
	public void close() throws IOException {
		this.in.close();
	}
}

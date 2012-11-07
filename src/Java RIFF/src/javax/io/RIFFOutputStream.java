package javax.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class RIFFOutputStream extends OutputStream {

	private final BaseOutputStream out;
	private String stType;
	private int stSize;
	private ArrayList<RIFFObject> subchunks = new ArrayList<RIFFObject>();
	
	public RIFFOutputStream(OutputStream stream, String stType) throws IOException
	{
		this.out = new BaseOutputStream(stream);
		this.out.writeDWORD("RIFF");
		this.out.flush();
		
		if(stType.length() > 4)
			throw new RuntimeException("Stream type tag '" + stType + "' is " + (stType.length() - 4) + " characters to long!");
		if(stType.length() < 4)
		{
			this.stType = stType;
			for(int i = 0; i < 4 - stType.length(); i++)
				this.stType += ' ';
		}
		if(stType.length() == 4)
			this.stType = stType;
	}
	
	@Override
	public void write(int b) throws IOException {
		this.out.write(b);
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		this.out.write(b);
	}

	public void write(RIFFObject chunk) {
		this.subchunks.add(chunk);
		this.stSize += (chunk.getChunkData().length + 8);
	}
	
	public BaseOutputStream getBaseStream() {
		return this.out;
	}
	
	@Override
	public void close() throws IOException
	{
		this.out.writeIntLE(this.stSize);
		this.out.writeDWORD(this.stType);
		this.out.flush();
		
		for(; this.subchunks.size() > 0;)
		{
			RIFFObject chunk = this.subchunks.remove(0);
			
			this.out.writeDWORD(chunk.getChunkTag());
			this.out.writeIntLE(chunk.getChunkData().length);
			
			if(!chunk.write(this))
				write(chunk.getChunkData());
			
			this.out.flush();
		}
	}
}

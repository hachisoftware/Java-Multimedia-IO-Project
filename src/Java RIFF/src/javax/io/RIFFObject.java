package javax.io;

import java.io.IOException;

public abstract class RIFFObject {
	
	public abstract String getChunkTag();
	
	public abstract byte[] getChunkData();
	
	public boolean write(RIFFOutputStream data) throws IOException {
		return false;
	}
	
}

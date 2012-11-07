package javax.io;

public class RIFFChunk extends RIFFObject {

	private String ckTag;
	private byte[] ckData;
	
	public RIFFChunk(String ckTag, byte[] ckData)
	{
		if(ckTag.length() > 4)
			throw new RuntimeException("Chunk tag '" + ckTag + "' is " + (ckTag.length() - 4) + " characters to long!");
		if(ckTag.length() < 4)
		{
			this.ckTag = ckTag;
			for(int i = 0; i < 4 - ckTag.length(); i++)
				this.ckTag += ' ';
		}
		if(ckTag.length() == 4)
			this.ckTag = ckTag;
		
		if(ckData == null || ckData.length == 0)
		{
			this.ckData = new byte[2];
			this.ckData[0] = 0x000;
			this.ckData[1] = 0x000;
		}
		
		if(ckData.length % 2 != 0)
		{
			this.ckData = new byte[ckData.length + 1];
			System.arraycopy(ckData, 0, this.ckData, 0, ckData.length);
			
			this.ckData[ckData.length] = 0x000;
		}
		
		if(ckData != null && ckData.length % 2 == 0)
			this.ckData = ckData;
	}

	@Override
	public String getChunkTag() {
		return this.ckTag;
	}

	@Override
	public byte[] getChunkData() {
		return this.ckData;
	}
}

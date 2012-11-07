package javax.io;

import java.io.IOException;
import java.util.ArrayList;

public class RIFFList extends RIFFObject {

	private ArrayList<RIFFObject> subchunks = new ArrayList<RIFFObject>();
	private byte[] ckData;
	private String lstId;
	
	public RIFFList(byte[] ckData) 
	{
		this.ckData = ckData;
		this.lstId = RIFFUtils.convertDWORD(ckData, 0);
		
		int pos = 4;
		
		while(pos < ckData.length)
		{
			String sckId = RIFFUtils.convertDWORD(ckData, pos);
			int sckSize = RIFFUtils.convertIntLE(ckData, pos + 4);
			pos += 8;

			byte[] sckData = new byte[sckSize];
			System.arraycopy(ckData, pos, sckData, 0, sckSize);
			pos += sckSize;
			
			if(sckId.equals("JUNK"))
				continue;
			else if(sckId.equals("LIST"))
				this.subchunks.add(new RIFFList(sckData));
			else
				this.subchunks.add(new RIFFChunk(sckId, sckData));
		}
	}

	@Override
	public String getChunkTag() {
		return "LIST";
	}

	@Override
	public byte[] getChunkData() {
		return this.ckData;
	}

	public String getListID() {
		return this.lstId;
	}
	
	public ArrayList<RIFFObject> getSubchunks() {
		return this.subchunks;
	}
	
	@Override
	public boolean write(RIFFOutputStream data) throws IOException 
	{
		data.getBaseStream().writeDWORD(this.lstId);
		
		for(; this.subchunks.size() > 0;)
			data.write(this.subchunks.get(0));
		
		return true;
	}
}

package javax.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RIFFUtils {

	public static String convertWORD(byte[] data, int offset)
	{
		byte[] word = new byte[2];
		System.arraycopy(data, offset, word, 0, 2);
		return new String(word);
	}
	
	public static String convertDWORD(byte[] data, int offset)
	{
		byte[] word = new byte[4];
		System.arraycopy(data, offset, word, 0, 4);
		return new String(word);
	}
	
	public static String convertQWORD(byte[] data, int offset)
	{
		byte[] word = new byte[8];
		System.arraycopy(data, offset, word, 0, 8);
		return new String(word);
	}
	
	public static int convertIntLE(byte[] data, int offset)
	{
		byte[] integer = new byte[4];
		System.arraycopy(data, offset, integer, 0, 4);
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(integer);
		buffer.rewind();
		
		return buffer.asIntBuffer().get(0);
	}
	
	public static int convertIntBE(byte[] data, int offset)
	{
		byte[] integer = new byte[4];
		System.arraycopy(data, offset, integer, 0, 4);
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(4);
		buffer.order(ByteOrder.BIG_ENDIAN);
		buffer.put(integer);
		buffer.rewind();
		
		return buffer.asIntBuffer().get(0);
	}
}

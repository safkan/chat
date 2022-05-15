package tr.edu.ozyegin.chat.protocol;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringToByteBufferConverter {

	private static final int BUFFER_SIZE = 4096;
	
	
	public static ByteBuffer[] convert(String string) {
		if (string == null) {
			throw new IllegalArgumentException("The string to be converted may not be null.");
		}
		
		ArrayList<ByteBuffer> buffers = new ArrayList<>();
		ByteBuffer buf = ByteBuffer.allocateDirect(BUFFER_SIZE);

		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
		
		buf.putInt(bytes.length);
		
		for (int i = 0; i < bytes.length; i++) {
			
			try {
				buf.put(bytes[i]);
			} catch (BufferOverflowException e) {
				buffers.add(buf.rewind());
				buf = ByteBuffer.allocateDirect(BUFFER_SIZE);
				
				buf.put(bytes[i]);
			}
		}
		
		

		buffers.add(buf.flip());
		
		return buffers.toArray(new ByteBuffer[0]);
	}
	
	
}

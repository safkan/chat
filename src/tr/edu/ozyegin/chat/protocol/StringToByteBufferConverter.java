package tr.edu.ozyegin.chat.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StringToByteBufferConverter {

	
	
	public static ByteBuffer convert(String string) {
		if (string == null) {
			throw new IllegalArgumentException("The string to be converted may not be null.");
		}

		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

		ByteBuffer buf = ByteBuffer.allocateDirect(bytes.length + 4);
		
		
		buf.putInt(bytes.length);
		buf.put(bytes);
		
		return buf;
	}
	
	
}

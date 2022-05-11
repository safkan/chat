package tr.ozyegin.chat.protocol;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class StringToByteBufferConverter {

	private static final int BUFFER_SIZE = 4096;
	private String string;
	
	public StringToByteBufferConverter(String string) {
		this.string = string;
	}
	
	public ByteBuffer[] convert() {
		if (this.string == null || this.string.isEmpty()) {
			return new ByteBuffer[0];
		}
		
		
		ArrayList<ByteBuffer> buffers = new ArrayList<>();
		
		
		StringCharacterIterator i = new StringCharacterIterator(this.string);
		
		char c;
		ByteBuffer buf = ByteBuffer.allocateDirect(BUFFER_SIZE);
		CharBuffer cbuf = buf.asCharBuffer();
		
		while((c = i.next()) != StringCharacterIterator.DONE) {
			try {
				cbuf.put(c);
			} catch (BufferOverflowException e) {
				buffers.add(buf);
				buf = ByteBuffer.allocateDirect(BUFFER_SIZE);
				cbuf = buf.asCharBuffer();
				cbuf.put(c);
			}
		}

		buffers.add(buf);

		
		return buffers.toArray(new ByteBuffer[0]);
	}
	
	
}

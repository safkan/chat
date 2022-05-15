package tr.edu.ozyegin.chat.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class ByteBufferToStringConverter {

	
	private LinkedList<String> queue;
	private byte[] bytes;
	private int position;
	
	public ByteBufferToStringConverter() {
		this.queue = new LinkedList<String>();
		this.bytes = null;
		this.position = 0;
	}
	
	
	public void consumeBuffer(ByteBuffer buf) {
		
		buf.flip();
		
		System.out.println("buf.remaining() :" + buf.remaining());
		
		while (buf.remaining() > 0) {
			if (bytes == null) {
				int size = buf.getInt();
				bytes = new byte[size];
				position = 0;
			}
		
			if (buf.remaining() < (bytes.length - position)) {
				// New data will not overflow, and is not sufficient to finish. Copy everything.
				int count = buf.remaining();
				buf.get(bytes, position, count);
				position += count;
			} else if (buf.remaining() == (bytes.length - position)) {
				// Just enough data to finish this string.
				int count = buf.remaining();
				buf.get(bytes, position, count);
				
				String s = new String(bytes, StandardCharsets.UTF_8);
				queue.add(s);
				
				bytes = null;
			} else {
				// Too much data. Finish this string, go back for more.
				
				int count = bytes.length - position;
				buf.get(bytes, position, count);
				
				String s = new String(bytes, StandardCharsets.UTF_8);
				queue.add(s);
				
				bytes = null;
				
			}
		}
		
		buf.clear();
		
		
	}

	public String getString() {
		if (queue.isEmpty()) {
			return null;
		} else {
			return queue.pop();
		}
	
	}
	
	
}

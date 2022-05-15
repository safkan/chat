package tr.edu.ozyegin.chat.protocol;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProtocolTest {

	ByteBufferToStringConverter decoder;
	
	@BeforeEach
	void setup() {
		this.decoder = new ByteBufferToStringConverter();
	}
	
	private void test(String s) {
		ByteBuffer[] buffers = StringToByteBufferConverter.convert(s);
		
		for (ByteBuffer buf : buffers) {
			this.decoder.consumeBuffer(buf); 
		}
		
		String p = this.decoder.getString();
		
		assertEquals(s, p); 
		
	}
	
	@Test
	void testEmptyString() {
		this.test("");
		
	}

	@Test
	void testShortString() {
		this.test("Hello");
	}
	
	@Test
	void testLongString() {
		StringBuilder buf = new StringBuilder();
		
		for (int i = 0; i < 13748; i++) {
			buf.append("z");
		}
		
		this.test(buf.toString());
	}

	
	
}

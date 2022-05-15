package tr.edu.ozyegin.chat.protocol;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonTest {

	public static class A {
		public String x;
		public int y;
		public boolean z;
		public double t;
	}
	
	JsonConverter converter;
	
	@BeforeEach
	void setup() {
		this.converter = new JsonConverter();
	}
	
	@Test
	void testJsonConversion() {
		A a = new A();
		a.x = "Hello";
		a.y = 25;
		a.z = true;
		a.t = 2.75;
		
		String serialized = this.converter.serialize(a);
		
		Object deserialized = this.converter.deserialize(serialized);
		
		assertTrue(deserialized instanceof A);
		
		A b = (A)deserialized;
		
		assertEquals(a.x, b.x);
		assertEquals(a.y, b.y);
		assertEquals(a.z, b.z);
		assertEquals(a.t, b.t);
	}

}

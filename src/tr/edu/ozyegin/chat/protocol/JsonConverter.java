package tr.edu.ozyegin.chat.protocol;

import com.google.gson.Gson;

public class JsonConverter {

	private static class Envelope {
		public String className;
		public String json;
	}
	
	private Gson gson;
	
	public JsonConverter() {
		this.gson = new Gson();
	}
	
	public String serialize(Object o) {
		Envelope envelope = new Envelope();
		
		envelope.className = o.getClass().getName();
		envelope.json = this.gson.toJson(o);
		
		
		return this.gson.toJson(envelope);
	}
	
	public Object deserialize(String s) {
		Envelope envelope = this.gson.fromJson(s, Envelope.class);
		
		try {
			Class<?> c = Class.forName(envelope.className);
			
			return this.gson.fromJson(envelope.json, c);
			
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Class not found while trying to deserialize " 
		                                     + envelope.className);
		}
	}
	
}

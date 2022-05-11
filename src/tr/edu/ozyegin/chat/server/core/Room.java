package tr.edu.ozyegin.chat.server.core;

import java.util.List;

import tr.edu.ozyegin.chat.server.communication.Message;
import tr.edu.ozyegin.chat.server.communication.MessageReceiver;

public class Room implements MessageReceiver {
	private List<Person> people;
	
	public Room() {
		
	}

	@Override
	public void receive(Message m) {
		
	}
	
}

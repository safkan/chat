package tr.edu.ozyegin.chat.server.core;

import java.util.ArrayList;
import java.util.List;

import tr.edu.ozyegin.chat.server.communication.ClientMessage;
import tr.edu.ozyegin.chat.server.communication.MessageReceiver;

public class Room implements MessageReceiver {
	private List<Person> people;
	
	public Room() {
		this.people = new ArrayList<Person>();
	}

	@Override
	public void receive(ClientMessage m) {
		
	}
	
}

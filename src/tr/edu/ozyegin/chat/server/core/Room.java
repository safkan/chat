package tr.edu.ozyegin.chat.server.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import tr.edu.ozyegin.chat.messages.LoginRequest;
import tr.edu.ozyegin.chat.messages.LoginResponse;
import tr.edu.ozyegin.chat.messages.MessageRequest;
import tr.edu.ozyegin.chat.messages.MessageResponse;
import tr.edu.ozyegin.chat.server.communication.ClientConnection;
import tr.edu.ozyegin.chat.server.communication.ClientMessage;
import tr.edu.ozyegin.chat.server.communication.MessageReceiver;

public class Room implements MessageReceiver {
	private Map<String, Person> people;
	
	public Room() {
		this.people = new HashMap<String, Person>();
	}

	@Override
	public void receive(ClientMessage m) throws Exception{
		if (m.messageObject instanceof LoginRequest) {
			this.handleLoginRequest(m.sourceConnection, (LoginRequest)m.messageObject);
		} else if (m.messageObject instanceof MessageRequest) {
			this.handleMessageRequest(m.sourceConnection, (MessageRequest)m.messageObject);
		}
	}
	
	private Person getPerson(String name) {
		return this.people.get(name);
	}
	
	
	private void handleMessageRequest(ClientConnection connection, MessageRequest messageRequest) throws Exception {
		MessageResponse response = new MessageResponse();
		response.sender = connection.getAuthenticatedUsername();
		response.message = messageRequest.message;
		response.time = this.time();
		
	}
	
	
	private String time() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
	}
	
	private void handleLoginRequest(ClientConnection connection, LoginRequest loginRequest) throws Exception {
		Person person = this.getPerson(loginRequest.username);
		
		if (person == null) {
			person = new Person(loginRequest.username);
			this.people.put(person.getName(), person);
		}
		
		person.setLastActivity(LocalDateTime.now());

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.success = true;

		connection.setAuthenticatedUsername(person.getName());
		
		connection.write(loginResponse);
	}
	
}

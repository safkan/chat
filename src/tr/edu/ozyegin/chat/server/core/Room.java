package tr.edu.ozyegin.chat.server.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tr.edu.ozyegin.chat.messages.LoginRequest;
import tr.edu.ozyegin.chat.messages.LoginResponse;
import tr.edu.ozyegin.chat.server.communication.ClientConnection;
import tr.edu.ozyegin.chat.server.communication.ClientMessage;
import tr.edu.ozyegin.chat.server.communication.MessageReceiver;

public class Room implements MessageReceiver {
	private List<Person> people;
	
	public Room() {
		this.people = new ArrayList<Person>();
	}

	@Override
	public void receive(ClientMessage m) throws Exception{
		if (m.messageObject instanceof LoginRequest) {
			this.handleLoginRequest(m.sourceConnection, (LoginRequest)m.messageObject);
		} 
		
	}
	
	private void handleLoginRequest(ClientConnection connection, LoginRequest loginRequest) throws Exception {
		LoginResponse loginResponse = new LoginResponse();
		
		loginResponse.success = true;
		loginResponse.accessToken = UUID.randomUUID().toString();
		
		connection.write(loginResponse);
	}
	
}

package tr.edu.ozyegin.chat.server.communication;


public class ClientMessage {
	
	public ClientMessage(ClientConnection sourceConnection, Object messageObject) {
		this.sourceConnection = sourceConnection;
		this.messageObject = messageObject;
	}
	
	public ClientConnection sourceConnection;
	public Object messageObject;
}

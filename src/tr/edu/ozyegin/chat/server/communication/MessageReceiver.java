package tr.edu.ozyegin.chat.server.communication;

public interface MessageReceiver {
	public void receive(ClientMessage m);
}

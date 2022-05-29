package tr.edu.ozyegin.chat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import tr.edu.ozyegin.chat.client.communication.ServerConnection;

public class ChatClient implements Runnable {
	
	private ServerConnection serverConnection;
	
	public ChatClient(String address, int port) {
		
		InetSocketAddress inetSocketAddress = new InetSocketAddress(address, port);
		
		this.serverConnection = new ServerConnection(inetSocketAddress);
		
	}

	public void registerChatMessageListener(ChatMessageListener chatMessageListener) {
		this.serverConnection.registerChatMessageListener(chatMessageListener);
	}
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public void connect() throws IOException, ExecutionException, InterruptedException {
		this.serverConnection.connect();
	}
	
	
	
	
}

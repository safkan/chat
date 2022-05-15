package tr.edu.ozyegin.chat.server.communication;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnectionManager implements Runnable {
	
	private ClientConnectionListener clientConnectionListener;
	
	private Thread runner;
	private LinkedBlockingQueue<ClientMessage> messageQueue;
	
	public ClientConnectionManager(SocketAddress localListeningAddress) {
		this.clientConnectionListener = new ClientConnectionListener(this, localListeningAddress);
		this.messageQueue = new LinkedBlockingQueue<ClientMessage>();
		this.runner = new Thread(this);
	}
	
	public void start() throws IOException {
		this.clientConnectionListener.start();
		this.runner.start();
	}

	
	public void handleNewClientConnection(ClientConnection clientConnection) {
		clientConnection.setClientConnectionManager(this);
		clientConnection.start();
	}

	public void processClientMessage(ClientMessage clientMessage) {
		this.messageQueue.add(clientMessage);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				ClientMessage message = messageQueue.take();
				
				
				
			} catch (Exception e) {
				
			}
			
		}
	}
	
}

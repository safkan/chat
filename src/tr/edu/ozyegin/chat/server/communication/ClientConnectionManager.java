package tr.edu.ozyegin.chat.server.communication;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import tr.edu.ozyegin.chat.messages.LoginRequest;

public class ClientConnectionManager implements Runnable {
	
	private ClientConnectionListener clientConnectionListener;
	
	private Thread runner;
	private LinkedBlockingQueue<ClientMessage> messageQueue;
	private MessageReceiver messageReceiver;
	
	public ClientConnectionManager(SocketAddress localListeningAddress, MessageReceiver messageReceiver) {
		this.clientConnectionListener = new ClientConnectionListener(this, localListeningAddress);
		this.messageQueue = new LinkedBlockingQueue<ClientMessage>();
		this.messageReceiver = messageReceiver;
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
				
				this.messageReceiver.receive(message);
				
				
			} catch (Exception e) {
				
			}
			
		}
	}
	
	
	
}

package tr.edu.ozyegin.chat.server.communication;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientConnectionManager implements NewClientConnectionHandler, Runnable {
	
	private ClientConnectionListener clientConnectionListener;
	private CopyOnWriteArrayList<ClientConnection> clientConnections;
	private Thread runner;
	
	
	public ClientConnectionManager(SocketAddress localListeningAddress) {
		this.clientConnectionListener = new ClientConnectionListener(this, localListeningAddress);
		this.clientConnections = new CopyOnWriteArrayList<>();
		this.runner = new Thread(this);
	}
	
	public void start() throws IOException {
		this.clientConnectionListener.start();
		this.runner.start();
	}

	@Override
	public void handleNewClientConnection(ClientConnection clientConnection) {
		this.clientConnections.add(clientConnection);
	}

	
	@Override
	public void run() {
		while(true) {
			for (ClientConnection c : this.clientConnections) {
				
			}
			
		}
	}
	
}

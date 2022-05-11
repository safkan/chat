package tr.edu.ozyegin.chat.server.communication;

import java.io.IOException;
import java.net.SocketAddress;

public class ClientConnectionManager implements NewClientConnectionHandler {
	
	private ClientConnectionListener clientConnectionListener;
	
	public ClientConnectionManager(SocketAddress localListeningAddress) {
		this.clientConnectionListener = new ClientConnectionListener(this, localListeningAddress);
	}
	
	public void start() throws IOException {
		this.clientConnectionListener.start();
	}

	@Override
	public void handleNewClientConnection(ClientConnection clientConnection) {
		
	}
	
}

package tr.edu.ozyegin.chat.server.communication;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ClientConnectionListener implements CompletionHandler<AsynchronousSocketChannel, Void> {
	private static final int DEFAULT_BACKLOG = 5;

	private ClientConnectionManager newClientConnectionHandler;
	private SocketAddress localListeningAddress;
	private int backlog;

	private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
	
	public ClientConnectionListener(ClientConnectionManager newClientConnectionHandler, SocketAddress localListeningAddress, int backlog) {
		this.newClientConnectionHandler = newClientConnectionHandler;
		this.localListeningAddress = localListeningAddress;
		this.backlog = backlog;
	}
	
	public ClientConnectionListener(ClientConnectionManager newClientConnectionHandler, SocketAddress localListeningAddress) {
		this(newClientConnectionHandler, localListeningAddress, DEFAULT_BACKLOG);
	}
	
	
	public void start() throws IOException {
		this.init();
	}
	
	private void init() throws IOException {
		this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
		
		this.asynchronousServerSocketChannel.bind(this.localListeningAddress, this.backlog);
		
		this.asynchronousServerSocketChannel.accept(null, this);
	}
	

	@Override
	public void completed(AsynchronousSocketChannel result, Void attachment) {
		ClientConnection clientConnection = new ClientConnection(result);
		
		this.newClientConnectionHandler.handleNewClientConnection(clientConnection);
		
		this.asynchronousServerSocketChannel.accept(null, this);
	}

	@Override
	public void failed(Throwable exc, Void attachment) {
		
	}
	
	
}

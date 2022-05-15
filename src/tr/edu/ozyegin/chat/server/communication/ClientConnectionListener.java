package tr.edu.ozyegin.chat.server.communication;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ClientConnectionListener implements Runnable, CompletionHandler<AsynchronousSocketChannel, Void> {
	private static final int DEFAULT_BACKLOG = 5;

	private NewClientConnectionHandler newClientConnectionHandler;
	private SocketAddress localListeningAddress;
	private int backlog;

	private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
	private Thread runner;
	
	public ClientConnectionListener(NewClientConnectionHandler newClientConnectionHandler, SocketAddress localListeningAddress, int backlog) {
		this.newClientConnectionHandler = newClientConnectionHandler;
		this.localListeningAddress = localListeningAddress;
		this.backlog = backlog;
	}
	
	public ClientConnectionListener(NewClientConnectionHandler newClientConnectionHandler, SocketAddress localListeningAddress) {
		this(newClientConnectionHandler, localListeningAddress, DEFAULT_BACKLOG);
	}
	
	
	public void start() throws IOException {
		this.init();
		this.runner.start();
	}
	
	private void init() throws IOException {
		this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
		
		this.asynchronousServerSocketChannel.bind(this.localListeningAddress, this.backlog);
		
		this.runner = new Thread(this);
	}
	
	public void run() {
		while(true) {
			this.asynchronousServerSocketChannel.accept(null, this);
		}
	}

	@Override
	public void completed(AsynchronousSocketChannel result, Void attachment) {
		ClientConnection clientConnection = new ClientConnection(result);
		
		this.newClientConnectionHandler.handleNewClientConnection(clientConnection);
	}

	@Override
	public void failed(Throwable exc, Void attachment) {
		
	}
	
	
}

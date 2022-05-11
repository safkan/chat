package tr.edu.ozyegin.chat.server.communication;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class ClientConnection {

	private AsynchronousSocketChannel asynchronousSocketChannel;
	private ByteBuffer byteBuffer;
	
	
	
	protected ClientConnection(AsynchronousSocketChannel asynchronousSocketChannel) {
		this.asynchronousSocketChannel = asynchronousSocketChannel;
		this.byteBuffer = ByteBuffer.allocateDirect(4096);
		
		
	}

	
	
}

package tr.edu.ozyegin.chat.server.communication;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import tr.edu.ozyegin.chat.protocol.ByteBufferToStringConverter;
import tr.edu.ozyegin.chat.protocol.JsonConverter;

public class ClientConnection implements CompletionHandler<Integer, ByteBuffer>{

	private AsynchronousSocketChannel asynchronousSocketChannel;
	private ByteBuffer byteBuffer;
	private ByteBufferToStringConverter byteBufferToStringConverter;
	private ClientConnectionManager clientConnectionManager;
	private JsonConverter jsonConverter;
	
	protected ClientConnection(AsynchronousSocketChannel asynchronousSocketChannel) {
		this.asynchronousSocketChannel = asynchronousSocketChannel;
		this.byteBufferToStringConverter = new ByteBufferToStringConverter();
		this.byteBuffer = ByteBuffer.allocateDirect(4096);
		this.jsonConverter = new JsonConverter();
		
	}

	protected void setClientConnectionManager(ClientConnectionManager clientConnectionManager) {
		this.clientConnectionManager = clientConnectionManager;
	}

	public void start() {
		this.read();
	}

	
	private void read() {
		this.asynchronousSocketChannel.read(this.byteBuffer, this.byteBuffer, this);;
	}

	@Override
	public void completed(Integer bytesRead, ByteBuffer buf) {
		if (bytesRead <= 0) {
			return;
		}
		
		this.byteBufferToStringConverter.consumeBuffer(buf);
		
		String s;
		
		do {
			s = this.byteBufferToStringConverter.getString();
			
			Object message = this.jsonConverter.deserialize(s);
			
			ClientMessage clientMessage = new ClientMessage(this, message);
			
			
			
		} while(s != null);
	}


	@Override
	public void failed(Throwable exc, ByteBuffer buf) {
		// TODO Auto-generated method stub
		
	}


	
	
}

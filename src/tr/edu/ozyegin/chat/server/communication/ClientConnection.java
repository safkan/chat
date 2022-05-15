package tr.edu.ozyegin.chat.server.communication;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import tr.edu.ozyegin.chat.protocol.ByteBufferToStringConverter;
import tr.edu.ozyegin.chat.protocol.JsonConverter;
import tr.edu.ozyegin.chat.protocol.StringToByteBufferConverter;

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

	
	public void write(Object obj) throws Exception {
		String json = this.jsonConverter.serialize(obj);
		ByteBuffer[] buffers = StringToByteBufferConverter.convert(json);
		
		for (ByteBuffer buf : buffers) {
			this.asynchronousSocketChannel.write(buf).get();
		}
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
			
			if (s != null) {
			
				System.out.println("Server message received: " + s);
				
				Object message = this.jsonConverter.deserialize(s);
				
				ClientMessage clientMessage = new ClientMessage(this, message);
				
				this.clientConnectionManager.processClientMessage(clientMessage);
			}
		} while(s != null);
		
		this.read();
	}


	@Override
	public void failed(Throwable exc, ByteBuffer buf) {
		// TODO Auto-generated method stub
		
	}


	
	
}

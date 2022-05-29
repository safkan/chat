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
	private String authenticatedUsername;
	private String accessToken;
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAuthenticatedUsername() {
		return authenticatedUsername;
	}

	public void setAuthenticatedUsername(String authenticatedUsername) {
		this.authenticatedUsername = authenticatedUsername;
	}

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
		System.out.println("ClientConnection.start()");
		
		this.read();
	}

	
	public void write(Object obj) throws Exception {
		String json = this.jsonConverter.serialize(obj);
		ByteBuffer buffer = StringToByteBufferConverter.convert(json);
		this.asynchronousSocketChannel.write(buffer).get();
	}
	
	private void read() {
		this.asynchronousSocketChannel.read(this.byteBuffer, this.byteBuffer, this);
	}

	@Override
	public void completed(Integer bytesRead, ByteBuffer buf) {
		System.out.println("ClientConnection.completed, bytes read: " + bytesRead);
		
		System.out.println("remaining " + buf.remaining());
		
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
		System.out.println("Read failed." + exc.toString());
		
	}


	
	
}

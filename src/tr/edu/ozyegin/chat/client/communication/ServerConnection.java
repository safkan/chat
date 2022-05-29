package tr.edu.ozyegin.chat.client.communication;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

import tr.edu.ozyegin.chat.client.ChatMessageListener;
import tr.edu.ozyegin.chat.messages.LoginResponse;
import tr.edu.ozyegin.chat.messages.MessageHistoryResponse;
import tr.edu.ozyegin.chat.messages.MessageResponse;
import tr.edu.ozyegin.chat.protocol.ByteBufferToStringConverter;
import tr.edu.ozyegin.chat.protocol.JsonConverter;
import tr.edu.ozyegin.chat.protocol.StringToByteBufferConverter;

public class ServerConnection implements CompletionHandler<Integer, ByteBuffer>{

	private AsynchronousSocketChannel asynchronousSocketChannel;
	private SocketAddress socketAddress;
	private ByteBuffer byteBuffer;
	private ByteBufferToStringConverter byteBufferToStringConverter;
	private JsonConverter jsonConverter;
	private ChatMessageListener chatMessageListener;
	
	public ServerConnection(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
		this.byteBuffer = ByteBuffer.allocateDirect(4096);
		this.byteBufferToStringConverter = new ByteBufferToStringConverter();
		this.jsonConverter = new JsonConverter();
	}
	
	public void registerChatMessageListener(ChatMessageListener chatMessageListener) {
		if (this.chatMessageListener == null) {
			this.chatMessageListener = chatMessageListener;
		} else {
			throw new IllegalStateException("A ChatMessageListener is already registered.");
		}
	}
	
	private void publishMessage(Object message) {
		if (this.chatMessageListener == null) {
			return;
		}
		
		if (message instanceof LoginResponse) {
			this.chatMessageListener.loginResponseReceived((LoginResponse) message);
		} else if (message instanceof MessageHistoryResponse) {
			this.chatMessageListener.messageHistoryResponseReceived((MessageHistoryResponse)message);
		} else if (message instanceof MessageResponse) {
			this.chatMessageListener.messageResponseReceived((MessageResponse)message);
		}
	}
	
	public void send(Object obj) throws Exception {
		String json = jsonConverter.serialize(obj);
		
		ByteBuffer buffer = StringToByteBufferConverter.convert(json);
		
		buffer.flip();
		
		this.asynchronousSocketChannel.write(buffer).get();
		
	}
	
	public void connect() throws IOException, ExecutionException, InterruptedException {
		this.asynchronousSocketChannel = AsynchronousSocketChannel.open();
		
		this.asynchronousSocketChannel.connect(socketAddress).get();
		
		this.read();
		
	}
	
	private void read() {
		this.asynchronousSocketChannel.read(this.byteBuffer, this.byteBuffer, this);
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
			
				System.out.println("Client message received: " + s);
			
				Object message = this.jsonConverter.deserialize(s);
				
				this.publishMessage(message);
				
				
			}
			
		} while(s != null);
		
		this.read();
	}


	@Override
	public void failed(Throwable exc, ByteBuffer buf) {
		// TODO Auto-generated method stub
		
	}


	
}

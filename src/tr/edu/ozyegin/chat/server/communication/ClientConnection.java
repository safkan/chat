package tr.edu.ozyegin.chat.server.communication;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import tr.edu.ozyegin.chat.protocol.ByteBufferToStringConverter;

public class ClientConnection implements CompletionHandler<Integer, ByteBuffer>{

	private AsynchronousSocketChannel asynchronousSocketChannel;
	private ByteBuffer byteBuffer;
	private ByteBufferToStringConverter byteBufferToStringConverter;
	
	
	protected ClientConnection(AsynchronousSocketChannel asynchronousSocketChannel) {
		this.asynchronousSocketChannel = asynchronousSocketChannel;
		this.byteBufferToStringConverter = new ByteBufferToStringConverter();
		this.byteBuffer = ByteBuffer.allocateDirect(4096);
		
	}


	public void start() {
		this.asynchronousSocketChannel.read(this.byteBuffer, this.byteBuffer, this);;
	}


	@Override
	public void completed(Integer bytesRead, ByteBuffer buf) {
		
	}


	@Override
	public void failed(Throwable exc, ByteBuffer buf) {
		// TODO Auto-generated method stub
		
	}


	
	
}

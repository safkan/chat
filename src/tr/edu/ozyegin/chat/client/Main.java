package tr.edu.ozyegin.chat.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import tr.edu.ozyegin.chat.client.communication.ServerConnection;
import tr.edu.ozyegin.chat.messages.LoginRequest;

public class Main {

	public static void main(String[] args) throws Exception {
		SocketAddress socketAddress = new InetSocketAddress("localhost", 4777);

		ServerConnection serverConnection = new ServerConnection(socketAddress);
		
		serverConnection.connect();
		
		LoginRequest loginRequest = new LoginRequest();
		
		loginRequest.username = "safkan";
		loginRequest.password = "safkan";
		
		serverConnection.send(loginRequest);
		
		
		
		Thread.sleep(10000);
	}

}

package tr.edu.ozyegin.chat.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import tr.edu.ozyegin.chat.client.communication.ServerConnection;

public class Main {

	public static void main(String[] args) throws Exception {
		SocketAddress socketAddress = new InetSocketAddress("localhost", 4777);

		ServerConnection serverConnection = new ServerConnection(socketAddress);
		
		serverConnection.connect();
		
		serverConnection.send("The hell with the server, please.");
		
		Thread.sleep(10000);
	}

}

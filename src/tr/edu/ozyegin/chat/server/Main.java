package tr.edu.ozyegin.chat.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import tr.edu.ozyegin.chat.server.communication.ClientConnectionManager;

public class Main {

	public static void main(String[] args) throws Exception {
		SocketAddress socketAddress = new InetSocketAddress(4777);
		
		ClientConnectionManager clientConnectionManager = new ClientConnectionManager(socketAddress);
		
		clientConnectionManager.start(); 
		
		
	}

}

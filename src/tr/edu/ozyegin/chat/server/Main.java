package tr.edu.ozyegin.chat.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import tr.edu.ozyegin.chat.server.communication.ClientConnectionManager;
import tr.edu.ozyegin.chat.server.core.Room;

public class Main {

	public static void main(String[] args) throws Exception {
		SocketAddress socketAddress = new InetSocketAddress(4777);
		
		Room room = new Room();
		
		ClientConnectionManager clientConnectionManager = new ClientConnectionManager(socketAddress, room);
		
		clientConnectionManager.start(); 
		
		
	}

}

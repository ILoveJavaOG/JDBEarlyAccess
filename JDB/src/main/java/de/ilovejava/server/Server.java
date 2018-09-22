package de.ilovejava.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Server {
	public static ServerSocket server;
	
	public Server(int Port) throws UnknownHostException, IOException {
		System.out.println("The server has been started Port: " +  Port);
		server = new ServerSocket(Port);
	}
}

package de.ilovejava.netty;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class GetIncomingMessage {
	Socket s;
	public GetIncomingMessage(Socket c) {
		s = c;
	}
	
	public String getMsg() throws IOException {
		DataInputStream i = new DataInputStream(s.getInputStream());
		return i.readUTF();
	}
}

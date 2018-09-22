package de.ilovejava.netty;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendOutPutData {
	public SendOutPutData(Socket s, String msg) throws IOException {
		DataOutputStream c = new DataOutputStream(s.getOutputStream());
		c.writeUTF(msg);
	}
}

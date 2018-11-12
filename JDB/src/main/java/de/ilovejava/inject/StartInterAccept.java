package de.ilovejava.inject;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import de.ilovejava.configuration.file.YamlConfiguration;
import de.ilovejava.netty.GetIncomingMessage;
import de.ilovejava.netty.SendOutPutData;
import de.ilovejava.server.Server;

public class StartInterAccept {
	
	public static HashMap<Socket, String>Clients = new HashMap<Socket, String>();
	
	public StartInterAccept() {
		while (true) {
			Socket client;
			try {
				client = Server.server.accept();
				GetIncomingMessage d = new GetIncomingMessage(client);
				String s = d.getMsg();
				String[] sarray = s.split(":");
				File f = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\User");
				File fc = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\DataBases\\"+ sarray[3]);
				File cf = new File(f.getPath(),sarray[1]+".dvs");
				if(f.exists()) {
					if(fc.exists() && cf.exists()) {
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cf);
						if(cfg.getString("Config.Password").equalsIgnoreCase(sarray[2])) {
							new SendOutPutData(client, "Connection ACCEPT");
							Clients.put(client, sarray[1]);
						}else {new SendOutPutData(client, "JDB_PASSWORD_NOT_ACCEPT"); client.close();}
					}else {new SendOutPutData(client, "Connection error"); client.close();}
				}else {new SendOutPutData(client, "JDB_USER_NOT_EXISTS"); client.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

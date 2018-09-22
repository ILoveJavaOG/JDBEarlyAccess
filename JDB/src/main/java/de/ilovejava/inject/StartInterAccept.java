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
			try {
				Socket c = Server.server.accept();
				GetIncomingMessage d = new GetIncomingMessage(c);
				String s = d.getMsg();
				if(s.contains("Connection")) {
					checkConnection(c, s);
					c = null;
				}else {new SendOutPutData(c, "JDB_NOT_COMMAND_ERROR"); c.close();}
			} catch (IOException e) {
				System.out.println("JDB_THREAD_ERROR");
				break;
			}
		}
	}
	
	private void checkConnection(Socket c, String s) {
		try {
			String[] sarray = s.split(":");
			File f = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\User");
			File fc = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\DataBases\\"+ sarray[3]);
			File cf = new File(f.getPath(),sarray[1]+".dvs");
			if(f.exists()) {
				if(cf.exists()) {
					YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cf);
					if(cfg.getString("Config.Password").equalsIgnoreCase(sarray[2])) {
						File lf = new File(fc.getPath(), sarray[3]+"_SQL_Section.dvs");
						File ef = new File(fc.getPath(), sarray[3]+"_SQL_DB.dvs");
						if(lf.exists() && ef.exists()) {
							new SendOutPutData(c, "CONNECTION_ACCEPT");
							Clients.put(c, sarray[3]);
						}else {new SendOutPutData(c, "JDB_DATABASE_NOT_FOUND"); c.close();}
					}else {new SendOutPutData(c, "JDB_PASSWORD_NOT_FOUND_ERROR"); c.close();}
				}else {new SendOutPutData(c, "JDB_NOT_FOUND_ERROR"); c.close();}
			}else {new SendOutPutData(c, "JDB_NOT_USER_ERROR"); c.close();}
		}catch(Exception e) {}
	}
}

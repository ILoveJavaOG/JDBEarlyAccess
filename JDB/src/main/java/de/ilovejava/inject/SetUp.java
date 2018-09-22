package de.ilovejava.inject;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import de.ilovejava.configuration.file.YamlConfiguration;
import de.ilovejava.query.InComingData;
import de.ilovejava.server.Server;

public class SetUp{
	File f = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\DB_Settings");
	File cf = new File(f.getPath(), "DB_Settings.dvs");
	YamlConfiguration cfg; 
	Boolean isReady = false;
	HashMap<PasswordEnum, JDBEnum> JDBConnection = new HashMap<PasswordEnum, JDBEnum>();
	public SetUp() {
		if(!f.exists()) {
			f.mkdirs();
		}
		
		if(!cf.exists()) {
			try {cf.createNewFile();} catch (IOException e) {}
			cfg = YamlConfiguration.loadConfiguration(cf);
			cfg.set("Config.Password", "Password");
			cfg.set("Config.User", "User");
			cfg.set("Config.Port", 1337);
			try {cfg.save(cf);}catch(Exception e) {}
		}
		startUP();
	}
	
	private void startUP() {
		if(cf.exists()) {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cf);
			if(cfg.getString("Config.Password").equalsIgnoreCase("Password")) {
				JDBConnection.put(PasswordEnum.Password, JDBEnum.NOT_FOUND);
			}else {
				JDBConnection.put(PasswordEnum.Password, JDBEnum.FOUND);
			}
			if(cfg.getString("Config.User").equalsIgnoreCase("User")) {
				JDBConnection.put(PasswordEnum.User, JDBEnum.NOT_FOUND);
			}else {
				JDBConnection.put(PasswordEnum.User, JDBEnum.FOUND);
			}
			checkData();
		}else {System.out.println("JDB FILE_NOT_FOUND Exception");}
	}
	
	private void checkData() {
		for(PasswordEnum ek : JDBConnection.keySet()) {
			if(JDBConnection.get(ek).equals(JDBEnum.NOT_FOUND)) {
				System.out.println("JDB " + JDBConnection.get(ek) + " Exception");
				System.out.println("Please Change the Password or the User");
				isReady = false;
			}else {
				isReady = true;
			}
		}
		start();
	}
	
	private void start() {
		if(isReady) {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cf);
			try {
				new Server(cfg.getInt("Config.Port"));
				new InComingData();
				new StartInterAccept();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {System.out.println("JDB NOT_READY Exception!");}
	}
}

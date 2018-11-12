package de.ilovejava.query;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.ilovejava.configuration.file.YamlConfiguration;
import de.ilovejava.inject.StartInterAccept;
import de.ilovejava.netty.GetIncomingMessage;
import de.ilovejava.netty.SendOutPutData;

public class InComingData {
	public InComingData() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				for(Socket c : StartInterAccept.Clients.keySet()) {
					if(!c.isClosed()) {
						GetIncomingMessage d = new GetIncomingMessage(c);
						try {String s = d.getMsg(); SubStringQuery(c, s);} catch (IOException e) {}
					}else {
						StartInterAccept.Clients.remove(c);
						try{c.close();}catch(Exception e) {}
					}
				}
			}
		}, 0,50);
	}
	
	private void SubStringQuery(Socket c, String s) {
		//INSERT-QUEYR:Minecraft:Value1=Key:Value2=Key:Value3=Key:Value4=Key
			if(s.contains("INSERT-QUEYR")) {
				String[] dataValue = s.split(":");
				if(dataValue.length == 0 && dataValue.length == 1) {try {new SendOutPutData(c, "JDB_CONNECTION_ERROR!");} catch (IOException e) {e.printStackTrace();}}
				File fc = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\DataBases\\"+ dataValue[1]);
				File lf = new File(fc.getPath(), dataValue[1]+"_SQL_Section.dvs");
				File ef = new File(fc.getPath(), dataValue[1]+"_SQL_DB.dvs");
				
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(lf);
				Integer values = 1;
				
				if(!lf.exists() && !ef.exists()) {try {new SendOutPutData(c, "JDB_DATABASE_NOT_FOUND");} catch (IOException e) {e.printStackTrace();} return;}
				for(int i = 1; i < 150000; i++) {
					if(cfg.isSet("Config.Key."+i)) {
						if(cfg.get("Config.Key."+i) != null) {
							values++;
						}
					}else {continue;}
				}
				//Value1 = uuid=Key{UUID}
				values = values;
				ArrayList<String> dataValues = new ArrayList<String>();
				ArrayList<String> dataKeys = new ArrayList<String>();
				for(int i = 0; i < values; i++) {
					if(cfg.getString("Config.Key."+i) != null) {
						dataValues.add(cfg.getString("Config.Key."+i));
						dataKeys.add(cfg.getString("Config.Value."+i));
					}
				}
				cfg = YamlConfiguration.loadConfiguration(ef);
				
				HashMap<String, String> insertValues = new HashMap<String, String>();
				for(String target : dataValue) {
					if(target.contains("=")) {
						String[] spiltValue = target.split("=");
						insertValues.put(spiltValue[0], spiltValue[1]);
					}
				}
				Integer check = 0;
				for(String dataKey : insertValues.keySet()) {
					if(dataValues.contains(dataKey)) {
						check ++;
					}else {try {new SendOutPutData(c, "JDB_VALUE_IN_DATABASE_NOT_FOUND");} catch (IOException e) {e.printStackTrace();} return;}
				}
				values = values-1;
				int key = 0;
				
				String Key = dataValues.get(0);
				
				if(check == values) {
					for(String t : dataValues) {
						cfg.set("Config.Value."+insertValues.get(Key)+"."+t, insertValues.get(t)+":"+dataKeys.get(key));
						key ++;
					}
					try {cfg.save(ef);}catch(Exception e) {}
				}else {try {new SendOutPutData(c, "JDB_VALUE_NOT_READY");} catch (IOException e) {e.printStackTrace();}}
			}else if(s.contains("SELECT-QUERY")) {
				//SELECT-QUERY:Minecraft:uuid:key
				String[] dataValue = s.split(":");
				File fc = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\DataBases\\"+ dataValue[1]);
				File lf = new File(fc.getPath(), dataValue[1]+"_SQL_Section.dvs");
				File ef = new File(fc.getPath(), dataValue[1]+"_SQL_DB.dvs");
				
				HashMap<String, Integer> valueKeys = new HashMap<String, Integer>();
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(lf);
				if(!lf.exists() && !ef.exists()) {try {new SendOutPutData(c, "JDB_DATABASE_NOT_FOUND");} catch (IOException e) {e.printStackTrace();} return;}
				for(int i = 1; i < 150000; i++) {
					if(cfg.isSet("Config.Key."+i)) {
						valueKeys.put(cfg.getString("Config.Key."+i), i);
					}
				}
				if(valueKeys.containsKey(dataValue[2])) {
					cfg = YamlConfiguration.loadConfiguration(ef);
					System.out.println(dataValue[2]);
					if(cfg.isSet("Config.Value."+dataValue[3]+"."+dataValue[2])) {
						try {new SendOutPutData(c, "RETURN-SELECT-QUERY:"+cfg.getString("Config.Value."+dataValue[3]+"."+dataValue[2]));} catch (IOException e) {e.printStackTrace();}
					}else {try {new SendOutPutData(c, "JDB_COLUM_NOT_FOUND");} catch (IOException e) {e.printStackTrace();}}
				}
			}else if(s.contains("DISSCONNECT")) {
				StartInterAccept.Clients.remove(c);
				try {c.close();} catch (IOException e) {e.printStackTrace();}
				System.out.println("Connection is lost");
			}
	}
}

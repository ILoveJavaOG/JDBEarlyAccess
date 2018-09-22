package de.ilovejava.query;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import de.ilovejava.configuration.file.YamlConfiguration;
import de.ilovejava.inject.StartInterAccept;
import de.ilovejava.netty.GetIncomingMessage;

public class InComingData {
	public InComingData() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				for(Socket c : StartInterAccept.Clients.keySet()) {
					GetIncomingMessage d = new GetIncomingMessage(c);
					try {String s = d.getMsg(); SubStringQuery(c, s);} catch (IOException e) {e.printStackTrace();}
				}
			}
		}, 0,50);
	}
	
	private void SubStringQuery(Socket c, String s) {
		if(s.contains("INSERT-QUEYR")) {
			System.out.println("Incoming Data");
			File fc = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\DataBases\\"+ StartInterAccept.Clients.get(c));
			File lf = new File(fc.getPath(), StartInterAccept.Clients.get(c)+"_SQL_Section.dvs");
			File ef = new File(fc.getPath(), StartInterAccept.Clients.get(c)+"_SQL_DB.dvs");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(ef);
			YamlConfiguration ecfg = YamlConfiguration.loadConfiguration(lf);
			String[] sarray = s.split(":");
			for(int i = 1; i<1000; i++) {
				if(ecfg.isSet("Config.Value."+i)) {
					cfg.set("Config."+sarray[1]+i, sarray[i]);
				}else {break;}
			}
			try {cfg.save(ef);}catch(Exception e) {}
		}
	}
}

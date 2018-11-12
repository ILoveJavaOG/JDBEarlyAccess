package de.ilovejava.jdb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.ilovejava.command.Command;
import de.ilovejava.configuration.file.YamlConfiguration;

public class Command_CreateDataBase {
	private static int i = 0;
	private static String Name;
	private static HashMap<Integer, String>StringValues = new HashMap<Integer, String>();
	private static int QuerySection;
	private static int QueryStringSection = 0;
	private static HashMap<Integer, String> Values = new HashMap<Integer, String>();
	public static void CreateDataBase(String s) {
		if(s.equalsIgnoreCase("ready")) {
			i = 2;
			System.out.println("Your Query: " + Values.toString());
			System.out.println("Please write a Name for the Values! Number: " + Values.size());
			return;
		}
		if(i == 0) {
			Name = s;
			i++;
			System.out.println("Write the Colum off the Values!");
		}else if(i == 1) {
			if(s.equalsIgnoreCase("int")) {
				QuerySection++;
				Values.put(QuerySection, "INT");
			}else if(s.equalsIgnoreCase("string")) {
				QuerySection++;
				Values.put(QuerySection, "STRING");
			}else if(s.equalsIgnoreCase("long")) {
				QuerySection++;
				Values.put(QuerySection, "LONG");
			}else if(s.equalsIgnoreCase("double")) {
				QuerySection++;
				Values.put(QuerySection, "DOUBLE");
			}else if(s.equalsIgnoreCase("boolean")){
				QuerySection++;
				Values.put(QuerySection, "BOOLEAN");
			}else if(s.equalsIgnoreCase("List")) {
				QuerySection++;
				Values.put(QuerySection, "LIST");
			}else {System.out.println("Write: Long,Int,String,Double,Boolean,list");}
		}else if(i == 2) {
			QueryStringSection ++;
			StringValues.put(QueryStringSection, s);
			System.out.println("Word "  + QueryStringSection + " of " + QuerySection);
			if(QueryStringSection >= QuerySection) {
				i = 0;
				Command.ce = null;
				System.out.println("Saving Database!!");
				createFile();
			}
		}
	}
	
	private static void createFile() {
		File f = new File("C:\\Users\\Jeremy\\Desktop\\nojs\\DataBases\\"+ Name);
		File cf = new File(f.getPath(), Name+"_SQL_Section.dvs");
		File ef = new File(f.getPath(), Name+"_SQL_DB.dvs");
		if(!f.exists()) {
			f.mkdirs();
			if(!cf.exists()) {
				try {cf.createNewFile(); ef.createNewFile();} catch (IOException e) {}
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cf);
				for(Integer i : Values.keySet()) {
					if(Values.get(i) != null) {
						cfg.set("Config.Value."+i, Values.get(i));
					}
				}
				
				System.out.println("Saving......");
				for(Integer i : StringValues.keySet()) {
					if(StringValues.get(i) != null) {
						cfg.set("Config.Key."+i, StringValues.get(i).toLowerCase());
					}
				}
				
				try {cfg.save(cf);}catch(Exception e) {}
				
				System.out.println("The Database is now Active");
			}else {System.out.println("Warning The Database " + Name + " is already Exists!"); Command.ce = null; i = 0;}
		}else {System.out.println("Warning The Database " + Name + " is already Exists!"); Command.ce = null; i = 0;}
	}
	
}

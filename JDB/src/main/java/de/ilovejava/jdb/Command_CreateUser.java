package de.ilovejava.jdb;

import java.io.File;
import java.io.IOException;

import de.ilovejava.command.Command;
import de.ilovejava.configuration.file.YamlConfiguration;

public class Command_CreateUser {
	public static int i = 0;
	public static String Name;
	public static String Password;
	@SuppressWarnings("unused")
	public static void createUser(String s) {
		if(s.equalsIgnoreCase("Break")) {
			i = 0;
			Command.ce = null;
			System.out.println("You kill the Command!");
			return;
		}
		if(i == 0) {
			if(s != null) {
				Name = s;
				i ++;
				System.out.println("Please write a Password!");
			}else {System.out.println("Please write a Name!");}
		}else if(i == 1){
			if(s != null) {
				Password = s;
				i ++;
				System.out.println("Do you want to finish? y or n");
			}else {System.out.println("Please write a Password!");}
		}else if(i == 2) {
			if(s.equalsIgnoreCase("y")) {
				System.out.println("The User " + Name + " has ben created Password: " + Password);
				createFile();
			}else {i = 0; System.out.println("The User has not Create!"); Command.ce = null;}
		}
	}
	
	private static void createFile() {
		File f = new File("C:\\\\Users\\\\Jeremy\\\\Desktop\\\\nojs\\\\User");
		File cf = new File(f.getPath(),Name+".dvs");
		if(!cf.exists()) {
			if(!f.exists()) {
				f.mkdirs();
			}
			try {cf.createNewFile();} catch (IOException e) {e.printStackTrace();}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cf);
			cfg.set("Config.Name", Name);
			cfg.set("Config.Password",Password);
			try {cfg.save(cf);}catch(Exception e) {}
		}else {System.out.println("This User is already exists!");}
	}
}

package de.ilovejava.command;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import de.ilovejava.jdb.CommandEnum;
import de.ilovejava.jdb.Command_CreateDataBase;
import de.ilovejava.jdb.Command_CreateUser;

public class Command {
	
	public static CommandEnum ce;
	
	public Command() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				@SuppressWarnings("resource")
				Scanner s = new Scanner(System.in);
				getCommand(s.nextLine());
			}
		}, 0, 50);
	}
	
	private void getCommand(String s) {
		if(ce == null) {
			if(s.equalsIgnoreCase("createUser")) {
				System.out.println("Please write a Username");
				ce = CommandEnum.CreateUser;
			}else if(s.equalsIgnoreCase("createDatabase")) {
				System.out.println("Please write a Name for the Database");
				ce = CommandEnum.CreateDataBase;
			}
		}else if(ce == CommandEnum.CreateUser) {
			Command_CreateUser.createUser(s);
		}else if(ce == CommandEnum.CreateDataBase) {
			Command_CreateDataBase.CreateDataBase(s);
		}
	}
}

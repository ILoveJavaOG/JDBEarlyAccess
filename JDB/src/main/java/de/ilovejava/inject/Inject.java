package de.ilovejava.inject;

import java.io.File;

public class Inject implements Utils{

	public String getFilePath() {
		
		return "C:\\Users\\Jeremy\\Desktop\\nojs";
	}

	public String getFileName() {
		
		return "DB_Settings.dvs";
	}

	public File getFile() {
		
		return new File(getFilePath(),getFileName());
	}
	
}

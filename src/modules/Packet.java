package modules;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Packet implements Serializable {
	
	public ArrayList<String> files;

	public Packet(ArrayList<String> files) {
		this.files = files;
	}



}

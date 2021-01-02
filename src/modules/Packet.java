package modules;

import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<String> files;

	public Packet(ArrayList<String> files) {
		this.files = files;
	}



}

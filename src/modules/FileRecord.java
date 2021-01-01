package modules;

import javafx.scene.control.CheckBox;

public class FileRecord {

	public int sl_no;
	public String file_name;
	public String file_path;
	public CheckBox is_select;
	
	
	public int getSl_no() {
		return sl_no;
	}
	public void setSl_no(int sl_no) {
		this.sl_no = sl_no;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public FileRecord(int sl_no, String file_name, String file_path) {
		this.sl_no = sl_no;
		this.file_name = file_name;
		this.file_path = file_path;
		this.is_select = new CheckBox();
	}
	public CheckBox getIs_select() {
		return is_select;
	}
	public void setIs_select(CheckBox is_select) {
		this.is_select = is_select;
	}

	

}

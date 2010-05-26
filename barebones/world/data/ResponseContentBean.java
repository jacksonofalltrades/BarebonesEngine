package barebones.world.data;

import java.util.HashMap;

public class ResponseContentBean 
{
	public String text;
	public HashMap<String,String> metadata;
	
	public ResponseContentBean() {		
	}
	
	public ResponseContentBean(String text) {
		this.text = text;
	}
	
	public ResponseContentBean(ResponseContentBean other) {
		this.text = other.text;
	}
	
	public void settext(String t) {
		text = t;
	}
	
	public String gettext() {
		return text;
	}
	
	public void setmetadata(HashMap<String,String> md) {
		metadata = md;
	}
	
	public HashMap<String,String> getmetadata() {
		return metadata;
	}
}

package barebones.world.data;

import java.util.HashMap;
import java.util.Vector;

import barebones.io.dal.ObjectMarshaller;

public class WORoomFeatureMapBean extends WORootDataBean 
{
	protected static final String ROOM_FEATURE_PATH = "features/";
	
	static {
		ObjectMarshaller.registerBean(WORoomFeatureMapBean.class, null, ROOM_FEATURE_PATH);
	}
	
	public HashMap<String,Vector<WORoomFeatureBean>> roomFeatureMap;
	
	public void setroomFeatureMap(HashMap<String,Vector<WORoomFeatureBean>> map) {
		roomFeatureMap = map;
	}
	
	public HashMap<String,Vector<WORoomFeatureBean>> getroomFeatureMap() {
		return roomFeatureMap;
	}
}

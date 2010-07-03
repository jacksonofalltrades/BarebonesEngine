package barebones.world.data;

import java.util.HashMap;
import java.util.Vector;

import utils.io.dal.RootDataBean;

import barebones.io.dal.GameObjectMarshaller;

public class WORoomFeatureMapBean extends RootDataBean 
{
	protected static final String ROOM_FEATURE_PATH = "features/";
	
	static {
		GameObjectMarshaller.registerBean(WORoomFeatureMapBean.class, null, ROOM_FEATURE_PATH);
	}
	
	public HashMap<String,Vector<WORoomFeatureBean>> roomFeatureMap;
	
	public void setroomFeatureMap(HashMap<String,Vector<WORoomFeatureBean>> map) {
		roomFeatureMap = map;
	}
	
	public HashMap<String,Vector<WORoomFeatureBean>> getroomFeatureMap() {
		return roomFeatureMap;
	}
}

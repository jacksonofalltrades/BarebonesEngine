package barebones.world.data;

import java.util.HashMap;
import java.util.Vector;

import utils.IntegerRange;

import barebones.io.dal.ObjectMarshaller;

public class WORegionMapBean extends WORootDataBean 
{
	protected static final String REGION_MAP_PATH = "regions/";

	static {
		ObjectMarshaller.registerBean(WORegionMapBean.class, null, REGION_MAP_PATH);
	}
	
	public HashMap<String,String> roomToRegionMap;
	public HashMap<String,String> rangeToRegionMap;
	public Vector<IntegerRange> rangeList;
	
	public void setroomToRegionMap(HashMap<String,String> regionMap) {
		this.roomToRegionMap = regionMap;
	}
	
	public HashMap<String,String> getroomToRegionMap() {
		return this.roomToRegionMap;
	}

	public void setrangeToRegionMap(HashMap<String,String> regionMap) {
		this.rangeToRegionMap = regionMap;
	}
	
	public HashMap<String,String> getrangeToRegionMap() {
		return this.rangeToRegionMap;
	}
	
	public void setrangeList(Vector<IntegerRange> rl) {
		this.rangeList = rl;
	}
	
	public Vector<IntegerRange> getrangeList() {
		return this.rangeList;
	}
}

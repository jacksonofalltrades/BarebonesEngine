package barebones.world.data;

import java.util.HashSet;

import barebones.engine.GameEngineAccessor;
import barebones.world.feature.Door;
import barebones.world.feature.RoomFeature;
import barebones.world.filter.WODoorFilter;

public class WODoorBean extends WODataBean implements WORoomFeatureBean
{
	static {
		WODataBean.registerMergeFilter(WODoorBean.class, new WODoorFilter());
	}
	
	public boolean state;
	public String dir;

	public WODoorBean() {		
	}
	
	public WODoorBean(String id, boolean s, String d) {
		this.id = id;
		state = s;
		dir = d;
	}
	
	public void setstate(boolean s) {
		state = s;
	}
	
	public boolean getstate() {
		return state;
	}
	
	public void setdir(String d) {
		dir = d;
	}
	
	public String getdir() {
		return dir;
	}
	
	public RoomFeature newInstance(GameEngineAccessor engineRef) {
		return new Door(engineRef, this);
	}
	
	public Object clone() 
	{
		WODoorBean copy = new WODoorBean();
		copy.id = this.id;
		copy.detailId = this.detailId;
		copy.dir = this.dir;
		copy.state = this.state;
		copy.containedItemSet = new HashSet<String>(this.containedItemSet);
		
		return copy;
	}
}

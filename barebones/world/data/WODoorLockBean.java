package barebones.world.data;

import java.util.HashSet;

import barebones.engine.GameEngineAccessor;
import barebones.world.feature.DoorLock;
import barebones.world.feature.RoomFeature;
import barebones.world.filter.WODoorLockFilter;

public class WODoorLockBean extends WODataBean implements WORoomFeatureBean 
{
	static {
		WODataBean.registerMergeFilter(WODoorLockBean.class, new WODoorLockFilter());
	}
	
	public boolean state;
	public String dir;
	public String itemId;
	
	public WODoorLockBean() {
	}
	
	public WODoorLockBean(String id, boolean s, String d, String i) {
		this.id = id;
		state = s;
		dir = d;
		itemId = i;
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
	
	public void setitemId(String id) {
		itemId = id;
	}
	
	public String getitemId() {
		return itemId;
	}

	public RoomFeature newInstance(GameEngineAccessor engineRef) {
		return new DoorLock(engineRef, this);
	}
	
	public Object clone()
	{
		WODoorLockBean copy = new WODoorLockBean();
		copy.id = this.id;
		copy.detailId = this.detailId;
		copy.dir = this.dir;
		copy.state = this.state;
		copy.itemId = this.itemId;
		copy.containedItemSet = new HashSet<String>(this.containedItemSet);
		
		return copy;
	}
}

package barebones.world.data;

import java.util.HashSet;
import java.util.Vector;

import barebones.engine.GameEngineAccessor;
import barebones.world.feature.RoomFeature;
import barebones.world.feature.SecretDoor;
import barebones.world.filter.WOSecretDoorFilter;

public class WOSecretDoorBean extends WODataBean implements WORoomFeatureBean 
{
	static {
		WODataBean.registerMergeFilter(WOSecretDoorBean.class, new WOSecretDoorFilter());
	}
	
	public boolean state;
	public String dir;
	public Vector<CommandTrigger> triggerList;
	
	public WOSecretDoorBean() {
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
	
	public void settriggerList(Vector<CommandTrigger> tl) {
		this.triggerList = tl;
	}
	
	public Vector<CommandTrigger> gettriggerList() {
		return this.triggerList;
	}

	public RoomFeature newInstance(GameEngineAccessor engineRef) {
		return new SecretDoor(engineRef, this);
	}
	
	public Object clone()
	{
		WOSecretDoorBean copy = new WOSecretDoorBean();
		copy.id = this.id;
		copy.detailId = this.detailId;
		copy.dir = this.dir;
		copy.state = this.state;
		copy.triggerList = this.triggerList;
		copy.containedItemSet = new HashSet<String>(this.containedItemSet);
		
		return copy;
	}
}

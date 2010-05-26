package barebones.world.data;

import barebones.io.WODataFilter;

public class PlayerStateFilter extends WODataFilter 
{
	public PlayerStateFilter() {
		this.addBeanToWrite(PlayerStateBean.class);
		this.addBeanToWrite(PlayerStateData.class);		
		this.addPropToWrite(WODoorBean.class, "id");
		this.addPropToWrite(WODoorBean.class, "state");
		this.addPropToWrite(WODoorLockBean.class, "id");
		this.addPropToWrite(WODoorLockBean.class, "state");
		this.addPropToWrite(WORoomBean.class, "id");
		this.addPropToWrite(WORoomBean.class, "containedItemSet");
	}
}

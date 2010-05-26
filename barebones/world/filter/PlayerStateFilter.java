package barebones.world.filter;

import barebones.world.data.PlayerStateBean;
import barebones.world.data.PlayerStateData;
import barebones.world.data.WODoorBean;
import barebones.world.data.WODoorLockBean;
import barebones.world.data.WORoomBean;
import utils.io.xml.XMLWriteFilter;


public class PlayerStateFilter extends XMLWriteFilter 
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

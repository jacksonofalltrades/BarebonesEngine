package barebones.world.data;

import java.util.HashMap;
import java.util.Vector;

import utils.io.dal.RootDataBean;

import barebones.io.dal.GameObjectMarshaller;


public class WORegionBean extends RootDataBean
{
	protected static final String REGION_PATH = "regions/";
	
	public HashMap<String,WORoomBean> rooms;
	public Vector<WORoomBlockBean> roomBlocks;
	
	static {
		GameObjectMarshaller.registerBean(WORegionBean.class, null, REGION_PATH);
	}
	
	public void setrooms(HashMap<String,WORoomBean> rooms) {
		this.rooms = rooms;
	}
	
	public HashMap<String,WORoomBean> getrooms() {
		return this.rooms;
	}
	
	public void setroomBlocks(Vector<WORoomBlockBean> roomBlocks)
	{
		this.roomBlocks = roomBlocks;
	}
	
	public Vector<WORoomBlockBean> getroomBlocks()
	{
		return this.roomBlocks;
	}
}

package barebones.world.data;

import java.util.HashMap;
import java.util.Vector;

import barebones.io.dal.ObjectMarshaller;


public class WORegionBean extends WORootDataBean
{
	protected static final String REGION_PATH = "regions/";
	
	public HashMap<String,WORoomBean> rooms;
	public Vector<WORoomBlockBean> roomBlocks;
	
	static {
		ObjectMarshaller.registerBean(WORegionBean.class, null, REGION_PATH);
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

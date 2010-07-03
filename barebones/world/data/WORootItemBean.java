package barebones.world.data;

import utils.io.dal.RootDataBean;
import barebones.io.dal.GameObjectMarshaller;

public class WORootItemBean extends RootDataBean 
{
	protected static final String ITEM_PATH = "items/";
	
	static {
		GameObjectMarshaller.registerBean(WORootItemBean.class, null, ITEM_PATH);
	}
	
	public WOItemBean itemData;
	
	public WORootItemBean() {
	}
	
	public WORootItemBean(WOItemBean i) {
		this.id = i.id;
		itemData = i;
	}
	
	public void setitemData(WOItemBean bean) {
		itemData = bean;
	}
	
	public WOItemBean getitemData() {
		return itemData;
	}
}

package barebones.world.data;

import barebones.io.dal.ObjectMarshaller;

public class WORootItemBean extends WORootDataBean 
{
	protected static final String ITEM_PATH = "items/";
	
	static {
		ObjectMarshaller.registerBean(WORootItemBean.class, null, ITEM_PATH);
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

package barebones.world.object;

import java.util.Comparator;
import java.util.HashSet;

import barebones.engine.GameEngineAccessor;
import barebones.engine.Player;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.dal.ObjectDetailLoader;
import barebones.world.data.WODataBean;

abstract public class WorldObjectImpl implements WorldObject, Comparator<WorldObjectFeature>
{
	protected GameEngineAccessor m_engineRef;
	
	protected WODataBean data;
	
	//protected String m_objId;
	//protected String m_overridingDetailId;
	
	//protected HashSet<String> m_containedItemSet;
			
	public WorldObjectImpl(GameEngineAccessor engineRef, WODataBean data)
	{
		m_engineRef = engineRef;
		this.data = data;
		this.data.containedItemSet = new HashSet<String>();
		//m_objId = id;
		//m_overridingDetailId = detailId;
				
		//m_containedItemSet = new HashSet<String>();
		
		// Load default Detail for this object
		ObjectDetail base = ObjectDetailLoader.instance().load(m_engineRef, this.getClass().getSimpleName());
		ObjectDetail custom = ObjectDetailLoader.instance().load(m_engineRef, getCustomDetailId());

		if (null != base && null != custom) {
			// the override() method grabs everything from base, except for those keys
			// defined in custom
			custom.override(base);
		}
		
		if (null != custom)
			data.containedItemSet.addAll(custom.getItems());
	}
	
	public String id() {
		return data.id;
	}

	public void addItem(Item item) {
		data.containedItemSet.add(item.id());
	}
	
	public void addItems(HashSet<String> items) {
		data.containedItemSet.addAll(items);
	}

	public void removeItem(String itemId) {
		if (data.containedItemSet.contains(itemId)) {
			data.containedItemSet.remove(itemId);
		}
	}
	
	/**
	 * @return container id
	 */
	public String findItem(String itemId) {
		if (data.containedItemSet.contains(itemId)) {
			return this.id();
		}
		// Now recurse
		return m_engineRef.search(data.containedItemSet, itemId);
	}
	
	public Item getItem(String itemId) {
		if (data.containedItemSet.contains(itemId)) {
			return m_engineRef.getItem(itemId);
		}
		return null;
	}

	public HashSet<String> getItems() {
		HashSet<String> l_copy = new HashSet<String>(data.containedItemSet);
		return l_copy;
	}
	
	private String getCustomDetailId()
	{
		String useDetailId = (null!=data.detailId?data.detailId:data.id);
		return useDetailId;
	}
	
	private ObjectDetail getDetail() {
		// Try to get obj specific one first
		ObjectDetail detail = ObjectDetailLoader.instance().load(m_engineRef, getCustomDetailId());
		if (null == detail) {
			detail = ObjectDetailLoader.instance().load(m_engineRef, this.getClass().getSimpleName());
			if (null == detail) {
				throw new RuntimeException("No object detail for base class "+this.getClass().getSimpleName());
			}
		}

		return detail;
	}
	
	protected final ResponseContent getCmdKeyContent(String cmdId)
	{
		ObjectDetail detail = getDetail();
		ResponseContent c = detail.getCmdKeyContent(cmdId);
		return c;
	}
	
	protected final ResponseContent getObjKeyContent(String objkey)
	{
		ObjectDetail detail = getDetail();		
		ResponseContent c = detail.getObjKeyContent(objkey);
		return c;
	}
	
	protected final ResponseContent getPreTriggerContent()
	{
		ObjectDetail detail = getDetail();		
		ResponseContent c = detail.getPreTriggerContent();
		return c;
	}

	protected final ResponseContent getTriggerContent()
	{
		ObjectDetail detail = getDetail();		
		ResponseContent c = detail.getTriggerContent();
		return c;
	}

	protected final ResponseContent getPostTriggerContent()
	{
		ObjectDetail detail = getDetail();		
		ResponseContent c = detail.getPostTriggerContent();
		return c;
	}
	
	public int compare(WorldObjectFeature o1, WorldObjectFeature o2) {
		WorldObjectFeature s1 = (WorldObjectFeature)o1;
		WorldObjectFeature s2 = (WorldObjectFeature)o2;
		
		if (s1.sequenceIndex() > s2.sequenceIndex()) {
			return 1;
		}
		else if (s2.sequenceIndex() > s1.sequenceIndex()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public void updateChangedState(UserCommand cmd) {
		cmd.updatePlayerState(this.id(), this.data);
	}
	
	public void applyPlayerState() {
		Player p = m_engineRef.getPlayer();
		WODataBean otherBean = p.getWOPlayerStateById(this.id());
		this.data.merge(otherBean);
	}
}

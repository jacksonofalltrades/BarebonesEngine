package barebones.world.object;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import barebones.io.ResponseContent;
import barebones.world.data.ObjectDetailBean;
import barebones.world.data.ResponseContentBean;

public class ObjectDetail
{
	protected ObjectDetailBean data;

	protected void copyItems(ObjectDetail other)
	{
		this.data.containedItemSet.addAll(other.data.containedItemSet);
	}
	
	public ObjectDetail(ObjectDetailBean data)
	{
		this.data = data;
	}

	/*
	public void addObjKeyContent(String key, ResponseContent content) {
		data.objKeyMap.put(key, content);
	}
	
	public void addCmdKeyContent(String cmd, ResponseContent content) {
		data.cmdKeyMap.put(cmd, content);
	}
	
	public void setPreTriggerContent(ResponseContent content) {
		m_preTriggerContent = content;
	}
	
	public void setTriggerContent(ResponseContent content) {
		m_triggerContent = content;
	}
	
	public void setPostTriggerContent(ResponseContent content) {
		m_postTriggerContent = content;
	}
	
	*/

	public void addItem(String itemId) {
		this.data.containedItemSet.add(itemId);
	}
	
	public HashSet<String> getItems() {
		return this.data.containedItemSet;
	}
	
	// Prioritize keys in THIS object, but steal keys not present here, from other.
	public void override(ObjectDetail other) {
		Set<String> objKeys = other.data.objKeyMap.keySet();
		Iterator<String> iter = objKeys.iterator();
		while(iter.hasNext()) {
			String key = (String)iter.next();
			if (!data.objKeyMap.containsKey(key)) {
				data.objKeyMap.put(key, other.data.objKeyMap.get(key));
			}
		}
		
		Set<String> cmdKeys = data.cmdKeyMap.keySet();
		iter = cmdKeys.iterator();
		while(iter.hasNext()) {
			String key = (String)iter.next();
			if (!data.cmdKeyMap.containsKey(key)) {
				data.cmdKeyMap.put(key, other.data.cmdKeyMap.get(key));
			}
		}
		
		if (null == data.preTriggerContent) {
			data.preTriggerContent = other.data.preTriggerContent;
		}
		if (null == data.triggerContent) {
			data.triggerContent = other.data.triggerContent;
		}
		if (null == data.postTriggerContent) {
			data.postTriggerContent = other.data.postTriggerContent;
		}
		
		copyItems(other);
	}
	
	public ResponseContent getCmdKeyContent(String cmd) {
		ResponseContentBean rcb = data.cmdKeyMap.get(cmd);
		if (null != rcb)
			return new ResponseContent(new ResponseContentBean(rcb));
		else
			return null;
	}
	
	public ResponseContent getObjKeyContent(String key) {
		ResponseContentBean rcb = data.objKeyMap.get(key);
		if (null != rcb)
			return new ResponseContent(new ResponseContentBean(rcb));
		else
			return null;
	}
	
	public ResponseContent getPreTriggerContent() {
		if (null != data.preTriggerContent)
			return new ResponseContent(data.preTriggerContent);
		else
			return null;
	}
	
	public ResponseContent getTriggerContent() {
		if (null != data.triggerContent)
			return new ResponseContent(data.triggerContent);
		else
			return null;
	}
	
	public ResponseContent getPostTriggerContent() {
		if (null != data.postTriggerContent)
			return new ResponseContent(data.postTriggerContent);
		else
			return null;
	}
}

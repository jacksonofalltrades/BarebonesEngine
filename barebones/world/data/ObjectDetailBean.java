package barebones.world.data;

import java.util.HashMap;
import java.util.HashSet;

import barebones.io.dal.ObjectMarshaller;

public class ObjectDetailBean extends WORootDataBean {
	
	protected static final String DETAIL_PATH = "details/";
	
	static {
		ObjectMarshaller.registerBean(ObjectDetailBean.class, null, DETAIL_PATH);
	}
	
	public static ObjectDetailBean createEmpty(String id) {
		ObjectDetailBean empty = new ObjectDetailBean();
		empty.setid(id);
		
		return empty;
	}
	
	public String id;
	public HashMap<String,ResponseContentBean> objKeyMap;
	public HashMap<String,ResponseContentBean> cmdKeyMap;
	public ResponseContentBean preTriggerContent;
	public ResponseContentBean triggerContent;
	public ResponseContentBean postTriggerContent;
	public HashSet<String> containedItemSet;
	
	public void setid(String i) {
		id = i;
	}
	
	public String getid() {
		return id;
	}
	
	public void setobjKeyMap(HashMap<String,ResponseContentBean> okm) {
		objKeyMap = okm;
	}
	
	public HashMap<String,ResponseContentBean> getobjKeyMap() {
		return objKeyMap;
	}
	
	public void setcmdKeyMap(HashMap<String,ResponseContentBean> ckm) {
		cmdKeyMap = ckm;
	}
	
	public HashMap<String,ResponseContentBean> getcmdKeyMap() {
		return cmdKeyMap;
	}
	
	public void setpreTriggerContent(ResponseContentBean ptc) {
		preTriggerContent = ptc;
	}
	
	public ResponseContentBean getpreTriggerContent() {
		return preTriggerContent;
	}
	
	public void settriggerContent(ResponseContentBean tc) {
		triggerContent = tc;
	}
	
	public ResponseContentBean gettriggerContent() {
		return triggerContent;
	}
	
	public void setpostTriggerContent(ResponseContentBean ptc) {
		postTriggerContent = ptc;
	}
	
	public ResponseContentBean getpostTriggerContent() {
		return postTriggerContent;
	}
	
	public void setcontainedItemSet(HashSet<String> cis) {
		containedItemSet = cis;
	}
	
	public HashSet<String> getcontainedItemSet() {
		return containedItemSet;
	}
}

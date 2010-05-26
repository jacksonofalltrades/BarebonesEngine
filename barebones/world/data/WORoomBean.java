package barebones.world.data;

import java.util.HashSet;

import barebones.world.filter.WORoomFilter;
import barebones.world.object.Room;

public class WORoomBean extends WODataBean
{
	static {
		WODataBean.registerMergeFilter(WORoomBean.class, new WORoomFilter());
	}
	
	public String defaultDesc;
	public Integer roomId;
	public Integer nid;
	public Integer eid;
	public Integer wid;
	public Integer sid;
	public Integer uid;
	public Integer did;
	
	public WORoomBean() {		
	}
	
	public WORoomBean(Integer roomId, String desc, Integer n, Integer e, Integer w, Integer s, Integer u, Integer d, String detailId) {
		this.roomId = roomId;
		this.id = Room.getStringId(roomId);
		this.detailId = detailId;
		defaultDesc = desc;
		nid = n;
		eid = e;
		wid = w;
		sid = s;
		uid = u;
		did = d;
	}
	
	public void setroomId(Integer roomId) {
		this.roomId = roomId;
	}
	
	public Integer getroomId() {
		return this.roomId;
	}
	
	public void setdefaultDesc(String desc) {
		defaultDesc = desc;
	}
	
	public String getdefaultDesc() {
		return defaultDesc;
	}
	
	public void setnid(Integer n) {
		nid = n;
	}
	
	public Integer getnid() {
		return nid;
	}

	public void seteid(Integer e) {
		eid = e;
	}
	
	public Integer geteid() {
		return eid;
	}

	public void setwid(Integer w) {
		wid = w;
	}
	
	public Integer getwid() {
		return wid;
	}

	public void setsid(Integer s) {
		sid = s;
	}
	
	public Integer getsid() {
		return sid;
	}

	public void setuid(Integer u) {
		uid = u;
	}
	
	public Integer getuid() {
		return uid;
	}

	public void setdid(Integer d) {
		did = d;
	}
	
	public Integer getdid() {
		return did;
	}
	
	public Object clone() {
		WORoomBean copy = new WORoomBean();
		copy.id = this.id;
		copy.roomId = this.roomId;
		copy.detailId = this.detailId;
		copy.defaultDesc = this.defaultDesc;
		copy.nid = this.nid;
		copy.eid = this.eid;
		copy.wid = this.wid;
		copy.sid = this.sid;
		copy.uid = this.uid;
		copy.did = this.did;
		if (null != this.containedItemSet) {
			copy.containedItemSet = new HashSet<String>(this.containedItemSet);
		}
		
		return copy;
	}
}

package barebones.world.data;

import java.util.HashSet;

public class WOItemBean extends WODataBean 
{
	public String baseDesc;
	public String baseDescNa;
	public String longDesc;
	public int getpts;
	public int usepts;
	public boolean equipped;
	
	public WOItemBean() {
	}
	
	public WOItemBean(String id, String bd, String bdn, String ld, int gp, int up, boolean e) {
		this.id = id;
		baseDesc = bd;
		baseDescNa = bdn;
		longDesc = ld;
		getpts = gp;
		usepts = up;
		equipped = e;
	}
	
	public void setbaseDesc(String bd) {
		baseDesc = bd;
	}
	
	public String getbaseDesc() {
		return baseDesc;
	}
	
	public void setbaseDescNa(String bdn) {
		baseDescNa = bdn;
	}
	
	public String getbaseDescNa() {
		return baseDescNa;
	}
	
	public void setlongDesc(String ld) {
		longDesc = ld;
	}
	
	public String getlongDesc() {
		return longDesc;
	}
	
	public void setgetpts(int gp) {
		getpts = gp;
	}
	
	public int getgetpts() {
		return getpts;
	}
	
	public void setusepts(int up) {
		usepts = up;
	}
	
	public int getusepts() {
		return usepts;
	}
	
	public void setequipped(boolean e) {
		equipped = e;
	}
	
	public boolean getequipped() {
		return equipped;
	}
	
	public Object clone() {
		WOItemBean copy = new WOItemBean();
		copy.id = this.id;
		copy.detailId = this.detailId;
		copy.baseDesc = this.baseDesc;
		copy.baseDescNa = this.baseDescNa;
		copy.longDesc = this.longDesc;
		copy.equipped = this.equipped;
		copy.getpts = this.getpts;
		copy.usepts = this.usepts;
		copy.containedItemSet = new HashSet<String>(this.containedItemSet);
		
		return copy;
	}
}

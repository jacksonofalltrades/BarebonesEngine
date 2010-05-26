package barebones.world.filter;

import utils.io.xml.MergeMode;
import barebones.io.WOMergeFilter;
import barebones.world.data.WORoomBean;

public class WORoomFilter extends WOMergeFilter {
	public WORoomFilter() {
		this.addMergeRule(WORoomBean.class, "containedItemSet", MergeMode.PRIORITY_OTHER_SOFT);
	}
}

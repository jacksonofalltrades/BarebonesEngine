package barebones.world.filter;

import utils.io.xml.MergeMode;
import barebones.io.WOMergeFilter;
import barebones.world.data.WORoomBean;

public class WORoomBlockFilter extends WOMergeFilter {
	public WORoomBlockFilter() {
		this.addMergeRule(WORoomBean.class, "containedItemSet", MergeMode.PRIORITY_OTHER_SOFT);
		this.addMergeRule(WORoomBean.class, "did", MergeMode.PRIORITY_OTHER_SOFT);
		this.addMergeRule(WORoomBean.class, "eid", MergeMode.PRIORITY_OTHER_SOFT);
		this.addMergeRule(WORoomBean.class, "nid", MergeMode.PRIORITY_OTHER_SOFT);
		this.addMergeRule(WORoomBean.class, "sid", MergeMode.PRIORITY_OTHER_SOFT);
		this.addMergeRule(WORoomBean.class, "uid", MergeMode.PRIORITY_OTHER_SOFT);
		this.addMergeRule(WORoomBean.class, "wid", MergeMode.PRIORITY_OTHER_SOFT);
		this.addMergeRule(WORoomBean.class, "detailId", MergeMode.PRIORITY_OTHER_SOFT);
	}
}

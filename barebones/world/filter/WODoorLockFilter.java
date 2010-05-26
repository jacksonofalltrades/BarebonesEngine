package barebones.world.filter;

import utils.io.xml.MergeMode;
import barebones.io.WOMergeFilter;
import barebones.world.data.WODoorLockBean;

public class WODoorLockFilter extends WOMergeFilter {
	public WODoorLockFilter() {
		this.addMergeRule(WODoorLockBean.class, "state", MergeMode.PRIORITY_OTHER_SOFT);
	}
}

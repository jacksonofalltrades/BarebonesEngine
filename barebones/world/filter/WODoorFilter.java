package barebones.world.filter;

import utils.io.xml.MergeMode;
import barebones.io.WOMergeFilter;
import barebones.world.data.WODoorBean;

public class WODoorFilter extends WOMergeFilter {
	public WODoorFilter() {
		this.addMergeRule(WODoorBean.class, "state", MergeMode.PRIORITY_OTHER_SOFT);
	}
}

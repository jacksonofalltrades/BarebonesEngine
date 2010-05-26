package barebones.world.filter;

import utils.io.xml.MergeMode;
import barebones.io.WOMergeFilter;
import barebones.world.data.WOSecretDoorBean;

public class WOSecretDoorFilter extends WOMergeFilter {
	public WOSecretDoorFilter() {
		this.addMergeRule(WOSecretDoorBean.class, "state", MergeMode.PRIORITY_OTHER_SOFT);
	}
}

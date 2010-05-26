package barebones.io;

import java.util.HashMap;

import utils.io.xml.MergeMode;

/**
 * This class should be subclassed to set default merge modes for all
 * properties of beans for which you want to specify merging policy.
 * @author dej
 *
 */
abstract public class WOMergeFilter {
	private MergeMode m_defaultMergeMode;
	
	// Merging only occurs when reading in saved data
	// and merging with existing in-memory data
	private HashMap<String,MergeMode> fieldMergeModeMap;

	protected WOMergeFilter() {
		m_defaultMergeMode = MergeMode.PRIORITY_THIS_HARD;
		fieldMergeModeMap = new HashMap<String,MergeMode>();
	}
	
	protected final void setDefaultMergeMode(MergeMode mode) {
		m_defaultMergeMode = mode;
	}
	
	protected final void addMergeRule(Class<?> beanClass, String propName, MergeMode mode) {
		fieldMergeModeMap.put(beanClass.getName()+"."+propName, mode);
	}
	
	public final MergeMode findMergeMode(Class<?> beanClass, String propName) {
		String fullName = beanClass.getName()+"."+propName;
		if (fieldMergeModeMap.containsKey(fullName)) {
			return fieldMergeModeMap.get(fullName);
		}
		else {
			return m_defaultMergeMode;
		}
	}
}

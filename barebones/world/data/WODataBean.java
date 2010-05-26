package barebones.world.data;

import java.util.HashMap;
import java.util.HashSet;

import utils.io.BeanUtils;
import utils.io.xml.MergeMode;

import barebones.io.WOMergeFilter;

abstract public class WODataBean
{
	protected static HashMap<Class<?>,WOMergeFilter> sm_beanFilterMap;
	protected static DefaultFilter sm_defaultFilter;
	
	static class DefaultFilter extends WOMergeFilter {		
	}
	
	static {
		sm_beanFilterMap = new HashMap<Class<?>,WOMergeFilter>();
		sm_defaultFilter = new DefaultFilter();
	}
	
	// Register data bean filters
	public static void registerMergeFilter(Class<?> beanClass, WOMergeFilter filter) {
		sm_beanFilterMap.put(beanClass, filter);
	}
	
	public static WOMergeFilter getFilter(Class<?> beanClass) {
		if (sm_beanFilterMap.containsKey(beanClass)) {
			return sm_beanFilterMap.get(beanClass);
		}
		else {
			return sm_defaultFilter;
		}
	}
	
	public String id;
	public String detailId;
	public HashSet<String> containedItemSet;
		
	public String getid() {
		return id;
	}
	
	public void setid(String id) {
		this.id = id;
	}

	public void setdetailId(String id) {
		detailId = id;
	}
	
	public String getdetailId() {
		return detailId;
	}

	public void setcontainedItemSet(HashSet<String> set) {
		containedItemSet = set;
	}
	
	public HashSet<String> getcontainedItemSet() {
		return containedItemSet;
	}

	public void merge(WODataBean other) {
		merge(other, null);
	}
		
	public void merge(WODataBean other, WOMergeFilter forceFilter) {
		if (null != other) {
			if (this.getClass().equals(other.getClass())) {
				
				// If filter is non-null, use it overriding
				// filter specified for this class
			// Do lots of complicated merging stuff
			/*
	PRIORITY_THIS_SOFT, // use "this" object's prop if they are not null
	PRIORITY_OTHER_SOFT, // use "other" object's prop if they are not null
	PRIORITY_THIS_HARD,  // use "this" object's prop even if they are null
	PRIORITY_OTHER_HARD, // use "other" object's prop even if they are null
	PRIORITY_FILLED, // use prop of whichever object is non-null (if any)
	PRIORITY_EMPTY // use prop of whichever object is null (if any)
			 * 
			 */
				WOMergeFilter filter;
				if (null == forceFilter) {
					filter = WODataBean.getFilter(this.getClass());					
				}
				else {
					filter = forceFilter;
				}
				HashMap<String,Object> thisProps = new HashMap<String,Object>();
				HashMap<String,Class<?>> thisTypes = new HashMap<String,Class<?>>();
				HashMap<String,Object> otherProps = new HashMap<String,Object>();
				HashMap<String,Class<?>> otherTypes = new HashMap<String,Class<?>>();
				BeanUtils.getProps(this, thisProps, thisTypes);
				BeanUtils.getProps(other, otherProps, otherTypes);
				for(String propName : thisProps.keySet()) {
					Object thisVal = thisProps.get(propName);
					Object otherVal = otherProps.get(propName);

					MergeMode mode = filter.findMergeMode(this.getClass(), propName);
					switch(mode) {
					case PRIORITY_THIS_HARD:
						// Do nothing - keep all of "this" properties as-is
						break;
					case PRIORITY_OTHER_HARD:
						BeanUtils.setPropertyValue(this, propName, otherProps.get(propName));
						break;
					case PRIORITY_THIS_SOFT:
						if (null == thisVal)
							BeanUtils.setPropertyValue(this, propName, otherVal);
						break;
					case PRIORITY_OTHER_SOFT:
						if (null != otherVal)
							BeanUtils.setPropertyValue(this, propName, otherVal);
						break;
					case PRIORITY_FILLED:
						if (null == thisVal)
							BeanUtils.setPropertyValue(this, propName, otherVal);
						break;
					case PRIORITY_EMPTY:
						if (null == otherVal)
							BeanUtils.setPropertyValue(this, propName, otherVal);
						break;							
					}
				}
			}
		}
	}
}

package barebones.io.dal;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import barebones.engine.GameEngineAccessor;
import barebones.world.data.WORootItemBean;
import barebones.world.object.Item;

public class ItemLoader extends GameObjectMarshaller implements FilenameFilter {
	public static boolean DEBUG;
	
	/*
	class ItemData {
		String type = null;
		String desc = null;
		String descNa = null;
		String longDesc = null;
		int getpts;
		int usepts;
		HashSet<String> itemlist = null;
		String wt = null;
		int dmg;
		int acbonus;
		String detailId = null;
	};
*/		
	// Caching
	protected HashMap<String,Item> m_itemCache;
	
	protected WORootItemBean m_lastItemData;
	
	//protected ItemData m_lastItemData;
	
	public ItemLoader()
	{
		super("");
					
		m_itemCache = new HashMap<String,Item>();
		ItemLoader.DEBUG = true;		
	}
	
	public void reset()
	{
		m_itemCache.clear();
		m_lastItemData = null;
	}

	public boolean accept(File dir, String name) {
		int dotIndex = name.indexOf(".");
		String suffix = name.substring(dotIndex);
		if (suffix.equals(FILE_EXT)) {
			return true;
		}
		return false;
	}
	
	public HashSet<Item> loadAll(GameEngineAccessor engineRef) {
		HashSet<Item> l_allItemSet = new HashSet<Item>();
		
		GameObjectMarshaller.forceRegister(WORootItemBean.class);
		String beanPath = GameObjectMarshaller.getPath(WORootItemBean.class);
		
		String path = engineRef.getGameDataRootPath()+beanPath;
		File f = new File(path);
		
		if (f.exists() && f.isDirectory()) {
			File[] allItems = f.listFiles(this);			
			if (allItems.length > m_itemCache.size()) {
				for(int i = 0; i < allItems.length; i++) {
					File itemFile = allItems[i];
					String name = itemFile.getName();
					int dotIndex = name.indexOf(".");
					String itemId = name.substring(0, dotIndex);
					if (!m_itemCache.containsKey(itemId)) {					
						Item item = load(engineRef, itemId);
						m_itemCache.put(itemId, item);
					}
				}
			}
		}
		
		Collection<Item> vals = m_itemCache.values();
		
		l_allItemSet.addAll(vals);
		
		return l_allItemSet;
	}

	public Item load(GameEngineAccessor engineRef, String itemId)
	{
		if (!m_itemCache.containsKey(itemId)) {
			
			setRootPath(engineRef.getGameDataRootPath());
			
			m_lastItemData = (WORootItemBean)load(WORootItemBean.class, itemId, false);
			if (null != m_lastItemData) {
				Item item = new Item(engineRef, m_lastItemData.itemData);
				m_itemCache.put(itemId, item);
			}			
			else {
				if (DEBUG)
					System.err.println("ItemLoader.load: Cannot find "+itemId+". But that's ok, returning null");
			}
		}
		
		return m_itemCache.get(itemId);
	}
	
}

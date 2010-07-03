package barebones.io.dal;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import utils.IntegerRange;

import barebones.engine.GameEngineAccessor;
import barebones.world.data.WORegionBean;
import barebones.world.data.WORegionMapBean;
import barebones.world.data.WORoomBean;
import barebones.world.data.WORoomBlockBean;
import barebones.world.data.WORoomFeatureBean;
import barebones.world.data.WORoomFeatureMapBean;
import barebones.world.feature.RoomFeature;
import barebones.world.filter.WORoomBlockFilter;
import barebones.world.object.Room;

public class RoomLoader extends GameObjectMarshaller {
	protected static final String REGION_PATH = "regions/";
	protected static final String ROOM2REG_ID = "room2reg";
				
	protected GameEngineAccessor m_engineRef;
				
	protected HashMap<String,Room> m_lastRegionMap;
	
	protected HashMap<String,WORoomBean> m_generatedRoomBeans;
	
	protected HashMap<String,Vector<RoomFeature>> m_lastRoomFeatureMap;
	
	protected WORegionMapBean m_roomToRegionMap;
	
	
	public RoomLoader(GameEngineAccessor engineRef)
	{
		super(engineRef);
		m_engineRef = engineRef;
				
		m_lastRegionMap = new HashMap<String,Room>();
		m_lastRoomFeatureMap = new HashMap<String,Vector<RoomFeature>>();		
		m_generatedRoomBeans = new HashMap<String,WORoomBean>();
	}
	
	public void reset()
	{
		m_lastRegionMap.clear();
		m_lastRoomFeatureMap.clear();
		m_roomToRegionMap = null;
	}

	public void loadRoomToRegionMap()
	{
		m_roomToRegionMap = (WORegionMapBean)load(WORegionMapBean.class, ROOM2REG_ID);		
	}
	
	public String getRegionFor(int roomId)
	{
		// First try direct access
		if (m_roomToRegionMap.roomToRegionMap.containsKey(String.valueOf(roomId))) {
			return m_roomToRegionMap.roomToRegionMap.get(String.valueOf(roomId));
		}
		else {
			// If that fails, try ranges
			Vector<IntegerRange> rangeList = m_roomToRegionMap.rangeList;
			if (null != rangeList) {
				for(IntegerRange range : rangeList) {
					if (range.isInRange(roomId)) {
						if (null != m_roomToRegionMap.rangeToRegionMap) {
							if (m_roomToRegionMap.rangeToRegionMap.containsKey(range.getid())) {
								return m_roomToRegionMap.rangeToRegionMap.get(range.getid());
							}
						}
					}
				}
			}
			
			return null;
		}
	}
	
	public HashMap<String,Room> loadRegion(String regionName)
	{		
		// Clear last data
		m_lastRegionMap.clear();
		
		WORegionBean region = (WORegionBean)load(WORegionBean.class, regionName);
		
		// First load room-blocks
		Vector<WORoomBlockBean> roomBlocks = region.getroomBlocks();
		if (null != roomBlocks) {
			HashMap<String,WORoomBean> roomMap;
			for(WORoomBlockBean block : roomBlocks) {
				roomMap = RoomBlockUtil.generateRoomsFromBlock(block);
				m_generatedRoomBeans.putAll(roomMap);
			}
		}
		
		WORoomBlockFilter blockFilter = new WORoomBlockFilter();
		
		// If any room already loaded by room-block
		for(Map.Entry<String, WORoomBean> entry : region.rooms.entrySet()) {
			WORoomBean beanToUse;

			// Check if room was loaded by room-block
			if (m_generatedRoomBeans.containsKey(entry.getKey())) {
				// Override with detailId and containedItemSet
				WORoomBean existing = m_generatedRoomBeans.get(entry.getKey());
				WORoomBean current = entry.getValue();
				
				existing.merge(current, blockFilter);
				beanToUse = existing;
			}
			else {			
				beanToUse = entry.getValue();
			}
			
			Room r = new Room(m_engineRef, (WORoomBean)beanToUse.clone());
			m_lastRegionMap.put(entry.getKey(), r);
		}
		
		// Add generated rooms that were NOT in the regular list of rooms
		// (i.e. those not merged)
		for(Map.Entry<String, WORoomBean> entry : m_generatedRoomBeans.entrySet()) {
			if (!m_lastRegionMap.containsKey(entry.getKey())) {
				WORoomBean beanToUse = entry.getValue();
				
				Room r = new Room(m_engineRef, (WORoomBean)beanToUse.clone());
				m_lastRegionMap.put(entry.getKey(), r);
			}
		}
		
		// Clear so we don't hold too much crap in memory
		m_generatedRoomBeans.clear();

		HashMap<String,Room> mapCopy = new HashMap<String,Room>(m_lastRegionMap);
			
		return mapCopy;
	}
	
	public HashMap<String,Vector<RoomFeature>> loadFeatures(String regionName)
	{		
		m_lastRoomFeatureMap.clear();
		
		WORoomFeatureMapBean featureMap = (WORoomFeatureMapBean)load(WORoomFeatureMapBean.class, regionName, false);
		if (null != featureMap) {
			for(Map.Entry<String, Vector<WORoomFeatureBean>> entry : featureMap.roomFeatureMap.entrySet()) {
				String key = entry.getKey();
				Vector<RoomFeature> specialVec = new Vector<RoomFeature>();
				Vector<WORoomFeatureBean> specialDataVec = entry.getValue();
				for(WORoomFeatureBean special : specialDataVec) {
					specialVec.add(special.newInstance(m_engineRef));
				}
				m_lastRoomFeatureMap.put(key, specialVec);
			}
		}
		
		HashMap<String,Vector<RoomFeature>> mapCopy = new HashMap<String,Vector<RoomFeature>>(m_lastRoomFeatureMap);
		
		return mapCopy;
	}
}

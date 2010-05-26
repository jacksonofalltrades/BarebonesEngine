package barebones.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import barebones.io.dal.RoomLoader;
import barebones.world.feature.RoomFeature;
import barebones.world.object.Room;
import barebones.world.object.WorldObject;

public class RoomManager extends ManagerImpl 
{		
	protected RoomLoader m_loader;
	protected HashMap<String,HashMap<String,Room>> m_regionMap;
	
	protected Integer getPlayerRoomId()
	{
		Player player = m_engineRef.getPlayer();
		int rmid = player.getCurrentRoomId();
		return rmid;
	}
	
	public Room getPlayerRoom()
	{
		Integer playerRoom = getPlayerRoomId();
		String region = getPlayerRegion();
		
		loadRegionAsNeeded(region);

		HashMap<String,Room> l_roomMap = m_regionMap.get(region);
		Room l_room = l_roomMap.get(String.valueOf(playerRoom));

		return l_room;
	}
	
	protected String getPlayerRegion()
	{
		Player player = m_engineRef.getPlayer();
		int rmid = player.getCurrentRoomId();
		String region = getRegionFor(rmid);

		return region;
	}
	
	public RoomManager(GameEngineAccessor engineRef)
	{
		super(engineRef);
				
		m_loader = new RoomLoader(engineRef);
		m_regionMap = new HashMap<String,HashMap<String,Room>>();
	}
	
	public void reset()
	{
		m_loader.reset();
		m_regionMap.clear();
	}

	protected Vector<WorldObject> getCanFilter(String commandId,
			String targetId, int roomId) {
		Vector<WorldObject> l_rooms = new Vector<WorldObject>();
		
		Room l_room = getPlayerRoom();
		
		l_rooms.add(l_room);
		
		return l_rooms;
	}

	protected Vector<WorldObject> getCanProcess(String commandId,
			String targetId, int roomId) {
		Vector<WorldObject> l_rooms = new Vector<WorldObject>();
		
		Room l_room = getPlayerRoom();
		
		l_rooms.add(l_room);
		
		return l_rooms;
	}
	
	protected void loadRegionAsNeeded(String region) {
		if (!m_regionMap.containsKey(region)) {	
			HashMap<String,Room> l_regionRooms = m_loader.loadRegion(region);
			HashMap<String,Vector<RoomFeature>> l_specials = m_loader.loadFeatures(region);
			Set<String> l_keys = l_regionRooms.keySet();
			Iterator<String> l_iter = l_keys.iterator();
			while(l_iter.hasNext()) {
				String l_key = (String)l_iter.next();
				Vector<RoomFeature> specialVec = l_specials.get(l_key);
				Room l_room = l_regionRooms.get(l_key);
				if (null != specialVec) {
					l_room.addFeatures(specialVec);
				}
				l_room.applyPlayerState();
			}
			
			m_regionMap.put(region, l_regionRooms);		
		}
	}


	public void load() {
		// First load room2region map
		m_loader.loadRoomToRegionMap();
		
		String region = getPlayerRegion();
		
		loadRegionAsNeeded(region);
	}

	public String getRegionFor(int roomId)
	{
		return m_loader.getRegionFor(roomId);
	}
}

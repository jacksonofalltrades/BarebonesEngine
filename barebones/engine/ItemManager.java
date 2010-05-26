package barebones.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import barebones.io.dal.ItemLoader;
import barebones.world.object.Item;
import barebones.world.object.WorldObject;

public class ItemManager extends ManagerImpl 
{
	protected ItemLoader m_loader;

	public String search(HashSet<String> itemList, String itemId)
	{
		Iterator<String> iter = itemList.iterator();
		while(iter.hasNext()) {
			String id = (String)iter.next();
			Item item = m_loader.load(m_engineRef, id);
			String container = item.findItem(itemId);
			if (!"".equals(container)) {
				return container;
			}
		}
		return "";
	}
	
	public ItemManager(GameEngineAccessor engineRef) {
		super(engineRef);
		
		m_loader = new ItemLoader();
	}
	
	public void reset()
	{
		m_loader.reset();
	}
	
	public Item getItem(String id) {
		Item item = m_loader.load(m_engineRef, id);
		item.applyPlayerState();
		return item;
	}

	/**
	 * Filter only if the item is:
	 * 1) In the Player's inventory
	 * 2) In the Player's current room
	 * 3) In an NPC's inventory, who is in the room
	 * 4) Within some other item that is in the Player's current room
	 * 
	 * FOR NOW ONLY SUPPORT (1) and (2)
	 */
	protected Vector<WorldObject> getCanFilter(String commandId,
			String targetId, int roomId) {
		Vector<WorldObject> l_vec = new Vector<WorldObject>();
		
		// For now, ignore command and targetId and just look through all items in game
		HashSet<Item> l_items = m_loader.loadAll(m_engineRef);
		Iterator<Item> iter = l_items.iterator();
		while(iter.hasNext()) {
			Item item = (Item)iter.next();

			/*
			Player player = m_engineRef.getPlayer();	
			String container = player.findItem(item.id());
			if ("".equals(container)) {
				Room room = m_engineRef.getPlayerRoom();
				container = room.findItem(item.id());
			}
			*/
			//if (!"".equals(container)) {
				l_vec.add(item);
			//}
		}
		
		return l_vec;
	}

	protected Vector<WorldObject> getCanProcess(String commandId,
			String targetId, int roomId) {
		Vector<WorldObject> l_vec = new Vector<WorldObject>();
		
		// For now, ignore command and targetId and just look through all items in game
		HashSet<Item> l_items = m_loader.loadAll(m_engineRef);
		Iterator<Item> iter = l_items.iterator();
		while(iter.hasNext()) {
			Item item = (Item)iter.next();

			/*
			Player player = m_engineRef.getPlayer();	
			String container = player.findItem(item.id());
			if ("".equals(container)) {
				Room room = m_engineRef.getPlayerRoom();
				container = room.findItem(item.id());
			}
			*/
			//if (!"".equals(container)) {
				l_vec.add(item);
			//}
		}
		
		return l_vec;
	}

	public void load() {
		m_loader.loadAll(m_engineRef);
	}
}

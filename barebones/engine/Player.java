package barebones.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import barebones.event.GetInventoryCommand;
import barebones.event.TickEvent;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.User;
import barebones.io.UserResponseImpl;
import barebones.io.dal.ObjectDetailLoader;
import barebones.world.data.PlayerStateBean;
import barebones.world.data.PlayerStateData;
import barebones.world.data.WODataBean;
import barebones.world.object.Item;
import barebones.world.object.ObjectDetail;
import barebones.world.object.WorldObject;

public class Player extends ManagerImpl {
	public static final String ID = "PLAYER";
	
	protected static final String INV_HEADER_TXT = "player.invheadertxt";
	
	protected PlayerStateBean pdata;
			
	protected User m_userRef;
	
	protected boolean m_isNewUser;
		
	protected ResponseContent getObjKeyContent(String key)
	{
		ObjectDetail detail = ObjectDetailLoader.instance().load(m_engineRef, this.getClass().getSimpleName());
		ResponseContent rc = detail.getObjKeyContent(key);
		if (null != rc) {
			return rc;
		}
		else {
			return null;
		}
	}
	
	public Player(GameEngineAccessor engineRef, User userRef)
	{
		super(engineRef);
		
		m_userRef = userRef;

		// If user exists, do nothing, but if not, create new user
		m_isNewUser = m_userRef.createNew(engineRef);
				
		pdata = new PlayerStateBean();
		pdata.data = new PlayerStateData();
		pdata.id = userRef.id();
		pdata.data.currRoomId = -1;
		pdata.data.currTicks = 0;
		pdata.data.currScore = 0;
		pdata.data.inventory = new HashSet<String>();
		pdata.worldDeltas = new HashMap<String,WODataBean>();
	}
	
	public boolean isNewUser()
	{
		return m_isNewUser;
	}
	
	public void reset()
	{
		// Does nothing for now
	}
	
	/**
	 * Try to convert a piece of arbitrary text passed from
	 * the client into a valid item id that is in the player's inventory.
	 * @param itemText
	 * @return item id
	 */
	public String parseItem(String itemText) {
		String realItemId;
		// TODO: Implement this - for now just use what's passed in
		realItemId = itemText;
		
		// Check against inventory
		if (pdata.data.inventory.contains(realItemId)) {
			return realItemId;	
		}
		else {
			return null;
		}
	}
	
	public String findItem(String itemId) {
		if (pdata.data.inventory.contains(itemId))
			return Player.ID;
		return m_engineRef.search(pdata.data.inventory, itemId);
	}
	
	public void addItem(Item item) {
		pdata.data.inventory.add(item.id());
	}
	
	public void removeItem(Item item) {
		pdata.data.inventory.remove(item.id());
	}
	
	public HashSet<String> getInventory() {
		HashSet<String> l_copy = new HashSet<String>(pdata.data.inventory);
		return l_copy;
	}
		
	/* Not used by this class */
	protected Vector<WorldObject> getCanFilter(String commandId, String targetId, int roomId)
	{
		return null;
	}

	/* Not used by this class */
	protected Vector<WorldObject> getCanProcess(String commandId, String targetId, int roomId)
	{
		return null;
	}

	public void load()
	{
		load("");
	}
	
	/**
	 * Attempt to load player data for user ID retrieved from GameEngine
	 * If data file cannot be found, treat as a new player
	 */
	public boolean load(String savedGameId)
	{
		pdata = m_userRef.load(m_engineRef, savedGameId);
		if (null == pdata) {
			return false;
		}
		
		ObjectDetailLoader.instance().load(m_engineRef, this.getClass().getSimpleName());
		
		return true;
	}
	
	public void save() {
		save("");
	}
	
	public void save(String savedGameId)
	{
		m_userRef.save(m_engineRef, pdata, savedGameId);
	}
		
	// Override ManagerImpl - might not have to, we'll see
	public void filter(UserCommand cmd)
	{
		
	}
	
	// Override ManagerImpl - might not have to, we'll see
	public void process(UserCommand cmd)
	{
		if (GetInventoryCommand.class.isInstance(cmd)) {
			ResponseContent invHeaderTxt = this.getObjKeyContent(INV_HEADER_TXT);
			
			cmd.addResponse(new UserResponseImpl(invHeaderTxt));

			Iterator<String> iter = pdata.data.inventory.iterator();
			while(iter.hasNext()) {
				String itemid = (String)iter.next();
				
				Item item = m_engineRef.getItem(itemid);
				ResponseContent itemDesc = item.getDesc();
				cmd.addResponse(new UserResponseImpl(itemDesc));
			}
		}
		else if (TickEvent.class.isInstance(cmd)) {
			WorldClock clock = m_engineRef.getClock();
			long ticks = clock.getCurrentTicks();
			this.updateCurrentTicks(ticks);
		}
	}
	
	public void updatePlayerState(HashMap<String,WODataBean> deltas) {
		this.pdata.worldDeltas.putAll(deltas);
	}
	
	public WODataBean getWOPlayerStateById(String id) {
		if ((null != this.pdata) && (null != this.pdata.worldDeltas)) {
			if (this.pdata.worldDeltas.containsKey(id)) {
				return this.pdata.worldDeltas.get(id);
			}
		}
		return null;
	}
	
	public void setCurrRoomId(int rmid)
	{
		pdata.data.currRoomId = rmid;
	}
		
	public void addToScore(int score)
	{
		pdata.data.currScore += score;
	}
	
	public Integer getCurrentRoomId()
	{
		return pdata.data.currRoomId;
	}
	
	public void updateCurrentTicks(long ticks) {
		pdata.data.currTicks = ticks;
	}
	
	public long getCurrentTicks()
	{
		return pdata.data.currTicks;
	}
}

package barebones.world.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import barebones.engine.GameEngineAccessor;
import barebones.engine.Player;
import barebones.event.ExamineCommand;
import barebones.event.MoveCommand;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.UserResponseImpl;
import barebones.world.data.WODataBean;
import barebones.world.data.WORoomBean;
import barebones.world.feature.RoomFeature;

public class Room extends WorldObjectImpl implements WorldObject {
	
	public static final int INVALID_DIRECTION = -77;
	public static final int NO_PASSAGE = -1;
	public static final String ROOM_ID_PREFIX = "room";
	public static final String PREP = "to the ";
	public static final String NORTH = "N";
	public static final String EAST = "E";
	public static final String WEST = "W";
	public static final String SOUTH = "S";
	public static final String UP = "U";
	public static final String DOWN = "D";

	// Object detail keys
	protected static final String INVALID_DIR_TXT = "room.invaliddirtxt";
	protected static final String NOT_VALID_DIR_MSG = "room.notvaliddirmsg";
	protected static final String ONE_PASSAGE_MSG	= "room.onepsgmsg";
	protected static final String MULTI_PASSAGE_MSG = "room.multipsgmsg";
	protected static final String MOVE_TXT = "room.movetxt";
	
	
	protected static HashMap<String,String> sm_dirTextMap;
	
	static {
		sm_dirTextMap = new HashMap<String,String>();
		sm_dirTextMap.put(NORTH, "North");
		sm_dirTextMap.put(EAST, "East");
		sm_dirTextMap.put(WEST, "West");
		sm_dirTextMap.put(SOUTH, "South");
		sm_dirTextMap.put(UP, "Above");
		sm_dirTextMap.put(DOWN, "Below");
	}
	
	public static boolean isValidDirection(String dir)
	{
		return sm_dirTextMap.containsKey(dir);
	}
	
	public static String getDirText(String normTarget)
	{
		if (UP.equals(normTarget) || DOWN.equals(normTarget)) {
			return sm_dirTextMap.get(normTarget);
		}
		else {
			return PREP+sm_dirTextMap.get(normTarget);			
		}
	}
		
	protected WORoomBean rdata;
	/*
	protected int id;
	protected String defaultDesc;
	protected int nid;
	protected int eid;
	protected int wid;
	protected int sid;
	protected int uid;
	protected int did;
	*/
	
	protected HashSet<String> m_presentNPCIdSet;
	
	protected Vector<RoomFeature> m_featureVec;
	
	public static String getStringId(int id)
	{
		return ROOM_ID_PREFIX+id;
	}
	
	protected String getAvailablePassagesText(UserCommand cmd)
	{
		StringBuffer p = new StringBuffer();
				
		if (cmd.isEnabledFor(NORTH) && (NO_PASSAGE != rdata.nid)) {
			p.append(getDirText(NORTH));
		}
		if (cmd.isEnabledFor(EAST) && (NO_PASSAGE != rdata.eid)) {
			if (p.length() > 0) {
				p.append(", ");
			}
			p.append(getDirText(EAST));
		}
		if (cmd.isEnabledFor(WEST) && (NO_PASSAGE != rdata.wid)) {
			if (p.length() > 0) {
				p.append(", ");
			}
			p.append(getDirText(WEST));
		}
		if (cmd.isEnabledFor(SOUTH) && (NO_PASSAGE != rdata.sid)) {
			if (p.length() > 0) {
				p.append(", ");
			}
			p.append(getDirText(SOUTH));			
		}
		if (cmd.isEnabledFor(UP) && (NO_PASSAGE != rdata.uid)) {
			if (p.length() > 0) {
				p.append(", ");
			}
			p.append(getDirText(UP));
		}
		if (cmd.isEnabledFor(DOWN) && (NO_PASSAGE != rdata.did)) {
			if (p.length() > 0) {
				p.append(", ");
			}
			p.append(getDirText(DOWN));
		}
		
		p.append(".");
		
		return p.toString();
	}
	
	protected int getPassageRoomIdForTarget(String target)
	{
		if (NORTH.equals(target)) {
			return rdata.nid;
		}
		else if (EAST.equals(target)) {
			return rdata.eid;
		}
		else if (WEST.equals(target)) {
			return rdata.wid;
		}
		else if (SOUTH.equals(target)) {
			return rdata.sid;
		}
		else if (UP.equals(target)) {
			return rdata.uid;
		}
		else if (DOWN.equals(target)) {
			return rdata.did;
		}
		else {
			return INVALID_DIRECTION;
		}
	}
	
	public Room(GameEngineAccessor engineRef, WORoomBean rdata)
	{
		super(engineRef, rdata);
		this.rdata = rdata;
		m_featureVec = new Vector<RoomFeature>();
		
		m_presentNPCIdSet = new HashSet<String>();		
	}
	
	public void addNPC(String npcId) {
		m_presentNPCIdSet.add(npcId);
	}
	
	public void removeNPC(String npcId) {
		m_presentNPCIdSet.remove(npcId);
	}
	
	public boolean findNPC(String npcId) {
		return m_presentNPCIdSet.contains(npcId);
	}
	
	/*
	public Npc getNpc(String npcId) {
		if (findNPC(npcId)) {
			return NpcManager.instance().getNpc(npcId);
		}
		else {
			return null;
		}
	}
	*/
	
	public String findItem(String itemId) {
		String inHere = super.findItem(itemId);
		if (null != inHere) 
			return inHere;
		
		// Search any NPCs in here
		/*
		Iterator iter = m_presentNPCIdSet.iterator();
		while(iter.hasNext()) {
			String npcId = (String)iter.next();
			Npc npc = getNpc(npcId);
			String container = npc.findItem(itemId);
			if (!"".equals(container)) {
			   return container;
			}
		}
		*/
		
		return "";
	}
		
	public void addFeatures(Vector<RoomFeature> rs)
	{
		m_featureVec.addAll(rs);
	}

	public void filter(UserCommand cmd) {
		// Order filtering of specials
		Collections.sort(m_featureVec, this);	
		
		// First filter on specials, then on room
		for(int i = 0; i < m_featureVec.size(); i++) {
			RoomFeature rs = m_featureVec.get(i);
			rs.filter(cmd);
		}
		
		// Filter first by deciding what commands we can handle:
		// Examine
		// Move
		if (ExamineCommand.class.isInstance(cmd)) {
			// Do nothing, bare room cannot disable Examine
		}
		else if (MoveCommand.class.isInstance(cmd)) {
			// After normalizing, target MUST be constant defined here
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			int passageRoomId = getPassageRoomIdForTarget(normTarget);
			String dirText = getDirText(normTarget);
			if (INVALID_DIRECTION == passageRoomId) {
				cmd.disableFor(origTarget);
				
				ResponseContent c = this.getObjKeyContent(NOT_VALID_DIR_MSG);
				c.formatText(origTarget);
				cmd.addResponse(new UserResponseImpl(c));
			}
			else if (NO_PASSAGE == passageRoomId) {
				cmd.disableFor(origTarget);
				
				ResponseContent c = this.getObjKeyContent(INVALID_DIR_TXT);
				c.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(c));
			}
			else {
				// Do nothing, this direction is fine
			}
		}
	}

	public void process(UserCommand cmd) {
		
		// Process first on room, then process on specials
		if (ExamineCommand.class.isInstance(cmd)) {
			// Ignore if target is anything other than "" or "room"
			String target = cmd.getTarget();
			if ((target.length() > 0) && !target.equals("room")) {
				// Do nothing
			}
			else {
				ResponseContent desc = this.getCmdKeyContent(cmd.id());
				if (null != desc) {
					cmd.addResponse(new UserResponseImpl(desc));
				}
				else {
					// Give basic room description
					cmd.addResponse(new UserResponseImpl(rdata.defaultDesc));
				}
				
				// There are passages to the %s, %s...
				// There is a passage to the %s
				ResponseContent msg;
				String availText = getAvailablePassagesText(cmd);
				int index = availText.indexOf(",");
				if (-1 == index) {
					msg = this.getObjKeyContent(ONE_PASSAGE_MSG);
				}
				else {
					msg = this.getObjKeyContent(MULTI_PASSAGE_MSG);
				}
				msg.formatText(availText);
				cmd.addResponse(new UserResponseImpl(msg));
			}
		}
		else if (MoveCommand.class.isInstance(cmd)) {
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			if (cmd.isEnabledFor(origTarget)) {
				int newRoomId = getPassageRoomIdForTarget(normTarget);
				
				Player player = m_engineRef.getPlayer();
				player.setCurrRoomId(newRoomId);

				cmd.setSuccess();
				
				// Replace with custom text if there is any
				ResponseContent moveTxt = this.getCmdKeyContent(cmd.id());
				if (null == moveTxt) {
					moveTxt = this.getObjKeyContent(MOVE_TXT);
				}
				String dirText = getDirText(normTarget);
				moveTxt.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(moveTxt));
			}
		}
		else {
			ResponseContent cmdText = this.getCmdKeyContent(cmd.id());
			if (null != cmdText) {
				cmd.addResponse(new UserResponseImpl(cmdText));
			}
		}
		
		// Specials next
		for(int i = 0; i < m_featureVec.size(); i++) {
			RoomFeature rs = m_featureVec.get(i);
			rs.process(cmd);
		}
		
	}

	public void applyPlayerState() {
		Player p = m_engineRef.getPlayer();
		WODataBean bean = p.getWOPlayerStateById(this.id());
		if (null != bean) {
			this.data.merge(bean);
		}
		
		// Call on specials
		for(RoomFeature rs: m_featureVec) {
			rs.applyPlayerState();
		}
	}
}

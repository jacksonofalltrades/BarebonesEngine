package barebones.world.object;

import java.util.Collections;
import java.util.Vector;

import barebones.engine.GameEngineAccessor;
import barebones.engine.Player;
import barebones.event.DropCommand;
import barebones.event.ExamineCommand;
import barebones.event.GiveCommand;
import barebones.event.TakeCommand;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.UserResponseImpl;
import barebones.world.data.WOItemBean;
import barebones.world.feature.ItemFeature;

public class Item extends WorldObjectImpl {

	protected static final String STILL_EQUIPPED_MSG = "item.stillequipped";
	protected static final String NOT_OWNED_MSG = "item.notowned";
	protected static final String RECIPIENT_NOT_PRESENT_MSG = "item.recipnotpresent";
	protected static final String ALREADY_HAVE_ITEM_MSG = "item.alreadyhave";
	protected static final String CANNOT_FIND_ITEM_MSG = "item.cannotfind";
	protected static final String DROP_MSG = "item.drop";
	protected static final String GIVE_MSG = "item.give";
	protected static final String TAKE_MSG = "item.take";
	protected static final String ROOM_ITEM_DESC_MSG = "item.roomdesc";
		
	protected WOItemBean idata;
	
	protected Vector<ItemFeature> m_featureVec;	
	
	public Item(GameEngineAccessor engineRef, WOItemBean data) {
		super(engineRef, data);
		this.idata = data;
		
		m_featureVec = new Vector<ItemFeature>();
	}

	public void addFeatures(Vector<ItemFeature> is)
	{
		m_featureVec.addAll(is);
	}
	
	public ResponseContent getDesc()
	{
		return new ResponseContent(idata.baseDesc);
	}
		
	public void filter(UserCommand cmd) {
		// Order filtering of specials
		Collections.sort(m_featureVec, this);	

		// First filter on specials, then on item
		for(int i = 0; i < m_featureVec.size(); i++) {
			ItemFeature is = m_featureVec.get(i);
			is.filter(cmd);
		}

		String normTarget = m_engineRef.parseTarget(cmd);
		if (!normTarget.equals(data.id)) {
			// Not applicable to this item
			return;
		}
		
		// Filter on equipped, even though base item does not handle equip
		if (DropCommand.class.isInstance(cmd) || GiveCommand.class.isInstance(cmd)) {
			Player p = m_engineRef.getPlayer();
			if (idata.equipped) {
				String target = cmd.getTarget();
				
				cmd.disableFor(target);
				
				ResponseContent c = this.getObjKeyContent(STILL_EQUIPPED_MSG);
				c.formatText(target);
				cmd.addResponse(new UserResponseImpl(c));		
			}
			else if (!Player.ID.equals(p.findItem(data.id))) {
				String target = cmd.getTarget();
				cmd.disableFor(target);
				
				ResponseContent c = this.getObjKeyContent(NOT_OWNED_MSG);
				c.formatText(target);
				cmd.addResponse(new UserResponseImpl(c));
			}
			else if (GiveCommand.class.isInstance(cmd)) {
				GiveCommand gcmd = (GiveCommand)cmd;
				String recipient = gcmd.getRecipient();
				String normRecip = m_engineRef.parseNPC(recipient);
				Room currRoom = m_engineRef.getPlayerRoom();
				if (!currRoom.findNPC(normRecip)) {
					cmd.disableFor(recipient);
					
					ResponseContent c = this.getObjKeyContent(RECIPIENT_NOT_PRESENT_MSG);
					c.formatText(recipient);
					cmd.addResponse(new UserResponseImpl(c));
				}
			}
		}
		else if (TakeCommand.class.isInstance(cmd)) {
			// Does player already have it?
			// Is item in the room?
			// Is item with npc?
			String target = cmd.getTarget();
			
			Player p = m_engineRef.getPlayer();
			if (Player.ID.equals(p.findItem(data.id))) {
				cmd.disableFor(target);
				
				ResponseContent c = this.getObjKeyContent(ALREADY_HAVE_ITEM_MSG);
				c.formatText(target);
				cmd.addResponse(new UserResponseImpl(c));
			}
			else {
				Room currRoom = m_engineRef.getPlayerRoom();
				
				String itemContainer = currRoom.findItem(data.id);			

				boolean isItemInRoom = currRoom.id().equals(itemContainer);
				boolean isItemWithNpc = (!"".equals(itemContainer));
				
				if (!isItemInRoom && !isItemWithNpc) {
					cmd.disableFor(target);
					
					ResponseContent c = this.getObjKeyContent(CANNOT_FIND_ITEM_MSG);
					c.formatText(target);
					cmd.addResponse(new UserResponseImpl(c));
				}
			}
		}
	}

	// Base item only handles "Examine", "Take", "Drop", "Give"
	public void process(UserCommand cmd) {

		String target = cmd.getTarget();
		if (target.isEmpty()) target = null;
		
		String normTarget = m_engineRef.parseTarget(cmd);
		
		// If there is a target for the command and it is not this item
		// do not handle
		if ((null != target) && !normTarget.equals(data.id)) {
			// Not applicable to this item
			return;
		}
		
		if (!cmd.isEnabledFor(cmd.getTarget())) {
			return;
		}
		
		if (ExamineCommand.class.isInstance(cmd)) {
			
			if (null == target) {
				// If this item is in the player's current room, give it's brief description
				Room currRoom = m_engineRef.getPlayerRoom();
				if (currRoom.id().equals(currRoom.findItem(data.id))) {
					ResponseContent c = this.getObjKeyContent(ROOM_ITEM_DESC_MSG);
					c.formatText(idata.baseDesc);					
					cmd.addResponse(new UserResponseImpl(c));
				}
			}
			else {
				ResponseContent desc = this.getCmdKeyContent(cmd.id());
				if (null != desc) {
					cmd.addResponse(new UserResponseImpl(desc));
				}
				else {
					// Give basic room description
					cmd.addResponse(new UserResponseImpl(idata.longDesc));
				}
			}
		}
		else if (DropCommand.class.isInstance(cmd)) {
			Player p = m_engineRef.getPlayer();
			if (Player.ID.equals(p.findItem(data.id))) {
				Room room = m_engineRef.getPlayerRoom();				
				room.addItem(this);
				p.removeItem(this);
				
				room.updateChangedState(cmd);
				
				cmd.setSuccess();
																
				ResponseContent c = this.getObjKeyContent(DROP_MSG);
				c.formatText(target);
				cmd.addResponse(new UserResponseImpl(c));
			}
		}
		else if (GiveCommand.class.isInstance(cmd)) {
			GiveCommand gcmd = (GiveCommand)cmd;
			String recipient = gcmd.getRecipient();
			//String normRecip = m_engineRef.parseNPC(recipient);
			//Player p = m_engineRef.getPlayer();
			//Room room = m_engineRef.getPlayerRoom();			
			/*
			Npc recip = room.getNpc(normRecip);
			recip.addItem(this);
			p.removeItem(this);
			 */
						
			ResponseContent c = this.getObjKeyContent(GIVE_MSG);
			c.formatText(target, recipient);
			cmd.addResponse(new UserResponseImpl(c));
		}
		else if (TakeCommand.class.isInstance(cmd)) {
			Room room = m_engineRef.getPlayerRoom();
			String container = room.findItem(data.id);
			if (room.id().equals(container)) {
				room.removeItem(data.id);
				
				room.updateChangedState(cmd);				
			}
			// Must be an Npc
			if (!"".equals(container)) {
				// Npc npc = room.getNpc(container);
				// npc.removeItem(m_objId);
			}
			
			Player p = m_engineRef.getPlayer();
			p.addItem(this);
			
			cmd.setSuccess();
						
			ResponseContent c = this.getObjKeyContent(TAKE_MSG);
			c.formatText(target);
			cmd.addResponse(new UserResponseImpl(c));
		}
		else {
			ResponseContent cmdText = this.getCmdKeyContent(cmd.id());
			if (null != cmdText) {
				cmd.addResponse(new UserResponseImpl(cmdText));
			}
		}
		
		// Specials next
		for(int i = 0; i < m_featureVec.size(); i++) {
			ItemFeature is = m_featureVec.get(i);
			is.process(cmd);
		}
	}

}

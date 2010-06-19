package barebones.event;

import java.util.HashMap;

import barebones.concept.DirectionConcept;
import barebones.concept.ItemConcept;

public class UnlockCommand extends UserCommandImpl 
{
	public static final String id = "UNLK";
	public static final String UNLOCK_DIR = "dir";
	public static final String UNLOCK_ITEM = "item";

	protected String m_itemId;
	
	@SuppressWarnings("unchecked")
	public static UnlockCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(UNLOCK_DIR, ArgumentType.DIRECTION), 
				arg(UNLOCK_ITEM, ArgumentType.ITEM));
		return new UnlockCommand(args.get(UNLOCK_DIR), args.get(UNLOCK_ITEM));
	}
	
	public UnlockCommand(String dir, String itemId) {
		super(TargetScope.CURRENT_ROOM);
		m_target = dir;
		m_itemId = itemId;
	}
	
	@SuppressWarnings("unchecked")
	public UnlockCommand(DirectionConcept dir, ItemConcept item) 
	{
		super(TargetScope.CURRENT_ROOM);
		addSlotConcepts(
				makeSlot(UNLOCK_DIR, dir),
				makeSlot(UNLOCK_ITEM, item)
				);
		m_target = dir.toString();
		m_itemId = item.toString();
	}
	
	public String getItemId() {
		return m_itemId;
	}

	public boolean causesTick() {
		return true;
	}

	public String id() {
		return id;
	}
	
	public String toString() {
		return "Unlock "+m_target+" with "+m_itemId;
	}
}

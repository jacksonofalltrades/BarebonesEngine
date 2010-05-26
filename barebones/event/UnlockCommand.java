package barebones.event;

import java.util.HashMap;

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
		m_target = dir;
		m_itemId = itemId;
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

package barebones.event;

import java.util.HashMap;

import barebones.concept.ItemConcept;

public class DropCommand extends UserCommandImpl {
	public static final String id = "DROP";
	public static final String DROP_ITEM = "item";
	
	@SuppressWarnings("unchecked")
	public static DropCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(DROP_ITEM, ArgumentType.ITEM));
		return new DropCommand(args.get(DROP_ITEM));
	}
	
	public DropCommand(String target) {
		m_target = target;
	}
	
	@SuppressWarnings("unchecked")
	public DropCommand(ItemConcept itemConcept)
	{
		addSlotConcepts(makeSlot(DROP_ITEM, itemConcept));
		m_target = itemConcept.toString();
	}
	
	public String toString() {
		return "Drop"+(null!=m_target?" "+m_target:"");
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}

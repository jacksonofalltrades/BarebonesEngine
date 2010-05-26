package barebones.event;

import java.util.HashMap;

import barebones.concept.ItemConcept;

public class TakeCommand extends UserCommandImpl 
{
	public static final String id = "TAKE";
	public static final String TAKE_ITEM = "item";
	
	@SuppressWarnings("unchecked")
	public static TakeCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(TAKE_ITEM, ArgumentType.ITEM));
		return new TakeCommand(args.get(TAKE_ITEM));
	}

	public TakeCommand(String target) {
		m_target = target;
	}

	@SuppressWarnings("unchecked")
	public TakeCommand(ItemConcept itemConcept)
	{
		addSlotConcepts(makeSlot(TAKE_ITEM, itemConcept));
		m_target = itemConcept.toString();
	}

	public String toString() {
		return "Take"+(null!=m_target?" "+m_target:"");
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}

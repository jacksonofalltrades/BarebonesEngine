package barebones.event;

import java.util.HashMap;

import barebones.concept.DirectionConcept;
import barebones.concept.ItemConcept;

public class OpenCommand extends UserCommandImpl {
	public static final String id = "OPEN";
	public static final String OPEN_DIR = "dir";
	public static final String OPEN_TARGET = "item";
	
	@SuppressWarnings("unchecked")
	public static OpenCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(OPEN_DIR, ArgumentType.DIRECTION));		
		return new OpenCommand(normalizeMoveDir(args.get(OPEN_DIR)));
	}
	
	public OpenCommand(String target)
	{
		m_target = target;
	}
	
	@SuppressWarnings("unchecked")
	public OpenCommand(DirectionConcept dirConcept)
	{
		addSlotConcepts(makeSlot(OPEN_DIR, dirConcept));
		m_target = dirConcept.toString();
	}
	
	@SuppressWarnings("unchecked")
	public OpenCommand(ItemConcept itemConcept)
	{
		addSlotConcepts(makeSlot(OPEN_TARGET, itemConcept));
		m_target = itemConcept.toString();
	}
	
	public boolean causesTick() {
		return true;
	}

	public String id() {
		return id;
	}
	
	public String toString() {
		return "Open"+(null!=m_target?" "+m_target:"");
	}
}

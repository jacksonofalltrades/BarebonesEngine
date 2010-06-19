package barebones.event;

import java.util.HashMap;

import barebones.concept.DirectionConcept;

public class MoveCommand extends UserCommandImpl 
{
	public static final String id = "MOVE";
	public static final String MOVE_DIR = "dir";
		
	@SuppressWarnings("unchecked")
	public static MoveCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(MOVE_DIR, ArgumentType.DIRECTION));
		return new MoveCommand(normalizeMoveDir(args.get(MOVE_DIR)));
	}
	
	@SuppressWarnings("unchecked")
	public MoveCommand(DirectionConcept dirConcept)
	{
		super(TargetScope.CURRENT_ROOM);
		addSlotConcepts(makeSlot(MOVE_DIR, dirConcept));
		m_target = dirConcept.toString();
	}
	
	public MoveCommand(String target)
	{
		super(TargetScope.CURRENT_ROOM);
		m_target = target;
	}

	public boolean causesTick() {
		return true;
	}

	public String id() {
		return id;
	}

	public String toString() {
		return "Move"+(null!=m_target?" "+m_target:"");
	}
}

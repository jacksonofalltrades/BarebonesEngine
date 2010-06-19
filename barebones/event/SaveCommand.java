package barebones.event;

import java.util.HashMap;

import barebones.concept.IdentifierConcept;

public class SaveCommand extends UserCommandImpl 
{
	public static final String id = "SAVE";
	public static final String SAVE_GAMEID = "gameid";
	
	@SuppressWarnings("unchecked")
	public static SaveCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(SAVE_GAMEID, ArgumentType.GAMEID));
		return new SaveCommand(args.get(SAVE_GAMEID));
	}
	
	@SuppressWarnings("unchecked")
	public SaveCommand(IdentifierConcept id)
	{
		super(TargetScope.NONE);
		addSlotConcepts(makeSlot(SAVE_GAMEID, id));
		m_target = id.toString();
	}
	
	public SaveCommand(String saveId) {		
		super(TargetScope.NONE);
		m_target = saveId;
	}

	@Override
	public String toString() {
		return "Save ["+m_target+"]";
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}

package barebones.event;

import java.util.HashMap;

import barebones.concept.IdentifierConcept;

public class RestoreCommand extends UserCommandImpl 
{
	public static final String id = "LOAD";
	public static final String LOAD_GAMEID = "gameid";
	
	@SuppressWarnings("unchecked")
	public static RestoreCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(LOAD_GAMEID, ArgumentType.GAMEID));
		return new RestoreCommand(args.get(LOAD_GAMEID));
	}

	@SuppressWarnings("unchecked")
	public RestoreCommand(IdentifierConcept id)
	{
		addSlotConcepts(makeSlot(LOAD_GAMEID, id));
		m_target = id.toString();
	}

	public RestoreCommand(String savedGameId) {
		m_target = savedGameId;
	}

	public String toString() {
		return "Restore "+m_target;
	}

	public boolean causesTick() {
		return false;
	}

	public String getSubcommandDesc()
	{
		return "Restoring a game will cause you to lose all your current progress. Are you sure you want to do that?";
	}

	public String id() {
		return id;
	}

}

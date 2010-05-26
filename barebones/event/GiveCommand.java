package barebones.event;

import java.util.HashMap;

public class GiveCommand extends UserCommandImpl 
{
	public static final String id = "GIVE";
	public static final String GIVE_ITEM = "item";
	public static final String GIVE_RECIP = "recip";
	protected String m_npcId;
	
	@SuppressWarnings("unchecked")
	public static GiveCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(GIVE_ITEM, ArgumentType.ITEM),
				arg(GIVE_RECIP, ArgumentType.RECIPIENT));
		return new GiveCommand(args.get(GIVE_ITEM), args.get(GIVE_RECIP));
	}
	
	public GiveCommand(String item, String recipient) 
	{		
		m_target = item;
		m_npcId = recipient;
	}
	
	public String getRecipient() {
		return m_npcId;
	}

	public String toString() {
		return "Give "+m_target+" to "+m_npcId;
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}

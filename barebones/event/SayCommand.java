package barebones.event;

import java.util.HashMap;

import barebones.concept.StatementConcept;

public class SayCommand extends UserCommandImpl 
{
	public static final String id = "SAY";
	public static final String SAY_STATEMENT = "statement";
	
	@SuppressWarnings("unchecked")
	public static SayCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(SAY_STATEMENT, ArgumentType.STATEMENT));
		return new SayCommand(args.get(SAY_STATEMENT));
	}
	
	@SuppressWarnings("unchecked")
	public SayCommand(StatementConcept statement)
	{
		super(TargetScope.NONE);
		addSlotConcepts(makeSlot(SAY_STATEMENT, statement));
		m_target = statement.toString();
	}
	
	public SayCommand(String statement) 
	{		
		super(TargetScope.NONE);
		m_target = statement;
	}
	
	public String toString() {
		return "Push "+m_target;
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}

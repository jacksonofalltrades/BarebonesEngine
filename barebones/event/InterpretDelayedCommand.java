package barebones.event;

/**
 * This is a special user command that represents
 * a command whose details we do not yet know.
 * 
 * This is used for passing raw input text to the server to do the parsing and semantic
 * processing.
 * @author dej
 *
 */
public class InterpretDelayedCommand extends UserCommandImpl 
{
	public static final String id = "IDC";

	public InterpretDelayedCommand(String rawInputText)
	{
		super(TargetScope.NONE);
		this.m_target = rawInputText;
	}
	
	@Override
	public String toString() {
		return id;
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return InterpretDelayedCommand.id;
	}

}

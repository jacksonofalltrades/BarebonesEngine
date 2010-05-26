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
		this.m_target = rawInputText;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean causesTick() {
		// TODO Auto-generated method stub
		return false;
	}

	public String id() {
		// TODO Auto-generated method stub
		return InterpretDelayedCommand.id;
	}

}

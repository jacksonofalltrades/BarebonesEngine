package barebones.event;

import java.util.HashSet;

public class MissingCommandArgumentException extends RuntimeException 
{
	public static final long serialVersionUID = 1L;
		
	protected String m_commandId;
	protected HashSet<ArgumentType> m_missingArgTypes;
	
	/*
	 * @param types - list of argument types missing
	 */
	public MissingCommandArgumentException(String commandId) 
	{
		m_commandId = commandId;
		m_missingArgTypes = new HashSet<ArgumentType>();
	}
	
	public void addArg(ArgumentType type) {
		m_missingArgTypes.add(type);
	}
	
	public String getCommandId() {
		return m_commandId;
	}
	
	public int getArgCount() {
		return m_missingArgTypes.size();
	}
	
	public HashSet<ArgumentType> getMissingArgs() {
		return m_missingArgTypes;
	}
}

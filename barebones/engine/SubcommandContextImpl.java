package barebones.engine;

import barebones.event.UserCommand;

abstract public class SubcommandContextImpl implements SubcommandContext 
{
	protected boolean m_completed;
	protected UserCommand m_source;
	
	private String m_contextDesc;
	
	public String getContextDesc()
	{
		return m_contextDesc;
	}
	
	public SubcommandContextImpl(UserCommand source, String desc)
	{
		m_source = source;
		m_contextDesc = desc;
		m_completed = false;
	}	
	
	public boolean isCompleted()
	{
		return m_completed;
	}
	
	public boolean isSource(UserCommand cmd)
	{
		return (cmd == m_source);
	}
}

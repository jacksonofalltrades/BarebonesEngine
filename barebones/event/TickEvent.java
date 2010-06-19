package barebones.event;

public class TickEvent extends UserCommandImpl
{
	protected long m_totalSecondsPassed;
	
	public TickEvent(long totalSecondsPassed)
	{
		super(TargetScope.NONE);
		m_totalSecondsPassed = totalSecondsPassed;
		m_target = "";
	}
	
	public String id()
	{
		return "TICK";
	}
	
	public boolean causesTick()
	{
		// Well that would be awfully recursive, now wouldn't it?
		// and we can't have that...
		return false;
	}
	
	public long getSeconds()
	{
		return m_totalSecondsPassed;
	}
	
	public String toString()
	{
		return "Tick";
	}
}

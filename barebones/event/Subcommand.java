package barebones.event;

public class Subcommand extends UserCommandImpl {
	public Subcommand(String value) {
		super(TargetScope.NONE);
		m_target = value;
	}
	
	public boolean causesTick() {
		return false;
	}
	
	public String id() {
		return "SUBC";
	}
	
	public String toString()
	{
		return m_target;
	}
}

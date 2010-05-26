package barebones.event;

import java.util.HashMap;

public class GetTimeCommand extends UserCommandImpl
{	
	public static final String id = "TIME";

	public static GetTimeCommand instance(HashMap<String,String> args) {
		return new GetTimeCommand();
	}
	
	public String id()
	{
		return id;
	}
	
	public boolean causesTick()
	{
		return false;
	}
	
	public String toString()
	{
		return "Check the time";
	}
}

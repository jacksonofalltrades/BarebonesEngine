package barebones.event;

import java.util.HashMap;

public class GetInventoryCommand extends UserCommandImpl 
{
	public static final String id = "GINV";
		
	public static GetInventoryCommand instance(HashMap<String,String> args) {
		return new GetInventoryCommand();
	}
	
	public GetInventoryCommand() {
		super(TargetScope.NONE);
		this.m_target = "";
	}
	
	public String toString() {
		return "Inventory";
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}

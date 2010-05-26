package barebones.world.data;

public class CommandTrigger {
	public String commandId;
	public String target;
	
	public void setcommandId(String id) {
		this.commandId = id;
	}
	
	public String getcommandId() {
		return this.commandId;
	}
	
	public void settarget(String t) {
		this.target = t;
	}
	
	public String gettarget() {
		return this.target;
	}
	
	public Object clone() {
		CommandTrigger other = new CommandTrigger();
		other.commandId = this.commandId;
		other.target = this.target;
		
		return other;
	}
}

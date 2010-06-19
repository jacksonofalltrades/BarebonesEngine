package barebones.event;

import java.util.HashMap;

public class QuitCommand extends UserCommandImpl 
{
	public static final String id = "QUIT";
	
	public static QuitCommand instance(HashMap<String,String> args) {
		return new QuitCommand();
	}
	
	public QuitCommand()
	{
		super(TargetScope.NONE);
	}

	@Override
	public String toString() {
		return "You exit the game";
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}
}

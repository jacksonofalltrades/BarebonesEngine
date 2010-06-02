package barebones.client.textlocal;

import barebones.client.ClientAction;
import barebones.client.ClientConfig;
import barebones.client.ExecuteCommandAction;
import barebones.client.UserInput;
import barebones.client.UserInputInterpreter;
import barebones.event.InterpretDelayedCommand;

public class DelayedTextInterpreter implements UserInputInterpreter 
{
	public DelayedTextInterpreter(ClientConfig config) {		
	}

	public ClientAction interpret(UserInput input) {
		RawTextInput rti = (RawTextInput)input;
		
		InterpretDelayedCommand cmd = new InterpretDelayedCommand(rti.text);

		ExecuteCommandAction act = new ExecuteCommandAction();
	    act.command = cmd;
		
		return act;
	}

}

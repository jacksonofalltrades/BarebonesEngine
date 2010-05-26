package barebones.client.textlocal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import barebones.client.ClientAction;
import barebones.client.ClientBase;
import barebones.client.ClientConfig;
import barebones.client.ExecuteCommandAction;
import barebones.client.UserInput;
import barebones.client.UserInputInterpreter;
import barebones.event.ArgumentType;
import barebones.event.MissingCommandArgumentException;
import barebones.event.UserCommand;
import barebones.interpreter.CommandPatternLoader;
import barebones.interpreter.PatternSequenceMatcher;
import barebones.interpreter.PatternSequenceNode;

public class SimpleTextInterpreter implements UserInputInterpreter 
{
	protected static final String UNRECOGNIZED_INPUT = "Sorry I don't understand.";
	protected static final String MISSING_ARGS = "'%s' requires arguments (%s)";

	protected static HashMap<ArgumentType,String> sm_argStringMap;

	protected static String getArgString(ArgumentType type) {
		return sm_argStringMap.get(type);
	}
	
	protected static String getMissingArgString(HashSet<ArgumentType> argSet) {
		// Sort argSet so it has a predictable ordering
		Vector<ArgumentType> argVec = new Vector<ArgumentType>();
		argVec.addAll(argSet);
		Collections.sort(argVec);
		
		StringBuffer sb = new StringBuffer();
		for(ArgumentType t : argVec) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(getArgString(t));
		}
		
		return sb.toString();
	}
	
	static {
		sm_argStringMap = new HashMap<ArgumentType,String>();
		sm_argStringMap.put(ArgumentType.DIRECTION, "direction");
		sm_argStringMap.put(ArgumentType.GAMEID, "game id");
		sm_argStringMap.put(ArgumentType.ITEM, "item");
		sm_argStringMap.put(ArgumentType.RECIPIENT, "recipient");
		
		ClientBase.registerInput(RawTextInput.class, SimpleTextInterpreter.class, TextLocalConfig.getInstance());
	}
	
	protected CommandPatternLoader m_loader;
	
	public SimpleTextInterpreter(ClientConfig config) {
		
		// User config to find pattern loader
		
		// Eventually point to client root path + root path for patterns
		m_loader = new CommandPatternLoader("");
		m_loader.load("");
	}
	
	protected UserCommand makeCommand(Class<?> c, Object[] args)
	{		
		UserCommand cmd = null;
		try {
			Method m = c.getMethod("instance", HashMap.class);
			cmd = (UserCommand)m.invoke(c, args);
		}
		catch(NoSuchMethodException nsme) {
			nsme.printStackTrace(System.err);
		}
		catch(IllegalAccessException iae) {
			iae.printStackTrace(System.err);
		}
		catch(InvocationTargetException ite) {
			// Expect potential MissingCommandArgumentException
			Throwable target = ite.getTargetException();
			if (target instanceof MissingCommandArgumentException) {
				MissingCommandArgumentException mcae = (MissingCommandArgumentException)target;
				throw mcae;
			}
			
			ite.printStackTrace(System.err);
		}
		
		return cmd;
	}
	
	public ClientAction interpret(UserInput input) {
		RawTextInput rti = (RawTextInput)input;
		
		HashSet<ArgumentType> missingArgs = null;
		String cmdId = null;
		UserCommand cmd = null;		
		Vector<AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>>> patternList =
			m_loader.getCommandPatternList();
		
		for(AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>> pair : patternList) {
			PatternSequenceNode p = pair.getKey();
			PatternSequenceMatcher m = p.matcher(rti.text);
			if (m.matches()) {
				HashMap<String,String> results = m.groupMap();
								
				Object[] args = {results};
				Class<?> c = pair.getValue();
				try {
					cmd = makeCommand(c, args);
				}
				catch(MissingCommandArgumentException mcae) {
					cmdId = mcae.getCommandId();
					missingArgs = mcae.getMissingArgs();
				}
				break;
			}
			else {
				//System.out.println("did not match "+p);
			}
		}
		
		ClientAction cact = null;
		if (null != cmd) {
			// For now, we just generate a user command based on mapping
			ExecuteCommandAction act = new ExecuteCommandAction();
		    act.command = cmd;
		    cact = act;
		}
		else {
			DisplayResponseAction act = new DisplayResponseAction();
			act.responseLines = new Vector<String>();
			if (null != missingArgs) {
				String missingArgStr = getMissingArgString(missingArgs);
				act.responseLines.add(String.format(MISSING_ARGS, cmdId, missingArgStr));
			}
			else {
				act.responseLines.add(UNRECOGNIZED_INPUT);
			}
		    cact = act;
		}
		
		return cact;
	}
}

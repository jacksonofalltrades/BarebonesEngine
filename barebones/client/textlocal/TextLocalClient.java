package barebones.client.textlocal;

import java.util.Vector;

import barebones.client.ClientAction;
import barebones.client.ClientBase;
import barebones.client.ClientConfig;
import barebones.client.ClientHandlerException;
import barebones.client.ExecuteCommandAction;
import barebones.client.textlocal.display.TextLocalClientDisplay;
import barebones.io.ResponseContent;
import barebones.io.UserResponse;

public class TextLocalClient extends ClientBase 
{
	protected ClientConfig m_config;
	
	protected TextLocalClientDisplay m_display;
	
	static {
		ClientBase.registerClientAction(DisplayResponseAction.class, TextLocalClient.class, "handleDisplayResponseAction");
		ClientBase.registerClientAction(ExecuteCommandAction.class, TextLocalClient.class, "handleExecuteCommandAction");
		
		ClientBase.registerInput(RawTextInput.class, DelayedTextInterpreter.class, TextLocalConfig.getInstance());
	}
	
	public TextLocalClient() {
		m_config = TextLocalConfig.getInstance();
		m_display = new TextLocalClientDisplay(this);
		m_display.setVisible(true);
		
		// Initially disable all elements of display until user logs in to a game
		m_display.disableControls();
	}

	@Override
	protected ClientConfig getClientConfig() {
		return m_config;
	}

	/*
	 * Client action handlers
	 */
	
	public ClientAction handleDisplayResponseAction(ClientAction act) 
	{
		if (act instanceof DisplayResponseAction) {
			DisplayResponseAction dra = (DisplayResponseAction)act;
			for(String line : dra.responseLines) {
				m_display.displayLine(line);
			}
		}
		else {
			throw new ClientHandlerException(this.getClass(), "handleDisplayResponseAction", act.getClass(), DisplayResponseAction.class);			
		}
		
		// This never has a next action
		return null;
	}
	
	protected ClientAction createActionFromResponse(UserResponse resp)
	{
		if (resp.requiresSubcommand())
			this.m_isSubcommandContext = true;
		else
			this.m_isSubcommandContext = false;
		
		// Construct a DisplayResponseAction and return it
		DisplayResponseAction dra = new DisplayResponseAction();
		dra.responseLines = new Vector<String>();
		for(ResponseContent c : resp.getResponseList()) {
			dra.responseLines.add(c.text());
		}
		
		return dra;		
	}
		
	public void run() {
	}

	public static void main(String[] args) {
		
		String l_rootPath = System.getProperty(ClientConfig.ROOT_PATH_ENV_KEY);
			//System.getenv(ClientConfig.ROOT_PATH_ENV_KEY);
		if (null == l_rootPath) {
			System.err.println("usage: TextLocalClient -Droot.path=<rootpath>");
			System.exit(1);			
		}
		
		//System.setProperty(ClientConfig.ROOT_PATH_ENV_KEY, l_rootPath);
							
		TextLocalClient client = new TextLocalClient();
		client.run();
	}	
}

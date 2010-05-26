package barebones.client.textlocal.display;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import barebones.client.Client;

public class ClientMenuBar extends MenuBar 
{
	private static final long serialVersionUID = 1L;
	
	public static final String CMD_CONNECT = "@";
	public static final String CMD_DISCONNECT = "!";
	public static final String CMD_QUIT = "Q";
	
	protected Client m_clientRef;
	
	protected MenuItem m_connect;
	protected MenuItem m_disconnect;
	protected MenuItem m_quit;
	
	protected HashMap<String,MenuItem> m_commandMap;
	
	public ClientMenuBar(TextLocalClientDisplay display, Client clientRef) {
		m_clientRef = clientRef;
		
		m_commandMap = new HashMap<String,MenuItem>();
		
		Menu l_playMenu = new Menu("Play");
		m_connect = new MenuItem("Connect...", new MenuShortcut(KeyEvent.VK_AT));
		m_connect.addActionListener(display);
		m_connect.setActionCommand(CMD_CONNECT);
		
		m_disconnect = new MenuItem("Disconnect", new MenuShortcut(KeyEvent.VK_END));
		m_disconnect.addActionListener(display);
		m_disconnect.setActionCommand(CMD_DISCONNECT);
		
		m_quit = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_ESCAPE));
		m_quit.addActionListener(display);
		m_quit.setActionCommand(CMD_QUIT);
		
		m_commandMap.put(CMD_CONNECT, m_connect);
		m_commandMap.put(CMD_DISCONNECT, m_disconnect);
		m_commandMap.put(CMD_QUIT, m_quit);
		l_playMenu.add(m_connect);
		l_playMenu.add(m_disconnect);
		l_playMenu.add(m_quit);
		
		add(l_playMenu);
	}
	
	public void disableCommand(String command) {
		if (m_commandMap.containsKey(command)) {
			MenuItem mi = m_commandMap.get(command);
			mi.setEnabled(false);
		}
	}
	
	public void enableCommand(String command) {
		if (m_commandMap.containsKey(command)) {
			MenuItem mi = m_commandMap.get(command);
			mi.setEnabled(true);
		}
	}
}

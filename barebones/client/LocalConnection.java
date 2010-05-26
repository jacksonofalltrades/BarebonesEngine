package barebones.client;

import java.io.BufferedInputStream;
import java.util.HashMap;

import barebones.engine.GameEngine;
import barebones.event.UserCommand;
import barebones.io.User;
import barebones.io.UserImpl;
import barebones.io.UserResponse;

/**
 * This represents a non-remote connection to a GameEngine
 * Treat the client root path as the server root path
 * @author dej
 *
 */
public class LocalConnection implements GameConnection 
{
	protected GameEngine m_currEngine;
	protected User m_currUser;
	protected ClientConfig m_config;
	protected boolean m_connected;
	
	protected UserResponse m_lastResponse;
	
	public LocalConnection(ClientConfig config) {
		m_config = config;
		m_currEngine = null;
		m_currUser = null;
		m_connected = false;
		m_lastResponse = null;
	}

	public void connect(String gameId, String userId, String pwd) {
		if (m_connected) {
			return;
		}

		if (null == m_currUser || !m_currUser.id().equals(userId)) {
			m_currUser = new UserImpl(userId);
		}
		m_currEngine = new GameEngine(m_config.getClientRootPath(), gameId, m_currUser);
				
		m_currEngine.init();
		m_connected = true;
	}

	public void disconnect() {
		if (m_connected) {
			m_currEngine.shutdown();
			m_currEngine = null;
			m_connected = false;
		}
	}

	public void downloadGameResources(
			HashMap<String, BufferedInputStream> resourceMap) {
		if (m_connected) {
			// TODO Auto-generated method stub
		}
	}

	public UserResponse receive() {
		if (m_connected) {
			return m_lastResponse;
		}
		System.err.println("LocalConnection::receive: Not connected.");
		return null;
	}

	public void send(UserCommand cmd) {
		if (m_connected) {
			m_currEngine.notify(cmd);
			m_lastResponse = cmd.getResponse();
		}
	}
	
	public boolean isConnected() {
		return m_connected;
	}
}

package barebones.client;

import java.io.BufferedInputStream;
import java.util.HashMap;

import barebones.event.UserCommand;
import barebones.io.UserResponse;

public interface GameConnection 
{
	public void connect(String gameId, String userId, String pwd);

	/**
	 * @return a map of local path - stream pairs
	 * Streams should be read and output to the local path specified relative to
	 * the client game root path.
	 * 
	 * If the resourceMap is empty, it means all resources are already local
	 */
	void downloadGameResources(HashMap<String,BufferedInputStream> resourceMap);
	
	void send(UserCommand cmd);
	
	UserResponse receive();

	void disconnect();

	boolean isConnected();
}

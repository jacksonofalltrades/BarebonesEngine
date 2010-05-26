package barebones.client;

import java.util.HashMap;

public final class GameConnectionFactory 
{
	protected static HashMap<ClientConfig,GameConnection> sm_configConnectionMap;
	
	protected GameConnectionFactory() {}
	
	public static GameConnection getInstance(ClientConfig config) {
		if (null == sm_configConnectionMap) {
			sm_configConnectionMap = new HashMap<ClientConfig,GameConnection>();
		}
		GameConnection conn = null;
		if (sm_configConnectionMap.containsKey(config)) {
			conn = sm_configConnectionMap.get(config);
		}
		else {
			if (config.isLocal()) {
				conn = new LocalConnection(config);
				sm_configConnectionMap.put(config, conn);
			}
			else {
				// TODO: Fill in for remote connection
			}
		}
		
		return conn;
	}
}

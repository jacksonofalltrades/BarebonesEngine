package barebones.client.textlocal;

import barebones.client.ClientConfig;

public class TextLocalConfig implements ClientConfig 
{
	///protected static final String ROOT_PATH = "/home/dej/svnroot/software/trunk/BarebonesEngine/";
	
	protected static ClientConfig sm_config;
	
	protected String m_rootPath;
	
	protected static ClientConfig getInstance() {
		if (null == sm_config) {
			sm_config = new TextLocalConfig();
		}
		return sm_config;
	}
	
	private TextLocalConfig() {
		// Get root path from system property
		m_rootPath = System.getProperty(ClientConfig.ROOT_PATH_ENV_KEY);
	}
	
	public String getClientRootPath() {
		return m_rootPath;
	}
	
	public String getRemoteIP() {
		return null;
	}

	public String getRemotePort() {
		return null;
	}

	public boolean isLocal() {
		return true;
	}

	public String getGameTitle() {
		return "";
	}
}

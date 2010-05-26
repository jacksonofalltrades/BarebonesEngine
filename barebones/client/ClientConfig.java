package barebones.client;

public interface ClientConfig 
{
	public static final String ROOT_PATH_ENV_KEY = "root.path";

	public boolean isLocal();
	public String getRemoteIP();
	public String getRemotePort();
	public String getClientRootPath();
	public String getGameTitle();
}

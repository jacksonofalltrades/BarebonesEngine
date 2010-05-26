package barebones.client;

public interface Client
{
	public boolean start(String gameId, String userId, String pwd);
	public void shutdown();
	public void sendInput(UserInput input);
	
	public String getGameTitle();
	public boolean requiresSubcommand();
}

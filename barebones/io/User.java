package barebones.io;

import barebones.engine.GameEngineAccessor;
import barebones.event.UserCommand;
import barebones.world.data.PlayerStateBean;

public interface User {
	
	public String id();

	public boolean createNew(GameEngineAccessor engineRef);
	public PlayerStateBean load(GameEngineAccessor engineRef);
	public PlayerStateBean load(GameEngineAccessor engineRef, String savedGameId);
	public void save(GameEngineAccessor engineRef, PlayerStateBean bean, String savedGameId);
	public void save(GameEngineAccessor engineRef, PlayerStateBean bean);
	public void sendCommand(UserCommand cmd);
	public UserResponse nextResponse();
}

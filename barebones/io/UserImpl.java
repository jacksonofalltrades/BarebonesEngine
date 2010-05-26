package barebones.io;

import barebones.engine.GameEngineAccessor;
import barebones.event.UserCommand;
import barebones.io.dal.PlayerStateLoader;
import barebones.world.data.NewUserCreationException;
import barebones.world.data.PlayerStateBean;

public class UserImpl implements User 
{
	protected String m_id;
			
	protected PlayerStateLoader m_loader;
		
	public UserImpl(String id)
	{
		m_id = id;
	}
	
	public boolean createNew(GameEngineAccessor engineRef) {
		String gameRootPath = engineRef.getGameDataRootPath();
		boolean isNewUser = false;
		
		// Check if user file exists
		try {
			if (PlayerStateBean.create(gameRootPath, m_id)) {
				isNewUser = true;
			}
		}
		catch(NewUserCreationException nce) {
			if (null != nce.getSource())
				nce.getSource().printStackTrace(System.err);
			else
				nce.printStackTrace(System.err);
		}
		return isNewUser;
	}

	public PlayerStateBean load(GameEngineAccessor engineRef) {
		return load(engineRef, "");
	}

	public PlayerStateBean load(GameEngineAccessor engineRef, String savedGameId) {
		if (null == m_loader) {
			m_loader = new PlayerStateLoader(engineRef);
		}
		return m_loader.load(m_id, savedGameId);
	}
	
	public void save(GameEngineAccessor engineRef, PlayerStateBean bean) {
		save(engineRef, bean, "");
	}
	
	public void save(GameEngineAccessor engineRef, PlayerStateBean bean, String savedGameId) {
		if (null == m_loader) {
			m_loader = new PlayerStateLoader(engineRef);
		}
		m_loader.save(bean, savedGameId);
	}
		
	public String id() {
		return m_id;
	}

	public UserResponse nextResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Eventually this will be called by the game client
	 * and will very likely involve a call to a server
	 */
	public void sendCommand(UserCommand cmd) {
		// TODO Auto-generated method stub
	}
}

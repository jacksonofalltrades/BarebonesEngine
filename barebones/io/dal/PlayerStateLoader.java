package barebones.io.dal;

import java.util.HashMap;
import barebones.engine.GameEngineAccessor;
import barebones.world.data.PlayerStateBean;

public class PlayerStateLoader extends GameObjectMarshaller {	
	protected GameEngineAccessor m_engineRef;
		
	protected HashMap<String,PlayerStateBean> m_playerStateCache;
	
	public PlayerStateLoader(GameEngineAccessor engineRef) {
		super(engineRef);
		
		m_engineRef = engineRef;
						
		m_playerStateCache = new HashMap<String,PlayerStateBean>();
	}
		
	public PlayerStateBean load(String userId) {
		return load(userId, "");
	}
	
	public PlayerStateBean load(String userId, String savedGameId) {
		
		if ((savedGameId.length()>0) || !m_playerStateCache.containsKey(userId)) {
			// Construct objId with userId.savedGameId (if savedGameId is not empty)
			String objId = (savedGameId.length()>0)?userId+GameObjectMarshaller.QUALIFIER_SEPARATOR+savedGameId:userId;
			
			PlayerStateBean data = (PlayerStateBean)load(PlayerStateBean.class, objId, false);
			if (null != data) {
				m_playerStateCache.put(userId, data);
			}
			else {
				System.err.println("UNABLE TO LOAD PLAYER STATE FOR USERID=["+userId+"] and game id ["+savedGameId+"]");
				return null;
			}
		}
		
		return m_playerStateCache.get(userId);
	}
	
	public void save(PlayerStateBean data, String savedGameId) {
		super.save(data, savedGameId);		
	}
	
	public void save(PlayerStateBean data) {		
		this.save(data, "");
	}
}

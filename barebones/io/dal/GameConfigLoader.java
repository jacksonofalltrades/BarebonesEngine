package barebones.io.dal;

import barebones.world.data.GameConfigDataBean;

public class GameConfigLoader extends GameObjectMarshaller
{
	protected static final String GAME_CONFIG_ID = "game";
	
	protected static GameConfigLoader sm_gcParser;
	
	static {
		sm_gcParser = null;
	}
	
	public static GameConfigLoader instance()
	{
		if (null == sm_gcParser) {
			sm_gcParser = new GameConfigLoader();
		}
		return sm_gcParser;
	}
		
	protected GameConfigDataBean m_configData;
	
	protected GameConfigLoader()
	{
		super("");
	}
	
	public GameConfigDataBean load(String path)
	{
		this.setRootPath(path);
		
		m_configData = (GameConfigDataBean)load(GameConfigDataBean.class, GAME_CONFIG_ID);
		
		GameConfigDataBean copy = new GameConfigDataBean(m_configData);
		
		return copy;
	}
}

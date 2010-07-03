package barebones.io.dal;

import barebones.engine.GameEngineAccessor;

import utils.io.dal.ObjectMarshaller;

public class GameObjectMarshaller extends ObjectMarshaller {
	
	public GameObjectMarshaller(GameEngineAccessor engineRef) 
	{
		super(engineRef.getGameDataRootPath());
	}	
	
	public GameObjectMarshaller(String rootPath) {
		super(rootPath);
	}
}

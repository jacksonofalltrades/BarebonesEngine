package barebones.world.data;

import barebones.engine.GameEngineAccessor;
import barebones.world.feature.RoomFeature;

public interface WORoomFeatureBean 
{
	public RoomFeature newInstance(GameEngineAccessor engineRef);
}

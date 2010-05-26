package barebones.engine;

import java.util.Vector;

import barebones.event.UserCommand;
import barebones.world.object.WorldObject;

abstract public class ManagerImpl implements Manager 
{
	protected String m_gameDataRootPath;
	protected GameEngineAccessor m_engineRef;
	
	protected ManagerImpl(GameEngineAccessor engineRef)
	{
		m_gameDataRootPath = engineRef.getGameDataRootPath();
		m_engineRef = engineRef;
	}

	abstract protected Vector<WorldObject> getCanFilter(String commandId, String targetId, int roomId);
	abstract protected Vector<WorldObject> getCanProcess(String commandId, String targetId, int roomId);
	
	public void filter(UserCommand cmd) {
		Player player = m_engineRef.getPlayer();
		Vector<WorldObject> filterObjects = getCanFilter(cmd.id(), m_engineRef.parseTarget(cmd), player.getCurrentRoomId());
		for(int i = 0; i < filterObjects.size(); i++) {
			WorldObject wo = filterObjects.get(i);
			wo.filter(cmd);
		}
	}

	public void process(UserCommand cmd) {
		Player player = m_engineRef.getPlayer();
		Vector<WorldObject> processObjects = getCanProcess(cmd.id(), m_engineRef.parseTarget(cmd), player.getCurrentRoomId());
		for(int i = 0; i < processObjects.size(); i++) {
			WorldObject wo = processObjects.get(i);
			wo.process(cmd);
		}
	}
}

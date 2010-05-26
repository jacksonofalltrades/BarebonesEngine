package barebones.engine;

import java.util.HashSet;

import barebones.event.UserCommand;
import barebones.world.object.Item;
import barebones.world.object.Room;

public interface GameEngineAccessor 
{
	/**
	 * Convert free text target to an id of some real object in the game world
	 * @param cmd User command received
	 * @return id of real object in the game world
	 */
	public String parseTarget(UserCommand cmd);
	public String parseNPC(String npcDesc);
	public String getGameDataRootPath();
	public Player getPlayer();
	public Room getPlayerRoom();
	public Item getItem(String id);
	public String getUserId();
	public GameConfig getGameConfig();
	public Integer getStartingRoomId();
	public String getRegionFor(int roomId);
	public String search(HashSet<String> itemList, String itemId);
	public WorldClock getClock();
	public void setSubcommandContext(SubcommandContext ctx);
}

package barebones.event;

import java.util.HashMap;

import barebones.client.UserInput;
import barebones.engine.GameEngineAccessor;
import barebones.io.ResponseContent;
import barebones.io.UserResponse;
import barebones.world.data.WODataBean;

public interface UserCommand extends GameEvent, UserInput
{
	public enum TargetScope {
		NONE, // Indicates no targets are present for the command
		WORLD, // Search the entire game world for a target
		REGION, // Search the entire current region for a target
		CURRENT_ROOM, // Search only the current room for a target
		PLAYERS_INVENTORY // Search only the player's inventory for a target
	};
	
	public String id();
	public boolean causesTick();
	
	public void addResponse(ResponseContent content);
	public void addResponse(UserResponse response);
	public void disableFor(String targetId);
	public int getResponseCount();
	public boolean isEnabledFor(String targetId);
	public boolean changedPlayerState();
	public void updatePlayerState(String objId, WODataBean objData);
	public HashMap<String,WODataBean> getPlayerState();
	public String getTarget();
	public UserResponse getResponse();
	public void clearResponses();
	public void setSuccess();
	public boolean isSuccessful();
	public void setRequiresSubcommand(boolean r);
	public boolean requiresSubcommand();
	public String getSubcommandDesc();
	public String getCancelText();
	public String getDisambigText();
	public void semanticallyResolveTargets(GameEngineAccessor engineRef);
}

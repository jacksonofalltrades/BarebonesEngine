package barebones.world.object;

import java.util.HashSet;

import barebones.event.UserCommand;

public interface WorldObject 
{	
	public void filter(UserCommand cmd);
	public void process(UserCommand cmd);
	public String id();
	
	public void addItem(Item item);
	public void addItems(HashSet<String> items);
	public void removeItem(String itemId);
	public String findItem(String itemId);
	public Item getItem(String itemId);
	public HashSet<String> getItems();
	
	public void updateChangedState(UserCommand cmd);
	public void applyPlayerState();
}

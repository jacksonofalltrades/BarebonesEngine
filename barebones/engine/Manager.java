package barebones.engine;

import barebones.event.UserCommand;

public interface Manager 
{
	public void filter(UserCommand cmd);
	public void process(UserCommand cmd);
	public void load();
	public void reset();
}

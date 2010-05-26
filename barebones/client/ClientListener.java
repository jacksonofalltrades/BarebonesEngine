package barebones.client;

import barebones.io.UserResponse;

public interface ClientListener 
{
	public void update(UserResponse response);
}

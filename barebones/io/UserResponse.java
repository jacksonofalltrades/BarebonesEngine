package barebones.io;

import java.util.Vector;

public interface UserResponse 
{
	public void addResponseContent(ResponseContent content);
	public void addResponseContent(UserResponse response);
	public Vector<ResponseContent> getResponseList();
	//public String getText();
	public void clear();
	public void setRequiresSubcommand(boolean rs);
	public boolean requiresSubcommand();
}

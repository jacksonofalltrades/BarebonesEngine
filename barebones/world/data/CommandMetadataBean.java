package barebones.world.data;

import utils.io.dal.RootDataBean;
import barebones.io.dal.GameObjectMarshaller;
import barebones.world.filter.CommandMetadataFilter;

public class CommandMetadataBean extends RootDataBean {
	protected static final String METADATA_PATH = "commands/";
	
	static {
		GameObjectMarshaller.registerBean(CommandMetadataBean.class, new CommandMetadataFilter(), METADATA_PATH);
	}
	
	public String disambigText;
	
	public void setdisambigText(String txt)
	{
		this.disambigText = txt;
	}
	
	public String getdisambigText()
	{
		return this.disambigText;
	}

}

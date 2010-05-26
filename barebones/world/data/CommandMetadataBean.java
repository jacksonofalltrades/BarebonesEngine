package barebones.world.data;

import barebones.io.dal.ObjectMarshaller;
import barebones.world.filter.CommandMetadataFilter;

public class CommandMetadataBean extends WORootDataBean {
	protected static final String METADATA_PATH = "commands/";
	
	static {
		ObjectMarshaller.registerBean(CommandMetadataBean.class, new CommandMetadataFilter(), METADATA_PATH);
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

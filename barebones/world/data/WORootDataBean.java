package barebones.world.data;

import utils.io.xml.XMLWriteFilter;
import utils.io.xml.XMLAnnotations.DecoderIgnore;

public class WORootDataBean
{
	public String id;
	protected XMLWriteFilter filter;

	@DecoderIgnore public void setFilter(XMLWriteFilter filter) {
		this.filter = filter;
	}
	
	public String getid() {
		return id;
	}
	
	public void setid(String id) {
		this.id = id;
	}

}

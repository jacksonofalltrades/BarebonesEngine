package barebones.io;

import java.util.HashMap;
import java.util.Vector;

/**
 * For now there is only one constructor for text responses.
 * Eventually add extra info that maps content to specific world 
 * objects (for example, "change North door image")
 * 
 * Eventually add more for graphics, sound, animation, etc. to the ResponseContent class
 * @author dej
 *
 */
public class UserResponseImpl implements UserResponse 
{
	protected boolean m_requiresSubcommand;
	
	protected Vector<ResponseContent> m_contentVec;
	
	protected HashMap<String,String> m_globalResponseMetadata;

	public boolean requiresSubcommand()
	{
		return m_requiresSubcommand;
	}

	public UserResponseImpl()
	{
		m_requiresSubcommand = false;
		m_contentVec = new Vector<ResponseContent>();
		m_globalResponseMetadata = new HashMap<String,String>();
	}
	
	public UserResponseImpl(String text)
	{
		this();
		m_requiresSubcommand = false;
		ResponseContent content = ResponseContent.text(text);
		this.addResponseContent(content);
	}
	
	public UserResponseImpl(ResponseContent content) {
		this();
		m_requiresSubcommand = false;
		this.addResponseContent(content);
	}
	
	public void clear() {
		m_contentVec.clear();
	}
	
	public Vector<ResponseContent> getResponseList()
	{
		return m_contentVec;
	}

	public void addResponseContent(ResponseContent content) {
		m_contentVec.add(content);
	}
	
	public void addResponseContent(UserResponse response) {
		for(ResponseContent r : response.getResponseList()) {
			m_contentVec.add(r);
		}
	}

	public void addMetadata(String key, String val) {
		m_globalResponseMetadata.put(key, val);
	}
	
	public String getMetadataVal(String key) {
		return m_globalResponseMetadata.get(key);
	}
	
	public void setRequiresSubcommand(boolean rs)
	{
		m_requiresSubcommand = rs;
	}
}

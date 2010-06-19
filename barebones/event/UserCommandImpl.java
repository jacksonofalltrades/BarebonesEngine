package barebones.event;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import barebones.concept.Concept;
import barebones.io.ResponseContent;
import barebones.io.UserResponse;
import barebones.io.UserResponseImpl;
import barebones.world.data.CommandMetadataBean;
import barebones.world.data.WODataBean;

abstract public class UserCommandImpl implements UserCommand 
{
	protected boolean m_success;
	
	protected TargetScope m_scope;
	
	// Replace target with HashMap<String,Concept> - slot-name, concept map
	protected String m_target;
	protected HashMap<String,Concept> m_conceptMap;	
	
	protected HashMap<String,Boolean> m_targetEnableMap;
	protected UserResponse m_response;
	
	protected boolean m_requiresSubcommand;
	
	protected CommandMetadataBean cmdata;
	
	// Represents delta to player-state as of this command's execution
	// If there are no keys in here, nothing changed
	protected HashMap<String,WODataBean> m_changeMap;
	
	protected static Map.Entry<String, Concept> makeSlot(String slotName, Concept c)
	{
		return new AbstractMap.SimpleEntry<String, Concept>(slotName, c);
	}
	
	protected static AbstractMap.SimpleEntry<String, ArgumentType> arg(String arg, ArgumentType type)
	{
		return new AbstractMap.SimpleEntry<String, ArgumentType>(arg, type);
	}
	
	protected static void checkMissingArgs(String id, HashMap<String,String> argMap, AbstractMap.SimpleEntry<String,ArgumentType>... args) {
		MissingCommandArgumentException ex = new MissingCommandArgumentException(id);
		
		for(AbstractMap.SimpleEntry<String, ArgumentType> pair : args) {
			if (!argMap.containsKey(pair.getKey())) ex.addArg(pair.getValue());
		}
		if (ex.getArgCount() > 0) {
			throw ex;
		}
	}

	protected static String normalizeMoveDir(String dir) {
		// Just take the first letter and capitalize
		String normDir = String.valueOf(dir.charAt(0)).toUpperCase();
		return normDir;
	}
	
	protected void addSlotConcepts(Map.Entry<String, Concept> ... conceptSlotEntry)
	{
		for(Map.Entry<String, Concept> entry : conceptSlotEntry) {
			m_conceptMap.put(entry.getKey(), entry.getValue());
		}
	}
	
	public UserCommandImpl(TargetScope scope)
	{
		m_success = false;
		m_scope = scope;
		m_targetEnableMap = new HashMap<String,Boolean>();
		m_response = new UserResponseImpl();
		m_changeMap = new HashMap<String,WODataBean>();
		m_requiresSubcommand = false;
		m_conceptMap = new HashMap<String,Concept>();
	}
	
	public void loadMetadata()
	{
		
	}
	
	public int getResponseCount() {
		return m_response.getResponseList().size();
	}

	public void addResponse(UserResponse response) {
		m_response.addResponseContent(response);
	}

	public String getCancelText()
	{
		return String.format("%s is canceled.", this.toString());
	}
	
	public String getDisambigText()
	{
		return cmdata.disambigText;
	}
			
	public void addResponse(ResponseContent content) {
		m_response.addResponseContent(content);
	}

	public void disableFor(String targetId)
	{
		m_targetEnableMap.put(targetId, false);
	}

	public boolean isEnabledFor(String targetId)
	{
		if (m_targetEnableMap.containsKey(targetId))
		{
			return m_targetEnableMap.get(targetId);
		}
		else {
			return true;
		}
	}
	
	public void updatePlayerState(String objId, WODataBean objData) {
		m_changeMap.put(objId, objData);
	}
		
	public HashMap<String,WODataBean> getPlayerState()
	{
		return m_changeMap;
	}
	
	public boolean changedPlayerState()
	{
		return (m_changeMap.size() > 0);
	}
		
	public String getTarget()
	{
		return m_target;
	}
	
	public UserResponse getResponse()
	{
		if (this.requiresSubcommand())
		{
			m_response.setRequiresSubcommand(true);
		}
		else {
			m_response.setRequiresSubcommand(false);
		}
		return m_response;
	}
		
	public void clearResponses()
	{
		m_response.clear();
		m_changeMap.clear();
	}
	
	public void setRequiresSubcommand(boolean r)
	{
		m_requiresSubcommand = r;
	}

	public boolean requiresSubcommand()
	{
		return m_requiresSubcommand;
	}
	
	// Most don't need this - override in subclasses
	public String getSubcommandDesc()
	{
		return "";
	}
	
	public void setSuccess() {
		m_success = true;
	}
	
	public boolean isSuccessful() {
		return m_success;
	}
	
	public TargetScope getScope() {
		return m_scope;
	}
	
	public void semanticallyResolveTargets()
	{
	}
	
	abstract public String toString();
}

package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public class IdentifierConcept implements Concept 
{
	protected String m_idText;
	
	public IdentifierConcept(String id) 
	{
		m_idText = id;
	}

	public String disambiguate() {
		return "";
	}

	/**
	 * For now, no pre-filtering is done, so patternIdMap is ignored.
	 */
	public String getId(HashMap<Pattern, String> patternIdMap) 
	{
		return m_idText;
	}

	public String toString()
	{
		return m_idText;
	}
}

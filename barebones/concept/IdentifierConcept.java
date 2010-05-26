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
		// TODO Auto-generated method stub
		return null;
	}

	public String getId(HashMap<Pattern, String> patternIdMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString()
	{
		return m_idText;
	}
}

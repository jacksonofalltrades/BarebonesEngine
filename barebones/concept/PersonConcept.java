package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public class PersonConcept implements Concept 
{
	protected String m_personText;
	
	public PersonConcept(String personText) {
		m_personText = personText;
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
		return m_personText;
	}
}

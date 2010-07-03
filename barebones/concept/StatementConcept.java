package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public class StatementConcept implements Concept 
{
	protected String m_statementText;
	
	public StatementConcept(String statementText)
	{
		m_statementText = statementText;
	}

	public String disambiguate() {
		return "";
	}

	/**
	 * In this case, "id" is simply the statement text. It's up to the NPC
	 * to further interpret it.
	 */
	public String getId(HashMap<Pattern, String> patternIdMap) 
	{
		return m_statementText;
	}
	
	public String toString() 
	{
		return m_statementText;
	}
}

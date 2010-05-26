package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ItemConcept implements Concept 
{
	protected String m_itemText;
	
	public ItemConcept(String itemText) {
		m_itemText = itemText;
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
		return m_itemText;
	}
}

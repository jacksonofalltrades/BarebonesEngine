package barebones.concept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AmbiguousConcept implements Concept 
{
	protected ArrayList<Concept> m_possibleConcepts;
	
	public AmbiguousConcept(Concept ... concept)
	{
		m_possibleConcepts = new ArrayList<Concept>();
		for(Concept c : concept) {
			m_possibleConcepts.add(c);
		}
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
		StringBuffer sb = new StringBuffer();		
		for(Concept c : m_possibleConcepts) {
			if (sb.length() > 0)
				sb.append(" ");
			sb.append(c.getClass().getName()+"="+c.toString());
		}
		sb.insert(0, "AmbiguousConcept { ");
		sb.append(" }");
		
		return sb.toString();
	}
}

package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public class FeatureConcept implements Concept 
{
	protected String m_featureText;
	
	public FeatureConcept(String featureText)
	{
		m_featureText = featureText;
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
		return m_featureText;
	}
}

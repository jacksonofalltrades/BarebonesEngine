package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public interface ConceptContainer 
{	
	/**
	 * Return map of feature patterns to ids.
	 * @return
	 */
	public void registerPatternIdMapping(Pattern pattern, String id);
	
	public HashMap<Pattern, String> getFeaturePatternIdMap();
	public HashMap<Pattern, String> getItemPatternIdMap();
	public HashMap<Pattern, String> getPersonPatternIdMap();
}

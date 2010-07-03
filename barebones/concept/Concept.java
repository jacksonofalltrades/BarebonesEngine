package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public interface Concept 
{	
	/**
	 * Given a mapping of patterns to real-world ids, return the one in the map
	 * that matches this Concept's raw data (also based on the inherent type of
	 * concept this is).
	 * @param patternIdMap
	 * @return in-game id of WorldObject or other entity to which concept refers, 
	 * null if this concept is ambiguous.
	 */
	public String getId(HashMap<Pattern,String> patternIdMap);
	public String disambiguate();
}

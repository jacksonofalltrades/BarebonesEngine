package barebones.concept;

import java.util.HashMap;
import java.util.regex.Pattern;

public class DirectionConcept implements Concept 
{
	protected String m_dirText;
	
	protected enum DirVals {
		N,
		E,
		W,
		S,
		U,
		D
	}
	
	protected static HashMap<String,DirVals> sm_textToDirMap;
	
	static {
		sm_textToDirMap = new HashMap<String,DirVals>();
		sm_textToDirMap.put(DirVals.N.toString(), DirVals.N);
		sm_textToDirMap.put(DirVals.E.toString(), DirVals.E);
		sm_textToDirMap.put(DirVals.W.toString(), DirVals.W);
		sm_textToDirMap.put(DirVals.S.toString(), DirVals.S);
		sm_textToDirMap.put(DirVals.U.toString(), DirVals.U);
		sm_textToDirMap.put(DirVals.D.toString(), DirVals.D);
	}
	
	public static DirectionConcept N = new DirectionConcept(DirVals.N.toString());
	public static DirectionConcept E = new DirectionConcept(DirVals.E.toString());
	public static DirectionConcept W = new DirectionConcept(DirVals.W.toString());
	public static DirectionConcept S = new DirectionConcept(DirVals.S.toString());
	public static DirectionConcept U = new DirectionConcept(DirVals.U.toString());
	public static DirectionConcept D = new DirectionConcept(DirVals.D.toString());
	
	public static DirectionConcept instance(String text)
	{
		if (sm_textToDirMap.containsKey(text)) {
			DirVals dv = sm_textToDirMap.get(text);
			return new DirectionConcept(dv.toString());
		}
		else {
			return null;
		}
	}
	
	private DirectionConcept(String dirText)
	{		
		m_dirText = dirText;
	}

	public String disambiguate() {
		return "";
	}

	// Ignores pattern id map since it's not relevant for directions
	public String getId(HashMap<Pattern, String> patternIdMap) 
	{
		return m_dirText;
	}
	
	public String toString() {
		return m_dirText;
	}
}

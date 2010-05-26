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
	
	public static DirectionConcept N = new DirectionConcept(DirVals.N.toString());
	public static DirectionConcept E = new DirectionConcept(DirVals.E.toString());
	public static DirectionConcept W = new DirectionConcept(DirVals.W.toString());
	public static DirectionConcept S = new DirectionConcept(DirVals.S.toString());
	public static DirectionConcept U = new DirectionConcept(DirVals.U.toString());
	public static DirectionConcept D = new DirectionConcept(DirVals.D.toString());
	
	private DirectionConcept(String dirText)
	{		
		m_dirText = dirText;
	}

	public String disambiguate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId(HashMap<Pattern, String> patternIdMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return m_dirText;
	}
}

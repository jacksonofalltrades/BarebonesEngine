package barebones.interpreter;

import java.util.HashMap;

public class PatternSequenceMatcher 
{
	protected PatternSequenceNode m_lastNodeList;
	protected String[] m_textParts;
	protected HashMap<String,String> m_lastMatchMap;
	
	// Don't know how to do unbounded whitespace separated word groups
	// so for now assume no more than 7
	
	PatternSequenceMatcher(PatternSequenceNode node, String text)
	{
		m_lastNodeList = node;
		
		// Split text on whitespace
		if (null != text) m_textParts = text.split("\\s+");
		else m_textParts = null;
	}
	
	public boolean matches() {
		if (null == m_lastNodeList) return false;
		if (null == m_textParts) return false;
		
		boolean anyMatches = false;
		m_lastMatchMap = null;
		m_lastMatchMap = new HashMap<String,String>();
		PatternSequenceNode curr = m_lastNodeList;
		for(String part : m_textParts) {
			if (null == curr) break;
			String matchStr = curr.match(part, m_lastMatchMap);
			if (null != matchStr) {
				anyMatches = true;
				curr = curr.next();
			}
			else {
				break;
			}
		}
				
		return anyMatches;
	}
	
	/*
	 * @return a copy so we don't blow it away on the next call to match()
	 */
	public HashMap<String,String> groupMap() {
		return new HashMap<String,String>(m_lastMatchMap);
	}
}

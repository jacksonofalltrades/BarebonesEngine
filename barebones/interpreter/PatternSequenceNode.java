package barebones.interpreter;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to match a string against a sequence of patterns as follows:
 * 1. Match as much of the string against the first pattern as possible
 * 2. Remove the part matched by the first pattern from the string
 * 3. Match the remainder of the string against the next pattern in the sequence
 * 4. Repeat 1-3
 * 
 * A key part of this algorithm is that it is greedy and will match whatever portion of the
 * pattern sequence that it can. i.e. it will always return results for whatever it can
 * rather than failing if any one part fails to match.
 * 
 * This is important so that we can try to identify as much as possible of 
 * what the text was trying to say.
 * 
 * Each pattern in the sequence has a semantic mapping: an id indicating what it means
 * in the context of the entire string 
 * (i.e., it matches a user command, it matches an argument with a specific meaning for the commend, 
 * etc.)
 * @author dej
 *
 */
public class PatternSequenceNode 
{
	protected String m_semanticId;
	protected Pattern m_pattern;
	
	// Map pattern match group indices to semantic ids
	protected HashMap<Integer,String> m_indexIdMap;
	
	protected PatternSequenceNode m_nextNode;
	
	protected PatternSequenceMatcher m_matcher;
	
	public static PatternSequenceNode compile(String p, int flags, AbstractMap.SimpleEntry<Integer, String>... pairs) {
		return new PatternSequenceNode(p, flags, pairs);
	}

	private PatternSequenceNode(String p, int flags, AbstractMap.SimpleEntry<Integer, String>... pairs)
	{
		m_pattern = Pattern.compile(p, flags);
		m_indexIdMap = new HashMap<Integer,String>();
		for(AbstractMap.SimpleEntry<Integer, String> pair : pairs) {
			m_indexIdMap.put(pair.getKey(), pair.getValue());
		}
		m_nextNode = null;
		m_matcher = null;
	}
	
	private void append(PatternSequenceNode node) {
		if (null == m_nextNode)
			m_nextNode = node;
		else
			m_nextNode.append(node);
	}
	
	public PatternSequenceMatcher matcher(String text) {
		m_matcher = null;
		m_matcher = new PatternSequenceMatcher(this, text);
		return m_matcher;
	}
	
	public PatternSequenceNode append(String p, int flags, AbstractMap.SimpleEntry<Integer, String>... pairs) {
		PatternSequenceNode node = PatternSequenceNode.compile(p, flags, pairs);
		append(node);
		return this;
	}
	
	boolean hasNext() {
		return (null != m_nextNode);
	}
	
	PatternSequenceNode next() {
		return m_nextNode;
	}
	
	/*
	 * Match as much as possible of the text and return
	 * semantic names mapped to match groups
	 * @return full portion of text matched
	 */
	String match(String text, HashMap<String,String> resultMap) 
	{
		String fullMatch = null;
		Matcher m = m_pattern.matcher(text);
		if (m.matches()) {
			fullMatch = m.group();
			//System.out.println("matched "+m_pattern);
			for(int i = 1; i <= m.groupCount(); i++) {
				String val = m.group(i);
				if (null != val) {
					String id = m_indexIdMap.get(i);
					resultMap.put(id, val);
				}				
			}			
		}
		return fullMatch;
	}
	
	public String toString() {
		return toString("");
	}		
	
	public String toString(String s) {
		if (null == m_nextNode) {
			return s+" "+m_pattern.toString();
		}
		else {
			return s+" "+m_nextNode.toString(m_pattern.toString());
		}
	}
}

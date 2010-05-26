package barebones.interpreter;

import java.util.AbstractMap;
import java.util.Vector;
import java.util.regex.Pattern;

import barebones.event.DropCommand;
import barebones.event.ExamineCommand;
import barebones.event.GetInventoryCommand;
import barebones.event.GetTimeCommand;
import barebones.event.GiveCommand;
import barebones.event.MoveCommand;
import barebones.event.OpenCommand;
import barebones.event.QuitCommand;
import barebones.event.RestoreCommand;
import barebones.event.SaveCommand;
import barebones.event.TakeCommand;
import barebones.event.UnlockCommand;

/**
 * Represents a registry for mappings between patterns and commands
 * 
 * Eventually, this should use some sort of database to retrieve mappings.
 * @author dej
 *
 */
public class CommandPatternLoader 
{
	protected String m_rootPath;

	protected Vector<AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>>> m_inputCommandList;
			
	protected static AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>> makePatternPair(PatternSequenceNode node, Class<?> c) {
		return new AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>>(node, c);
	}
	
	protected void addPatternPair(PatternSequenceNode node, Class<?> c) {
		AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>> pair =
			makePatternPair(node, c);
		m_inputCommandList.add(pair);
	}
	
	protected static AbstractMap.SimpleEntry<Integer, String> grp(int i, String s) {
		return new AbstractMap.SimpleEntry<Integer, String>(i, s);
	}
	
	// makeVeryOptional
	protected static String m(int minChars, String text)
	{
		StringBuffer sb = new StringBuffer();
				
		int parenCount = 0;
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (i < minChars) {
				sb.append(c);
			}
			else {
				sb.append("(?:"+c);
				parenCount++;
			}
		}
		
		// Add ending parens
		for(int i = 0; i < parenCount; i++) {
			sb.append(")?");
		}
				
		return sb.toString();
	}

	protected static String d() {
		return d(false, false);
	}
	
	// getOptionalDirections
	protected static String d(boolean anchorLeft, boolean anchorRight)
	{
		String a = anchorLeft?"^":"";
		String r = anchorRight?"$":"";
		return ("("+a+m(1,"north")+r+"|"+a+
				m(1,"east")+r+"|"+a+
				m(1,"west")+r+"|"+a+
				m(1,"south")+r+"|"+a+
				m(1,"up")+r+"|"+a+
				m(1,"down")+r+")");
	}	
	
	public CommandPatternLoader(String rootPath) {
		m_rootPath = rootPath;
		m_inputCommandList = new Vector<AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>>>();
	}
	
	@SuppressWarnings("unchecked")
	public void load(String id) {
		// Right now just manually register - eventually get from data file
		addPatternPair(PatternSequenceNode.compile("go", Pattern.CASE_INSENSITIVE).
				append(d(false,true), Pattern.CASE_INSENSITIVE, grp(1, MoveCommand.MOVE_DIR)), MoveCommand.class);
		addPatternPair(PatternSequenceNode.compile(d(false,true), Pattern.CASE_INSENSITIVE, grp(1, MoveCommand.MOVE_DIR)), MoveCommand.class);		
		addPatternPair(PatternSequenceNode.compile(m(1, "quit"), Pattern.CASE_INSENSITIVE), QuitCommand.class);
		addPatternPair(PatternSequenceNode.compile(m(2, "time"), Pattern.CASE_INSENSITIVE), GetTimeCommand.class);
		addPatternPair(PatternSequenceNode.compile(m(1, "inventory"), Pattern.CASE_INSENSITIVE), GetInventoryCommand.class);
		addPatternPair(PatternSequenceNode.compile(m(2, "save"), Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, SaveCommand.SAVE_GAMEID)), SaveCommand.class);

		addPatternPair(PatternSequenceNode.compile(m(1, "restore"), Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, RestoreCommand.LOAD_GAMEID)), RestoreCommand.class);
		
		addPatternPair(PatternSequenceNode.compile(m(2, "drop"), Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, DropCommand.DROP_ITEM)), DropCommand.class);

		addPatternPair(PatternSequenceNode.compile(m(1, "open"), Pattern.CASE_INSENSITIVE).
				append(d(false,true), Pattern.CASE_INSENSITIVE, grp(1, OpenCommand.OPEN_DIR)), OpenCommand.class);

		addPatternPair(PatternSequenceNode.compile(m(2, "take"), Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, TakeCommand.TAKE_ITEM)), TakeCommand.class);

		// Order matters - always order patterns from most specific to least specific
		addPatternPair(PatternSequenceNode.compile(m(1, "give"), Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, GiveCommand.GIVE_ITEM)).
				append("to", Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, GiveCommand.GIVE_RECIP)), GiveCommand.class);
		/*
		addPatternPair(PatternSequenceNode.compile(m(1, "give"), Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, GiveCommand.GIVE_ITEM)).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, GiveCommand.GIVE_RECIP)), GiveCommand.class);
		*/

		addPatternPair(PatternSequenceNode.compile(m(2, "unlock"), Pattern.CASE_INSENSITIVE).
				append(d(false,true), Pattern.CASE_INSENSITIVE, grp(1, UnlockCommand.UNLOCK_DIR)).
				append("with", Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, UnlockCommand.UNLOCK_ITEM)), UnlockCommand.class);
	
		addPatternPair(PatternSequenceNode.compile(m(2, "examine"), Pattern.CASE_INSENSITIVE).
				append("([a-zA-Z]+)", Pattern.CASE_INSENSITIVE, grp(1, ExamineCommand.EXAM_TARGET)), ExamineCommand.class);
		
		//addPatternPair(PatternSequenceNode.compile(m(5, "intro"), Pattern.CASE_INSENSITIVE), GetIntroCommand.class);
	}
	
	public Vector<AbstractMap.SimpleEntry<PatternSequenceNode, Class<?>>> getCommandPatternList() {
		return m_inputCommandList;
	}
}

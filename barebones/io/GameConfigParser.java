package barebones.io;

import java.util.HashMap;

import utils.io.LinearParser;
import utils.io.ParsedBlock;

public class GameConfigParser extends LinearParser 
{
	public static final String SECONDS_PER_TICK = "game.spt";
	public static final String HOURS_PER_DAY = "game.hpd";
	public static final String TIME_TEXT = "game.timetext";
	public static final String STARTING_ROOM_ID = "game.startrmid";
	public static final String SPECIALS_DIR = "specials/";
	public static final String SUBCMD_ONLY_TXT = "game.subcmdonlytxt";

	protected static GameConfigParser sm_gcParser;
	
	static {
		sm_gcParser = null;
	}
	
	public static GameConfigParser instance()
	{
		if (null == sm_gcParser) {
			sm_gcParser = new GameConfigParser();
		}
		return sm_gcParser;
	}
		
	// For string configs
	protected HashMap<String,Object> m_configMap;
	
	protected GameConfigParser()
	{
		super("UTF8", '#', false, true/*parse numbers*/, true/*quote char*/, "GameConfigParser");

		setVerbose(false);

		setHeader("BBEGameConfigFormat");		
				
		m_configMap = new HashMap<String,Object>();

		boolean ok = true;
		/*
		Vector<Class> vals;

		vals = new Vector<Class>();
		vals.addElement(String.class); // key
		vals.addElement(String.class); // value
		 */
		ok = addSingleValueTag(SECONDS_PER_TICK, Long.class);
		ok = ok && addSingleValueTag(HOURS_PER_DAY, Integer.class);
		ok = ok && addSingleValueTag(TIME_TEXT, String.class);
		ok = ok && addSingleValueTag(STARTING_ROOM_ID, Integer.class);
		ok = ok && addSingleValueTag(SUBCMD_ONLY_TXT, String.class);
		
		if (!ok)
		{
			System.err.println("GameConfigParser::GameConfigParser: failed to add parse params");
		}		
	}
	
	public HashMap<String,Object> load(String path)
	{
		m_configMap.clear();
		parseFile(path);
		
		HashMap<String,Object> mapCopy = new HashMap<String,Object>(m_configMap);
		
		return mapCopy;
	}

	protected ParsedBlock nextParsedBlock()
	{
		return  null;
	}

	protected void handleParsedBlock(ParsedBlock pb)
	{
		String l_tag = pb.m_tag;
		
		if (l_tag.equals(SECONDS_PER_TICK)) {
			Long val = (Long)pb.m_values.elementAt(0);
			m_configMap.put(l_tag, val);
		}
		else if (l_tag.equals(HOURS_PER_DAY)) {
			Integer val = (Integer)pb.m_values.elementAt(0);
			m_configMap.put(l_tag, val);
		}
		else if (l_tag.equals(TIME_TEXT)) {
			String val = (String)pb.m_values.elementAt(0);
			m_configMap.put(l_tag, val);
		}
		else if (l_tag.equals(STARTING_ROOM_ID)) {
			Integer val = (Integer)pb.m_values.elementAt(0);
			m_configMap.put(l_tag, val);
		}
		else if (l_tag.equals(SUBCMD_ONLY_TXT)) {
			String val = (String)pb.m_values.elementAt(0);
			m_configMap.put(l_tag, val);
		}
	}	
}

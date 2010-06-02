package barebones.interpreter;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import barebones.engine.GameEngineAccessor;
import barebones.event.InterpretDelayedCommand;
import barebones.event.UserCommand;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.Tree;

public class LexicalizedParserInterpreter 
{
	private static final String ROOT_KEY = "ROOT";
	private static final String SENTENCE_KEY = "S";
	private static final String VERB_PHRASE_KEY = "VP";
	/*
	private static final String NOUN_PHRASE_KEY = "NP";
	private static final String PREP_PHRASE_KEY = "PP";
	private static final String ADJECTIVE_KEY = "JJ";
	private static final String NOUN_KEY = "NN";
	private static final String VERB_KEY = "VB";
	private static final String DETERMINATIVE_KEY = "DT";
	*/
	
	protected GameEngineAccessor m_engineRef;
	protected CommandPatternLoader m_patternLoader;
	protected HashMap<String,Pattern> m_patternCache;
	protected LexicalizedParser m_parser;
	
	protected Tree processSentence(LexicalizedParser lp, String sentence) {		
	    TokenizerFactory<? extends HasWord> tokenizerFactory = PTBTokenizer.factory(false, new CoreLabelTokenFactory());
	    List<? extends HasWord> rawWords = tokenizerFactory.getTokenizer(new StringReader(sentence)).tokenize();
	    lp.parse(rawWords);
	    Tree bestParse = lp.getBestParse();
	    
	    return bestParse;
	}
	
	protected UserCommand processVerbPhrase(Tree t)
	{
		return null;
	}
	
	protected void processNounPhrase(Tree t)
	{
		
	}

	protected UserCommand interpretAsCommand(Tree t)
	{
		if (t.isPhrasal() && (VERB_PHRASE_KEY == t.value())) {
			return processVerbPhrase(t);
		}
		else if (t.isPhrasal()) {
			if (ROOT_KEY == t.value() || SENTENCE_KEY == t.value()) {
				for(Tree child : t.children()) {
					return interpretAsCommand(child);
				}
			}
		}
		
		throw new UnidentifiedCommandException("");
	}
	
	protected Pattern getPattern(String patStr) {
		if (!m_patternCache.containsKey(patStr)) {
			Pattern pattern = Pattern.compile(patStr, Pattern.CASE_INSENSITIVE);
			m_patternCache.put(patStr, pattern);
		}
		return m_patternCache.get(patStr);
	}
		
	public LexicalizedParserInterpreter(GameEngineAccessor engineRef)
	{
		m_engineRef = engineRef;
		
		String parserPath = "/usr/local/lib/stanford-parser/englishPCFG.ser.gz";
		
	    m_parser = new LexicalizedParser(parserPath); //"/usr/local/lib/stanford-parser/englishPCFG.ser.gz");
	    m_parser.setOptionFlags(new String[]{"-outputFormat", "penn,typedDependenciesCollapsed", "-retainTmpSubcategories"});
	}
	
	public UserCommand interpret(InterpretDelayedCommand idc)
	{
		String text = idc.getTarget();
		
		//System.out.println(text);
		//System.out.println(expandedText);

		Tree parseTree = processSentence(m_parser, text);
	    
	    parseTree.pennPrint();
	    System.out.println();
	    /*
	    UserCommand cmd = interpretAsCommand(parseTree);
	    		
		return cmd;
		*/
	    
	    return null;
	}
}

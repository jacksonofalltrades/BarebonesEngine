package barebones.interpreter;

import java.io.ByteArrayInputStream;

import barebones.engine.GameEngineAccessor;
import barebones.event.InterpretDelayedCommand;
import barebones.event.UserCommand;
import barebones.interpreter.parsers.DefaultInterpreter;
import barebones.interpreter.parsers.ParseException;

public class ConceptParserInterpreter 
{
	protected GameEngineAccessor m_engineRef;
	protected DefaultInterpreter m_parser;
	
	public ConceptParserInterpreter(GameEngineAccessor engineRef)
	{
		m_engineRef = engineRef;
	}
	
	public UserCommand interpret(InterpretDelayedCommand idc)
	{
		String text = idc.getTarget();
		
        ByteArrayInputStream bastream = new ByteArrayInputStream(text.getBytes());
        
        if (null == m_parser) {
        	m_parser = new DefaultInterpreter(bastream);
        }
        else {
        	m_parser.ReInit(bastream);
        }
		UserCommand cmd = null;
		try {
			cmd = m_parser.interpret();
		} catch (ParseException e) {
			throw new UnidentifiedCommandException("I don't understand.");
		}
		return cmd;
	}
}

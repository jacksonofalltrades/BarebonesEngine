package barebones.event;

import barebones.engine.SubcommandContextImpl;
import barebones.io.ResponseContent;

public class YesNoSubcommand extends SubcommandContextImpl
{
	protected static final String CHOICES = "%s Please choose Yes or No";
	protected boolean m_answer;
	
	public YesNoSubcommand(UserCommand source)
	{
		super(source, String.format(CHOICES, source.getSubcommandDesc()));
		m_answer = false;
	}

	public UserCommand execute(Subcommand cmd) {
		// Make sure command parses into yes or no
		// and store that result and set as completed
		String target = cmd.getTarget();
		
		// For now, ghetto parsing
		if (null != target && target.toLowerCase().equals("yes"))
		{
			m_answer = true;
			m_completed = true;
			return m_source;
		}
		else if (null != target && target.toLowerCase().equals("no")){
			m_answer = false;
			m_completed = true;
			return m_source;
		}
		else {
			cmd.addResponse(ResponseContent.text(getContextDesc()));
			return null;
		}
	}
	
	public boolean result()
	{
		return m_answer;
	}
}

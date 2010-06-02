package barebones.engine;

import java.util.HashSet;

import barebones.event.ExamineCommand;
import barebones.event.GameEvent;
import barebones.event.GameEventListener;
import barebones.event.GetIntroCommand;
import barebones.event.GetTimeCommand;
import barebones.event.InterpretDelayedCommand;
import barebones.event.MoveCommand;
import barebones.event.QuitCommand;
import barebones.event.RestoreCommand;
import barebones.event.SaveCommand;
import barebones.event.Subcommand;
import barebones.event.UserCommand;
import barebones.event.YesNoSubcommand;
import barebones.interpreter.ConceptParserInterpreter;
import barebones.interpreter.LexicalizedParserInterpreter;
import barebones.interpreter.UnidentifiedCommandException;
import barebones.io.ResponseContent;
import barebones.io.User;
import barebones.io.UserResponse;
import barebones.io.UserResponseImpl;
import barebones.io.dal.GameConfigLoader;
import barebones.world.object.Item;
import barebones.world.object.Room;

/*
 * For now, each instance of GameEngine runs for a single user.
 * All games are single player.
 * 
 * GameEngines can run within a GameServer or locally standalone
 */
public class GameEngine implements GameEventListener, GameEngineAccessor 
{
	protected boolean m_isRunning;
	protected String m_gameDataRootPath;
	protected String m_userId;
	protected GameConfig m_gameConfig;
	protected GameConfigLoader m_gameConfigParser;	
	protected WorldClock m_clock;
	protected Player m_player;
	protected RoomManager m_roomManager;
	protected ItemManager m_itemManager;
	protected SubcommandContext m_subcommandContext;
	protected ConceptParserInterpreter m_commandInterpreter;
			
	public GameEngine(String gameDataRootPath, String gameId, User user)
	{
		m_isRunning = false;
		m_userId = user.id();
		m_gameDataRootPath = gameDataRootPath+gameId+"/";
		
		m_gameConfig = new GameConfig(GameConfigLoader.instance().load(m_gameDataRootPath));
				
		m_player = new Player(this, user);
		
		m_roomManager = new RoomManager(this);
		m_itemManager = new ItemManager(this);
		
		m_subcommandContext = null;
		
		m_commandInterpreter = new ConceptParserInterpreter(this);
	}
	
	public String getGameDataRootPath()
	{
		return m_gameDataRootPath;
	}
	
	public void init()
	{				
		m_player.load();
		m_roomManager.load();
		m_itemManager.load();
							
		m_clock = new WorldClock(m_player.getCurrentTicks(), m_gameConfig.getSecondsPerTick(), m_gameConfig.getHoursPerDay(), this);
		m_isRunning = true;
	}
	
	public boolean restore(String savedGameId)
	{
		boolean ok = true;
		
		// First save just in case restore for this id fails
		m_player.save();
	
		m_player.reset();
		m_roomManager.reset();
		m_itemManager.reset();
		
		// Reload with savedGameId
		if (!m_player.load(savedGameId)) {
			// Restore with default (default load can never fail) and return failure
			m_player.load();
			ok = false;
		}

		m_roomManager.load();
		m_itemManager.load();		
		m_clock.reset(m_player.getCurrentTicks(), m_gameConfig.getSecondsPerTick(), m_gameConfig.getHoursPerDay());
		
		return ok;
	}
	
	public void shutdown() {
		if (m_isRunning) {
			m_player.save();
			
			m_isRunning = false;
		}
	}
	
	public GameConfig getGameConfig()
	{
		return m_gameConfig;
	}
	
	public Integer getStartingRoomId() {
		return m_gameConfig.getStartingRoomId();
	}
			
	public String getRegionFor(int roomId)
	{
		return m_roomManager.getRegionFor(roomId);
	}
	
	public void setSubcommandContext(SubcommandContext ctx)
	{
		m_subcommandContext = ctx;
	}
	
	public String parseTarget(UserCommand cmd)
	{
		// TODO: Do actual parsing
		String origTarget = cmd.getTarget();
		if (null == origTarget) {
			return "";
		}
		
		return origTarget;
	}
	
	public String parseNPC(String npcDesc) {
		// TODO: Do actual parsing
		return npcDesc;
	}
	
	public Player getPlayer()
	{
		return m_player;
	}
	
	public Room getPlayerRoom()
	{
		return m_roomManager.getPlayerRoom();
	}
	
	public Item getItem(String id) {
		return m_itemManager.getItem(id);
	}
	
	public String getUserId()
	{
		return m_userId;
	}

	public WorldClock getClock()
	{
		return m_clock;
	}

	public String search(HashSet<String> itemList, String itemId)
	{
		return m_itemManager.search(itemList, itemId);
	}
		
	public void notify(GameEvent event)
	{
		if (false == m_isRunning) {
			System.err.println("GameEngine { path=["+m_gameDataRootPath+"], user=["+m_userId+"] } is not running");
			return;
		}
		
		// Always handle subcommand context first if it exists
		if (null != m_subcommandContext && !m_subcommandContext.isCompleted()) {
			if (Subcommand.class.isInstance(event)) {
				Subcommand scmd = (Subcommand)event;
				
				UserCommand source = m_subcommandContext.execute(scmd);
				if (null != source) {
					this.notify(source);
					UserResponse l_resp = source.getResponse();
					scmd.addResponse(l_resp);
				}
			}
			else if (UserCommand.class.isInstance(event)) {
				UserCommand cmd = (UserCommand)event;
				cmd.setRequiresSubcommand(true);
				cmd.addResponse(ResponseContent.text(m_subcommandContext.getContextDesc()));
			}
		}
		else if (UserCommand.class.isInstance(event))
		{
			UserCommand tempCmd = (UserCommand)event;
			UserCommand cmd = tempCmd;
			
			// Check to see if the command is a InterpretDelayedCommand
			if (InterpretDelayedCommand.id == cmd.id()) {
				InterpretDelayedCommand idcCmd = (InterpretDelayedCommand)cmd;
				
				try {
					cmd = m_commandInterpreter.interpret(idcCmd);
				}
				catch(UnidentifiedCommandException uce) {
					cmd.addResponse(ResponseContent.text(uce.getMessage()));
					return;
				}
			}
			
			System.out.println(cmd.toString());
			
			// Handle all other game-level commands here
			if (cmd.id().equals(GetTimeCommand.id))
			{
				String timeText = m_gameConfig.getTimeText();
				long h = m_clock.getCurrentHours();
				long m = m_clock.getCurrentMinutes();
				long s = m_clock.getCurrentSeconds();
				long mDiff = m - (h*60);
				long sDiff = s - (m*60);
				String time = h+" hour"+(h!=1?"s, ":", ")+mDiff+" minute"+(m!=1?"s, ":", ")+"and "+sDiff+" seconds";
				
				cmd.addResponse(ResponseContent.text(time+timeText));
			}
			else if (cmd.id().equals(QuitCommand.id)) {
				// At some point, add subcommand for "Are you sure you want to quit?"
				// Actually, probably let clients do that...
				
				shutdown();
				
				cmd.addResponse(ResponseContent.text(m_gameConfig.getQuitText()));
				if (tempCmd != cmd) {
					tempCmd.addResponse(cmd.getResponse());
				}
				return;
			}
			else if (cmd.id().equals(SaveCommand.id)) {
				m_player.save(cmd.getTarget());
				ResponseContent content = new ResponseContent(m_gameConfig.getSaveText());
				content.formatText(cmd.getTarget());
				cmd.addResponse(content);
			}
			else if (cmd.id().equals(RestoreCommand.id)) {
				if (null != m_subcommandContext)
				{
					if (m_subcommandContext.isSource(cmd)) {
						cmd.clearResponses();
						if (m_subcommandContext.isCompleted()) {
							cmd.setRequiresSubcommand(false);
							YesNoSubcommand yns = (YesNoSubcommand)m_subcommandContext;
							m_subcommandContext = null;
							if (yns.result()) {
								if (this.restore(cmd.getTarget())) {
									ExamineCommand exCmd = new ExamineCommand();
									this.notify(exCmd);
									UserResponse l_resp = exCmd.getResponse();
									cmd.addResponse(l_resp);
								}
								else {
									cmd.addResponse(new UserResponseImpl(ResponseContent.formattedText(GameConfig.RESTORE_FAIL_TEXT, cmd.getTarget())));
								}
							}
							else {
								cmd.addResponse(new UserResponseImpl(ResponseContent.text(cmd.getCancelText())));
							}
						}
					}
					else {
						// UNDEFINED STATE!
						// Should NEVER BE ABLE TO GET HERE
					}
				}
				else {
					this.setSubcommandContext(new YesNoSubcommand(cmd));
					cmd.setRequiresSubcommand(true);
					cmd.addResponse(ResponseContent.text(this.m_subcommandContext.getContextDesc()));
				}
			}
			else if (cmd.id().equals(GetIntroCommand.id)) {
				String text = m_gameConfig.getIntroText();
				cmd.addResponse(ResponseContent.text(text));
			}
			else {
				
				// Pass through all managers first
				m_roomManager.filter(cmd);
				m_itemManager.filter(cmd);
				// m_creatureManager.filter(cmd);
				m_player.filter(cmd);

				m_roomManager.process(cmd);				
				m_itemManager.process(cmd);
				// m_creatureManager.process(cmd);
				m_player.process(cmd);				
				
				// Special case for SubcommandContext
				if (false == cmd.requiresSubcommand())
				{
					// If no changed state and no messages, must be a command we don't recognize
					if (!cmd.changedPlayerState() && cmd.getResponseCount() <= 0) {
						// Nothing happened message
						cmd.addResponse(new UserResponseImpl("Nothing happens."));
					}
					
					// Handle auto-ExamineCommand if it was a Move
					if (cmd.isSuccessful() && MoveCommand.class.isInstance(cmd)) {
						ExamineCommand exCmd = new ExamineCommand();
						this.notify(exCmd);
						UserResponse l_resp = exCmd.getResponse();
						cmd.addResponse(l_resp);
					}
					
				}
				
				if (cmd.changedPlayerState()) {
					m_player.updatePlayerState(cmd.getPlayerState());
				}

				if (cmd.isSuccessful() && cmd.causesTick()) {
					m_clock.tick();
				}
			}
			if (tempCmd != cmd) {
				tempCmd.addResponse(cmd.getResponse());
			}
		}
	}
}

package barebones.engine;

import barebones.event.GameEventListener;
import barebones.event.TickEvent;

/*
 * This class keeps track of time in the game world.
 * Internally it has a "tick" count which indicates how many
 * units of time have passed since the game began.
 * 
 * This class is initialized with the scale, that is, how many
 * world-seconds have passed with each tick. Depending on the
 * requirements of a particular game, this could be anywhere
 * between 1 second and 1 year, though likely will be closer
 * to a few minutes.
 * 
 * The clock can be used to manage time-driven events in the world
 * as well as allowing the game world to vary based on time of day,
 * season of year, etc.
 * 
 * Eventually this class can be extended to be able to provide a mapping
 * of things like name of days, months, seasons, to add more realism.
 * 
 * For now it will just keep track of ticks and allow retrieval of "seconds"
 * passed.
 */
public class WorldClock 
{
	protected static final String CLOCK_CONFIG = "spt";
	protected long m_tick;
	protected long m_secondsPerTick;
	protected int m_hoursPerDay;
	
	protected GameEventListener m_eventListener;
	
	public WorldClock(long ticksPassed, long secondsPerTick, int hoursPerDay, GameEventListener listener)
	{
		m_tick = ticksPassed;
		m_secondsPerTick = secondsPerTick;
		m_hoursPerDay = hoursPerDay;
		m_eventListener = listener;		
	}
	
	public void reset(long ticksPassed, long secondsPerTick, int hoursPerDay)
	{
		m_tick = ticksPassed;
		m_secondsPerTick = secondsPerTick;
		m_hoursPerDay = hoursPerDay;
	}

	public long getCurrentTicks()
	{
		return m_tick;
	}
	
	public long getCurrentSeconds()
	{
		return m_tick*m_secondsPerTick;
	}
	
	public long getCurrentMinutes()
	{
		long currSecs = getCurrentSeconds();
		float currSecsf = (float)currSecs;
		return (long)Math.floor(currSecsf/60.0f);
	}
	
	public long getCurrentHours()
	{
		long currSecs = getCurrentSeconds();
		float currSecsf = (float)currSecs;
		return (long)Math.floor(currSecsf/3600.0f);
	}
	
	/**
	 * For user-driven turn-based games, allow a manual
	 * method for making the clock tick
	 */
	public synchronized void tick()
	{
		m_tick++;
		
		m_eventListener.notify(new TickEvent(getCurrentSeconds()));
	}
}

package barebones.engine;

import barebones.world.data.GameConfigDataBean;

public class GameConfig
{
	public static final String RESTORE_FAIL_TEXT = "Failed to restore game with id '%s'";

	protected GameConfigDataBean data;
	
	public GameConfig(GameConfigDataBean data) {
		this.data = data;
	}
	
	public long getSecondsPerTick() {
		return this.data.secondsPerTick;
	}
	
	public int getHoursPerDay() {
		return this.data.hoursPerDay;
	}
	
	public String getTimeText() {
		return this.data.timeText;
	}

	public int getStartingRoomId() {
		return this.data.startingRoomId;
	}
	
	public String getSubcmdOnlyText() {
		return this.data.subcmdOnlyText;
	}
	
	public String getQuitText() {
		return this.data.quitText;
	}
	
	public String getSaveText() {
		return this.data.saveText;
	}
	
	public String getIntroText() {
		return this.data.introText;
	}
}

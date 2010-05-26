package barebones.world.data;

import barebones.io.dal.ObjectMarshaller;

public class GameConfigDataBean extends WORootDataBean {
	
	static {
		// no filter, stored in game-root-dir (no subdir path, thus "")
		ObjectMarshaller.registerBean(GameConfigDataBean.class, null, "");
	}
	
	public long secondsPerTick;
	public int hoursPerDay;
	public String timeText;
	public int startingRoomId;
	public String subcmdOnlyText;
	public String quitText;
	public String saveText;
	public String introText;
	
	public GameConfigDataBean() {		
	}
	
	public GameConfigDataBean(GameConfigDataBean other) {
		this.secondsPerTick = other.secondsPerTick;
		this.hoursPerDay = other.hoursPerDay;
		this.timeText = other.timeText;
		this.startingRoomId = other.startingRoomId;
		this.subcmdOnlyText = other.subcmdOnlyText;
		this.quitText = other.quitText;
		this.saveText = other.saveText;
		this.introText = other.introText;
	}
	
	public void setsecondsPerTick(long spt) {
		secondsPerTick = spt;
	}
	
	public long getsecondsPerTick() {
		return secondsPerTick;
	}
	
	public void sethoursPerDay(int hpd) {
		hoursPerDay = hpd;
	}
	
	public int gethoursPerDay() {
		return hoursPerDay;
	}
	
	public void settimeText(String tt) {
		timeText = tt;
	}
	
	public String gettimeText() {
		return timeText;
	}
	
	public void setstartingRoomId(int roomId) {
		startingRoomId = roomId;
	}
	
	public int getstartingRoomId() {
		return startingRoomId;
	}
	
	public void setsubcmdOnlyText(String sot) {
		subcmdOnlyText = sot;
	}
	
	public String getsubcmdOnlyText() {
		return subcmdOnlyText;
	}
	
	public void setquitText(String qt) {
		quitText = qt;
	}
	
	public String getquitText() {
		return quitText;
	}
	
	public void setsaveText(String st) {
		saveText = st;
	}
	
	public String getsaveText() {
		return saveText;
	}
	
	public void setintroText(String it) {
		introText = it;
	}
	
	public String getintroText() {
		return introText;
	}	
}

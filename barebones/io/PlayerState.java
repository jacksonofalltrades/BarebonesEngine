package barebones.io;

import java.util.Vector;

public class PlayerState {
	int rmid;
	long ticks;
	int score;
	int basehit;
	int baseac;
	int maxhp;
	int currhp;
	
	protected Vector<String> itemIdVec;
	
	public int getRoomId() { return rmid; }
	public long getTicks() { return ticks; }
	public int getScore() { return score; }
	public int getBaseHit() { return basehit; }
	public int getBaseAC() { return baseac; }
	public int getMaxHP() { return maxhp; }
	public int getCurrHP() { return currhp; }
	
	public Vector<String> getItemIdList() {
		return itemIdVec;
	}

	public PlayerState() {		
		itemIdVec = new Vector<String>();
	}
	
	public PlayerState(PlayerState other) {
		rmid = other.rmid;
		ticks = other.ticks;
		score = other.score;
		basehit = other.basehit;
		baseac = other.baseac;
		maxhp = other.maxhp;
		currhp = other.currhp;
		itemIdVec = new Vector<String>(other.itemIdVec);		
	}
	
	public void addItemId(String itemId) {
		itemIdVec.add(itemId);
	}	
}

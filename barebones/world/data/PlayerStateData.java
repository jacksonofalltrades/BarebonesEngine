package barebones.world.data;

import java.util.HashSet;

public class PlayerStateData 
{
	public int currRoomId;
	public long currTicks;
	public int currScore;
	public HashSet<String> inventory;

	public void setcurrRoomId(int cri) {
		currRoomId = cri;
	}
	
	public int getcurrRoomId() {
		return currRoomId;
	}
		
	public void setcurrTicks(long ticks) {
		currTicks = ticks;
	}
	
	public long getcurrTicks() {
		return currTicks;
	}
	
	public void setcurrScore(int score) {
		currScore = score;
	}
	
	public int getcurrScore() {
		return currScore;
	}
	
	public void setinventory(HashSet<String> inv) {
		inventory = inv;
	}
	
	public HashSet<String> getinventory() {
		return inventory;
	}
	
	public Object clone() {
		PlayerStateData copy = new PlayerStateData();
		copy.currRoomId = this.currRoomId;
		copy.currScore = this.currScore;
		copy.currTicks = this.currTicks;
		copy.inventory = new HashSet<String>(this.inventory);
		return copy;
	}
}

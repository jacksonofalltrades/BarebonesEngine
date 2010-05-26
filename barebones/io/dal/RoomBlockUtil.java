package barebones.io.dal;

import java.util.HashMap;
import java.util.Random;

import barebones.world.data.WORoomBean;
import barebones.world.data.WORoomBlockBean;

public class RoomBlockUtil 
{
	protected static Random sm_resourceRandomizer;
	
	// Rules for passages:
	// if x=0, no west
	// if x= xEnd, no east
	// if y=0, no north
	// if y= yEnd, no south
	// No up, no down
	// Otherwise:
	// n,e,w,s
	// w = curr-step
	// e = curr+step
	// n = curr - (width*step)
	// s = curr + (width*step)

	protected static int getWestPassageId(WORoomBlockBean block, int x, int y, int currId)
	{
		if (x>0) {
			return currId-block.step;
		}
		else {
			return -1;
		}
	}
	protected static int getEastPassageId(WORoomBlockBean block, int x, int y, int currId)
	{
		if (x<(block.width-1)) {
			return currId+block.step;
		}
		else {
			return -1;
		}
	}
	protected static int getNorthPassageId(WORoomBlockBean block, int x, int y, int currId)
	{
		if (y>0) {
			return currId-(block.width*block.step);
		}
		else {
			return -1;
		}
	}
	protected static int getSouthPassageId(WORoomBlockBean block, int x, int y, int currId)
	{
		if (y<(block.height-1)) {
			return currId+(block.width*block.step);
		}
		else {
			return -1;
		}
	}
	
	protected static void initRandomDescs(WORoomBlockBean block)
	{
		sm_resourceRandomizer = new Random(block.descSeed);
	}
	
	protected static String getNextRoomDesc(WORoomBlockBean block)
	{
		if (null != block.possibleDescs && (block.possibleDescs.size() > 0)) {
			int nextIndex = sm_resourceRandomizer.nextInt(block.possibleDescs.size());
			return block.possibleDescs.get(nextIndex);
		}
		else {
			return "";
		}
	}
	
	public static HashMap<String,WORoomBean> generateRoomsFromBlock(WORoomBlockBean block)
	{
		initRandomDescs(block);
		
		HashMap<String,WORoomBean> roomMap = new HashMap<String,WORoomBean>();
		
		WORoomBean currRoom;
		int w = block.width;
		int h = block.height;
		int startIndex = block.startIndex;
		int currId = startIndex;
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				currRoom = new WORoomBean();
				currRoom.roomId = currId;
				currRoom.id = String.valueOf(currId);
				currRoom.wid = getWestPassageId(block, x, y, currId);
				currRoom.eid = getEastPassageId(block, x, y, currId);
				currRoom.sid = getSouthPassageId(block, x, y, currId);
				currRoom.nid = getNorthPassageId(block, x, y, currId);
				currRoom.uid = -1;
				currRoom.did = -1;				
				currRoom.defaultDesc = getNextRoomDesc(block);				
				currRoom.detailId = block.detailId;
				
				roomMap.put(currRoom.id, currRoom);
				
				// Prime for next room id
				currId += block.step;				
			}
		}
		
		return roomMap;
	}
}

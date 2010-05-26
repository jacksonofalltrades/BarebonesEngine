package barebones.io.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import barebones.world.data.GameConfigDataBean;
import barebones.world.data.ObjectDetailBean;
import barebones.world.data.PlayerStateBean;
import barebones.world.data.PlayerStateData;
import barebones.world.data.ResponseContentBean;
import barebones.world.data.WODataBean;
import barebones.world.data.WODoorBean;
import barebones.world.data.WODoorLockBean;
import barebones.world.data.WOItemBean;
import barebones.world.data.WORegionBean;
import barebones.world.data.WORegionMapBean;
import barebones.world.data.WORoomBean;
import barebones.world.data.WORoomBlockBean;
import barebones.world.data.WORoomFeatureBean;
import barebones.world.data.WORoomFeatureMapBean;
import barebones.world.data.WORootItemBean;
import utils.io.xml.XMLDecoder;
import utils.io.xml.XMLEncoder;
import utils.io.xml.XMLProcessorFactory;

public class WORegionMarshallerTest {
	protected static final String ROOT_PATH = "/home/dej/svnroot/software/trunk/BarebonesEngine/barebones/io/test/";
	protected static XMLEncoder encoder;
	protected static XMLDecoder decoder;
		
	protected static ObjectDetailBean create(String id, 
			String preTriggerContent,
			String triggerContent,
			String postTriggerContent) {
		ObjectDetailBean bean = new ObjectDetailBean();
		
		bean.setid(id);
		bean.cmdKeyMap = new HashMap<String,ResponseContentBean>();
		bean.objKeyMap = new HashMap<String,ResponseContentBean>();
		bean.containedItemSet = new HashSet<String>();
		if (null != preTriggerContent) {
			bean.preTriggerContent = new ResponseContentBean(preTriggerContent);
		}
		if (null != triggerContent) {
			bean.triggerContent = new ResponseContentBean(triggerContent);
		}
		if (null != postTriggerContent) {
			bean.postTriggerContent = new ResponseContentBean(postTriggerContent);
		}
		return bean;
	}
	
	protected static void writePlayerState() {
		PlayerStateBean bean;
		
		bean = new PlayerStateBean();
		bean.id = "dej";
		bean.data = new PlayerStateData();
		bean.data.currRoomId = 1002;
		bean.data.currTicks = 10;
		bean.data.currScore = 100;
		bean.data.inventory = new HashSet<String>();
		bean.data.inventory.add("silverkey");
		bean.data.inventory.add("sword");
		bean.data.inventory.add("shield");
		bean.worldDeltas = new HashMap<String,WODataBean>();
		encoder.write(ROOT_PATH+"games/"+bean.getid()+".xml", bean.getid(), bean);

		bean = new PlayerStateBean();
		bean.id = "test";
		bean.data = new PlayerStateData();
		bean.data.currRoomId = 1010;
		bean.data.currTicks = 0;
		bean.data.currScore = 5;
		bean.data.inventory = new HashSet<String>();
		bean.data.inventory.add("goldkey");
		bean.data.inventory.add("sword");
		bean.data.inventory.add("shield");
		bean.worldDeltas = new HashMap<String,WODataBean>();
		encoder.write(ROOT_PATH+"games/"+bean.getid()+".xml", bean.getid(), bean);		
	}
	
	protected static void writeObjectDetails() {
		ObjectDetailBean bean;
		bean = create("Door", null, null, null);
		bean.objKeyMap.put("door.closedtxt", new ResponseContentBean("The door %s is closed. You cannot go that way."));
		bean.objKeyMap.put("door.desctxt", new ResponseContentBean("There is %s door %s."));
		bean.objKeyMap.put("door.opentxt", new ResponseContentBean("You open the door %s."));
		bean.objKeyMap.put("door.alreadyopen", new ResponseContentBean("The door %s is already opened."));
		bean.objKeyMap.put("door.openadj", new ResponseContentBean("an open"));
		bean.objKeyMap.put("door.closedadj", new ResponseContentBean("a closed"));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);

		bean = create("door2", null, null, null);
		bean.objKeyMap.put("door.openadj", new ResponseContentBean("a slightly ajar"));
		bean.objKeyMap.put("door.closedadj", new ResponseContentBean("a sealed"));
		bean.cmdKeyMap.put("EXAM", new ResponseContentBean("There is %s ornately carved wooden door %s."));
		bean.cmdKeyMap.put("OPEN", new ResponseContentBean("You rip the door %s off it's hinges in a fury."));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);

		bean = create("DoorLock", null, null, null);
		bean.objKeyMap.put("lock.alreadyopen", new ResponseContentBean("The door %s is already unlocked."));
		bean.objKeyMap.put("lock.badkey", new ResponseContentBean("%s is not the right key for the door %s"));
		bean.objKeyMap.put("lock.lockedtxt", new ResponseContentBean("You cannot open the door %s. It is locked."));
		bean.objKeyMap.put("lock.donthaveitem", new ResponseContentBean("You do not have %s."));
		bean.objKeyMap.put("lock.unlocktxt", new ResponseContentBean("You unlock the door %s with %s."));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);
		
		bean = create("Item", null, null, null);
		bean.objKeyMap.put("item.stillequipped", new ResponseContentBean("You cannot do that. This item is still equipped."));
		bean.objKeyMap.put("item.notowned", new ResponseContentBean("You do not have %s."));
		bean.objKeyMap.put("item.recipnotpresent", new ResponseContentBean("%s is not here."));
		bean.objKeyMap.put("item.cannotfind", new ResponseContentBean("%s is not here."));
		bean.objKeyMap.put("item.drop", new ResponseContentBean("You drop %s."));
		bean.objKeyMap.put("item.give", new ResponseContentBean("You give %s to %s."));
		bean.objKeyMap.put("item.take", new ResponseContentBean("You take %s."));
		bean.objKeyMap.put("item.roomdesc", new ResponseContentBean("There is %s here."));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);
		
		bean = create("lock3", null, null, null);
		bean.objKeyMap.put("lock.alreadyopen", new ResponseContentBean("The door %s is already unlocked."));
		bean.objKeyMap.put("lock.badkey", new ResponseContentBean("%s is not even close to the key for the door."));
		bean.objKeyMap.put("lock.lockedtxt", new ResponseContentBean("The door is locked, fool!"));
		bean.cmdKeyMap.put("UNLK", new ResponseContentBean("You shove the key in the lock and it clicks open."));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);

		bean = create("Player", null, null, null);
		bean.objKeyMap.put("player.invheadertxt", new ResponseContentBean("You are carrying the following items:"));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);
		
		bean = create("Room", null, null, null);
		bean.objKeyMap.put("room.invaliddirtxt", new ResponseContentBean("There is no passage %s"));
		bean.objKeyMap.put("room.notvaliddirmsg", new ResponseContentBean("%s is not a valid direction"));
		bean.objKeyMap.put("room.onepsgmsg", new ResponseContentBean("There is a passage %s"));
		bean.objKeyMap.put("room.multipsgmsg", new ResponseContentBean("There are passages %s"));
		bean.objKeyMap.put("room.movetxt", new ResponseContentBean("You amble %s."));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);
		
		bean = create("room1002", null, null, null);
		bean.cmdKeyMap.put("MOVE", new ResponseContentBean("You slowly glide %s."));
		bean.containedItemSet.add("box");
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);
		
		bean = create("stoneroom", null, null, null);
		bean.cmdKeyMap.put("EXAM", new ResponseContentBean("You enter a large stone chamber. There are finely detailed carvings of gargoyles along the walls."));
		encoder.write(ROOT_PATH+"details/"+bean.getid()+".xml", bean.getid(), bean);
	}
	
	protected static void writeItems() {
		WORootItemBean rib;
		rib = new WORootItemBean(new WOItemBean("box",
				"a wooden box",
				"wooden box",
				"This wooden box has ashen carvings in a strange language and a bronze latch. There is a keyhole below the latch.",	
				0,
				0,
				false
				));
		encoder.write(ROOT_PATH+"items/"+rib.getid()+".xml", rib.getid(), rib);		

		rib = new WORootItemBean(new WOItemBean("goldkey",
				"a golden key",
				"golden key",
				"This ornate golden key is heavy and very probably solid gold. It has the King's crest on it.",
				0,
				0,
				false
		));
		encoder.write(ROOT_PATH+"items/"+rib.getid()+".xml", rib.getid(), rib);		
		
		rib = new WORootItemBean(new WOItemBean("shield",
				"a tarnished bronze shield",				
				"tarnished bronze shield",
				"This shield needs a good polishing, but it's still quite sturdy.",
				0,
				0,
				false
		));
		encoder.write(ROOT_PATH+"items/"+rib.getid()+".xml", rib.getid(), rib);		

		rib = new WORootItemBean(new WOItemBean("silverkey",
				"a silver key",
				"silver key",
				"This silver key is very simple, with a cloverleaf at the end.",
				0,
				0,
				false
		));
		encoder.write(ROOT_PATH+"items/"+rib.getid()+".xml", rib.getid(), rib);		
			
		rib = new WORootItemBean(new WOItemBean("sword",
				"a dented steel sword",
				"dented steel sword",
				"This steel sword has some very obvious nicks along the blade. You might want to try to find a better one.",
				0,
				0,
				false
		));
		encoder.write(ROOT_PATH+"items/"+rib.getid()+".xml", rib.getid(), rib);		
	}

	protected static void writeGameConfig() {
		GameConfigDataBean gameConfig = new GameConfigDataBean();
		gameConfig.setid("game");
		gameConfig.secondsPerTick = 600;
		gameConfig.hoursPerDay = 30;
		gameConfig.timeText = " have passed since you began your quest.";
		gameConfig.startingRoomId = 1010;
		gameConfig.subcmdOnlyText = "You cannot %s right now.";
		
		encoder.write(ROOT_PATH+gameConfig.getid()+".xml", gameConfig.getid(), gameConfig);		
	}
	
	protected static void writeFeatures() {
		WORoomFeatureMapBean featureMap = new WORoomFeatureMapBean();
		featureMap.setid("main-specials");

		featureMap.roomFeatureMap = new HashMap<String,Vector<WORoomFeatureBean>>();

		Vector<WORoomFeatureBean> vec1 = new Vector<WORoomFeatureBean>();
		vec1.add(new WODoorBean("door1", true, "N"));
		featureMap.roomFeatureMap.put("1010", vec1);

		Vector<WORoomFeatureBean> vec2 = new Vector<WORoomFeatureBean>();
		vec2.add(new WODoorBean("door2", false, "E"));
		featureMap.roomFeatureMap.put("1001", vec2);
		
		Vector<WORoomFeatureBean> vec3 = new Vector<WORoomFeatureBean>();
		vec3.add(new WODoorBean("door3", false, "S"));
		vec3.add(new WODoorLockBean("lock3", false, "S", "silverkey"));		
		featureMap.roomFeatureMap.put("1002", vec3);
		
		encoder.write(ROOT_PATH+featureMap.getid()+".xml", featureMap.getid(), featureMap);
	}
	
	protected static void writeRegions() {
		WORegionBean region = new WORegionBean();
		region.setid("main");
		
		HashMap<String,WORoomBean> vec = new HashMap<String,WORoomBean>();
		WORoomBean room;
		room = new WORoomBean(1010, "You enter a grey room.", 1001, 1020, -1, 1040, -1, -1, null);
		vec.put("1010", room);		
		room = new WORoomBean(1020, "You enter a blue room.", -1, -1, 1010, 1030, -1, -1, "stoneroom");
		vec.put("1020", room);
		room = new WORoomBean(1030, "You enter a peach room.", 1020, -1, 1040, 1050, -1, -1, null);
		vec.put("1030", room);
		room = new WORoomBean(1040, "You enter a green room.", 1010, 1030, -1, 1070, -1, -1, null);
		vec.put("1040", room);
		room = new WORoomBean(1001, "You enter a pale room.", -1, 1002, -1, 1010, -1, -1, null);
		vec.put("1001", room);
		room = new WORoomBean(1002, "You enter a cherry room.", -1, -1, 1001, 1020, -1, -1, null);
		vec.put("1002", room);
		
		region.setrooms(vec);
		
		// Add room blocks
		region.roomBlocks = new Vector<WORoomBlockBean>();
		
		WORoomBlockBean block1 = new WORoomBlockBean();
		block1.descSeed = 123;
		block1.possibleDescs = new Vector<String>();
		block1.possibleDescs.add("You are on a verdant hill");
		block1.possibleDescs.add("You are in a muddy pasture");
		block1.possibleDescs.add("You are in a grassy field");
		block1.possibleDescs.add("You are in a lightly forested grove of pine trees");
		block1.possibleDescs.add("You are in a green field with a small stream running through it");
		block1.possibleDescs.add("You are in a slightly scorched meadow");
		block1.setstartIndex(1100);
		block1.step = 1;
		block1.width = 20;
		block1.height = 20;
		region.roomBlocks.add(block1);

		WORoomBlockBean block2 = new WORoomBlockBean();
		block2.descSeed = 237;
		block2.possibleDescs = new Vector<String>();
		block2.possibleDescs.add("You are near a rocky cliff");
		block2.possibleDescs.add("You are on a wasted plateau");
		block2.possibleDescs.add("You are surrounded by rocky scrub");
		block2.possibleDescs.add("There is a dank, deep, mossy well here");
		block2.possibleDescs.add("You are on a brick-paved ruin of a town");
		block2.possibleDescs.add("You are in the ruins of an old mill");
		block2.setstartIndex(2000);
		block2.step = 1;
		block2.width = 10;
		block2.height = 4;
		region.roomBlocks.add(block2);
		
		WORoomBlockBean block3 = new WORoomBlockBean();
		block3.descSeed = 397;
		block3.possibleDescs = new Vector<String>();
		block3.possibleDescs.add("You are by the shore. The central river flows quickly by you");
		block3.possibleDescs.add("You are by the shore. The central river flows peacefully by you");
		block3.possibleDescs.add("You are by the shore. The central river carries dead things on it");
		block3.possibleDescs.add("You are by the shore. The central river seems clear now");
		block3.possibleDescs.add("You are by the shore. The central river is looking dirtier here");
		block3.possibleDescs.add("You are by the shore. The central river rushes by you");
		block3.setstartIndex(3000);
		block3.step = 1;
		block3.width = 1;
		block3.height = 20;
		region.roomBlocks.add(block3);
				
		encoder.write(ROOT_PATH+region.getid()+".xml", region.getid(), region);		
		
		region = new WORegionBean();
		region.setid("south");
		
		/*
		 * 
		 */
		
		vec = new HashMap<String,WORoomBean>();
		room = new WORoomBean(1050, "You enter a purple room.", 1030, -1, 1070, 1060, -1, -1, null);
		vec.put("1050", room);
		room = new WORoomBean(1060, "You enter a yellow room.", 1050, -1, -1, -1, -1, -1, null);
		vec.put("1060", room);
		room = new WORoomBean(1070, "You enter an orange room.", 1040, 1050, -1, -1, -1, -1, null);
		vec.put("1070", room);
		
		region.setrooms(vec);
		encoder.write(ROOT_PATH+region.getid()+".xml", region.getid(), region);		
	}
	
	protected static void writeRoomToRegionMap() {
		/*
		M 1001 main
		M 1002 main
		M 1010 main
		M 1020 main
		M 1030 main
		M 1040 main
		M 1050 south
		M 1060 south
		M 1070 south		
		*/
		
		WORegionMapBean map = new WORegionMapBean();
		map.roomToRegionMap = new HashMap<String,String>();
		map.roomToRegionMap.put("1001", "main");
		map.roomToRegionMap.put("1002", "main");
		map.roomToRegionMap.put("1010", "main");
		map.roomToRegionMap.put("1020", "main");
		map.roomToRegionMap.put("1030", "main");
		map.roomToRegionMap.put("1040", "main");
		map.roomToRegionMap.put("1050", "south");
		map.roomToRegionMap.put("1060", "south");
		map.roomToRegionMap.put("1070", "south");
		
		encoder.write(ROOT_PATH+"room2reg.xml", "room2reg", map);
	}
		
	public static void main(String[] args) {
		encoder = XMLProcessorFactory.instance().createEncoder();
		decoder = XMLProcessorFactory.instance().createDecoder();
		
		writeRegions();
		writeRoomToRegionMap();
		writeFeatures();
		writeGameConfig();
		writeObjectDetails();
		writeItems();
		writePlayerState();
	}
}

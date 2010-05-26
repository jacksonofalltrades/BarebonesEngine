package barebones.engine.test;

import barebones.engine.GameEngine;
import barebones.event.OpenCommand;
import barebones.event.RestoreCommand;
import barebones.event.SaveCommand;
import barebones.event.TakeCommand;
import barebones.event.UnlockCommand;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.User;
import barebones.io.UserImpl;
import barebones.io.UserResponse;
import barebones.world.data.WORegionMapBean;
import junit.framework.TestCase;

public class GameEngineTests extends TestCase {
	public static final boolean DEBUG = true;
	
	protected WORegionMapBean regionMap;
	
	protected User m_user;
	protected GameEngine m_engine;
		
	protected String getFullResponseString(UserCommand cmd)
	{
		StringBuffer respText = new StringBuffer();
		UserResponse resp = cmd.getResponse();
		for(ResponseContent content : resp.getResponseList()) {
			respText.append(content.text());
			respText.append("\n");
		}
		
		if (DEBUG) {
			System.out.println("<RESPONSE>");
			System.out.println(respText.toString());
			System.out.println("</RESPONSE>");		
		}		
		return respText.toString();
	}
	
	public void setUp()
	{
		m_user = new UserImpl("test");
		m_engine = new GameEngine("/home/dej/workspace/BarebonesEngine/barebones_testroot/", "testgame", m_user);
		m_engine.init();		
	}
	/*
	public void testMoveFourRooms()
	{
		MoveCommand cmd1 = new MoveCommand("E");
		MoveCommand cmd2 = new MoveCommand("S");
		MoveCommand cmd3 = new MoveCommand("W");
		MoveCommand cmd4 = new MoveCommand("N");
		MoveCommand cmd5 = new MoveCommand("N");
		MoveCommand cmd6 = new MoveCommand("E");
		
		m_engine.notify(cmd1);
		String lastResp = getFullResponseString(cmd1);
		assertEquals("You amble to the East.\nYou enter a large stone chamber. There are finely detailed carvings of gargoyles along the walls.\nThere are passages to the West, to the South.\n", lastResp);
		
		m_engine.notify(cmd2);
		lastResp = getFullResponseString(cmd2);
		assertEquals("You amble to the South.\nYou enter a peach room.\nThere are passages to the North, to the West, to the South.\n", lastResp);

		m_engine.notify(cmd3);
		lastResp = getFullResponseString(cmd3);
		assertEquals("You amble to the West.\nYou enter a green room.\nThere are passages to the North, to the East, to the South.\n", lastResp);

		m_engine.notify(cmd4);
		lastResp = getFullResponseString(cmd4);
		assertEquals("You amble to the North.\nYou enter a grey room.\nThere are passages to the North, to the East, to the South.\nThere is an open door to the North.\n", lastResp);

		m_engine.notify(cmd5);
		lastResp = getFullResponseString(cmd5);
		assertEquals("You amble to the North.\nYou enter a pale room.\nThere are passages to the East, to the South.\nThere is a sealed ornately carved wooden door to the East.\n", lastResp);

		m_engine.notify(cmd6);
		lastResp = getFullResponseString(cmd6);
		assertEquals("The door to the East is closed. You cannot go that way.\n", lastResp);

		GetTimeCommand tCmd = new GetTimeCommand();
		
		m_engine.notify(tCmd);
		lastResp = getFullResponseString(tCmd);
		assertEquals("0 hours, 50 minutes, and 0 seconds have passed since you began your quest.\n", lastResp);
	}
	
	public void testBadDirection()
	{
		MoveCommand cmd1 = new MoveCommand("U");
		MoveCommand cmd2 = new MoveCommand("D");
		MoveCommand cmd3 = new MoveCommand("W");

		m_engine.notify(cmd1);
		String lastResp = getFullResponseString(cmd1);
		assertEquals("There is no passage Above\n", lastResp);
		
		m_engine.notify(cmd2);
		lastResp = getFullResponseString(cmd2);
		assertEquals("There is no passage Below\n", lastResp);

		m_engine.notify(cmd3);
		lastResp = getFullResponseString(cmd3);
		assertEquals("There is no passage to the West\n", lastResp);
}
	
	public void testMoveAndGetTime()
	{
		MoveCommand cmd = new MoveCommand("E");
		
		m_engine.notify(cmd);
		
		
		// Test empty response
		assertEquals("You amble to the East.\nYou enter a large stone chamber. There are finely detailed carvings of gargoyles along the walls.\nThere are passages to the West, to the South.\n", getFullResponseString(cmd));
		
		GetTimeCommand timeCmd = new GetTimeCommand();
		
		m_engine.notify(timeCmd);

		// Test one tick
		String lastResp = getFullResponseString(timeCmd);
		assertEquals("0 hours, 10 minutes, and 0 seconds have passed since you began your quest.\n", lastResp);
		
		// Test a few ticks
		for(int i = 0; i < 100; i++) {
			cmd.clearResponses();
			m_engine.notify(cmd);
			timeCmd.clearResponses();
			m_engine.notify(timeCmd);
			lastResp = getFullResponseString(timeCmd);
			if (i == 0) {
				//assertEquals("0 hours, 0 minutes, and 0 seconds have passed since you began your quest.", getFullResponseString(timeCmd));
			}
			System.out.println(lastResp);
			System.out.println();
		}
		
		MoveCommand wCmd = new MoveCommand("W");
		m_engine.notify(wCmd);
	}
	
	public void testMoveBetweenRegions()
	{
		MoveCommand cmd1 = new MoveCommand("E");
		MoveCommand cmd2 = new MoveCommand("S");
		MoveCommand cmd3 = new MoveCommand("S");
		MoveCommand cmd4 = new MoveCommand("S");
		
		m_engine.notify(cmd1);

		m_engine.notify(cmd2);
		
		m_engine.notify(cmd3);

		m_engine.notify(cmd4);

		String lastResp = getFullResponseString(cmd4);
		assertEquals("You amble to the South.\nYou enter a yellow room.\nThere is a passage to the North.\n", lastResp);

	}
	
	public void testOpenDoor()
	{
		OpenCommand cmd1 = new OpenCommand("N");
		MoveCommand cmd2 = new MoveCommand("N");
		MoveCommand cmd3 = new MoveCommand("E");
		OpenCommand cmd4 = new OpenCommand("E");
		MoveCommand cmd5 = new MoveCommand("E");

		m_engine.notify(cmd1);
		String lastResp = getFullResponseString(cmd1);
		assertEquals("The door to the North is already opened.\n", lastResp);

		m_engine.notify(cmd2);
		lastResp = getFullResponseString(cmd2);
		assertEquals("You amble to the North.\nYou enter a pale room.\nThere are passages to the East, to the South.\nThere is a sealed ornately carved wooden door to the East.\n", lastResp);
		
		m_engine.notify(cmd3);
		lastResp = getFullResponseString(cmd3);
		assertEquals("The door to the East is closed. You cannot go that way.\n", lastResp);

		m_engine.notify(cmd4);
		lastResp = getFullResponseString(cmd4);
		assertEquals("You rip the door to the East off it's hinges in a fury.\n", lastResp);		

		m_engine.notify(cmd5);
		lastResp = getFullResponseString(cmd5);
		assertEquals("You amble to the East.\nYou enter a cherry room.\nThere are passages to the West, to the South.\nThere is a closed door to the South.\nThere is a wooden box here.\n", lastResp);		
	}
		
	public void testRoomWithItem()
	{
		User user2 = new UserImpl("dej");
		GameEngine engine2 = new GameEngine("/home/dej/workspace/BarebonesEngine/barebones_testroot/", user2);
		engine2.init();
		
		ExamineCommand cmd1 = new ExamineCommand();
		engine2.notify(cmd1);
		String lastResp = getFullResponseString(cmd1);
		assertEquals("You enter a cherry room.\nThere are passages to the West, to the South.\nThere is a closed door to the South.\nThere is a wooden box here.\n", lastResp);
		
		TakeCommand cmd2 = new TakeCommand("box");
		engine2.notify(cmd2);
		lastResp = getFullResponseString(cmd2);
		assertEquals("You take box.\n", lastResp);
		
		DropCommand cmd3 = new DropCommand("sword");
		engine2.notify(cmd3);
		lastResp = getFullResponseString(cmd3);
		assertEquals("You drop sword.\n", lastResp);
		
		SaveCommand cmd4 = new SaveCommand("myfirstsave");
		engine2.notify(cmd4);
		lastResp = getFullResponseString(cmd4);		
		assertEquals("You save your progress with the label [myfirstsave].\n", lastResp);
	}

	public void testDoorLock() {
		User user2 = new UserImpl("dej");
		GameEngine engine2 = new GameEngine("/home/dej/workspace/BarebonesEngine/barebones_testroot/", user2);
		engine2.init();

		OpenCommand cmd1 = new OpenCommand("S");
		UnlockCommand cmd2 = new UnlockCommand("S", "goldkey");
		UnlockCommand cmd3 = new UnlockCommand("S", "sword");
		UnlockCommand cmd4 = new UnlockCommand("S", "silverkey");
		UnlockCommand cmd5 = new UnlockCommand("S", "silverkey");
		MoveCommand cmd6 = new MoveCommand("S");
		OpenCommand cmd7 = new OpenCommand("S");
		MoveCommand cmd8 = new MoveCommand("S");
		//QuitCommand cmd9 = new QuitCommand();

		engine2.notify(cmd1);
		String lastResp = getFullResponseString(cmd1);
		assertEquals("The door is locked, fool!\n", lastResp);

		engine2.notify(cmd2);
		lastResp = getFullResponseString(cmd2);
		assertEquals("You do not have goldkey.\n", lastResp);
		
		engine2.notify(cmd3);
		lastResp = getFullResponseString(cmd3);
		assertEquals("sword is not even close to the key for the door.\n", lastResp);

		engine2.notify(cmd4);
		lastResp = getFullResponseString(cmd4);
		assertEquals("You shove the key in the lock and it clicks open.\n", lastResp);		

		engine2.notify(cmd5);
		lastResp = getFullResponseString(cmd5);
		assertEquals("The door to the South is already unlocked.\n", lastResp);

		engine2.notify(cmd6);
		lastResp = getFullResponseString(cmd6);
		assertEquals("The door to the South is closed. You cannot go that way.\n", lastResp);

		engine2.notify(cmd7);
		lastResp = getFullResponseString(cmd7);
		assertEquals("You open the door to the South.\n", lastResp);

		engine2.notify(cmd8);
		lastResp = getFullResponseString(cmd8);
		assertEquals("You slowly glide to the South.\nYou enter a large stone chamber. There are finely detailed carvings of gargoyles along the walls.\nThere are passages to the West, to the South.\n", lastResp);
				
		//engine2.notify(cmd9);		
		//lastResp = getFullResponseString(cmd9);
	}
	*/
	public void testRestoreGame() {
		User user2 = new UserImpl("foobar");
		GameEngine engine2 = new GameEngine("/home/dej/workspace/BarebonesEngine/barebones_testroot/", "testgame", user2);
		engine2.init();
		
		TakeCommand cmd2 = new TakeCommand("box");
		engine2.notify(cmd2);
		String lastResp = getFullResponseString(cmd2);
		assertEquals("box is not here.\n", lastResp);
		
		// Drop sword
		TakeCommand cmd3 = new TakeCommand("sword");
		engine2.notify(cmd3);
		lastResp = getFullResponseString(cmd3);
		assertEquals("You take sword.\n", lastResp);
	}
	
	public void testSaveDoorAndDoorLock() {
		User user2 = new UserImpl("dej");
		GameEngine engine2 = new GameEngine("/home/dej/workspace/BarebonesEngine/barebones_testroot/", "testgame", user2);
		engine2.init();
		
		UnlockCommand cmd1 = new UnlockCommand("S", "silverkey");
		engine2.notify(cmd1);
		String lastResp = getFullResponseString(cmd1);
		assertEquals("You shove the key in the lock and it clicks open.\n", lastResp);
		
		OpenCommand cmd2 = new OpenCommand("S");
		engine2.notify(cmd2);
		lastResp = getFullResponseString(cmd2);
		assertEquals("You open the door to the South.\n", lastResp);
		
		SaveCommand cmd3 = new SaveCommand("dandl");
		engine2.notify(cmd3);
		lastResp = getFullResponseString(cmd3);
		assertEquals("You save your progress with the label [dandl].\n", lastResp);
	}
	
	public void testRestoreDoorAndDoorLock() {
		User user2 = new UserImpl("dej");
		GameEngine engine2 = new GameEngine("/home/dej/workspace/BarebonesEngine/barebones_testroot/", "testgame", user2);
		engine2.init();
		
		RestoreCommand cmd1 = new RestoreCommand("dandl");
		engine2.notify(cmd1);
		String lastResp = getFullResponseString(cmd1);
		assertEquals("You enter a cherry room.\nThere are passages to the West, to the South.\nThere is an open door to the South.\nThere is a wooden box here.\n", lastResp);
	}
}

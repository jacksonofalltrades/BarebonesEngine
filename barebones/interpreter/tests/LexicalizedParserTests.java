package barebones.interpreter.tests;

import java.util.HashSet;

import barebones.engine.GameConfig;
import barebones.engine.GameEngineAccessor;
import barebones.engine.Player;
import barebones.engine.SubcommandContext;
import barebones.engine.WorldClock;
import barebones.event.InterpretDelayedCommand;
import barebones.event.UserCommand;
import barebones.interpreter.LexicalizedParserInterpreter;
import barebones.world.object.Item;
import barebones.world.object.Room;

import junit.framework.TestCase;

public class LexicalizedParserTests extends TestCase {
	class GameEngineAccessorStub implements GameEngineAccessor {

		public WorldClock getClock() {
			return null;
		}

		public GameConfig getGameConfig() {
			return null;
		}

		public String getGameDataRootPath() {
			return "/home/dej/workspace/BarebonesEngine/barebones_testroot/testgame/";
		}

		public Item getItem(String id) {
			return null;
		}

		public Player getPlayer() {
			return null;
		}

		public Room getPlayerRoom() {
			return null;
		}

		public String getRegionFor(int roomId) {
			return null;
		}

		public Integer getStartingRoomId() {
			return null;
		}

		public String getUserId() {
			return null;
		}

		public String parseNPC(String npcDesc) {
			return null;
		}

		public String parseTarget(UserCommand cmd) {
			return null;
		}

		public String search(HashSet<String> itemList, String itemId) {
			return null;
		}

		public void setSubcommandContext(SubcommandContext ctx) {
		}		
	}
	
	protected void interpretTest(LexicalizedParserInterpreter lpi, String toTest)
	{
		InterpretDelayedCommand idc = new InterpretDelayedCommand(toTest);
		lpi.interpret(idc);		
	}
	
	public void testInterpret()
	{
		GameEngineAccessor engine = new GameEngineAccessorStub();
		LexicalizedParserInterpreter lpi = new LexicalizedParserInterpreter(engine);

		interpretTest(lpi, "open the door to the east");
		interpretTest(lpi, "un e");
		interpretTest(lpi, "inv");
		interpretTest(lpi, "ti");
		interpretTest(lpi, "op s");
		interpretTest(lpi, "dr the axe");
		interpretTest(lpi, "sa game1");
		interpretTest(lpi, "res game2");
		interpretTest(lpi, "go u");
		interpretTest(lpi, "go e");
		interpretTest(lpi, "go w");
	}
}

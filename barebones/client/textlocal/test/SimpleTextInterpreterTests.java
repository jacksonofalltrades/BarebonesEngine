package barebones.client.textlocal.test;

import java.util.Random;
import java.util.Vector;

import barebones.client.ClientAction;
import barebones.client.ExecuteCommandAction;
import barebones.client.textlocal.RawTextInput;
import barebones.client.textlocal.SimpleTextInterpreter;
import barebones.event.DropCommand;
import barebones.event.GetInventoryCommand;
import barebones.event.GetTimeCommand;
import barebones.event.GiveCommand;
import barebones.event.MoveCommand;
import barebones.event.OpenCommand;
import barebones.event.QuitCommand;
import barebones.event.RestoreCommand;
import barebones.event.SaveCommand;
import barebones.event.TakeCommand;
import barebones.event.UnlockCommand;
import junit.framework.TestCase;

public class SimpleTextInterpreterTests extends TestCase 
{
	protected SimpleTextInterpreter m_interp;
	
	protected String makeRandomItem()
	{
		Vector<String> i = new Vector<String>();
		i.add("easteregg");
		i.add("turtle");
		i.add("hat");
		i.add("sandwich");
		
		Random r = new Random();
		int index = r.nextInt(i.size());
		return i.get(index);
	}
	
	protected Vector<String> getDirVec()
	{
		Vector<String> v = new Vector<String>();
		v.add("n");
		v.add("no");
		v.add("nor");
		v.add("nort");
		v.add("north");
		v.add("e");
		v.add("ea");
		v.add("eas");
		v.add("east");
		v.add("w");
		v.add("we");
		v.add("wes");
		v.add("west");
		v.add("s");
		v.add("so");
		v.add("sou");
		v.add("sout");
		v.add("south");
		v.add("u");
		v.add("up");
		v.add("d");
		v.add("do");
		v.add("dow");
		v.add("down");
		
		return v;
	}
		
	protected void interpretTest(String text, boolean expectsCommand, String expectedResult) {
		RawTextInput ri = new RawTextInput(text);
		ClientAction act = m_interp.interpret(ri);
		System.out.println(act.toString());

		boolean isCommand = (act instanceof ExecuteCommandAction);
		assertEquals(expectsCommand, isCommand);
		
		if (isCommand) {
			ExecuteCommandAction execAct = (ExecuteCommandAction)act;
			assertEquals(execAct.command.id(), expectedResult);
			System.out.println(execAct.command);
		}
		else {
			assertEquals(act.toString(), expectedResult);
		}
	}
	
	public SimpleTextInterpreterTests() {
		m_interp = new SimpleTextInterpreter(null);
	}

	public final void testInterpret() {
		interpretTest("go north", true, MoveCommand.id);
		interpretTest("n", true, MoveCommand.id);
		interpretTest("no", true, MoveCommand.id);
		interpretTest("nor", true, MoveCommand.id);
		interpretTest("nort", true, MoveCommand.id);
		interpretTest("north", true, MoveCommand.id);
		interpretTest("e", true, MoveCommand.id);
		interpretTest("ea", true, MoveCommand.id);
		interpretTest("eas", true, MoveCommand.id);
		interpretTest("east", true, MoveCommand.id);
		interpretTest("w", true, MoveCommand.id);
		interpretTest("we", true, MoveCommand.id);
		interpretTest("wes", true, MoveCommand.id);
		interpretTest("west", true, MoveCommand.id);
		interpretTest("s", true, MoveCommand.id);
		interpretTest("so", true, MoveCommand.id);
		interpretTest("sou", true, MoveCommand.id);
		interpretTest("sout", true, MoveCommand.id);
		interpretTest("south", true, MoveCommand.id);
		interpretTest("u", true, MoveCommand.id);
		interpretTest("up", true, MoveCommand.id);
		interpretTest("d", true, MoveCommand.id);
		interpretTest("do", true, MoveCommand.id);
		interpretTest("dow", true, MoveCommand.id);
		interpretTest("down", true, MoveCommand.id);
		
		interpretTest("q", true, QuitCommand.id);
		interpretTest("qu", true, QuitCommand.id);
		interpretTest("qui", true, QuitCommand.id);
		interpretTest("quit", true, QuitCommand.id);

		interpretTest("ti", true, GetTimeCommand.id);
		interpretTest("tim", true, GetTimeCommand.id);
		interpretTest("time", true, GetTimeCommand.id);
		
		interpretTest("i", true, GetInventoryCommand.id);
		interpretTest("in", true, GetInventoryCommand.id);
		interpretTest("inv", true, GetInventoryCommand.id);
		interpretTest("inve", true, GetInventoryCommand.id);
		interpretTest("inven", true, GetInventoryCommand.id);
		interpretTest("invent", true, GetInventoryCommand.id);
		interpretTest("invento", true, GetInventoryCommand.id);
		interpretTest("inventor", true, GetInventoryCommand.id);
		interpretTest("inventory", true, GetInventoryCommand.id);

		interpretTest("sa", false, "['SAVE' requires arguments (game id)]");
		interpretTest("sav", false, "['SAVE' requires arguments (game id)]");
		interpretTest("save", false, "['SAVE' requires arguments (game id)]");

		interpretTest("sa mygame", true, SaveCommand.id);
		interpretTest("sav agame", true, SaveCommand.id);
		interpretTest("save somegame", true, SaveCommand.id);

		interpretTest("r", false, "['LOAD' requires arguments (game id)]");
		interpretTest("re", false, "['LOAD' requires arguments (game id)]");
		interpretTest("res", false, "['LOAD' requires arguments (game id)]");
		interpretTest("rest", false, "['LOAD' requires arguments (game id)]");
		interpretTest("resto", false, "['LOAD' requires arguments (game id)]");
		interpretTest("restor", false, "['LOAD' requires arguments (game id)]");
		interpretTest("restore", false, "['LOAD' requires arguments (game id)]");

		interpretTest("r agame", true, RestoreCommand.id);
		interpretTest("re somegame", true, RestoreCommand.id);
		interpretTest("res mygame", true, RestoreCommand.id);
		interpretTest("rest yourgame", true, RestoreCommand.id);
		interpretTest("resto oldgame", true, RestoreCommand.id);
		interpretTest("restor oldergame", true, RestoreCommand.id);
		interpretTest("restore thatgame", true, RestoreCommand.id);
		
		interpretTest("dr", false, "['DROP' requires arguments (item)]");
		interpretTest("dro", false, "['DROP' requires arguments (item)]");
		interpretTest("drop", false, "['DROP' requires arguments (item)]");
		
		interpretTest("dr sword", true, DropCommand.id);
		interpretTest("dro shield", true, DropCommand.id);
		interpretTest("drop key", true, DropCommand.id);

		interpretTest("o", false, "['OPEN' requires arguments (direction)]");
		interpretTest("op", false, "['OPEN' requires arguments (direction)]");
		interpretTest("ope", false, "['OPEN' requires arguments (direction)]");
		interpretTest("open", false, "['OPEN' requires arguments (direction)]");

		interpretTest("o sword", false, "['OPEN' requires arguments (direction)]");
		interpretTest("op", false, "['OPEN' requires arguments (direction)]");
		interpretTest("ope", false, "['OPEN' requires arguments (direction)]");
		interpretTest("open", false, "['OPEN' requires arguments (direction)]");

		interpretTest("o n", true, OpenCommand.id);
		interpretTest("op so", true, OpenCommand.id);
		interpretTest("ope up", true, OpenCommand.id);
		interpretTest("open east", true, OpenCommand.id);
		
		interpretTest("ta", false, "['TAKE' requires arguments (item)]");
		interpretTest("tak", false, "['TAKE' requires arguments (item)]");
		interpretTest("take", false, "['TAKE' requires arguments (item)]");

		interpretTest("ta shield", true, TakeCommand.id);
		interpretTest("tak silverkey", true, TakeCommand.id);
		interpretTest("take walrus", true, TakeCommand.id);
		
		interpretTest("g", false, "['GIVE' requires arguments (item, recipient)]");
		interpretTest("gi", false, "['GIVE' requires arguments (item, recipient)]");
		interpretTest("giv", false, "['GIVE' requires arguments (item, recipient)]");
		interpretTest("give", false, "['GIVE' requires arguments (item, recipient)]");

		interpretTest("g shield", false, "['GIVE' requires arguments (recipient)]");
		interpretTest("gi sword", false, "['GIVE' requires arguments (recipient)]");
		interpretTest("giv potato", false, "['GIVE' requires arguments (recipient)]");
		interpretTest("give orange", false, "['GIVE' requires arguments (recipient)]");

		/*
		interpretTest("g shield george", true, GiveCommand.id);
		interpretTest("gi sword charlie", true, GiveCommand.id);
		interpretTest("giv potato ron", true, GiveCommand.id);
		interpretTest("give orange booboo", true, GiveCommand.id);
*/
		interpretTest("g shield to", false, "['GIVE' requires arguments (recipient)]");
		interpretTest("gi sword to", false, "['GIVE' requires arguments (recipient)]");
		interpretTest("giv potato to", false, "['GIVE' requires arguments (recipient)]");
		interpretTest("give orange to", false, "['GIVE' requires arguments (recipient)]");	

		interpretTest("g shield to george", true, GiveCommand.id);
		interpretTest("gi sword to ralph", true, GiveCommand.id);
		interpretTest("giv potato to lewis", true, GiveCommand.id);
		interpretTest("give orange to jon", true, GiveCommand.id);

		interpretTest("un", false, "['UNLK' requires arguments (direction, item)]");
		interpretTest("unl", false, "['UNLK' requires arguments (direction, item)]");
		interpretTest("unlo", false, "['UNLK' requires arguments (direction, item)]");
		interpretTest("unloc", false, "['UNLK' requires arguments (direction, item)]");
		interpretTest("unlock", false, "['UNLK' requires arguments (direction, item)]");
		
		// Iterate over all dirs and cases
		Vector<String> dirVec = getDirVec();
		Vector<String> unlockVec = new Vector<String>();
		unlockVec.add("un"); unlockVec.add("unl"); unlockVec.add("unlo"); unlockVec.add("unloc");
		unlockVec.add("unlock");
		
		for(String dir : dirVec) {
			for(String act : unlockVec) {
				System.out.println("Testing "+act+" "+dir);
				interpretTest(act+" "+dir, false, "['UNLK' requires arguments (item)]");
			}
		}
		
		// Now match with items
		for(String dir : dirVec) {
			for(String act : unlockVec) {
				String item = makeRandomItem();
				System.out.println("Testing "+act+" "+dir+" with "+item);
				interpretTest(act+" "+dir+" with "+item, true, UnlockCommand.id);
			}
		}		
	}

}

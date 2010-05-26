package barebones.event;

import java.util.HashMap;

/**
 * This command fetches any introductory resources
 * if the game is just started
 * 
 * If the game has just begun for this player, the intro.xml is
 * loaded from <game root> directory.
 *
 * It is expected that this will always be the first command a game
 * client will send upon start-up.
 * 
 * @author dej
 *
 */
public class GetIntroCommand extends UserCommandImpl 
{
	public static final String id = "INTRO";

	public static GetIntroCommand instance(HashMap<String,String> args) {
		return new GetIntroCommand();
	}
		
	public String toString() {
		return id;
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}
}

package barebones.engine;

import barebones.event.Subcommand;
import barebones.event.UserCommand;

/**
 * This interface represents the player being in a state that takes
 * them out of normal game-play mode.
 * 
 * While in a SubcommandContext, only legal subcommands are allowed.
 * Also, no time passes while in this context.
 * 
 * Some examples:
 * -A normal command requires more information in order to be executed
 * -Combat is taking place
 * -A conversation with an NPC is taking place
 * 
 * Any WorldObject can move the Player into a SubcommandContext
 * by calling the following:
 * cmd.setSubcommandContext(<specific subcommand class>);
 * 
 * The only commands allowed for a SubcommandContext are Subcommands.
 * A Subcommand is a user action that must convert into a single string value.
 * Typically it is the result of choosing from a menu of options.
 * 
 * If the Player exits in the middle of an uncompleted SubcommandContext,
 * no state changes will be saved. 
 * 
 * @author dej
 *
 */
public interface SubcommandContext {
	/**
	 * Execute subcommand
	 * @param cmd
	 * @return UserCommand to be executed upon success of subcmd
	 */
	public UserCommand execute(Subcommand cmd);
	
	public boolean isCompleted();
	
	public boolean isSource(UserCommand cmd);
	
	public String getContextDesc();
}

package barebones.client;

/**
 * Represents an action the client should take that is dictated by the 
 * UserInputInterpreter
 * 
 * For example, display a dialog, offer a multiple choice option,
 * display a graphic, play a sound, etc.
 * 
 * A ClientAction can be the start of a chain of actions
 * The handler for a specific ClientAction may in turn create a new ClientAction
 * and return it to the caller.
 * 
 * @author dej
 *
 */
public interface ClientAction {
}

package barebones.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import barebones.event.UserCommand;
import barebones.io.UserResponse;

public abstract class ClientBase implements Client 
{
	protected static HashMap<Class<?>,Class<?> > sm_inputInterpreterMap;
	protected static HashMap<Class<?>,UserInputInterpreter> sm_interpClassInstanceMap;
	
	protected static HashMap<Class<?>,Method> sm_clientActionHandlerMap;

	protected static UserInputInterpreter getInterpreter(Class<? extends Object> inputClass)
	{
		if (sm_inputInterpreterMap.containsKey(inputClass)) {
			Class<? extends Object> interpClass = sm_inputInterpreterMap.get(inputClass);
			UserInputInterpreter interp = sm_interpClassInstanceMap.get(interpClass);
			return interp;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Expected method signature is ClientAction handlerName(SpecificClientActionSubclassName action)
	 * Can return null
	 * @param clientActionClass
	 * @param clientClass
	 * @param methodName
	 */
	public static void registerClientAction(Class<?> clientActionClass, Class<?> clientClass, String methodName) {
		if (null == sm_clientActionHandlerMap) {
			sm_clientActionHandlerMap = new HashMap<Class<?>,Method>();
		}
		  
		// Attempt to validate the method belongs to the client class
		Method m = null;
		try {
			m = clientClass.getMethod(methodName, ClientAction.class);
			sm_clientActionHandlerMap.put(clientActionClass, m);
		}
		catch(NoSuchMethodException nsme) {
			nsme.printStackTrace(System.err);
		}
	}

	/**
	 * Do reflection stuff to get handler and call handler on client 
	 * and pass specific action subclass to handler
	 * 
	 * @param client
	 * @param action
	 * @return
	 */
	protected static ClientAction executeClientAction(Client client, ClientAction action) {
		if (!sm_clientActionHandlerMap.containsKey(action.getClass())) {
			System.err.println("ClientBase::executeClientAction: class ["+action.getClass().getName()+" does not have a registered handler in client ["+client.getClass().getName()+"]");
			return null;
		}
		ClientAction caRetval = null;
		Method m = sm_clientActionHandlerMap.get(action.getClass());
		Object[] args = {action};
		try {
			Object retval = m.invoke(client, args);
			if (null != retval) {
				caRetval = (ClientAction)retval;
			}
		}
		catch(InvocationTargetException ite) {
			ite.getTargetException().printStackTrace(System.err);
		}
		catch(IllegalAccessException iae) {
			iae.printStackTrace(System.err);
		}		
		
		return caRetval;
	}
		
	public static void registerInput(Class<?> inputClass, Class<?> interpClass, ClientConfig config)
	{
		if (null == sm_inputInterpreterMap) {
			sm_inputInterpreterMap = new HashMap<Class<?>,Class<?> >();
		}
		sm_inputInterpreterMap.put(inputClass, interpClass);
		
		if (null == sm_interpClassInstanceMap) {
			sm_interpClassInstanceMap = new HashMap<Class<?>,UserInputInterpreter>();
		}
		
		if (!sm_interpClassInstanceMap.containsKey(interpClass)) {
			try {
				Class<?>[] argTypes = {ClientConfig.class};
				Constructor<?> con = interpClass.getConstructor(argTypes);
				UserInputInterpreter interObj = (UserInputInterpreter)con.newInstance(config);
				
				sm_interpClassInstanceMap.put(interpClass, interObj);
			}
			catch(NoSuchMethodException nsme) {
				nsme.printStackTrace(System.err);
			}
			catch(IllegalAccessException iae) {
				iae.printStackTrace(System.err);
			}
			catch(InstantiationException ie) {
				ie.printStackTrace(System.err);
			}
			catch(InvocationTargetException ite) {
				ite.getTargetException().printStackTrace(System.err);
			}
		}
	}
		
	protected GameConnection m_connection;
	protected boolean m_isSubcommandContext;
	
	abstract protected ClientConfig getClientConfig();
	
	protected void handleAction(ClientAction action) {
		ClientAction nextAct = action;
		while(null != (nextAct = ClientBase.executeClientAction(this, nextAct)) ) {}
	}
		
	abstract protected ClientAction createActionFromResponse(UserResponse resp);
	
	public ClientAction handleExecuteCommandAction(ClientAction act) {
		// Make sure correct type is passed
		if (act instanceof ExecuteCommandAction) {
			if ((null != m_connection) && m_connection.isConnected()) {
				ExecuteCommandAction eca = (ExecuteCommandAction)act;
				this.m_connection.send(eca.command);
				UserResponse resp = this.m_connection.receive();

				return createActionFromResponse(resp);
			}
		}
		else {
			throw new ClientHandlerException(this.getClass(), "handleExecuteCommandAction", act.getClass(), ExecuteCommandAction.class);
		}
		
		return null;
	}	
	
	public ClientBase()
	{
		m_connection = null;
		m_isSubcommandContext = false;
	}

	public void sendInput(UserInput input) 
	{
		ClientAction act = null;
		
		// Handle direct command if necessary
		if (input instanceof UserCommand) {
			ExecuteCommandAction eca = new ExecuteCommandAction();
			eca.command = (UserCommand)input;
			act = eca;
		}
		else {
			UserInputInterpreter interp = ClientBase.getInterpreter(input.getClass());
			act = interp.interpret(input);
		}		
		this.handleAction(act);		
	}

	/**
	 * Override as necessary in subclasses to shutdown client.
	 */
	public void shutdown() {
		if (null != m_connection) {
			m_connection.disconnect();
		}
	}

	public boolean start(String gameId, String userId, String pwd) {
		if (null == m_connection) {
			ClientConfig config = getClientConfig();
			m_connection = GameConnectionFactory.getInstance(config);
		}
		m_connection.connect(gameId, userId, pwd);
		
		return m_connection.isConnected();
	}
	
	public String getGameTitle() {
		return getClientConfig().getGameTitle();
	}
	
	public boolean requiresSubcommand()
	{
		return m_isSubcommandContext;
	}

}

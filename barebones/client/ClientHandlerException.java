package barebones.client;

public class ClientHandlerException extends RuntimeException 
{
	public static final long serialVersionUID = 1L;
	
	protected Class<?> m_clientCaller;
	protected String m_methodName;
	protected Class<?> m_actualActionClass;
	protected Class<?> m_requiredActionClass;
	
	public ClientHandlerException(Class<?> clientCaller, String methodName, Class<?> actualActionClass, Class<?> requiredActionClass) {
		m_clientCaller = clientCaller;
		m_methodName = methodName;
		m_actualActionClass = actualActionClass;
		m_requiredActionClass = requiredActionClass;
	}
	
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append(m_clientCaller.getName()+"."+m_methodName+" called with action class "+m_actualActionClass.getName()+". This must be called with "+m_requiredActionClass.getName());
		
		return sb.toString();
	}
}

package barebones.world.data;

public class NewUserCreationException extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Throwable m_source;
	
	public NewUserCreationException(Throwable s) {
		m_source = s;
	}

	public NewUserCreationException(String s) {
		super(s);
	}
	
	public Throwable getSource() {
		return m_source;
	}
}

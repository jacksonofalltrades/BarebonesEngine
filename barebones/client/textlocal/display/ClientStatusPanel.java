package barebones.client.textlocal.display;

import javax.swing.JPanel;

import barebones.client.Client;

public class ClientStatusPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected Client m_clientRef;
	
	public ClientStatusPanel(Client clientRef) {
		m_clientRef = clientRef;
	}
}

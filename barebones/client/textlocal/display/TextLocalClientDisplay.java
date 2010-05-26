package barebones.client.textlocal.display;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import barebones.client.Client;
import barebones.client.textlocal.RawTextInput;
import barebones.event.ExamineCommand;
import barebones.event.GetIntroCommand;
import barebones.event.Subcommand;

public class TextLocalClientDisplay extends JFrame implements KeyListener, ActionListener, WindowListener
{
	public static final long serialVersionUID = 1L;
	
	protected static final String USER_INPUT_PREFIX = ">>> ";
	
	protected Client m_clientRef;
	
	protected AppDisplay m_appDisplay;
	
	protected ClientMenuBar m_menuBar;
	
	protected ClientInputPanel m_inputPanel;
	protected ClientResponsePanel m_responsePanel;
			
	public TextLocalClientDisplay(Client clientRef) 
	{
		if (null == clientRef) {
			throw new RuntimeException("clientRef is null!");
		}
				
		m_clientRef = clientRef;

		m_menuBar = new ClientMenuBar(this, m_clientRef);
		
		m_responsePanel = new ClientResponsePanel(m_clientRef);
		m_inputPanel = new ClientInputPanel(m_clientRef);
		ClientStatusPanel sp = new ClientStatusPanel(m_clientRef);
		
		m_appDisplay = new AppDisplay(clientRef.getGameTitle(), m_responsePanel, m_inputPanel, sp);
		
		m_appDisplay.addKeyListener(this);
		m_appDisplay.addActionListener(this);
		
		addWindowListener(this);
		
		setMenuBar(m_menuBar);
		
		setLayout(new BorderLayout(5, 5));
		
		getContentPane().add(m_appDisplay, BorderLayout.CENTER);
		
		pack();
		
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());			
		} catch(Exception xcp) {
		}
	}
	
	public void disableControls() {
		m_inputPanel.disableInput();
		m_menuBar.enableCommand(ClientMenuBar.CMD_CONNECT);
		m_menuBar.disableCommand(ClientMenuBar.CMD_DISCONNECT);
	}
	
	public void enableControls() {
		m_inputPanel.enableInput();		
		m_inputPanel.returnFocusToInputField();		
		m_menuBar.disableCommand(ClientMenuBar.CMD_CONNECT);
		m_menuBar.enableCommand(ClientMenuBar.CMD_DISCONNECT);
	}
	
	public void keyPressed(KeyEvent ke) {
	}
	
	public void keyReleased(KeyEvent ke) {	
	}

	public void keyTyped(KeyEvent ke) {
		Component src = (Component)ke.getSource();
		if (src.getName().equals(AppDisplay.INPUT_FIELD_NAME)) {
			// Check if the key was enter/return
			char keyChar = ke.getKeyChar();
			if ((int)keyChar == KeyEvent.VK_ENTER) {
				handleUserInput();
			}
		}		
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object osrc = ae.getSource();
		if (osrc instanceof Component) {
			Component src = (Component)osrc;
			if (src.getName().equals(AppDisplay.INPUT_BUTTON_NAME)) {
				handleUserInput();
			}
		}
		else if (osrc instanceof MenuItem) {
			MenuItem l_mi = (MenuItem)osrc;
			String l_cmd = l_mi.getActionCommand();
			if (l_cmd.equals(ClientMenuBar.CMD_CONNECT)) {
				// Replace this with a call to bring the login panel to the front
				// of the response panel
				if (m_clientRef.start("testgame", "testuser", "")) {
					this.enableControls();
					GetIntroCommand gic = new GetIntroCommand();
					m_clientRef.sendInput(gic);
					ExamineCommand exc = new ExamineCommand();
					m_clientRef.sendInput(exc);
				}
			}
			else if (l_cmd.equals(ClientMenuBar.CMD_DISCONNECT)) {
				m_clientRef.shutdown();
				this.disableControls();
			}
			else if (l_cmd.equals(ClientMenuBar.CMD_QUIT)) {
				quit();
				
			}
		}
	}
	
	public void windowActivated(WindowEvent we) {		
	}

	public void windowClosed(WindowEvent we) {		
	}

	public void windowOpened(WindowEvent we) {		
	}

	public void windowDeiconified(WindowEvent we) {		
	}

	public void windowIconified(WindowEvent we) {		
	}

	public void windowDeactivated(WindowEvent we) {		
	}

	public void windowClosing(WindowEvent we) {
		quit();
	}
	
	protected void quit() {
		m_clientRef.shutdown();
		System.exit(0);		
	}
	
	protected void handleUserInput() {
		String input = m_inputPanel.consumeInput();
		if (null != input) {
			displayLine(USER_INPUT_PREFIX+input);
			
			if (m_clientRef.requiresSubcommand()) {
				Subcommand scmd = new Subcommand(input);
				m_clientRef.sendInput(scmd);
			}
			else {
				RawTextInput rti = new RawTextInput(input);
				m_clientRef.sendInput(rti);
			}
			m_inputPanel.returnFocusToInputField();
		}		
	}
		
	public void displayLine(String line) {
		m_responsePanel.addLine(line);
	}
}

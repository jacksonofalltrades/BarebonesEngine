package barebones.client.textlocal.display;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import barebones.client.Client;

public class ClientInputPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	protected static final String CMD_BTN_LABEL = "Do";
	protected Client m_clientRef;
	protected JTextField m_textField;
	protected JButton m_commandBtn;
	
	public ClientInputPanel(Client clientRef) {
		m_clientRef = clientRef;

		m_textField = new JTextField(AppDisplay.CLIENT_WIDTH);
		m_textField.setName(AppDisplay.INPUT_FIELD_NAME);
		
		m_commandBtn = new JButton(CMD_BTN_LABEL);
		m_commandBtn.setName(AppDisplay.INPUT_BUTTON_NAME);
		
		this.setLayout(new FlowLayout());
		
		this.add(m_textField);
		this.add(m_commandBtn);
	}
	
	public void enableInput() {
		m_textField.setEditable(true);		
		m_commandBtn.setEnabled(true);
	}
	
	public void disableInput() {
		m_textField.setEditable(false);
		m_commandBtn.setEnabled(false);
	}
	
	public void addActionListener(ActionListener al) {
		m_commandBtn.addActionListener(al);
	}
	
	public void addKeyListener(KeyListener kl) {
		super.addKeyListener(kl);
		m_textField.addKeyListener(kl);
	}
	
	public void returnFocusToInputField() {
		m_textField.requestFocus();		
	}

	public String consumeInput() {
		String text = m_textField.getText();
		text = text.trim();
		m_textField.setText("");
		if (text.length() > 0) {
			return text; 
		}
		else {
			return null;
		}
	}
}

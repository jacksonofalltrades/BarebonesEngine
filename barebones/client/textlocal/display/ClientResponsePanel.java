package barebones.client.textlocal.display;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import barebones.client.Client;

public class ClientResponsePanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	protected Client m_clientRef;
	protected JTextArea m_textArea;
	
	public ClientResponsePanel(Client clientRef) {
		m_clientRef = clientRef;
		
		StringBuffer newLines = new StringBuffer();
		for(int i = 0; i < 10; i++) {
			newLines.append("\n");
		}
		
		m_textArea = new JTextArea(newLines.toString(), 10, AppDisplay.CLIENT_WIDTH);
		m_textArea.setBackground(Color.BLACK);
		m_textArea.setForeground(Color.WHITE);
		m_textArea.setEditable(false);
		m_textArea.setMargin(new Insets(10,10,10,10));
		m_textArea.setLineWrap(true);
		m_textArea.setWrapStyleWord(true);

		JScrollPane l_scroller = new JScrollPane(m_textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		l_scroller.setAutoscrolls(true);
		
		setLayout(new FlowLayout());
		add(l_scroller);
	}
	
	public void addLine(String text) {
		// Compare char count against text area width and break up line if necessary
		m_textArea.append(text);
		m_textArea.append("\n");
		m_textArea.setCaretPosition(m_textArea.getDocument().getLength());		
	}
}

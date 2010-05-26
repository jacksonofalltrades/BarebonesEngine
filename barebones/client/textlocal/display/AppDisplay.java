package barebones.client.textlocal.display;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AppDisplay extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public static final int CLIENT_WIDTH = 50;
	
	public static final String INPUT_FIELD_NAME = "TLC_IF";
	public static final String INPUT_BUTTON_NAME = "TLC_IB";
		
	public static final String INPUT_DISPLAY = "I";
	public static final String MENU_DISPLAY = "M";
	public static final String RESPONSE_DISPLAY = "R";
	public static final String STATUS_DISPLAY = "S";
	
	protected JLabel m_titleLabel;
	protected JPanel m_southPanel;
	
	protected ClientInputPanel m_inputPanel;
	protected ClientResponsePanel m_responsePanel;
	
	public AppDisplay(String gameTitle,
			ClientResponsePanel responsePanel,
			ClientInputPanel inputPanel,
			ClientStatusPanel statusPanel)
	{
		m_titleLabel = new JLabel(gameTitle);
		
		m_inputPanel = inputPanel;
		m_responsePanel = responsePanel;
		
		m_southPanel = new JPanel(new GridLayout(2,1));
		m_southPanel.add(m_inputPanel);
		m_southPanel.add(statusPanel);
		
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(5, 5));
		add(m_titleLabel, BorderLayout.NORTH);
		add(responsePanel, BorderLayout.CENTER);
		add(m_southPanel, BorderLayout.SOUTH);
	}
		
	public void addActionListener(ActionListener al) {
		m_inputPanel.addActionListener(al);
	}
	
	public void addKeyListener(KeyListener kl)
	{
		super.addKeyListener(kl);

		m_titleLabel.addKeyListener(kl);
		m_southPanel.addKeyListener(kl);
		m_inputPanel.addKeyListener(kl);
		m_responsePanel.addKeyListener(kl);
	}

	public void actionPerformed(ActionEvent event)
	{
	}
}

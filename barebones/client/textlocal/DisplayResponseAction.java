package barebones.client.textlocal;

import java.util.Vector;

import barebones.client.ClientAction;

public class DisplayResponseAction implements ClientAction 
{
	Vector<String> responseLines;
	
	public String toString() {
		return responseLines.toString();
	}
}

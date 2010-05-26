package barebones.client.textlocal;

import barebones.client.UserInput;

public class RawTextInput implements UserInput 
{
	String text;
	
	public RawTextInput(String text) {
		this.text = text;
	}
}

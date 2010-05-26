package barebones.io;

import barebones.world.data.ResponseContentBean;

/**
 * This class represents any combination of potential content to be
 * given to the player as a response to a command or event.
 * 
 * Types of content that might be included include the following:
 * -A sound
 * -An image
 * -An animation
 * -A text sentence
 * 
 * @author dej
 *
 */
public class ResponseContent 
{
	protected ResponseContentBean data;
	
	public static ResponseContent text(String text) {
		return new ResponseContent(text);
	}
	
	public static ResponseContent formattedText(String text, String ... values) {
		ResponseContent rc = new ResponseContent(text);
		rc.formatText(values);
		return rc;
	}

	// Add more later

	// Make sure to update this with other stuff
	public ResponseContent(ResponseContent other) {
		this.data.text = other.data.text;
	}
	
	public ResponseContent(ResponseContentBean data) {
		this.data = data;
	}
	
	// Just text content
	public ResponseContent(String text)
	{
		this.data = new ResponseContentBean();
		this.data.text = text;
	}
	
	// Add more constructors later
	
	public void addText(String text) {
		this.data.text = text;
	}
	
	public void formatText(String ... values) {
		this.data.text = String.format(this.data.text, (Object[])values);
	}
	
	public String text()
	{
		return this.data.text;
	}
}

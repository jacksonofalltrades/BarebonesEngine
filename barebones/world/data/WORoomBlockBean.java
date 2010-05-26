package barebones.world.data;

import java.util.Vector;

public class WORoomBlockBean extends WODataBean
{	
	public int startIndex;
	public int step;
	public int width;
	public int height;
	public int descSeed;
	public Vector<String> possibleDescs;

	public WORoomBlockBean() {
	}
		
	public void setstep(int step)
	{
		this.step = step;
	}
	
	public int getstep()
	{
		return this.step;
	}
	
	public void setwidth(int w)
	{
		this.width = w;
	}
	
	public int getwidth()
	{
		return this.width;
	}
	
	public void setheight(int h)
	{
		this.height = h;
	}
	
	public int getheight()
	{
		return this.height;
	}
	
	public void setstartIndex(int i)
	{
		this.startIndex = i;
		this.id = String.valueOf(this.startIndex);
	}
	
	public int getstartIndex()
	{
		return this.startIndex;
	}
	
	public void setdescSeed(int seed)
	{
		this.descSeed = seed;
	}
	
	public int getdescSeed()
	{
		return this.descSeed;
	}
	
	public void setpossibleDescs(Vector<String> descs)
	{
		this.possibleDescs = descs;
	}
	
	public Vector<String> getpossibleDescs()
	{
		return this.possibleDescs;
	}
	
	public Object clone() {
		WORoomBlockBean copy = new WORoomBlockBean();
		copy.id = this.id;
		copy.detailId = this.detailId;
		copy.step = this.step;
		copy.width = this.width;
		copy.height = this.height;
		copy.startIndex = this.startIndex;
		copy.descSeed = this.descSeed;
		if (null != this.possibleDescs)
			copy.possibleDescs = new Vector<String>(this.possibleDescs);
		
		return copy;
	}
	
	/*
	Room blocks:
		#tag           startIndex step width height default_detail_id
		roomblock  1100         1     20     20      forest1
		# overrides should just use existing room def format
		*/
}

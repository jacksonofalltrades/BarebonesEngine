package barebones.world.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import barebones.io.dal.ObjectMarshaller;
import barebones.world.filter.PlayerStateFilter;

public class PlayerStateBean extends WORootDataBean 
{
	protected static final String DEFAULT_NEW_USER_PATH = "DefaultNewUser.xml";
	protected static final String PLAYER_PATH = "games/";
	
	static {
		ObjectMarshaller.registerBean(PlayerStateBean.class, new PlayerStateFilter(), PLAYER_PATH);
	}
	
	public static boolean create(String rootPath, String id) {
		String fullPath = rootPath+"/"+PLAYER_PATH+id+".xml";
		File f = new File(fullPath);
		if (!f.exists()) {
			// Get default file
			File defaultFile = new File(rootPath+"/"+PLAYER_PATH+DEFAULT_NEW_USER_PATH);			
			try {
				System.err.println("ABOUT TO TRY TO CREATE FILE: "+fullPath);
				if (f.createNewFile()) {
					FileInputStream fis = new FileInputStream(defaultFile);
					FileOutputStream fos = new FileOutputStream(f);
					int onebyte;
					while(-1 != (onebyte = fis.read())) {
						System.out.println("ABOUT TO WRITE A BYTE ["+onebyte+"]");
						fos.write(onebyte);
					}
					fos.flush();
					fis.close();
					fos.close();
					
					return true;
				}
				else {
					throw new NewUserCreationException("Could not create new file for path: "+fullPath);
				}
			}
			catch(IOException ioe) {
				throw new NewUserCreationException(ioe);
			}
		}
		
		return false;
	}

	public PlayerStateData data;
	public HashMap<String,WODataBean> worldDeltas;
	
	public void setdata(PlayerStateData data) {
		this.data = data;
	}
	
	public PlayerStateData getdata() {
		return this.data;
	}
		
	public void setworldDeltas(HashMap<String,WODataBean> deltas) {
		worldDeltas = deltas;
	}
	
	public HashMap<String,WODataBean> getworldDeltas() {
		return worldDeltas;
	}
}

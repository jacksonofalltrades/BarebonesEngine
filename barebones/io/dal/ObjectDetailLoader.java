package barebones.io.dal;

import java.util.HashMap;

import barebones.engine.GameEngineAccessor;
import barebones.world.data.ObjectDetailBean;
import barebones.world.object.ObjectDetail;

public class ObjectDetailLoader extends GameObjectMarshaller {
	public static final boolean DEBUG = false;
	
	protected static ObjectDetailLoader sm_odLoader;
	
	public static ObjectDetailLoader instance()
	{
		if (null ==sm_odLoader) {
			sm_odLoader = new ObjectDetailLoader();
		}
		return sm_odLoader;
	}
		
	// Caching
	protected HashMap<String,ObjectDetail> m_detailCache;
	
	protected ObjectDetail m_lastDetail;
	
	protected ObjectDetailLoader()
	{				
		super("");
		
		m_detailCache = new HashMap<String,ObjectDetail>();
	}
		
	public ObjectDetail load(GameEngineAccessor engineRef, String objectId)
	{
		if (!m_detailCache.containsKey(objectId)) {
			
			this.setRootPath(engineRef.getGameDataRootPath());
			
			ObjectDetailBean bean = (ObjectDetailBean)load(ObjectDetailBean.class, objectId, false);
			
			ObjectDetail detail = null;
			if (null != bean) {
				detail = new ObjectDetail(bean);
				m_detailCache.put(objectId, detail);			
			}
						
		}
		
		return m_detailCache.get(objectId);
	}

}

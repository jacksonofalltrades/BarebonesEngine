package barebones.io;

import java.util.HashMap;
import java.util.HashSet;

import barebones.engine.GameEngineAccessor;
import barebones.world.data.WORootDataBean;

import utils.io.xml.XMLDecoder;
import utils.io.xml.XMLEncoder;
import utils.io.xml.XMLProcessorFactory;

public class ObjectMarshaller {
	
	protected static final String FILE_EXT = ".xml";
	
	protected static HashMap<Class<?>,WODataFilter> sm_beanFilterMap;
	protected static HashMap<Class<?>,String> sm_beanPathMap;

	static {
		sm_beanFilterMap = new HashMap<Class<?>,WODataFilter>();
		sm_beanPathMap = new HashMap<Class<?>,String>();
	}
	
	public static void registerBean(Class<?> beanClass, WODataFilter beanFilter, String beanPath) {
		sm_beanFilterMap.put(beanClass, beanFilter);
		sm_beanPathMap.put(beanClass, beanPath);
	}
	
	protected static WODataFilter getFilter(Class<?> beanClass) {
		return sm_beanFilterMap.get(beanClass);
	}
	
	protected static String getPath(Class<?> beanClass) {
		return sm_beanPathMap.get(beanClass);
	}
	
	protected GameEngineAccessor m_engineRef;
	
	protected XMLEncoder m_encoder;
	protected XMLDecoder m_decoder;
	
	public ObjectMarshaller(GameEngineAccessor engineRef) {
		m_engineRef = engineRef;
		m_encoder = XMLProcessorFactory.instance().createEncoder();
		m_decoder = XMLProcessorFactory.instance().createDecoder();
	}
	
	public WORootDataBean load(Class<?> beanClass, String objId) {
		WODataFilter filter = ObjectMarshaller.getFilter(beanClass);
		String path = ObjectMarshaller.getPath(beanClass);
		
		WORootDataBean bean = (WORootDataBean)m_decoder.read(m_engineRef.getGameDataRootPath()+path+objId+FILE_EXT);
		bean.setFilter(filter);
		
		return bean;
	}
	
	public HashSet<WORootDataBean> loadAll(String path) {
		return null;
	}
	
	public void save(WORootDataBean bean) {
		Class<?> beanClass = bean.getClass();
		WODataFilter filter = ObjectMarshaller.getFilter(beanClass);
		String path = ObjectMarshaller.getPath(beanClass);
		
		m_encoder.setFilter(filter);
		
		m_encoder.write(path, bean.getid(), bean);
	}
}

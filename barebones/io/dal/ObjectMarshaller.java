package barebones.io.dal;

import java.io.File;
import java.util.HashMap;

import barebones.engine.GameEngineAccessor;
import barebones.world.data.WORootDataBean;

import utils.io.xml.XMLDecoder;
import utils.io.xml.XMLEncoder;
import utils.io.xml.XMLProcessorFactory;
import utils.io.xml.XMLWriteFilter;

public class ObjectMarshaller {
	
	protected static final String QUALIFIER_SEPARATOR = ".";
	protected static final String FILE_EXT = ".xml";
	
	protected static HashMap<Class<?>,XMLWriteFilter> sm_beanFilterMap;
	protected static HashMap<Class<?>,String> sm_beanPathMap;

	static {
		sm_beanFilterMap = new HashMap<Class<?>,XMLWriteFilter>();
		sm_beanPathMap = new HashMap<Class<?>,String>();
	}
	
	public static void registerBean(Class<?> beanClass, XMLWriteFilter beanFilter, String beanPath) {
		sm_beanFilterMap.put(beanClass, beanFilter);
		sm_beanPathMap.put(beanClass, beanPath);
	}
	
	protected static XMLWriteFilter getFilter(Class<?> beanClass) {
		return sm_beanFilterMap.get(beanClass);
	}
	
	protected static String getPath(Class<?> beanClass) {
		return sm_beanPathMap.get(beanClass);
	}
	
	public static void forceRegister(Class<?> beanClass) {
		try {
			Class.forName(beanClass.getName());
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace(System.err);
		}		
	}
	
	protected String m_rootPath;
	
	protected XMLEncoder m_encoder;
	protected XMLDecoder m_decoder;
	
	protected void initCoders() {
		m_encoder = XMLProcessorFactory.instance().createEncoder();
		m_decoder = XMLProcessorFactory.instance().createDecoder();
	}
	
	public ObjectMarshaller(String rootPath) {
		m_rootPath = rootPath;
		initCoders();
	}
	
	public ObjectMarshaller(GameEngineAccessor engineRef) {
		m_rootPath = engineRef.getGameDataRootPath();
		initCoders();
	}
	
	public void setRootPath(String rootPath) {
		m_rootPath = rootPath;
	}
	
	protected WORootDataBean load(Class<?> beanClass, String objId) {
		return load(beanClass, objId, true);
	}
	
	protected WORootDataBean load(Class<?> beanClass, String objId, boolean mustExist) {
		ObjectMarshaller.forceRegister(beanClass);
				
		XMLWriteFilter filter = ObjectMarshaller.getFilter(beanClass);
		String path = (String)ObjectMarshaller.getPath(beanClass);
		
		String fullPath = m_rootPath+path+objId+FILE_EXT;
		
		if (!mustExist) {
			File f = new File(fullPath);
			if (!f.exists()) {
				return null;
			}
		}
		// otherwise let it fail in the decoder
		
		System.err.println("FULL PATH="+fullPath);
		WORootDataBean bean = (WORootDataBean)m_decoder.read(fullPath);
		bean.setFilter(filter);
		
		return bean;
	}
		
	protected void save(WORootDataBean bean) {
		save(bean, "");
	}

	protected void save(WORootDataBean bean, String idQualifier) {
		
		Class<?> beanClass = bean.getClass();
		XMLWriteFilter filter = ObjectMarshaller.getFilter(beanClass);
		String path = ObjectMarshaller.getPath(beanClass);

		String fullPath = m_rootPath+path+bean.getid();
		if (!idQualifier.isEmpty())
			fullPath += QUALIFIER_SEPARATOR+idQualifier;
		
		fullPath += FILE_EXT;
		
		m_encoder.setFilter(filter);
		
		m_encoder.write(fullPath, bean.getid(), bean);
	}
}

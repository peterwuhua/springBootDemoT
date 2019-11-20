package cn.core.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.core.framework.log.Logger;

public class XMLUtils {
	private static final Logger log = Logger.getLogger(XMLUtils.class);

	public static Document readDocument(String classPathLocation)
			throws IOException {
		InputStream inputStream = FileUtils
				.loadClassPathFile2Stream(classPathLocation);
		Document d = XMLUtils.readDocument(inputStream);
		inputStream.close();
		return d;
	}

	public static Document readDocument(InputStream inputStream) {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(inputStream);
		} catch (DocumentException e) {
			log.error(e);
		}
		return document;
	}

	public static Map<String, Object> xml2Map(String classPathLocation) {
		Document doc = null;
		try {
			doc = readDocument(classPathLocation);
		} catch (IOException e1) {
			log.error("读取配置文件失败", e1);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator<?> iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			// System.out.println(e.getName());
			List<?> list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), xml2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> xml2Map(Element e) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<?> list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List<Object> mapList = new ArrayList<Object>();

				if (iter.elements().size() > 0) {
					Map<String, Object> m = xml2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList<Object>();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

}

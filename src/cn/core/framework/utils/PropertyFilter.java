package cn.core.framework.utils;

import java.util.Map;

public interface PropertyFilter {
	void filter(Map<String, Object> resultMap, String property, Object value);
}

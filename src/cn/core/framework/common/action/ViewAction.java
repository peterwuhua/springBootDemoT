package cn.core.framework.common.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UrlPathHelper;
/**
 * <strong>Create on : 2016年11月2日 上午10:48:35 </strong> <br>
 * <strong>Description :通过action返回 以.htm后缀的直接返回 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
@Controller
public class ViewAction extends Action{
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	@RequestMapping(value="/**/*.htm")
	public String view(){
		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		String filePath = lookupPath.replace(".htm", "");
		return filePath;
	}
}

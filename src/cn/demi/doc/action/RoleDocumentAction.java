package cn.demi.doc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.service.IRoleDocumentService;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月21日 下午3:03:27  <br>
 * Description : 文件赋权  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("document.roleDocumentAction")
@RequestMapping("/doc/roledocument")
public class RoleDocumentAction extends BaseAction<RoleDocumentVo> {
	final String VIEW_PATH = "/doc/roledocument/roledocument";
	
	@Autowired private IRoleDocumentService roleDocumentService;
	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<RoleDocumentVo> baseService() {
		return roleDocumentService;
	}
	
	/**
	 * Create on : Dave Yu 2016年12月22日 下午3:26:57 <br>
	 * Description : 跳转弹窗页面 <br>
	 * @param pageType
	 * @param pageValue
	 * @param inputValue
	 * @return mav
	 * @throws GlobalException
	 */
	@RequestMapping("/selectSeachValue.do")
	public ModelAndView selectSeachValue(String pageType,String pageValue,String inputValue) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		List<String> list = roleDocumentService.selectByPageType(pageType);
		mav.addObject("list", list);
		mav.addObject("type", pageType);
		mav.addObject("value", pageValue);
		mav.addObject("inputValue", inputValue);
		mav.setViewName(getViewPath() + "_select_layer");
		return mav;
	}
	
	/**
	 * Create on : Dave Yu 2016年12月22日 下午4:22:16 <br>
	 * Description : ajax动态筛选角色信息数据 <br>
	 * @param vo
	 * @return map
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachRoleNum.do")
	public Map<String, Integer> ajaxSeachRoleNum(RoleDocumentVo vo) throws GlobalException {
		List<RoleDocumentVo> list = roleDocumentService.list(vo);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (RoleDocumentVo v : list) {
			if (!map.containsKey(v.getRoleVo().getName()) && null != v.getDocumentVo()) {
				map.put(v.getRoleVo().getName(), 1);
			} else if (null != v.getDocumentVo()) {
				map.put(v.getRoleVo().getName(), map.get(v.getRoleVo().getName()) + 1);
			}
		}
		return map;
	}
}
package cn.demi.base.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.OrgTreeVo;
import cn.demi.base.system.vo.OrgVo;

/**
 * <strong>Create on : 2016年11月2日 下午2:19:54 </strong> <br>
 * <strong>Description : 组织action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.OrgAction")
@RequestMapping("sys/org")
public class OrgAction extends BaseAction<OrgVo> {

	final String VIEW_PATH = "/sys/org/org";

	@Autowired
	private IOrgService orgService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<OrgVo> baseService() {
		return orgService;
	}
	
	@Override
	public ModelAndView edit(OrgVo vo) throws GlobalException {
		OrgVo parentVo = null;
		ModelAndView mav=null;
		//新增
		if(StrUtils.isBlankOrNull(vo.getId())){
			//是否出入父级
			if (StrUtils.isBlankOrNull(vo.getPid())) {
				parentVo = orgService.findRoot();
			}else{
				parentVo = orgService.findById(vo.getPid());
			}
			mav=super.edit(vo);
			vo.setParentVo(parentVo);
		}else{
			mav = super.edit(vo);
		}
//		mav.addObject("orgList", orgService.treeList());
		return mav;
	}

	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:20:19 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @return List
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"treeData.do","treeData.json"})
	public List<Map<String, Object>> treeData() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<OrgVo> list = null;
//		if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
//			list = orgService.listChild(getCurrent().getOrgId());
//		}else {
			list = orgService.list();
//		}
		for (OrgVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId(): "");
			map.put("name", vo.getName());
			if(null == vo.getParentVo()){
				map.put("open", "true");
			}else {
				map.put("open", "false");
			}
			if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
				map.put("open", "true");
			}
			mapList.add(map);
		}
		return mapList;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改部门",module="部门管理")
	public ModelAndView update(OrgVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav =  super.update(v, attr);
		mav.addObject("id", v.getId());
		mav.addObject("pid", v.getPid());
		return mav;
	}

	@Override
	@Log(operation=Log.Operation.ADD,content="新增部门",module="部门管理")
	public ModelAndView add(OrgVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav =  super.add(v, attr);
		mav.addObject("id", v.getId());
		mav.addObject("pid", v.getPid());
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = {"treeData4All.do"})
	public List<Map<String, Object>> treeData4All() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<OrgVo> list = orgService.list();
		for (OrgVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId(): "");
			map.put("name", vo.getName());
			if(null == vo.getParentVo()){
				map.put("open", "true");
			}else {
				map.put("open", "false");
			}
			if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
				map.put("open", "true");
			}
			mapList.add(map);
		}
		return mapList;
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存部门",module="部门管理")
	public ModelAndView save(OrgVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav =  super.save(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}

	@Override
	@Log(operation=Log.Operation.DELETE,content="删除部门",module="部门管理")
	public ModelAndView update2del(OrgVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.update2del(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}
	
	
	/**Create on : Paddy Zhang 2017年3月7日 下午5:48:22 <br>
	 * Description : 检查组织编码是否存在 <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= "checkOrgCode.do")
	public Map<String, Object> checkOrgCode(OrgVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StrUtils.isBlankOrNull(v.getCode())){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","无组织编码！！");
			return map;
		}
		String code = v.getCode().trim();
		OrgVo oldOrgVo = null;
		if(StrUtils.isNotBlankOrNull(v.getId())){
			OrgVo orgVo = orgService.findById(v.getId());
			if(!code.equals(orgVo.getCode())){
				oldOrgVo= orgService.listByCode(code);
				if(null != oldOrgVo){
					map.put("type",Status.STATUS_ERROR);
					map.put("message","编码已存在："+code);
					return map;
				}
			}
		}else{
			oldOrgVo= orgService.listByCode(code);
			if(null != oldOrgVo){
				map.put("type",Status.STATUS_ERROR);
				map.put("message","编码已存在："+code);
				return map;
			}
		}
		map.put("type",Status.STATUS_SUCCESS);
		map.put("message","编码可以使用");
		return map;
	}
	/**
	 * 系统管理首页 组织树形结果显示
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= "showJsonTree.do")
	public OrgTreeVo showJsonTree()throws GlobalException {
		OrgTreeVo jsonTree=orgService.find2JsonTree();
		return jsonTree;
	}
}

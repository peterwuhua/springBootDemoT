package cn.demi.init.st.action;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

@Controller("init.samp_typeAction")
@RequestMapping("/init/sampType")
public class SampTypeAction extends BaseAction<SampTypeVo> {
	final String VIEW_PATH = "/init/samp_type/samp_type";
	
	@Autowired 
	private ISampTypeService sampTypeService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SampTypeVo> baseService() {
		return sampTypeService;
	}

	/**
	 * 获取树形集合
	 * 只展开2级
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"treeData4List.do","treeData4List.json"})
	public List<Map<String, Object>> treeData4List() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<SampTypeVo> list = sampTypeService.list();
		for (SampTypeVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId(): "");
			map.put("name", vo.getName());
			if(vo.getLevel()<1) {
				map.put("open", "true");
			}else {
				map.put("open", "false");
			}
			
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 获取树形集合
	 * 全部展开
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"treeData.do","treeData.json"})
	public List<Map<String, Object>> treeData() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<SampTypeVo> list = sampTypeService.list();
		for (SampTypeVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId(): "");
			map.put("name", vo.getName());
			map.put("open", "true");
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 获取树形集合
	 * 全部展开 父节点不显示选择框
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"treeData4NoCheck.do"})
	public List<Map<String, Object>> treeData4NoCheck(String name) throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String rootId=sampTypeService.findRoot().getId();
		if(!StrUtils.isBlankOrNull(name)) {
			SampTypeVo stVo=sampTypeService.findByName(name);
			if(null!=stVo) {
				rootId=stVo.getId();
			}
		}
		List<SampTypeVo> list = sampTypeService.treeList(rootId);
		for (SampTypeVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId(): "");
			map.put("name", vo.getName());
			map.put("open", "true");
			if(vo.isHasChild()) {
				map.put("nocheck", "true");
			}else {
				map.put("nocheck", "false");
			}
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 获取树形集合
	 * 第一级
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "treeData4Dl.do")
	public List<Map<String, Object>> treeData4Dl() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		SampTypeVo rootVo=sampTypeService.findRoot();
		List<SampTypeVo> list = sampTypeService.listByPid(rootVo.getId());
		list.add(rootVo);
		for (SampTypeVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId(): "");
			map.put("name", vo.getName());
			map.put("open", "true");
			mapList.add(map);
		}
		return mapList;
	}
	@ResponseBody
	@RequestMapping(value ="listData.do")
	public List<SampTypeVo> listData(SampTypeVo v) throws GlobalException {
		return sampTypeService.listData(v);
	}
	/**
	 * 获取某一类树形集合
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"treeData4Type.do"})
	public List<Map<String, Object>> treeData4Type(String name) throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<SampTypeVo> list = sampTypeService.treeData4Type(name);
		for (SampTypeVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId(): "");
			map.put("name", vo.getName());
			map.put("open", "true");
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 修改样品类别
	 * 通用编辑前方法
	 */
	@Override
	public ModelAndView edit(SampTypeVo vo) throws GlobalException {
		SampTypeVo parentVo = null;
		ModelAndView mav=null;
		//新增
		if(StrUtils.isBlankOrNull(vo.getId())){
			//是否出入父级
			if (StrUtils.isBlankOrNull(vo.getPid())) {
				parentVo = sampTypeService.findRoot();
			}else{
				parentVo = sampTypeService.findById(vo.getPid());
			}
			mav=super.edit(vo);
			vo.setParentVo(parentVo);
		}else{
			mav = super.edit(vo);
		}
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存类型",module="样品类型管理")
	public ModelAndView save(SampTypeVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav =  super.save(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增类型",module="样品类型管理")
	public Status addData(SampTypeVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改类型",module="样品类型管理")
	public Status updateData(SampTypeVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除类型",module="样品类型管理")
	public ModelAndView update2del(SampTypeVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.update2del(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}
}
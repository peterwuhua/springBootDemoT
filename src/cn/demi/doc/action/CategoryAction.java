package cn.demi.doc.action;

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
import cn.demi.doc.service.ICategoryService;
import cn.demi.doc.vo.CategoryVo;
/**
 * Create on : 2016年11月24日 下午4:36:04  <br>
 * Description : 文件夹ACTION <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("doc.categoryAction")
@RequestMapping("/doc/category")
public class CategoryAction extends BaseAction<CategoryVo> {
	final String VIEW_PATH = "/doc/category/category";
	@Autowired private ICategoryService categoryService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CategoryVo> baseService() {
		return categoryService;
	}
	
	/**
	 * Create on : Dave Yu 2016年11月17日 上午9:47:30 <br>
	 * Description : 文档类型树方法 <br>
	 * @return List
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "treeData.do")
	public List<Map<String, Object>> treeData() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<CategoryVo> list = categoryService.list();
		for (CategoryVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId()
					: "");
			map.put("name", vo.getName());
			if(null == vo.getParentVo()){
				map.put("open", "true");
			}else {
				map.put("open", "false");
			}
			mapList.add(map);
		}
		return mapList;
	}
	
	@Override
	public ModelAndView edit(CategoryVo vo) throws GlobalException {
		CategoryVo parentVo = null;
		if(!StrUtils.isBlankOrNull(vo.getId()))
			vo = categoryService.findById(vo.getId());
		if (StrUtils.isBlankOrNull(vo.getPid())) {
			parentVo = categoryService.findRoot();
		}else {
			parentVo = categoryService.findById(vo.getPid());
		}
		vo.setParentVo(parentVo);
		return super.edit(vo);
	}
	
	@ResponseBody
	@RequestMapping(value = "isExistName.do")
	public Status isExistName(CategoryVo v){
			try {
				boolean flag = categoryService.isExistName(v.getName(),v.getId());
				if(!flag){
					status = new Status("文件夹名不存在",Status.STATUS_SUCCESS);
				}else{
					status = new Status("文件夹名已存在",Status.STATUS_ERROR);
				}
			} catch (GlobalException e) {
				status = new Status("操作失败",Status.STATUS_ERROR);
			}
		return status;
	}
 
	@Override
	@Log(operation=Log.Operation.ADD,content="新增类型",module="文档类型")
	public Status addData(CategoryVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存类型",module="文档类型")
	public ModelAndView save(CategoryVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新类型",module="文档类型")
	public Status updateData(CategoryVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	 
}
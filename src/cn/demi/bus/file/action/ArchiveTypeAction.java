package cn.demi.bus.file.action;

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
import cn.demi.bus.file.service.IArchiveTypeService;
import cn.demi.bus.file.vo.ArchiveTypeVo;

@Controller("bus.archiveTypeAction")
@RequestMapping("/bus/archiveType")
public class ArchiveTypeAction extends BaseAction<ArchiveTypeVo> {
	final String VIEW_PATH = "/bus/archive/type/archive_type";
	@Autowired 
	private IArchiveTypeService archiveTypeService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ArchiveTypeVo> baseService() {
		return archiveTypeService;
	}
	
	/**
	 * Description : 文档类型树方法 <br>
	 * @return List
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "treeData.do")
	public List<Map<String, Object>> treeData() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<ArchiveTypeVo> list = archiveTypeService.list();
		for (ArchiveTypeVo vo : list) {
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
			if(vo.isHasChild()) {
				map.put("nocheck", "true");
			}else {
				map.put("nocheck", "false");
			}
			mapList.add(map);
		}
		return mapList;
	}
	
	@Override
	public ModelAndView edit(ArchiveTypeVo vo) throws GlobalException {
		ArchiveTypeVo parentVo = null;
		if(!StrUtils.isBlankOrNull(vo.getId()))
			vo = archiveTypeService.findById(vo.getId());
		if (StrUtils.isBlankOrNull(vo.getPid())) {
			parentVo = archiveTypeService.findRoot();
		}else {
			parentVo = archiveTypeService.findById(vo.getPid());
		}
		vo.setParentVo(parentVo);
		return super.edit(vo);
	}
	
	@ResponseBody
	@RequestMapping(value = "isExistName.do")
	public Status isExistName(ArchiveTypeVo v){
			try {
				boolean flag = archiveTypeService.isExistName(v.getName(),v.getId());
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
	public Status addData(ArchiveTypeVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存类型",module="文档类型")
	public ModelAndView save(ArchiveTypeVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新类型",module="文档类型")
	public Status updateData(ArchiveTypeVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
}
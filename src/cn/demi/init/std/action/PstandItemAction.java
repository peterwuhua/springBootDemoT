package cn.demi.init.std.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;
import cn.demi.init.std.service.IPstandItemService;
import cn.demi.init.std.vo.PstandItemVo;

@Controller("init.pstand_itemAction")
@RequestMapping("/init/pstandItem")
public class PstandItemAction extends BaseAction<PstandItemVo> {
	final String VIEW_PATH = "/init/pstandard/pstand_item";
	@Autowired 
	private IPstandItemService pstandItemService;	
	@Autowired 
	private ISampTypeService sampTypeService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<PstandItemVo> baseService() {
		return pstandItemService;
	}
	//新增页面
	@RequestMapping(value=EDIT)
	public ModelAndView edit(PstandItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=pstandItemService.findById(v.getId());
		}
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			v.setSampType(sampTypeService.findById(v.getSampTypeId()).getType());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	//新增页面 职卫标准
	@RequestMapping(value="edit4Zw.do")
	public ModelAndView edit4Zw(PstandItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=pstandItemService.findById(v.getId());
		}
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			v.setSampType(sampTypeService.findById(v.getSampTypeId()).getType());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_zw_edit");
		return mav;
	}
	//修改页面
	@RequestMapping(value="editData.do")
	public ModelAndView editData(PstandItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=pstandItemService.findById(v.getId());
		}
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			v.setSampType(sampTypeService.findById(v.getSampTypeId()).getType());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit2");
		return mav;
	}
	//职卫标准   修改页面
	@RequestMapping(value="editData4Zw.do")
	public ModelAndView editData4Zw(PstandItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=pstandItemService.findById(v.getId());
		}
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			v.setSampType(sampTypeService.findById(v.getSampTypeId()).getType());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_zw_edit2");
		return mav;
	}
	@Override
	public Status gridEdit(PstandItemVo v) throws GlobalException {
		try {
			pstandItemService.update4Grid(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		} catch (Exception e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"关联失败",e);
			status = new Status("关联失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "gridEdit4Zw.do")
	public Status gridEdit4Zw(PstandItemVo v) throws GlobalException {
		try {
			pstandItemService.update4Grid4Zw(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		} catch (Exception e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"关联失败",e);
			status = new Status("关联失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Description :  获取已关联的项目的ids <br>
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxgetContactItemIds.do")
	public Map<String, String> ajaxgetContactItemIds(PstandItemVo v,
			RedirectAttributes attr) throws GlobalException {
		List<PstandItemVo> siList = pstandItemService.listByStandId(v);
		String selectItemIds = "";
		for (int i = 0,j=siList.size(); i < j; i++) {
			if(0!=i) selectItemIds+=",";
			selectItemIds+=siList.get(i).getItemVo().getId();
		}
		Map<String, String> map = new HashMap<String,String>();
		map.put("selectItemIds",selectItemIds);
		return map;
		
	}
	/**
	 * 限值中加项目 ajax 方法
	 * @throws GlobalException
	 */
	@RequestMapping(value="ajaxSavePstandItem.do")
	@ResponseBody
	@Log(operation=Log.Operation.SAVE,content="保存关系",module="评价标准项目关系")
	public Status savePstandItem(PstandItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			boolean flag = pstandItemService.savePstandItem(v);
			if(flag){
				status = new Status("关联项目成功",Status.STATUS_SUCCESS);
				List<PstandItemVo> siList = pstandItemService.listByStandId(v);
				String selectItemIds = "";
				for (int i = 0,j=siList.size(); i < j; i++) {
					if(0!=i) selectItemIds+=",";
					selectItemIds+=siList.get(i).getItemVo().getId();
				}
				status.setObject(selectItemIds);
			}else{
				if(v.getItemVo()==null){
					status = new Status("请选择需要关联的项目",Status.STATUS_ERROR);
				}else{
					status = new Status("关联的项目("+v.getItemVo().getName()+")已存在，请重新关联项目",Status.STATUS_ERROR);
				}
			}
		} catch (Exception e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"关联失败",e);
			status = new Status("关联失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	/**
	 * 职卫标准
	 * 限值中加项目 ajax 方法
	 * @throws GlobalException
	 */
	@RequestMapping(value="savePstandItem4Zw.do")
	@ResponseBody
	@Log(operation=Log.Operation.SAVE,content="保存关系",module="评价标准项目关系")
	public Status savePstandItem4Zw(PstandItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			boolean flag = pstandItemService.savePstandItem4Zw(v);
			if(flag){
				status = new Status("关联项目成功",Status.STATUS_SUCCESS);
				List<PstandItemVo> siList = pstandItemService.listByStandId(v);
				String selectItemIds = "";
				for (int i = 0,j=siList.size(); i < j; i++) {
					if(0!=i) selectItemIds+=",";
					selectItemIds+=siList.get(i).getItemVo().getId();
				}
				status.setObject(selectItemIds);
			}else{
				if(v.getItemVo()==null){
					status = new Status("请选择需要关联的项目",Status.STATUS_ERROR);
				}else{
					status = new Status("关联的项目("+v.getItemVo().getName()+")已存在，请重新关联项目",Status.STATUS_ERROR);
				}
			}
		} catch (Exception e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"关联失败",e);
			status = new Status("关联失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "listItem.do")
	public List<PstandItemVo> listItem(GridVo gridVo,PstandItemVo v) throws GlobalException {
		return pstandItemService.listItem(gridVo,v);
	}
	@Override
	public ModelAndView gridPage(PstandItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			SampTypeVo sampVo=sampTypeService.findById(v.getSampTypeId());
			v.setSampType(sampVo.getType());
			v.setSampTypeId(sampVo.getId());
			v.setSampTypeName(sampVo.getName());
			if(v.getSampType().contains("化学")) {
				mav.setViewName(getViewPath()+"_zw_page");
			}else {
				mav.setViewName(getViewPath()+"_page");
			}
		}
		mav.addObject(VO, v);
		return mav;
	}
 	@Override
	@RequestMapping(value=DELETE)
 	@Log(operation=Log.Operation.DELETE,content="删除关系",module="评价标准项目关系")
	public ModelAndView delete(PstandItemVo v,RedirectAttributes attr) throws GlobalException{
		try {
			baseService().delete(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}  
		attr.addFlashAttribute(STATUS, status);
 		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		mav.addObject("standId", v.getStandId());
		mav.addObject("sampType", v.getSampType());
		mav.addObject("sampTypeId", v.getSampTypeId());
		return mav;
 	}
 	//现场采样 现场监测项目获取大气 速率 指标集合
	@ResponseBody
	@RequestMapping(value = "listPit4Cy.do")
	public List<PstandItemVo> listPit4Cy(PstandItemVo v) throws GlobalException {
		return pstandItemService.listItem4Cy(v);
	}
}
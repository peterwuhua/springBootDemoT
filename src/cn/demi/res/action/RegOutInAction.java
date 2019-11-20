package cn.demi.res.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.res.po.RegOutIn;
import cn.demi.res.service.IRegOutInService;
import cn.demi.res.vo.ReagentVo;
import cn.demi.res.vo.RegOutInVo;

/**
 * <strong>Description : 试剂出入库</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
@Controller("res.regOutInAction")
@RequestMapping("/res/regoutin")
public class RegOutInAction extends BaseAction<RegOutInVo> {
	final String VIEW_PATH = "/res/reagent/regoutin/";
	@Autowired private IRegOutInService regOutInService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<RegOutInVo> baseService() {
		return regOutInService;
	}
	
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :出库页面 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/outPage")
	public ModelAndView outPage(RegOutInVo v) throws GlobalException{
		ModelAndView mv = new ModelAndView();
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "out_page");
		return mv;
	}
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :出库页面 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/inPage")
	public ModelAndView inPage(RegOutInVo v) throws GlobalException{
		ModelAndView mv = new ModelAndView();
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "in_page");
		return mv;
	}
	/**
	 * <strong>Create on : 2017年5月25日 上午10:46:36 </strong> <br>
	 * <strong>Description : 获取出库gridData</strong> <br>
	 * @param gridVo  GridVo
	 * @param v  V
	 * @return GridVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/outData")
	public GridVo outData(GridVo gridVo,RegOutInVo v) throws GlobalException{
		v.setStatus(RegOutIn.out);
		return baseService().gridData(gridVo,v);
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:46:36 </strong> <br>
	 * <strong>Description : 获取入库gridData</strong> <br>
	 * @param gridVo  GridVo
	 * @param v  V
	 * @return GridVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/inData")
	public GridVo inData(GridVo gridVo,RegOutInVo v) throws GlobalException{
		v.setStatus(RegOutIn.in);
		return baseService().gridData(gridVo,v);
	}
	
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :出库页面 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/outDepot")
	public ModelAndView outDepot(ReagentVo v) throws GlobalException{
		//v = reagentService.findById(v.getId());
		RegOutInVo vo = new RegOutInVo();
		vo.setReagentVo(v);
		ModelAndView mv = new ModelAndView();
		if(StrUtils.isBlankOrNull(vo.getLeadingPerson())) {
			vo.setLeadingPerson(getCurrent().getUserName());
			vo.setLeadingDate(DateUtils.getCurrDateTimeStr());
		}
		mv.addObject(VO, vo);
		mv.setViewName(VIEW_PATH + "out_edit");
		return mv;
	}
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :入库页面 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/inDepot")
	public ModelAndView inDepot(ReagentVo v) throws GlobalException{
		//v = reagentService.findById(v.getId());
		RegOutInVo vo = new RegOutInVo();
		vo.setReagentVo(v);
		ModelAndView mv = new ModelAndView();
		if(StrUtils.isBlankOrNull(vo.getInPerson())) {
			vo.setInPerson(getCurrent().getUserName());
			vo.setInDate(DateUtils.getCurrDateTimeStr());
		}
		mv.addObject(VO, vo);
		mv.setViewName(VIEW_PATH + "in_edit");
		return mv;
	}
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :出库库提交 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/addOutDepot")
	@ResponseBody
	public Status addOutIn(RegOutInVo v,RedirectAttributes attr) throws GlobalException{
		try {
			regOutInService.addOutDepot(v);
			status = new Status("操作成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"操作失败",e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :入库提交 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/addInDepot")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增出入库信息",module="试剂管理")
	public Status addInDepot(RegOutInVo v,RedirectAttributes attr) throws GlobalException{
		try {
			regOutInService.addInDepot(v);
			status = new Status("操作成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"操作失败",e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	@RequestMapping(value = "exportOutData.do")
	public ResponseEntity<byte[]> export4WTD(RegOutInVo v,PageResult pageResult,
			@RequestParam(value = "_type", required = true)String type,
			@RequestParam(value = "_source", required = true)String source,
			@RequestParam(value = "_target", required = true)String target) throws GlobalException {
		v.setStatus(RegOutIn.out);
		Map<String, Object> map = getOutExportData(v,pageResult,type);
		String path = ExportUtils.export(source, target, map);
		return down(target, path);
	}
	@RequestMapping(value = "exportInData.do")
	public ResponseEntity<byte[]> exportInData(RegOutInVo v,PageResult pageResult,
			@RequestParam(value = "_type", required = true)String type,
			@RequestParam(value = "_source", required = true)String source,
			@RequestParam(value = "_target", required = true)String target) throws GlobalException {
		v.setStatus(RegOutIn.in);
		Map<String, Object> map = getOutExportData(v,pageResult,type);
		String path = ExportUtils.export(source, target, map);
		return down(target, path);
	}
	
	private Map<String, Object> getOutExportData(RegOutInVo v,PageResult pageResult,String type) throws GlobalException{
 		Map<String, Object> map = new HashMap<String, Object>();
 		if("0".equals(type)){//全部数据
 			List<RegOutInVo> exportDatas = null;
 			if(v.getStatus().equals(RegOutIn.in)){
 				exportDatas = regOutInService.getInList();
 			}else{
 				exportDatas = regOutInService.getOutList();
 			}
 			map.put("list", exportDatas);
 		}else if("1".equals(type)){//选择的数据
 			List<RegOutInVo> exportDatas = baseService().listByIds(v.getIds(),pageResult.getOrderColumn(),pageResult.getOrder());
 			map.put("list", exportDatas);
 		}else{
 			map = getExportData(map, v, pageResult, type);
 		}
 		return map;
 	}
}

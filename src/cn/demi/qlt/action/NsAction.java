package cn.demi.qlt.action;

import java.util.ArrayList;
import java.util.List;

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
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.service.INsDetailService;
import cn.demi.qlt.service.INsItemService;
import cn.demi.qlt.service.INsService;
import cn.demi.qlt.vo.NsDetailVo;
import cn.demi.qlt.vo.NsItemVo;
import cn.demi.qlt.vo.NsVo;

@Controller("qlt.nsAction")
@RequestMapping("/qlt/ns")
public class NsAction extends BaseAction<NsVo> {
	final String VIEW_PATH = "/qlt/ns/plan/ns";
	@Autowired private INsService nsService;	
	@Autowired private INsItemService nsItemService;
	@Autowired private INsDetailService nsDetailService;
	@Autowired 
	private IProgressLogService progressLogService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<NsVo> baseService() {
		return nsService;
	}
	@Override
	public ModelAndView edit(NsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=nsService.findById(v.getId());
			if(null!=v.getIsBack()&&v.getIsBack().equals(Constants.Y)) {
				ProgressLogVo logVo=progressLogService.findBack(v.getId());
				mav.addObject("logVo", logVo);
			}
		}else {
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setDate(DateUtils.getCurrDateStr());
			v.setYear(DateUtils.getYear());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增内审计划",module="内审计划")
	public Status addData(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
			status.setObject(v.getId());
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新内审计划",module="内审计划")
	public Status updateData(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
			status.setObject(v.getId());
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存内审计划",module="内审计划")
	public ModelAndView save(NsVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除内审计划",module="内审计划")
	public ModelAndView delete(NsVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
	@ResponseBody
	@RequestMapping(value="update2Stop.do")
	@Log(operation=Log.Operation.DELETE,content="终止内审计划",module="内审计划")
	public Status update2Stop(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			nsService.update(v);
			status = new Status("终止成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"终止失败",e);
			status = new Status("终止失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public GridVo gridData(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_00.getStatus());
		v.setUserId(getCurrent().getAccountId());
		return nsService.gridData(gridVo,v);
	}
	@Override
	public GridVo gridDatad(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_00.getStatus());
		return nsService.gridDatad(gridVo,v);
	}
	/**
	 * 要素选择 
	 * 去除ids
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "list4Ys.do")
	public List<NsItemVo> list4Ys(NsItemVo v, RedirectAttributes attr)throws GlobalException {
		NsVo ns=nsService.findById(v.getId());
		List<String> ids=new ArrayList<String>();
		for (NsDetailVo dt : ns.getDetailList()) {
			ids.add(dt.getItemId());
		}
		return nsItemService.list4Ys(String.join(",", ids));
	}
	@ResponseBody
	@RequestMapping(value="addDetail.do")
	@Log(operation=Log.Operation.ADD,content="新增内审计划要素内容",module="内审计划")
	public Status addDetail(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			NsVo nsVo=nsService.findById(v.getId());
			NsDetailVo dtVo=new NsDetailVo();
			dtVo.setNsVo(nsVo);
			dtVo.setIds(v.getIds());
			nsDetailService.add(dtVo);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="deleteDetail.do")
	@Log(operation=Log.Operation.DELETE,content="删除内审计划要素",module="内审计划")
	public Status deleteDetail(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			nsDetailService.delete(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}  
		return status;
	}
}
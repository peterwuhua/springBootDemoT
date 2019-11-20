package cn.demi.bus.file.action;

import org.apache.commons.lang3.StringUtils;
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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.po.Archive;
import cn.demi.bus.file.service.IArchiveSqService;
import cn.demi.bus.file.service.IArchiveTypeService;
import cn.demi.bus.file.vo.ArchiveTypeVo;
import cn.demi.bus.file.vo.ArchiveVo;
import cn.demi.init.car.vo.CarUseVo;
/**
 * 
 * @ClassName:  ArchiveSqAction   
 * @Description:归档申请   
 * @author: 吴华 
 * @date:   2019年4月28日 上午11:01:21       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("bus.archiveSqAction")
@RequestMapping("/bus/archiveSq")
public class ArchiveSqAction extends BaseAction<ArchiveVo> {
	final String VIEW_PATH = "/bus/archive/file/sq/archive";
	@Autowired 
	private IArchiveSqService archiveSqService;	
	@Autowired 
	private IArchiveTypeService archiveTypeService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ArchiveVo> baseService() {
		return archiveSqService;
	}
	@Override
	public ModelAndView gridPage(ArchiveVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		ArchiveTypeVo typeVo = new ArchiveTypeVo();
		if(StrUtils.isNotBlankOrNull(v.getDirIds())){
			typeVo = archiveTypeService.findById(v.getDirIds());
		}
		v.setArchiveTypeVo(typeVo);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public ModelAndView edit(ArchiveVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dirIds", v.getDirIds());
		ArchiveTypeVo typeVo = new ArchiveTypeVo();
		if(StrUtils.isNotBlankOrNull(v.getDirIds())){
			typeVo=archiveTypeService.findById(v.getDirIds());
		}else{
			typeVo=archiveTypeService.findRoot();
		}
		v.setArchiveTypeVo(typeVo);
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=archiveSqService.findById(v.getId());
		}else {
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setTime(DateUtils.getCurrDateTimeStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	/**
	 * 
	 * @Title: save4Data   
	 * @Description: 保存方法   
	 * @param: @param v
	 * @param: @param attr
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping(value="save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存归档申请",module="归档管理")
	public ModelAndView save4Data(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		try {
			v.setStatus(Archive.STATUS_YBC);//已保存
			baseService().save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}
	/**
	 * 
	 * @Title: add4Data   
	 * @Description: 新增提交 
	 * @param: @param v
	 * @param: @param attr
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: Status      
	 * @throws
	 */
	@RequestMapping(value="add4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增归档申请",module="归档管理")
	public Status add4Data(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		v.setStatus(Archive.STATUS_YTJ);//已提交
		return super.addData(v, attr);
	}
	/**
	 * 
	 * @Title: update4Data   
	 * @Description: 修改提交   
	 * @param: @param v
	 * @param: @param attr
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: Status      
	 * @throws
	 */
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="修改归档申请",module="归档管理")
	public Status update4Data(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		v.setStatus(Archive.STATUS_YTJ);//已提交
		return super.updateData(v, attr);
	}
	@Override
	public ModelAndView update2del(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		try {
			archiveSqService.update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dirIds", v.getDirIds());
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
	/**
	 * 
	 * @Title: deleteArchive   
	 * @Description: 删除归档信息以及归档文件  
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: Status      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value ="deleteArchive.do")
	public Status deleteArchive(CarUseVo v) throws GlobalException {
		try {
			archiveSqService.deleteArchive(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
}
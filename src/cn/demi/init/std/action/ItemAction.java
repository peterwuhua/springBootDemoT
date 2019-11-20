package cn.demi.init.std.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.init.std.service.IItemMethodService;
import cn.demi.init.std.service.IItemService;
import cn.demi.init.std.vo.ItemMethodVo;
import cn.demi.init.std.vo.ItemVo;

@Controller("init.itemAction")
@RequestMapping("/init/item")
public class ItemAction extends BaseAction<ItemVo> {
	final String VIEW_PATH = "/init/item/item";
	@Autowired 
	private IItemService itemService;	
	@Autowired 
	private IItemMethodService itemMethodService;	
	@Autowired
	private IFilesService filesService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ItemVo> baseService() {
		return itemService;
	}
	@Override
	public ModelAndView edit(ItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			List<ItemMethodVo> imList=itemMethodService.listMethod4AllUser(v.getId());
			v.setMethodList(imList);
			List<FilesVo> fileList=filesService.listByBusId(v.getId(),Constants.FILE_TYPE_ITEM);
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	/**
	 * 获取项目集合
	 * 有子项的子项代替父项
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="gridData4Select.do")
	public GridVo gridData4Select(GridVo gridVo, ItemVo v) throws GlobalException {
		return itemService.gridData4Select(gridVo,v);
	}
	/**
	 * 评价标准 限值维护 获取项目集合
	 */
	@ResponseBody
	@RequestMapping(value ="gridData4StandItem.do")
	public GridVo gridData4StandItem(GridVo gridVo, ItemVo v) throws GlobalException {
		return itemService.gridData4StandItem(gridVo,v);
	}
	@ResponseBody
	@RequestMapping(value="add4Data.do")
	@Log(operation=Log.Operation.ADD,content="新增检测项目",module="检测项目管理")
	public Status add4Data(ItemVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			ItemVo vo=itemService.findByName(v.getName());
			if(null!=vo) {
				status = new Status("新增失败,项目已存在！",Status.STATUS_ERROR);
			}else {
				itemService.add(v);
				//文件上传
				List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_ITEM);
				filesService.saveFiles(fileList);
				status = new Status("新增成功",Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="修改检测项目",module="检测项目管理")
	public Status update4Data(ItemVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			itemService.update(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_ITEM);
			filesService.saveFiles(fileList);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存检测项目",module="检测项目管理")
	public ModelAndView save4Data(ItemVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			itemService.save(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_ITEM);
			filesService.saveFiles(fileList);
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
	@ResponseBody
	@RequestMapping(value ="update4del.do")
	@Log(operation=Log.Operation.UPDATE,content="删除检测项目",module="检测项目管理")
	public Status update4del(ItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value ="select.do")
	public ModelAndView select(ItemVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<ItemVo> itList=itemService.list(v);
		mav.addObject("itList", itList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_select");
		return mav;
	}
	/**
	 * 获取项目集合
	 * 选择项目和方法得弹窗时   左侧项目集合数据源
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="gridData4Im.do")
	public GridVo gridData4Im(GridVo gridVo, ItemVo v) throws GlobalException {
		return itemService.gridData4Im(gridVo,v);
	}
	/**
	 *获取项目集合
	 *选择项目和方法得弹窗时   右侧项目方法列表数据源
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="list4Im.do")
	public List<ItemVo> list4Im(String ids) throws GlobalException {
		List<ItemVo> itList=itemService.list4Im(ids);
		return itList;
	}
}
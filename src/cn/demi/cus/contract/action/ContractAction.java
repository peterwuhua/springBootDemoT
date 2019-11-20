package cn.demi.cus.contract.action;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.Action;
import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.importx.ImportUtils;
import cn.demi.cus.contract.service.IContractDetailService;
import cn.demi.cus.contract.service.IContractExecuteService;
import cn.demi.cus.contract.service.IContractService;
import cn.demi.cus.contract.vo.ContractDetailVo;
import cn.demi.cus.contract.vo.ContractExecuteVo;
import cn.demi.cus.contract.vo.ContractVo;

/**
 * Create on : 2016年11月15日 下午3:02:21  <br>
 * Description : 合同信息Action <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Controller("cus.contractAction")
@RequestMapping("/cus/contract")
public class ContractAction extends BaseAction<ContractVo> {
	final String VIEW_PATH = "/cus/contract/contract";
	final String SALE_VIEW_PATH = "/cus/contract/crm/contract";
//	public static final String SERVER_CONTRACT_DIR = ApplicationUtils.getValue("config.upload.pjt.contract").toString().replace("\\", "/");
	
	@Autowired private IContractService contractService;
	@Autowired private IContractDetailService contractDetailService;
	@Autowired private IContractExecuteService contractExecuteService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	
	public String getoTherViewPath() {
		return SALE_VIEW_PATH;
	}
	
	@Override
	public IBaseService<ContractVo> baseService() {
		return contractService;
	}
	
	@Override
	public ModelAndView add(ContractVo v, RedirectAttributes attr) throws GlobalException {
		try {
			contractService.add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		ModelAndView mav = new ModelAndView();
		attr.addFlashAttribute(STATUS, status);
		if("1".equals(v.getFlag())){
			mav.setViewName(REDIRECT_PAGE);
		}else if("2".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Execut.do");
		}else if("3".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Com.do");
		}else if("4".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Fast.do");
		}else if("5".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Expired.do");
		}else{
			mav.setViewName(REDIRECT_PAGE);
		}
		return mav;
	}
	@ResponseBody
	@RequestMapping(value= "update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="修改合同",module="合同管理")
	public Status update4Data(ContractVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setPath(getPath(v,file.getOriginalFilename()));
			upload(file, v.getPath());
		}
		try {
			contractService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			status = new Status("更新失败，请检查数据是否过期！",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public ModelAndView update(ContractVo v, RedirectAttributes attr) throws GlobalException {
		try {
			contractService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		if("1".equals(v.getFlag())){
			mav.setViewName(REDIRECT_PAGE);
		}else if("2".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Execut.do");
		}else if("3".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Com.do");
		}else if("4".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Fast.do");
		}else if("5".equals(v.getFlag())){
			mav.setViewName("redirect:gridPage4Expired.do");
		}else{
			mav.setViewName(REDIRECT_PAGE);
		}
		return mav;
	}
	@RequestMapping(value= "save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存合同",module="合同管理")
	public ModelAndView save4Data(ContractVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setPath(getPath(v,file.getOriginalFilename()));
			upload(file, v.getPath());
		}
		try {
			contractService.save(v);
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
	 * Create on : Danlee Li 2016年11月16日 上午10:55:43 <br>
	 * Description : gridTab <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridTab.do")
	public ModelAndView gridTab(ContractVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_tab");
		return mav;
	}
	/**
	 * Create on : Danlee Li 2016年11月16日 下午2:41:32 <br>
	 * Description : gridData4Tab <br>
	 * @param gridVo
	 * @param v
	 * @return 返回JSON
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Tab.do")
	public GridVo gridData4Tab(GridVo gridVo,ContractVo v) throws GlobalException{
		return contractService.gridData4Tab(gridVo, v);
	}
	
	/**
	 * 
	 * @Title: editSale   
	 * @Description: 编辑   
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping(value="editSale.do")
	public ModelAndView editSale(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false; 
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			isEdit = true;
		}
		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getoTherViewPath()+"_edit");
		return mav;
	}
	
	
	
	
	
	/**
	 * 
	 * @Title: gridData4Sale   
	 * @Description: 销售合同管理   (从已签订、执行中、已完结、快到期、已超期)
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Sale.do")
	public GridVo gridData4Sale(GridVo gridVo,ContractVo v) throws GlobalException{
		return contractService.gridData4Sale(gridVo, v);
	}
	/**
	 * 
	 * <p>Title: gridPage</p>   
	 * <p>Description: </p>   
	 * @param v
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#gridPage(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value="saleGridPaged.do")
	public ModelAndView saleGridPaged(ContractVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getoTherViewPath()+"_sale_page");
		return mav;
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 快到期的合同  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4FastSale.do")
	public GridVo gridData4FastSale(GridVo gridVo,ContractVo v) throws GlobalException{
		return contractService.gridData4FastSale(gridVo, v);
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 快到期的合同  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@RequestMapping(value="saleFastGridPaged.do")
	public ModelAndView saleFastGridPaged(ContractVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getoTherViewPath()+"_fast_page");
		return mav;
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 已超期的合同  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4ExpiredSale.do")
	public GridVo gridData4ExpiredSale(GridVo gridVo,ContractVo v) throws GlobalException{
		return contractService.gridData4ExpiredSale(gridVo, v);
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 已超期的合同   
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@RequestMapping(value="saleExpiredGridPaged.do")
	public ModelAndView saleExpiredGridPaged(ContractVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getoTherViewPath()+"_expired_page");
		return mav;
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 执行中的合同  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4ExecuteSale.do")
	public GridVo gridData4ExecuteSale(GridVo gridVo,ContractVo v) throws GlobalException{
		return contractService.gridData4ExecuteSale(gridVo, v);
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 执行中的合同   
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@RequestMapping(value="saleExecuteGridPaged.do")
	public ModelAndView saleExecuteGridPaged(ContractVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getoTherViewPath()+"_execute_page");
		return mav;
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 已完结的合同  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4ComSale.do")
	public GridVo gridData4ComSale(GridVo gridVo,ContractVo v) throws GlobalException{
		return contractService.gridData4ComSale(gridVo, v);
	}
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description: 已完结的合同    
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@RequestMapping(value="saleComGridPaged.do")
	public ModelAndView saleComGridPaged(ContractVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getoTherViewPath()+"_com_page");
		return mav;
	}
	
	
	@Override
	public ModelAndView importExcel(ContractVo v, String param, String type, MultipartFile file,
			RedirectAttributes attr) throws GlobalException {
		try {
			contractService.importData(v, type,param,ImportUtils.importData(file));
			status = new Status("导入成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("数据导入失败 ",e);
			status = new Status("导入失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		attr.addAttribute("stat",status.getStatus());
		attr.addAttribute("msg",status.getMessage());
		ModelAndView mav = new ModelAndView();
		if(StrUtils.isNotBlankOrNull(param)){
			mav.setViewName("redirect:gridTab.do?customerVo.id="+param);
		}else {
			mav.setViewName(REDIRECT_PAGE);
		}
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月17日 下午2:31:14 </strong> <br>
	 * <strong>Description :获取文件存储路径 </strong> <br>
	 * @param v
	 * @return
	 */
	private String getPath(ContractVo v,String fileName){
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String path = UPLOAD_DIR+Constants.FILE_TYPE_PROJECT;
		path = path+"/"+v.getCode()+fileType;
		v.setTrueName(fileName);
		return path.replace("\\", "/");
	}
	
	/**
	 * Create on : Paddy Zhang 2017年1月10日 上午9:49:43 <br>
	 * Description :客户管理tab标签页合同信息新增修改 <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="editTab.do")
	public ModelAndView editTab(ContractVo v) throws GlobalException {
		ModelAndView mav = super.edit(v);
		mav.setViewName(getViewPath()+"_tab_edit");
		return mav;
	}
	/**
	 * Create on : Paddy Zhang 2017年1月10日 上午10:17:37 <br>
	 * Description : 客户管理tab标签页合同信息新增修改保存 <br>
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"addTab.do","updateTab.do"})
	public ModelAndView addOrUpdateTab(ContractVo v,RedirectAttributes attr) throws GlobalException{
		ModelAndView mav =super.save(v, attr);
		mav.addObject("customerVo.id", v.getCustomerVo().getId());
		mav.setViewName("redirect:gridTab.do");
		return mav;
	}
	/**
	 * Create on : Paddy Zhang 2017年1月10日 下午12:34:15 <br>
	 * Description :  客户管理tab标签合同删除<br>
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="update2del4Tab.do")
	public ModelAndView update2del4Tab(ContractVo v,RedirectAttributes attr) throws GlobalException{
		ModelAndView mav = super.update2del(v, attr);
		mav.addObject("customerVo.id", v.getCustomerVo().getId());
		mav.setViewName("redirect:gridTab.do");
		return mav;
	}
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:45:15 <br>
	 * Description : 执行中合同 <br>
	 * @param v ContractVo
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridPage4Execut.do")
	public ModelAndView gridPage4Execut(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_execut_page");
		return mav;
	}
	
	@RequestMapping(value="gridData4Execut.do")
	@ResponseBody
	public GridVo gridData4Execut(GridVo gridVo, ContractVo v) throws GlobalException {
		return contractService.gridData4Execut(gridVo, v);
	}
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:45:02 <br>
	 * Description : 已完结合同 <br>
	 * @param v ContractVo
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridPage4Com.do")
	public ModelAndView gridPage4Com(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_com_page");
		return mav;
	}
	@RequestMapping(value="gridData4Com.do")
	@ResponseBody
	public GridVo gridData4Com(GridVo gridVo, ContractVo v) throws GlobalException {
		return contractService.gridData4Com(gridVo, v);
	}
	
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:44:35 <br>
	 * Description :快到期合同  <br>
	 * @param v ContractVo
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridPage4Fast.do")
	public ModelAndView gridPage4Fast(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_fast_page");
		return mav;
	}
	@RequestMapping(value="gridData4Fast.do")
	@ResponseBody
	public GridVo gridData4Fast(GridVo gridVo, ContractVo v) throws GlobalException {
		return contractService.gridData4Fast(gridVo, v);
	}
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:44:35 <br>
	 * Description :已到期合同  <br>
	 * @param v ContractVo
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridPage4Expired.do")
	public ModelAndView gridPage4Expired(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_expired_page");
		return mav;
	}
	@RequestMapping(value="gridData4Expired.do")
	@ResponseBody
	public GridVo gridData4Expired(GridVo gridVo, ContractVo v) throws GlobalException {
		return contractService.gridData4Expired(gridVo, v);
	}
	
	@Override
	public ModelAndView show(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav = super.show(v);
		List<ContractDetailVo> detailList = contractDetailService.listByConId(v.getId());
		List<ContractExecuteVo> executeList = contractExecuteService.listByConId(v.getId());
		mav.addObject("detailList", detailList);
		mav.addObject("executeList", executeList);
		return mav;
	}
	
	
	@RequestMapping(value="showCrm.do")
	public ModelAndView showCrm(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav = super.show(v);
		List<ContractDetailVo> detailList = contractDetailService.listByConId(v.getId());
		List<ContractExecuteVo> executeList = contractExecuteService.listByConId(v.getId());
		mav.addObject("detailList", detailList);
		mav.addObject("executeList", executeList);
		mav.setViewName(getoTherViewPath()+"_show");
		return mav;
	}
	
	
	
	
	/**
	 * Description :合同查询  <br>
	 * @param v ContractVo
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridPage4Query.do")
	public ModelAndView gridPage4Query(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_query_page");
		return mav;
	}
	@RequestMapping(value="gridData4Query.do")
	@ResponseBody
	public GridVo gridData4Query(GridVo gridVo, ContractVo v) throws GlobalException {
		return contractService.gridData4Expired(gridVo, v);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "gridList.do")
	public ModelAndView gridList(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getoTherViewPath() + "_win_page");
		return mav;
	}
	
	@RequestMapping(value="gridListData.do")
	@ResponseBody
	public GridVo gridListData(GridVo gridVo, ContractVo v) throws GlobalException {
		return contractService.gridListData(gridVo, v);
	}
	
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = Action.SERVER_BASE;
		String downLoadPath = ctxPath + filePath.replace("/", File.separator);  
        return down(trueName, downLoadPath);
	}
	
	
}
package cn.demi.base.system.action;

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
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IDutyService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.service.IRoleService;
import cn.demi.base.system.service.IUserService;
import cn.demi.base.system.vo.AccountRoleVo;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.base.system.vo.UserVo;
/**
 * <strong>Create on : 2016年11月2日 下午2:28:22 </strong> <br>
 * <strong>Description : 用户信息 action </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.userAction")
@RequestMapping("sys/user")
public class UserAction extends BaseAction<UserVo> {

	final String VIEW_PATH = "/sys/user/user";

	@Autowired
	private IDutyService dutyService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IAccountRoleService accountRoleService;
	@Autowired
	private IFilesService filesService;
	@Autowired 
	private ICodeService codeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<UserVo> baseService() {
		return userService;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增人员",module="人员管理")
	public ModelAndView add(UserVo v, RedirectAttributes attr) throws GlobalException {
		return super.add(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存人员",module="人员管理")
	public ModelAndView save(UserVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改人员",module="人员管理")
	public ModelAndView update(UserVo v, RedirectAttributes attr) throws GlobalException {
		return super.update(v, attr);
	}
	@RequestMapping(value="save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存人员",module="人员管理")
	public ModelAndView save4Data(UserVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] file) throws GlobalException {
		try {
			baseService().save(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_USER);
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
	@RequestMapping(value="update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="修改人员",module="人员管理")
	public Status update4Data(UserVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] file) throws GlobalException {
		try {
			baseService().update(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_USER);
			filesService.saveFiles(fileList);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="add4Data.do")
	@Log(operation=Log.Operation.ADD,content="新增人员",module="人员管理")
	public Status add4Data(UserVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] file) throws GlobalException {
		try {
			baseService().add(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_USER);
			filesService.saveFiles(fileList);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除人员",module="人员管理")
	public ModelAndView update2del(UserVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
	@Override
	public ModelAndView edit(UserVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=baseService().findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		//样品保存条件
		List<String> dutyList=codeService.listByCode("user-duty");
		mav.addObject("dutyList", dutyList);
		boolean isNewAccount  = true;
		//查询用户账户信息
//		if (null != v && StrUtils.isNotBlankOrNull(v.getId())) {
//			mv.addObject("accountList", accountService.listByUserId(v.getId()));
//		}
		if(StrUtils.isBlankOrNull(v.getId())){
			 isNewAccount  = false;
		}
		mav.addObject("isNewAccount", isNewAccount);
		
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	/**
	 * Create on : Paddy Zhang 2016年11月18日 下午1:24:30 <br>
	 * Description : 人员管理>账户编辑 <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="accountEdit.do")
	public ModelAndView accountEidt(AccountVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		List<AccountVo> accountList = null;
		UserVo userVo = null;
		boolean isNewAccount = false;
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = accountService.findById(v.getId());
		}
		mav.addObject(VO,v);
		mav.setViewName(getViewPath()+"_account_edit");
		mav.addObject("roleList", roleService.list4Login());
		//查询组织信息
		OrgVo orgVo = null;
		try {
			if (null == v.getOrgVo()) {
				if (StrUtils.isBlankOrNull(v.getOrgId())) {
					orgVo = orgService.findRoot();
				}else {
					orgVo = orgService.findById(v.getOrgId());
				}
				v.setOrgVo(orgVo);
			}
		} catch (GlobalException e) {
			log.info("查询组织出错", e);
			log.info(e.getMessage(), e);
		}
		//查询账户信息
		try {
			if (null != v.getUserVo() && !StrUtils.isBlankOrNull(v.getUserVo().getId())) {
				//查询用户下所有账户信息
				userVo = userService.findById(v.getUserVo().getId());
				v.setUserVo(userVo);
				accountList = accountService.listByUserId(v.getUserVo().getId());
				mav.addObject("accountList", accountList);
				mav.addObject(IS_EDIT, true);
				if (StrUtils.isBlankOrNull(v.getId()) || CollectionUtils.isEmpty(accountList)) {
					isNewAccount = true;
					mav.addObject("isNewAccount", isNewAccount);
					return mav;
				}
			}
		} catch (GlobalException e) {
			log.info("查询账户信息出错", e);
			log.info(e.getMessage(), e);
			v = new AccountVo();
			v.setUserVo(userVo);
			mav.addObject(IS_EDIT, true);
			mav.addObject("isNewAccount", true);
			return mav;
		}
		List<AccountRoleVo> accountRoleList = accountRoleService.listByAccountId(v.getId());
		if(CollectionUtils.isNotEmpty(accountRoleList)){
			String roleSelectIds = "";
			for (AccountRoleVo accountRoleVo : accountRoleList) {
				roleSelectIds += accountRoleVo.getRoleVo().getId() + ",";
			}
			mav.addObject("roleSelectIds",roleSelectIds);
		}
		return mav;
	}
	/**
	 * 人员管理修改账户
	 * Create on : Paddy Zhang 2016年11月18日 下午1:23:17 <br>
	 * Description : 保存账户并返回原页面 <br>
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"saveAccount.do","addOrUpdateAccount.do"})
	@Log(operation=Log.Operation.SAVE,content="修改账户",module="人员管理")
	public ModelAndView saveAccount(AccountVo v,RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		
		try {
			if(null!=file && !file.isEmpty()){
				v.setSignature(getPath(v.getLoginName(),file.getOriginalFilename()));
				upload(file, v.getSignature());
			}
			if (!StrUtils.isBlankOrNull(v.getId())) {
				accountService.update(v);
				status = new Status("修改成功",Status.STATUS_SUCCESS);
			}else {
				accountService.add(v);
				status = new Status("新增成功",Status.STATUS_SUCCESS);
			}
			mav.addObject("id",v.getId());
		}catch (GlobalException e) {
			mav.addObject("userVo.id",v.getUserVo().getId());
			if (!StrUtils.isBlankOrNull(v.getId())) {
				log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
				status = new Status("修改失败",Status.STATUS_ERROR);
			}else {
				log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
				status = new Status("新增失败",Status.STATUS_ERROR);
			}
		}
		attr.addFlashAttribute(STATUS, status);
		String url = request.getRequestURI();
		if(url.contains("save")){
			mav.setViewName("redirect:accountEdit.do");
		}else {
			mav.setViewName(REDIRECT_2_GRID_PAGE);
		}
		return mav;
	}
	/**
	 * Create on : Paddy Zhang 2016年12月20日 下午7:04:25 <br>
	 * Description :  人员下新增账户保存电子签章<br>
	 * @param v
	 * @return
	 */
	private String getPath(String logName,String filename){
		String fileType = filename.substring(filename.lastIndexOf("."));
		String path = AccountAction.SERVER_SIGN_DIR;
		path = path+"/"+logName+fileType;
		return path.replace("\\", "/");
	}
	/**
	 * Create on : Paddy Zhang 2016年12月22日 下午2:48:41 <br>
	 * Description :  修改人员基本信息<br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="preUserInfoEdit.do")
	public ModelAndView preUserInfoEdit(UserVo v) throws GlobalException{
		if(StrUtils.isBlankOrNull(v.getId())) {
			v.setId(getCurrent().getUserId());
		}
		UserVo  vo = userService.findById(v.getId());
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, vo);
		mav.addObject("dutyList", dutyService.list());
		mav.setViewName(getViewPath()+"_info_edit");		
		return mav;
	}
	/**
	 * Create on : Paddy Zhang 2016年12月22日 下午2:48:56 <br>
	 * Description :  保存人员基本信息<br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="saveUserInfo.do")
	@Log(operation=Log.Operation.SAVE,content="保存人员基本信息",module="人员管理")
	public ModelAndView saveUserInfo(UserVo v,RedirectAttributes attr) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		try {
			userService.saveUserInfo(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (Exception e){
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		mav.setViewName("redirect:preUserInfoEdit.do");
		attr.addFlashAttribute(STATUS, status);
		mav.addObject("id",v.getId());
		return mav;
	}
	/**
	 * 获取用户集合
	 * @param 
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "listUser.do")
	public List<UserVo> listUser(GridVo gridVo,UserVo v) throws GlobalException {
		List<UserVo> userList = userService.list(gridVo,v);
		return userList;
		
	}
	@Override
	public GridVo gridData(GridVo gridVo, UserVo v) throws GlobalException {
		if(StrUtils.isBlankOrNull(v.getOrgId())&&!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
			v.setOrgId(getCurrent().getOrgId());
		}
		return super.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value = "updateTheme.do")
	@Log(operation=Log.Operation.UPDATE,content="修改主题",module="人员管理")
	public Status updateTheme(UserVo v,RedirectAttributes attr) throws GlobalException{
		try {
			userService.updateTheme(getCurrent().getUserId(), v.getTheme());
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
}

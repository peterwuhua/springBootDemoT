package cn.demi.base.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.service.IRolePermService;
import cn.demi.base.system.service.IRoleService;
import cn.demi.base.system.vo.AccountRoleVo;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.base.system.vo.RolePermVo;
import cn.demi.base.system.vo.RoleVo;

/**
 * <strong>Create on : 2016年11月2日 下午1:30:43 </strong> <br>
 * <strong>Description : 账户信息action</strong> <br>
 *
 * @author <strong>Dave Yu</strong><br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
@Controller("sys.accountAction")
@RequestMapping("sys/account")
public class AccountAction extends BaseAction<AccountVo> {

  final String VIEW_PATH = "/sys/account/account";
  public static final String SERVER_SIGN_DIR = ApplicationUtils.getValue("config.upload.sys.sign").toString().replace("\\", "/");

  @Autowired
  private IAccountService accountService;
  @Autowired
  private IOrgService orgService;
  @Autowired
  private IRoleService roleService;
  @Autowired
  private IAccountRoleService accountRoleService;
  @Autowired
  private IRolePermService rolePermService;

  @Override
  public String getViewPath() {
    return VIEW_PATH;
  }

  @Override
  public IBaseService<AccountVo> baseService() {
    return accountService;
  }

  @Override
  public ModelAndView edit(AccountVo v) throws GlobalException {
    ModelAndView mav = super.edit(v);
    mav.addObject("roleList", roleService.list4Login());
    OrgVo orgVo = null;
    if (null == v.getOrgVo()) {
      if (StrUtils.isBlankOrNull(v.getOrgId())) {
        orgVo = orgService.findRoot();
      } else {
        orgVo = orgService.findById(v.getOrgId());
      }
      v.setOrgVo(orgVo);
      v.setIsUse(String.valueOf(1));
    }
    if (StrUtils.isBlankOrNull(v.getId())) {
      return mav;
    }
    List<AccountRoleVo> accountRoleList = accountRoleService.listByAccountId(v.getId());
    if (CollectionUtils.isNotEmpty(accountRoleList)) {
      String roleSelectIds = "";
      for (AccountRoleVo accountRoleVo : accountRoleList) {
        roleSelectIds += accountRoleVo.getRoleVo().getId() + ",";
      }
      mav.addObject("roleSelectIds", roleSelectIds);
    }
    return mav;
  }

  @Override
  @Log(operation = Log.Operation.ADD, content = "新增账户", module = "账户管理")
  public ModelAndView add(AccountVo v, RedirectAttributes attr) throws GlobalException {
    ModelAndView mav = super.add(v, attr);
    mav.addObject("orgId", v.getOrgId());
    return mav;
  }

  @ResponseBody
  @RequestMapping(value = "add4Data.do")
  @Log(operation = Log.Operation.ADD, content = "新增账户", module = "账户管理")
  public Status addData(AccountVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile file) throws GlobalException {
    if (null != file && !file.isEmpty()) {
      v.setSignature(getPath(v.getLoginName(), file.getOriginalFilename()));
      upload(file, v.getSignature());
    }
    try {
      baseService().add(v);
      status = new Status("新增成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
      status = new Status("新增失败", Status.STATUS_ERROR);
    }
    return status;
  }

  @Override
  @Log(operation = Log.Operation.UPDATE, content = "修改账户", module = "账户管理")
  public ModelAndView update(AccountVo v, RedirectAttributes attr) throws GlobalException {
    ModelAndView mav = super.update(v, attr);
    mav.addObject("orgId", v.getOrgId());
    return mav;
  }

  @ResponseBody
  @RequestMapping(value = "update4Data.do")
  @Log(operation = Log.Operation.UPDATE, content = "修改账户", module = "账户管理")
  public Status updateData(AccountVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile file) throws GlobalException {
    if (null != file && !file.isEmpty()) {
      v.setSignature(getPath(System.currentTimeMillis() + "", file.getOriginalFilename()));
      upload(file, v.getSignature());
    }
    try {
      baseService().update(v);
      status = new Status("修改成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
      status = new Status("修改失败", Status.STATUS_ERROR);
    }
    return status;
  }

  @RequestMapping(value = "save4Data.do")
  @Log(operation = Log.Operation.SAVE, content = "保存账户", module = "账户管理")
  public ModelAndView save(AccountVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile file) throws GlobalException {
    if (null != file && !file.isEmpty()) {
      v.setSignature(getPath(v.getLoginName(), file.getOriginalFilename()));
      upload(file, v.getSignature());
    }
    ModelAndView mav = super.save(v, attr);
    mav.addObject("orgId", v.getOrgId());
    return mav;
  }

  @Override
  @Log(operation = Log.Operation.DELETE, content = "删除账户", module = "账户管理")
  public ModelAndView update2del(AccountVo v, RedirectAttributes attr) throws GlobalException {
    ModelAndView mav = super.update2del(v, attr);
    mav.addObject("orgId", v.getOrgId());
    return mav;
  }

  /**
   * <strong>Create on : 2016年11月17日 下午2:31:14 </strong> <br>
   * <strong>Description :获取文件存储路径 </strong> <br>
   *
   * @param loginName
   * @param fileName
   * @return
   */
  private String getPath(String loginName, String fileName) {
    String fileType = fileName.substring(fileName.lastIndexOf("."));
    String path = SERVER_SIGN_DIR;
    path = path + "/" + loginName + fileType;
    return path.replace("\\", "/");
  }

  /**
   * <strong>Create on : Dave Yu 2016年11月2日 下午1:31:44 </strong> <br>
   * <strong>Description :修改密码前 </strong> <br>
   *
   * @return ModelAndView
   */
  @RequestMapping(value = "resetPage.do")
  public ModelAndView resetPwd() {
    ModelAndView mav = new ModelAndView(getViewPath() + "_reset_pwd");
    return mav;
  }

  /**
   * <strong>Create on : Dave Yu 2016年11月2日 下午1:32:04 </strong> <br>
   * <strong>Description :修改密码 </strong> <br>
   *
   * @param attr
   * @param oldPwd 原密码
   * @param newPwd 新密码
   * @return ModelAndView
   */
  @RequestMapping(method = RequestMethod.POST, value = "resetPwd.do")
  @Log(operation = Log.Operation.UPDATE, content = "修改密码", module = "账户管理")
  public ModelAndView resetPwd(RedirectAttributes attr, @RequestParam(value = "oldPwd") String oldPwd, @RequestParam(value = "newPwd") String newPwd, @RequestParam(value = "newPwdConfirm") String newPwdConfirm) {
    if (newPwd.equals(newPwdConfirm)) {
      try {
        accountService.resetPwd(getCurrent().getAccountId(), oldPwd, newPwd);
        status = new Status("修改成功", Status.STATUS_SUCCESS);
      } catch (GlobalException e) {
        status = new Status("请输入旧密码", Status.STATUS_ERROR);
      }
    } else {
      status = new Status("新密码输入不一致", Status.STATUS_ERROR);
    }

    attr.addFlashAttribute(STATUS, status);
    return new ModelAndView("redirect:resetPage.do");
  }

  /**
   * Create on : Paddy Zhang 2016年11月30日 下午3:45:40 <br>
   * Description :  查看当前账户下每个角色拥有的功能与权限<br>
   *
   * @param v
   * @return
   * @throws GlobalException
   */
  @RequestMapping(value = "showPerm.do")
  public ModelAndView showPerm(AccountVo v, String roleId) throws GlobalException {
    ModelAndView mav = new ModelAndView();
    v = accountService.findById(v.getId());
    mav.addObject(VO, v);
    List<AccountRoleVo> accountRoleList = accountRoleService.listByAccountId(v.getId());
    if (CollectionUtils.isEmpty(accountRoleList)) {
      mav.setViewName(getViewPath() + "_perm_show");
      return mav;
    }
    mav.addObject("accountRoleList", accountRoleList);
    RoleVo roleVo = new RoleVo();
    if (StrUtils.isBlankOrNull(roleId)) {
      roleVo = accountRoleList.get(0).getRoleVo();
    } else {
      roleVo = roleService.findById(roleId);
    }
    mav.addObject("roleVo", roleVo);
    mav.setViewName(getViewPath() + "_perm_show");
    return mav;
  }

  /**
   * Create on : Paddy Zhang 2016年11月30日 下午4:47:20 <br>
   * Description : 获取账户下每个角色拥有的每个功能拥有的权限 <br>
   *
   * @param gridVo
   * @param v
   * @return
   * @throws GlobalException
   */
  @ResponseBody
  @RequestMapping(value = "gridData4RolePerm.do")
  public GridVo gridData4RolePerm(GridVo gridVo, RolePermVo v, @RequestParam(value = "roleId", required = false) String roleId)
      throws GlobalException {
    if (StrUtils.isBlankOrNull(roleId)) {
      log.info("请传入正确参数");
      return new GridVo();
    }
    RoleVo roleVo = new RoleVo();
    roleVo.setId(roleId);
    v.setRoleVo(roleVo);
    return rolePermService.gridData(gridVo, v);
  }

  /**
   * Create on : Paddy Zhang 2016年12月19日 下午6:16:26 <br>
   * Description :  保存账户状态启用与停用<br>
   *
   * @param attr
   * @param v
   * @param accountIsUse 账户状态
   * @return
   * @throws GlobalException
   */
  @RequestMapping(value = "updateAccountIsUse.do")
  @Log(operation = Log.Operation.UPDATE, content = "启用或停用账户", module = "账户管理")
  public ModelAndView updateAccountIsUse(RedirectAttributes attr, AccountVo v,
                                         @RequestParam(value = "accountIsUse", required = true) String accountIsUse) throws GlobalException {
    try {
      accountService.updateAccountIsuse(v.getIds().trim(), accountIsUse);
      status = new Status("保存账户启用状态成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      status = new Status("保存账户状态失败", Status.STATUS_ERROR);
    }
    attr.addFlashAttribute(STATUS, status);
    ModelAndView mav = new ModelAndView(REDIRECT_PAGE);
    mav.addObject("orgId", v.getOrgId());
    return mav;
  }

  /**
   * Create on : Paddy Zhang 2016年12月19日 下午6:49:05 <br>
   * Description :  检查登录名是否存在<br>
   *
   * @param v
   * @return
   * @throws GlobalException
   */
  @ResponseBody
  @RequestMapping(value = "checkLoginName.do")
  public Map<String, Object> checkLoginName(AccountVo v) throws GlobalException {
    Map<String, Object> map = new HashMap<String, Object>();
    if (StrUtils.isBlankOrNull(v.getLoginName())) {
      map.put("type", Status.STATUS_ERROR);
      map.put("message", "请输入账户名！！");
      return map;
    }
    String loginName = v.getLoginName().trim();
    AccountVo oldVo = null;
    if (StrUtils.isNotBlankOrNull(v.getId())) {
      AccountVo accountVo = accountService.findById(v.getId());
      if (!loginName.equals(accountVo.getLoginName())) {
        oldVo = accountService.find(loginName);
        if (null != oldVo) {
          map.put("type", Status.STATUS_ERROR);
          map.put("message", "账户已存在：" + oldVo.getLoginName());
          return map;
        }
      }
    } else {
      oldVo = accountService.find(loginName);
      if (null != oldVo) {
        map.put("type", Status.STATUS_ERROR);
        map.put("message", "账户已存在：" + oldVo.getLoginName());
        return map;
      }
    }
    map.put("type", Status.STATUS_SUCCESS);
    map.put("message", "账户可以使用");
    return map;

  }

  /**
   * 获取账号集合 （传入角色编号等信息）
   *
   * @param v
   * @param funCode
   * @return
   * @throws GlobalException
   */
  @ResponseBody
  @RequestMapping(value = "listAccount.do")
  public List<AccountVo> listAccount(GridVo gridVo, AccountVo v, String funCode) throws GlobalException {
    String orgCode = getCurrent().getOrgCode();
    if (null != v.getOrgVo() && null != v.getOrgVo().getId()) {
      orgCode = orgService.findById(v.getOrgVo().getId()).getCode();
    } else if (null != v.getOrgVo() && null != v.getOrgVo().getCode()) {
      orgCode = v.getOrgVo().getCode();
    }
    List<AccountVo> accountList = accountRoleService.listAccount(gridVo, orgCode, v.getRoleCode(), funCode);
    return accountList;
  }

  /**
   * 获取账号集合 （传入角色编号等信息）
   *
   * @param
   * @param roleCode
   * @return
   * @throws GlobalException
   */
  @ResponseBody
  @RequestMapping(value = "listAccount4All.do")
  public List<AccountVo> listAccount4All(GridVo gridVo, String roleCode) throws GlobalException {
    List<AccountVo> accountList = accountRoleService.listAccount(gridVo, roleCode);
    return accountList;
  }

  @Override
  public GridVo gridData(GridVo gridVo, AccountVo v) throws GlobalException {
//		if(StrUtils.isBlankOrNull(v.getOrgId())&&!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
//			v.setOrgId(getCurrent().getOrgId());
//		}
    return baseService().gridData(gridVo, v);
  }

  @Override
  public ModelAndView gridPage(AccountVo v) throws GlobalException {
    ModelAndView mav = new ModelAndView();
    mav.addObject(VO, v);
    if (getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
      mav.addObject(IS_EDIT, true);
    }
    mav.setViewName(getViewPath() + "_page");
    return mav;
  }

  @ResponseBody
  @RequestMapping(value = "synAccount.do")
  public Status synAccount() throws GlobalException {
    try {
      accountService.synAccount();
      status = new Status("同步成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "同步失败", e);
      status = new Status("同步失败", Status.STATUS_ERROR);
    }
    return status;
  }
}

package cn.core.framework.utils.current;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.core.framework.utils.DateUtils;
import cn.demi.base.system.vo.AccountVo;

public class CurrentUtils {
	public static final String TOCKET_COOKIE_KEY = "user.tocket";
	
	public static final Current getCurrent() {
		Subject currentUser =SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		return (Current) session.getAttribute(TOCKET_COOKIE_KEY);
	}
	
	public static final String getAccountId(){
		return getCurrent().getAccountId();
	}

	public static final String getUserId(){
		return getCurrent().getUserName();
	}

	public static final String getUserName(){
		return getCurrent().getUserName();
	}
	
	public static final void setCurrent(AccountVo accountVo) {
		// 使用ticket作为标示，防止过期的cookie内存在同key ticket；
		Current current = new Current();
		current.setAccountId(accountVo.getId());
		current.setRoleIds(accountVo.getRoleIds());
		current.setRoleNames(accountVo.getRoleNames());
		current.setLoginName(accountVo.getLoginName());
		current.setLoginTime(DateUtils.format(DateUtils.getCurrDate(), DateUtils.formatStr_yyyyMMddHHmm));
		current.setIsUse(String.valueOf(accountVo.getIsUse()));
		if(null!=accountVo.getUserVo()){
			current.setUserId(accountVo.getUserVo().getId());
			current.setAvatar(accountVo.getUserVo().getAvatar());
			current.setUserName(accountVo.getUserVo().getName());
			current.setDuty(accountVo.getUserVo().getDuty());
			current.setTheme(accountVo.getUserVo().getTheme());
		}
		
		if(null!=accountVo.getOrgVo()){
			current.setOrgId(accountVo.getOrgVo().getId());
			current.setOrgName(accountVo.getOrgVo().getName());
			current.setOrgCode(accountVo.getOrgVo().getCode());
		}
		if(null!=accountVo.getDepVo()) {
			current.setDepCode(accountVo.getDepVo().getCode());
			current.setDepName(accountVo.getDepVo().getName());
			current.setDepId(accountVo.getDepVo().getId());
		}
		Subject currentUser =SecurityUtils.getSubject();
		currentUser.getSession().setAttribute(CurrentUtils.TOCKET_COOKIE_KEY, current);
	}
	public static final void setCurrent(Current current) {
		Subject currentUser =SecurityUtils.getSubject();
		currentUser.getSession().setAttribute(CurrentUtils.TOCKET_COOKIE_KEY, current);
	}
}

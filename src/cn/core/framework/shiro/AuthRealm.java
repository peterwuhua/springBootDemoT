package cn.core.framework.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.service.IRolePermService;
import cn.demi.base.system.vo.AccountVo;
/**
 * 权限验证
 * @author QuJunLong
 *
 */
public class AuthRealm extends org.apache.shiro.realm.AuthorizingRealm {
	public Logger log = Logger.getLogger(this.getClass());
	
	@Autowired 
	private IAccountService accountService;
	@Autowired
	private IRolePermService rolePermService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginName = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Subject currentUser =SecurityUtils.getSubject();  
	    Session session = currentUser.getSession();  
		Set<String> permissionSet = new HashSet<String>();
		try {
			AccountVo vo = accountService.find(loginName);
			Set<String> pList=rolePermService.listPerm(vo.getRoleIds());
			permissionSet.addAll(pList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> roleSet = new HashSet<String>();
        roleSet.add("admin");
        roleSet.add("administrator");
		info.setStringPermissions(permissionSet);
		info.setRoles(roleSet);
		session.setAttribute("info", info);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//获取基于用户名和密码的令牌
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		try {
			AccountVo accountVo = accountService.login(token.getUsername(), String.valueOf(token.getPassword()));
			CurrentUtils.setCurrent(accountVo);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage());
		}
		
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(),token.getPassword(), this.getName());
		this.setSession("currentUser", token.getUsername());
		return authcInfo;
	}
	
	 public void clearCachedAuthorizationInfo(String principal) {  
        SimplePrincipalCollection principals = new SimplePrincipalCollection(  
                principal, getName());  
        clearCachedAuthorizationInfo(principals);  
    }  
	 
	private Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) 
				session = subject.getSession();
			if (session != null) 
				return session;
		} catch (InvalidSessionException e) {
			log.info(e);
		}
		return null;
	}
	
	/**
	 * 保存登录名
	 */
	private void setSession(Object key, Object value){
		Session session = getSession();
		//log.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
		if(null != session){
			session.setAttribute(key, value);
		}
	}

}

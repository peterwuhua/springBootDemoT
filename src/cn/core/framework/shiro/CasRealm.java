package cn.core.framework.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import cn.core.framework.log.Logger;
/**
 * 整合cas框架扩展自定义CasRealm
 * CAS 框架 用于单点登陆
 * @author QuJunLong
 */
public class CasRealm extends org.apache.shiro.cas.CasRealm{
	public Logger log = Logger.getLogger(this.getClass());
	//@Autowired private IAccountService accountService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//String loginName = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Subject currentUser =SecurityUtils.getSubject();  
	    Session session = currentUser.getSession();  
		Set<String> permissionSet = new HashSet<String>();
		try {
			/*AccountVo vo = accountService.find(loginName);
			List<RoleFunVo> list = roleFunService.listByRoleIds(vo.getRoleIds());
			
			for(RoleFunVo v:list){
				if(StrUtils.isBlankOrNull(v.getFunctionVo().getCode()))
					continue;
				permissionSet.add(v.getFunctionVo().getCode());
			}*/
			permissionSet.add("sys:role:edit");
			permissionSet.add("sys:org:treeData");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> roleSet = new HashSet<String>();
        roleSet.add("admin");
        roleSet.add("administrator");
        //System.out.println("loginName:"+loginName+"~~~~~~~~~"+permissionSet);
		info.setStringPermissions(permissionSet);
		info.setRoles(roleSet);
		session.setAttribute("info", info);
		return info;
	}
	
}

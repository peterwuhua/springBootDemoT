package cn.demi.ext.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.Action;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
/**
 * <strong>创建信息: 2015年7月8日 上午10:27:44 </strong> <br>
 * <strong>概要 :登录action </strong> <br>
 */
@Controller("common.loginAction")
public class LoginAction extends Action {
	@Autowired 
	private IAccountService accountService;
	@RequestMapping("login.do")
	public ModelAndView login(String loginname,String password,String captcha,RedirectAttributes attr,HttpServletRequest request,
			HttpServletResponse response) {
		boolean isMoblie = JudgeIsMoblie(request);
		ModelAndView mav = new ModelAndView();
		if(StrUtils.isBlankOrNull(loginname)){
			Object obj= ApplicationUtils.getValue("config.server");
			mav.addObject("server", obj);
			if(isMoblie) {
				mav.setViewName("/login_moblie");
			}else {
				mav.setViewName("/login");
			}
			return mav;
		}
		//登录过
		Current c =CurrentUtils.getCurrent();
		if(null!=c){
			if(loginname.equals(c.getLoginName())){//通账户登录
				mav.setViewName("redirect:/main.do");
			}else{//其他账户
				mav.addObject("message","<a title='点击跳转' href='/main.do'>"+c.getKey()+"</a>已登录系统 ");
				if(isMoblie) {
					mav.setViewName("/login_moblie");
				}else {
					mav.setViewName("/login");
				}
			}
			return mav;
		}
		
		try{
			password = StrUtils.md5(password);
			UsernamePasswordToken token = new UsernamePasswordToken(loginname,password.toCharArray(),true);
			Subject currentUser = SecurityUtils.getSubject(); 
			currentUser.login(token);
			//AccountVo accountVo = accountService.login(loginname, password);
			//UsernamePasswordToken token = new UsernamePasswordToken(accountVo.getLoginName(),password.toCharArray(),true);
			//Subject currentUser = SecurityUtils.getSubject(); 
			//currentUser.login(token);
			
//			List<AccountVo> accountList=accountService.listByUserId(getCurrent().getUserId());
//			if(accountList.size()>1) {
//				mav.setViewName("redirect:/org.do");
//			}else {
				mav.setViewName("redirect:/main.do");
//			}
			return mav;
		}catch(Exception e){
			log.info(e);
			String message = e.getMessage();
			if (StrUtils.isNotBlankOrNull(message)) {
				mav.addObject("message",message);
			}else{
				mav.addObject("message","用户名或密码错误，请联系管理员");
			}
			Object obj= ApplicationUtils.getValue("config.server");
			mav.addObject("server", obj);
			if(isMoblie) {
				mav.setViewName("/login_moblie");
			}else {
				mav.setViewName("/login");
			}
			return mav;
		}
	}
	@RequestMapping("updateAccount.do")
	public ModelAndView updateAccount(String loginname,RedirectAttributes attr,HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		//登录过
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		try{
			AccountVo vo = accountService.find(loginname);
			UsernamePasswordToken token = new UsernamePasswordToken(loginname,vo.getPassword().toCharArray(),true);
			Subject currentUser = SecurityUtils.getSubject(); 
			currentUser.login(token);
			mav.setViewName("redirect:/main.do");
			return mav;
		}catch(Exception e){
			log.info(e);
			String message = e.getMessage();
			if (StrUtils.isNotBlankOrNull(message)) {
				mav.addObject("message",message);
			}else{
				mav.addObject("message","账户选择异常，请联系管理员");
			}
			Object obj= ApplicationUtils.getValue("config.server");
			mav.addObject("server", obj);
			boolean isMoblie = JudgeIsMoblie(request);
			if(isMoblie) {
				mav.setViewName("/login_moblie");
			}else {
				mav.setViewName("/login");
			}
			return mav;
		}
	}
	@RequestMapping("logout.do")
	public ModelAndView logout(HttpServletResponse response,HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		ModelAndView mav = new ModelAndView();
		Object obj= ApplicationUtils.getValue("config.server");
		mav.addObject("server", obj);
		boolean isMoblie = JudgeIsMoblie(request);
		if(isMoblie) {
			mav.setViewName("/login_moblie");
		}else {
			mav.setViewName("/login");
		}
		return mav;
	}
	 public static boolean JudgeIsMoblie(HttpServletRequest request) {  
			boolean isMoblie = false;  
			String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",  
					"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",  
					"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",  
					"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",  
					"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",  
					"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",  
					"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",  
					"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",  
					"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",  
					"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",  
					"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",  
					"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",  
					"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",  
					"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",  
					"Googlebot-Mobile" };  
			if (request.getHeader("User-Agent") != null) {  
				String agent=request.getHeader("User-Agent");  
				for (String mobileAgent : mobileAgents) {  
					if (agent.toLowerCase().indexOf(mobileAgent) >= 0&&agent.toLowerCase().indexOf("windows nt")<=0 &&agent.toLowerCase().indexOf("macintosh")<=0) {  
						isMoblie = true;  
						break;  
					}  
				}  
			}  
			return isMoblie;  
		}
}
package cn.demi.ext.common.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.Action;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.service.IFunctionService;
import cn.demi.base.system.service.IMessageService;
import cn.demi.base.system.service.INoticeService;
import cn.demi.base.system.service.IRoleFunService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.FunctionVo;
import cn.demi.base.system.vo.RoleFunVo;
import cn.demi.bus.task.service.ITaskApService;
import cn.demi.cus.customer.service.IVisitService;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.service.IApparaInService;
import cn.demi.res.service.IApparaOutService;
import cn.demi.res.service.IApparaService;
import cn.demi.res.service.IConsumableService;
import cn.demi.res.service.IReagentService;

/**
 * <strong>创建信息: 2015年7月8日 上午10:27:18 </strong> <br>
 * <strong>概要 : 后台首页action</strong> <br>
 */
@Controller
public class HomeAction extends Action {
	private final String VIEW_PATH = "/home/";
	@Autowired
	private IRoleFunService roleFunService;
	@Autowired
	private IFunctionService functionService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IConsumableService consumableService;
	@Autowired
	private IReagentService reagentService;
	@Autowired
	private IApparaOutService apparaOutService;
	@Autowired
	private IApparaInService apparaInService;
	@Autowired
	private IApparaService apparaService;
	@Autowired
	private IAccountService accountService;
	@Autowired 
	private ITaskApService taskApService;
	@Autowired
	private IVisitService visitService;
	@RequestMapping("main.do")
	public ModelAndView main(@ModelAttribute(value = "accountVo") AccountVo accountVo,String sysId)
			throws GlobalException {
		if(StrUtils.isBlankOrNull(sysId)) {
			List<FunctionVo> sysList=roleFunService.list4First();
			Current ct=getCurrent();
			ct.setSysList(sysList);
			FunctionVo sys=sysList.get(0);
			ct.setSysId(sys.getId());
			ct.setSysName(sys.getName());
			if(!StrUtils.isBlankOrNull(sys.getWfCode())) {
				ct.setPortal(sys.getWfCode());
			}
			CurrentUtils.setCurrent(ct);
		}else {
			Current ct=getCurrent();
			FunctionVo sys=functionService.findById(sysId);
			ct.setSysId(sys.getId());
			ct.setSysName(sys.getName());
			if(!StrUtils.isBlankOrNull(sys.getWfCode())) {
				ct.setPortal(sys.getWfCode());
			}
			CurrentUtils.setCurrent(ct);
		}
		ModelAndView mav = new ModelAndView(VIEW_PATH + "home");
		mav.addObject("current", getCurrent());
		return mav;
	}

	@RequestMapping("left.do")
	public ModelAndView left() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "left");
		mav.addObject("menuList", roleFunService.getMenuList(getCurrent().getSysId(), getCurrent().getRoleIds()));
		return mav;
	}
	 
	@RequestMapping("top.do")
	public ModelAndView top() throws Exception {
		//   要取待办数据
		ModelAndView mav = new ModelAndView(VIEW_PATH + "top");
		List<FunctionVo> menuList=getCurrent().getSysList();
		List<FunctionVo> subMenuList =null;
		if(null!=menuList&&menuList.size()>6) {
			subMenuList=menuList.subList(6, menuList.size());
			menuList=menuList.subList(0, 6);
		}
		mav.addObject("sysList",menuList);
		mav.addObject("subSysList",subMenuList);
		mav.addObject("current", getCurrent());
		return mav;
	}
	@RequestMapping("org.do")
	public ModelAndView org() throws Exception {
		List<AccountVo> accountList=accountService.listByUserId(getCurrent().getUserId());
		ModelAndView mav = new ModelAndView(VIEW_PATH + "org");
		mav.addObject("accountList", accountList);
		return mav;
	}
	@RequestMapping(value="{act}.do")
	public ModelAndView act(@PathVariable("act") String act) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewPath()+act);
		mav.addObject("current", getCurrent());
		return mav;
	}
	
	@RequestMapping("unauthorized.do")
	public ModelAndView unauthorized() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "unauthorized");
		return mav;
	}
	@RequestMapping("portal.do")
	public ModelAndView portal() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal");
		getCurrent().setTz(noticeService.count(getCurrent().getDepId()));
		getCurrent().setYj(messageService.countInbox(getCurrent().getAccountId()));
		mav.addObject("current", getCurrent());
		List<RoleFunVo> menuList = roleFunService.getRoleFunList();
		mav.addObject("menuList", menuList);
		
		//提示区域
		List<TxVo> txList=new LinkedList<TxVo>();
		reagentService.list4Tx(txList);//试剂
		consumableService.list4Tx(txList);//耗材
		apparaOutService.list4Tx(txList);//设备出库
		apparaInService.list4Tx(txList);//设备入库
		apparaService.list4Tx(txList);//检定
		mav.addObject("txList", txList);
		return mav;
	}
	@RequestMapping("portal1.do")
	public ModelAndView portal1() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal_1");
		return mav;
	}
	@RequestMapping("portal2.do")
	public ModelAndView portal2() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal_2");
		return mav;
	}
	@RequestMapping("portal3.do")
	public ModelAndView portal3() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal_3");
//		mav.addObject("menuList", roleFunService.getMenuList(getCurrent().getSysId(), getCurrent().getRoleIds()));
		//未来7天采样安排
		Map<String, Object> panMap= taskApService.listApPlan(null);
		mav.addObject("panMap",panMap);
		return mav;
	}
	@RequestMapping("portal4.do")
	public ModelAndView portal4() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal_4");
//		mav.addObject("menuList", roleFunService.getMenuList(getCurrent().getSysId(), getCurrent().getRoleIds()));
		return mav;
	}
	@RequestMapping("portal5.do")
	public ModelAndView portal5() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal_5");
		return mav;
	}
	/**
	 * 
	 * @Title: portal6  
	 * @Description: CRM首页客户回访提醒   
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping("portal6.do")
	public ModelAndView portal6() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal6");
		mav.addObject("current", getCurrent());
		//提示区域
		List<TxVo> txList=new LinkedList<TxVo>();
		visitService.list4Tx(txList);//返回待回访的客户信息（经理可以看到所有人的）
		mav.addObject("txList", txList);
		return mav;
	}
	
	
	@RequestMapping("portalDj.do")
	public ModelAndView portalDj() throws Exception {
		ModelAndView mav = new ModelAndView(VIEW_PATH + "portal_dj");
		return mav;
	}
	public String getViewPath() {
		return VIEW_PATH;
	}
 
}
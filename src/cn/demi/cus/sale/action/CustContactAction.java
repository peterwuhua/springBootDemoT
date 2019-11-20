package cn.demi.cus.sale.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.service.IUserService;
import cn.demi.cus.sale.service.ICustContactService;
import cn.demi.cus.sale.vo.CustContactVo;

/**
 * 
 * @ClassName: CustContactAction
 * @Description:客户关怀
 * @author: 吴华
 * @date: 2019年1月29日 上午9:35:52
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("cus.custContactAction")
@RequestMapping("/cus/custContact")
public class CustContactAction extends BaseAction<CustContactVo> {
	final String VIEW_PATH = "/cus/custContact/custContact";
	@Autowired
	private ICustContactService custContactService;
	@Autowired
	private IUserService userService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CustContactVo> baseService() {
		return custContactService;
	}

	/**
	 * 
	 * <p>
	 * Title: gridPage
	 * </p>
	 * <p>
	 * Description:客户关怀列表
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridPage(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(CustContactVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description: 提前三天查询要过生日的联系人
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridData(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@ResponseBody
	@RequestMapping(value = { GRID_DATA, GRID_DATA_JSON })
	public GridVo gridData(GridVo gridVo, CustContactVo v) throws GlobalException {
		String auths = userService.saleJLAuth(getCurrent().getAccountId());
		if (auths.equals("XSJL")) {
			v.setLeaderId(getCurrent().getAccountId());
		} else if (auths.equals("XSRY")) {
			v.setSalerId(getCurrent().getAccountId());
		}
		return custContactService.query3dsCustBirth(gridVo, v);
		// return baseService().gridData(gridVo,v);
	}

}
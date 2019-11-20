package cn.demi.office.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.service.IXjService;
import cn.demi.office.vo.QjglVo;

/**
 * 
 * @ClassName: QjglAction
 * @Description: 销假
 * @author: 吴华
 * @date: 2019年3月11日 上午11:05:27
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("office.XjAction")
@RequestMapping("/office/xj")
public class XjAction extends BaseAction<QjglVo> {
	final String VIEW_PATH = "/office/qj/xj/xj";
	@Autowired
	private IXjService xjService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<QjglVo> baseService() {
		return xjService;
	}

	/**
	 * 
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description:销假
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@ResponseBody
	@RequestMapping(value = "updateStatus.do")
	public Status updateStatus(QjglVo v) throws GlobalException {
		try {
			xjService.updateStatus(v);
			status = new Status("销假成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"销假失败",e);
			status = new Status("销假失败",Status.STATUS_ERROR);
		}
		return status;
	}
}
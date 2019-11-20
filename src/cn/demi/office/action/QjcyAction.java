package cn.demi.office.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.office.service.IQjcyService;
import cn.demi.office.vo.QjglVo;

/**
 * 
 * @ClassName:  QjcyAction   
 * @Description:请假查阅 
 * @author: 吴华 
 * @date:   2019年3月11日 上午11:12:46       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("office.qjcyAction")
@RequestMapping("/office/qjcy")
public class QjcyAction extends BaseAction<QjglVo> {
	final String VIEW_PATH = "/office/qj/cy/qjcy";
	@Autowired private IQjcyService qjcyService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<QjglVo> baseService() {
		return qjcyService;
	}
	
	
	
	
}
package cn.demi.office.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import cn.demi.office.vo.OfficeAuditHistoryVo;
import cn.core.framework.common.service.IBaseService;
import cn.demi.office.service.IOfficeAuditHistoryService;
import cn.core.framework.common.action.BaseAction;

@Controller("office.auditHistoryAction")
@RequestMapping("/office/auditHistory")
public class OfficeAuditHistoryAction extends BaseAction<OfficeAuditHistoryVo> {
	final String VIEW_PATH = "/office/auditHistory/officeAuditHistory";
	@Autowired private IOfficeAuditHistoryService officeAuditHistoryService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<OfficeAuditHistoryVo> baseService() {
		return officeAuditHistoryService;
	}
}
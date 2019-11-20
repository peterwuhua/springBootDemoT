package cn.demi.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.demi.office.dao.IOfficeAuditHistoryDao;
import cn.demi.office.po.OfficeAuditHistory;
import cn.demi.office.vo.OfficeAuditHistoryVo;
import cn.demi.office.service.IOfficeAuditHistoryService;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;

@Service("office.auditHistoryService")
public class OfficeAuditHistoryServiceImpl extends BaseServiceImpl<OfficeAuditHistoryVo, OfficeAuditHistory> implements IOfficeAuditHistoryService {
	@Autowired
	private IOfficeAuditHistoryDao officeDao;

	@Override
	public List<OfficeAuditHistoryVo> listByCmId(String id) throws GlobalException {
		String hql = "select t from " + officeDao.getEntityName(OfficeAuditHistory.class) + " t where t.pId = '" + id + "' order by t.auditTime desc";
		return toVoList(officeDao.list(hql));
	}
}

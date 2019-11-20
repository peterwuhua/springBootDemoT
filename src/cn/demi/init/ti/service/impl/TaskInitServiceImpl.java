package cn.demi.init.ti.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ChineseFCUtil;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.init.ti.dao.ITaskInitDao;
import cn.demi.init.ti.po.TaskInit;
import cn.demi.init.ti.service.ITaskInitService;
import cn.demi.init.ti.vo.TaskInitVo;

@Service("init.task_initService")
public class TaskInitServiceImpl extends BaseServiceImpl<TaskInitVo,TaskInit> implements
		ITaskInitService {
	@Autowired
	private ITaskInitDao taskInitDao;
	@Override
	public Map<String, Object> po2map(TaskInit p) throws GlobalException {
		Map<String, Object> map=super.po2map(p);
		return map;
	}
	@Override
	public void add(TaskInitVo v) throws GlobalException {
		TaskInit p = vo2Po(v);
		p.setOrgName(getCurrent().getOrgName());
		p.setOrgId(getCurrent().getOrgId());
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		p.setEname(ChineseFCUtil.cn2py(v.getItemName()));
		taskInitDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void update(TaskInitVo v) throws GlobalException {
		TaskInit p = taskInitDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.setEname(ChineseFCUtil.cn2py(v.getItemName()));
		p.toPo(v, p);
		taskInitDao.update(p);
	}
	@Override
	public List<QueryCondition> toQueryList(TaskInitVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
			queryList.add(new QueryCondition(" orgId = '"+CurrentUtils.getCurrent().getOrgId()+"' or orgId is null or orgId='' " ));
		}
		return queryList;
	}
	@Override
	public List<TaskInitVo> listByItemIds(String itemIds) throws GlobalException {
		String jpql="FROM "+taskInitDao.getEntityName(TaskInit.class)+" WHERE isDel='"+Po.N+"' AND itemId in('"+itemIds.replace(",", "','")+"')";
		List<TaskInit> plist=taskInitDao.list(jpql);
		return toVoList(plist);
	}
}

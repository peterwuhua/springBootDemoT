package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.demi.base.system.dao.ISysMsgDao;
import cn.demi.base.system.po.SysMsg;
import cn.demi.base.system.vo.SysMsgVo;
import cn.demi.base.system.service.ISysMsgService;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;

@Service("sys.msgService")
public class SysMsgServiceImpl extends BaseServiceImpl<SysMsgVo,SysMsg> implements
		ISysMsgService {
	@Autowired
	private ISysMsgDao sysMsgDao;
	
	@Override
	public SysMsgVo findById(String id) throws GlobalException {
		SysMsg po;
		try {
			po = sysMsgDao.findById(id);
		} catch (Exception e) {
			throw new GlobalException("根据id获取对象失败  id:"+id,e);
		} 
		if(null==po) return null;
		return po2Vo(po);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SysMsgVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = sysMsgDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<SysMsg>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(SysMsgVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("receiverId='" + getCurrent().getAccountId()+ "' or receiverId in('"+getCurrent().getRoleIds().replace(",", "','")+"') or receiverId='"+getCurrent().getOrgId()+"'"));
		return queryList;
	}

	@Override
	public int getCount4Wd() throws GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SysMsgVo> list4Wd() throws GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysMsgVo> list4Show() throws GlobalException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

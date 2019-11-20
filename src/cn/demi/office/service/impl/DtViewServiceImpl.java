package cn.demi.office.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.IDtDao;
import cn.demi.office.po.Dt;
import cn.demi.office.service.IDtViewService;
import cn.demi.office.vo.DtVo;

@Service("office.dtViewService")
public class DtViewServiceImpl extends BaseServiceImpl<DtVo,Dt> implements
		IDtViewService {
	@Autowired
	private IDtDao dtDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public DtVo findById(String id) throws GlobalException {
		Dt po = dtDao.findById(id);
		DtVo vo=po2Vo(po);
		if(po.getViewEdIds()==null) {
			po.setViewEdIds(getCurrent().getAccountId());
			po.setViewEdNames(getCurrent().getUserName());
		}else if(!po.getViewEdIds().contains(getCurrent().getAccountId())){
			po.setViewEdIds(po.getViewEdIds()+","+getCurrent().getAccountId());
			po.setViewEdNames(po.getViewEdNames()+","+getCurrent().getUserName());
		}
		Progress pg=progressDao.findByBusId(po.getId());
		if(null!=pg) {
			String userId[]=pg.getUserId().split(",");
			String userName[]=pg.getUserName().split(",");
			List<String> newIds=new LinkedList<String>();
			List<String> newNames=new LinkedList<String>();
			int n=0;
			for (String uid : userId) {
				if(uid.trim().length()>0 && !uid.equals(getCurrent().getAccountId())) {
					newIds.add(uid);
					newNames.add(userName[n]);
				}
				n++;
			}
			if(newIds.size()>0) {
				progressDao.update(pg.getId(), EunmTask.DT_CK.getStatus(), String.join(",", newIds), String.join(",", newNames));
				progressLogDao.add(po.getId(),po.getId(), EunmTask.DT_CK.getStatus(),EunmTask.PASS_S,"文件查看",getCurrent().getDepId(),getCurrent().getDepName(),getCurrent().getAccountId(), getCurrent().getUserName());
			}else {
				progressDao.delete(pg);
			}
		}
		return vo;
	}
	@Override
	public void update(DtVo v) throws GlobalException {
		Dt p = dtDao.findById(v.getId());
		p.setResult(v.getResult());
		p.setRemark(v.getRemark());
		dtDao.update(p);
		progressLogDao.add(p.getId(),p.getId(), EunmTask.DT_CK.getStatus(),EunmTask.PASS_Y,v.getResult(),getCurrent().getDepId(),getCurrent().getDepName(),getCurrent().getAccountId(), getCurrent().getUserName());
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DtVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ,EunmTask.DT_CK.getStatus()));
		pageResult.addCondition(new QueryCondition("userIds", QueryCondition.AK, getCurrent().getAccountId()));
		pageResult = dtDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Dt>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<?, Object>> poList2mapList(List<Dt> list) throws GlobalException {
		List tempList = new ArrayList();
		for(Dt p:list){
			Map<String, Object> map=po2map(p);
			map.put("status", EunmTask.getName(map.get("status").toString()));
			tempList.add(map);
		}
		return tempList;
	}
}

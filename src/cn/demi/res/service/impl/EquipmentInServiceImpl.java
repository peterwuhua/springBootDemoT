package cn.demi.res.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.dao.IEquipmentDao;
import cn.demi.res.dao.IEquipmentInDao;
import cn.demi.res.dao.IEquipmentUsedDao;
import cn.demi.res.po.Equipment;
import cn.demi.res.po.EquipmentIn;
import cn.demi.res.po.EquipmentUsed;
import cn.demi.res.service.IEquipmentInService;
import cn.demi.res.vo.EquipmentInVo;

@Service("res.equipmentInService")
public class EquipmentInServiceImpl extends BaseServiceImpl<EquipmentInVo,EquipmentIn> implements
		IEquipmentInService {
	
	@Autowired
	private IEquipmentDao equiptDao;
	@Autowired
	private IEquipmentInDao equiptInDao;
	@Autowired
	private IEquipmentUsedDao equiptUsedDao;
	@Override
	public void update(EquipmentInVo v) throws GlobalException {
		EquipmentIn p = equiptInDao.findById(v.getId());
		//登记人
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		//归还人
		p.setUseId(v.getUseId());
		p.setUseName(v.getUseName());
		p.setUseTime(v.getUseTime());
		p.setMobile(v.getMobile());
		p.setAppStatus(v.getAppStatus());
		p.setStatus(v.getStatus());
		p.setRemark(v.getRemark());
		p.setDuration(v.getDuration());
		equiptInDao.update(p);
		if(v.getStatus().equals(EquipmentIn.ST_1)) {
			//更新仪器状态
			Equipment equipt=p.getEquipt();
			equipt.setStatus(v.getAppStatus());
			equiptDao.update(equipt);
			
			EquipmentUsed used=new EquipmentUsed();
			used.setEquipt(equipt);
			used.setBusId(p.getBusId());
			used.setAppStat(v.getAppStatus());
			used.setStartTime(p.getOut().getUseTime());
			used.setEndTime(v.getUseTime());
			used.setOrgId(p.getOrgId());
			used.setOrgName(p.getOrgName());
			used.setUserId(p.getOut().getUseId());
			used.setUserName(p.getOut().getUseName());
			try {
				used.setDuration(""+DateUtils.getIntevalMinutes(p.getOut().getUseTime(), v.getUseTime()));
			} catch (Exception e) {
				used.setDuration("");
				log.error("仪器使用时长计算异常", e);
			}
			used.setPurpose("采样仪器外借");
			used.onAdd();
			equiptUsedDao.add(used);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, EquipmentInVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.addOrder("lastUpdTime", OrderCondition.ORDER_DESC);
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		if(!StrUtils.isBlankOrNull(v.getStatus())) {
			pageResult.addCondition(new QueryCondition("status='"+v.getStatus()+"'"));
		}
		pageResult = equiptInDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<EquipmentIn>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException {
		String hql="FROM "+equiptInDao.getEntityName(EquipmentIn.class)+" WHERE isDel='"+Po.N
				+"' AND equipt.keepId='"+getCurrent().getAccountId()+"' AND  status='"+EquipmentIn.ST_0+"')";
		List<EquipmentIn>  pList=equiptInDao.list(hql);
		for (EquipmentIn ai : pList) {
			TxVo tx=new TxVo();
			tx.setIcon("fa fa-binoculars");
			tx.setType("仪器入库");
			tx.setStatus("待入库");
			tx.setName(ai.getEquipt().getNo()+" / "+ai.getEquipt().getName()+" "+ai.getEquipt().getSpec());
			tx.setContent("仪器"+ai.getEquipt().getName()+" "+ai.getEquipt().getSpec()+"已出库，出库日期："+ai.getOut().getUseTime()+"，预约入库日期："+ai.getUseTime());
			list.add(tx);
		}
		return list;
	}
}



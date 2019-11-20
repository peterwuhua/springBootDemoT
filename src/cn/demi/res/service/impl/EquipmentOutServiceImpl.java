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
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.dao.IEquipmentDao;
import cn.demi.res.dao.IEquipmentInDao;
import cn.demi.res.dao.IEquipmentOutDao;
import cn.demi.res.po.Equipment;
import cn.demi.res.po.EquipmentIn;
import cn.demi.res.po.EquipmentOut;
import cn.demi.res.service.IEquipmentOutService;
import cn.demi.res.vo.EquipmentOutVo;

@Service("res.equipmentOutService")
public class EquipmentOutServiceImpl extends BaseServiceImpl<EquipmentOutVo,EquipmentOut> implements
		IEquipmentOutService {
 
	@Autowired
	private IEquipmentOutDao equiptOutDao;
	@Autowired
	private IEquipmentInDao equiptInDao;
	@Autowired
	private IEquipmentDao equiptDao;
	@Autowired
	private IAccountDao accountDao;
	@Override
	public void add(EquipmentOutVo v) throws GlobalException {
		EquipmentOut p =new EquipmentOut();
		Equipment equipt=equiptDao.findById(v.getEquiptVo().getId());
		p.setEquipt(equipt);
		//登记人
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		//出库人
		Account at=accountDao.findById(v.getUseId());
		p.setOrgId(at.getOrg().getId());
		p.setOrgName(at.getOrg().getName());
		p.setUseId(v.getUseId());
		p.setUseName(v.getUseName());
		p.setUseTime(v.getUseTime());
		p.setBackTime(v.getBackTime());
		p.setMobile(v.getMobile());
		p.setAppStatus(v.getAppStatus());
		p.setStatus(v.getStatus());
		p.setRemark(v.getRemark());
		equiptOutDao.add(p);
		if(v.getStatus().equals(EquipmentOut.ST_1)) {
			//新增归还单
			EquipmentIn in=new EquipmentIn();
			in.setEquipt(p.getEquipt());
			in.setOut(p);
			in.setBusId(p.getBusId());
			in.setCyIds(p.getCyIds());
			in.setCyNames(p.getCyNames());
			in.setOrgId(p.getOrgId());
			in.setOrgName(p.getOrgName());
			in.setUseTime(v.getBackTime());
			in.setMobile(p.getMobile());
			in.setStatus(EquipmentIn.ST_0);
			equiptInDao.add(in);
		}
	}
	@Override
	public void update(EquipmentOutVo v) throws GlobalException {
		EquipmentOut p = equiptOutDao.findById(v.getId());
		Equipment equipt=equiptDao.findById(v.getEquiptVo().getId());
		p.setEquipt(equipt);
		//登记人
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		//出库人
		p.setUseId(v.getUseId());
		p.setUseName(v.getUseName());
		p.setUseTime(v.getUseTime());
		p.setBackTime(v.getBackTime());
		p.setMobile(v.getMobile());
		p.setAppStatus(v.getAppStatus());
		p.setStatus(v.getStatus());
		p.setRemark(v.getRemark());
		equiptOutDao.update(p);
		if(v.getStatus().equals(EquipmentOut.ST_1)) {
			//新增归还单
			EquipmentIn in=new EquipmentIn();
			in.setEquipt(p.getEquipt());
			in.setOut(p);
			in.setBusId(p.getBusId());
			in.setCyIds(p.getCyIds());
			in.setCyNames(p.getCyNames());
			in.setOrgId(p.getOrgId());
			in.setOrgName(p.getOrgName());
			in.setUseTime(v.getBackTime());
			in.setMobile(p.getMobile());
			in.setStatus(EquipmentIn.ST_0);
			equiptInDao.add(in);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, EquipmentOutVo v) throws GlobalException {
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
		pageResult = equiptOutDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<EquipmentOut>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<EquipmentOutVo> listData(EquipmentOutVo v) throws GlobalException {
		String hql="FROM "+equiptOutDao.getEntityName(EquipmentOut.class)+" WHERE isDel='"+Po.N
				+"' AND equipt.id='"+v.getEquiptVo().getId()+"' AND (useTime>='"+DateUtils.getCurrDateStr()+"' or useTime<'"+DateUtils.getCurrDateStr()+"' and backTime>='"+DateUtils.getCurrDateStr()+"')";
		List<EquipmentOut> list=equiptOutDao.list(hql);
		return toVoList(list);
	}
	 
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(EquipmentOut p:equiptOutDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			equiptOutDao.update(p);
		}
	}
	@Override
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException {
		String hql="FROM "+equiptOutDao.getEntityName(EquipmentOut.class)+" WHERE isDel='"+Po.N
				+"' AND equipt.keepId='"+getCurrent().getAccountId()+"' AND  status='"+EquipmentOut.ST_0+"')";
		List<EquipmentOut>  pList=equiptOutDao.list(hql);
		for (EquipmentOut ao : pList) {
			TxVo tx=new TxVo();
			tx.setIcon("fa fa-binoculars");
			tx.setType("仪器出库");
			tx.setStatus("待出库");
			tx.setName(ao.getEquipt().getNo()+" / "+ao.getEquipt().getName()+" "+ao.getEquipt().getSpec());
			tx.setContent("仪器"+ao.getEquipt().getName()+" "+ao.getEquipt().getSpec()+"预约出库，出库日期："+ao.getUseTime()+"，预约人："+ao.getCyNames());
			list.add(tx);
		}
		return list;
	}
}

package cn.demi.res.service.impl;

import java.util.ArrayList;
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
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.dao.IApparaInDao;
import cn.demi.res.dao.IApparaOutDao;
import cn.demi.res.po.Appara;
import cn.demi.res.po.ApparaIn;
import cn.demi.res.po.ApparaOut;
import cn.demi.res.service.IApparaOutService;
import cn.demi.res.vo.ApparaOutVo;
import cn.demi.res.vo.ApparaVo;

@Service("res.apparaOutService")
public class ApparaOutServiceImpl extends BaseServiceImpl<ApparaOutVo,ApparaOut> implements
		IApparaOutService {
 
	@Autowired
	private IApparaOutDao apparaOutDao;
	@Autowired
	private IApparaInDao apparaInDao;
	@Autowired
	private IApparaDao apparaDao;
	@Autowired
	private IAccountDao accountDao;
	@Override
	public void add(ApparaOutVo v) throws GlobalException {
		ApparaOut p =new ApparaOut();
		Appara app=apparaDao.findById(v.getApparaVo().getId());
		p.setAppara(app);
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
		apparaOutDao.add(p);
		if(v.getStatus().equals(ApparaOut.ST_1)) {
			//新增归还单
			ApparaIn in=new ApparaIn();
			in.setAppara(p.getAppara());
			in.setOut(p);
			in.setBusId(p.getBusId());
			in.setCyIds(p.getCyIds());
			in.setCyNames(p.getCyNames());
			in.setOrgId(p.getOrgId());
			in.setOrgName(p.getOrgName());
			in.setUseTime(v.getBackTime());
			in.setMobile(p.getMobile());
			in.setStatus(ApparaIn.ST_0);
			apparaInDao.add(in);
		}
	}
	@Override
	public void update(ApparaOutVo v) throws GlobalException {
		ApparaOut p = apparaOutDao.findById(v.getId());
		Appara app=apparaDao.findById(v.getApparaVo().getId());
		p.setAppara(app);
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
		apparaOutDao.update(p);
		if(v.getStatus().equals(ApparaOut.ST_1)) {
			//新增归还单
			ApparaIn in=new ApparaIn();
			in.setAppara(p.getAppara());
			in.setOut(p);
			in.setBusId(p.getBusId());
			in.setCyIds(p.getCyIds());
			in.setCyNames(p.getCyNames());
			in.setOrgId(p.getOrgId());
			in.setOrgName(p.getOrgName());
			in.setUseTime(v.getBackTime());
			in.setMobile(p.getMobile());
			in.setStatus(ApparaIn.ST_0);
			apparaInDao.add(in);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ApparaOutVo v) throws GlobalException {
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
		pageResult = apparaOutDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ApparaOut>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<ApparaOutVo> listData(ApparaOutVo v) throws GlobalException {
		String hql="FROM "+apparaOutDao.getEntityName(ApparaOut.class)+" WHERE isDel='"+Po.N
				+"' AND appara.id='"+v.getApparaVo().getId()+"' AND (useTime>='"+DateUtils.getCurrDateStr()+"' or useTime<'"+DateUtils.getCurrDateStr()+"' and backTime>='"+DateUtils.getCurrDateStr()+"')";
		List<ApparaOut> list=apparaOutDao.list(hql);
		return toVoList(list);
	}
	@Override
	public List<ApparaVo> listApp(ApparaOutVo v) throws GlobalException {
		String hql="FROM "+apparaDao.getEntityName(Appara.class)+" WHERE isDel='"+Po.N+"' ";
		hql+=" AND name like '"+v.getApparaVo().getName()+"'";
		hql+=" AND spec like '"+v.getApparaVo().getSpec()+"'";
		List<Appara>  pList=apparaDao.list(hql);
		List<ApparaVo> voList=new ArrayList<>();
		for (Appara appara : pList) {
			ApparaVo apparaVo=new ApparaVo();
			apparaVo=apparaVo.toVo(appara);
			voList.add(apparaVo);
		}
		return voList;
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(ApparaOut p:apparaOutDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			apparaOutDao.update(p);
		}
	}
	@Override
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException {
		String hql="FROM "+apparaOutDao.getEntityName(ApparaOut.class)+" WHERE isDel='"+Po.N
				+"' AND  status='"+ApparaOut.ST_0+"')";
		List<ApparaOut>  pList=apparaOutDao.list(hql);
		for (ApparaOut ao : pList) {
			TxVo tx=new TxVo();
			tx.setIcon("fa fa-binoculars");
			tx.setType("仪器出库");
			tx.setStatus("待出库");
			tx.setName(ao.getAppara().getNo()+" / "+ao.getAppara().getName()+" "+ao.getAppara().getSpec());
			tx.setContent("仪器"+ao.getAppara().getName()+" "+ao.getAppara().getSpec()+"预约出库，出库日期："+ao.getUseTime()+"，预约人："+ao.getCyNames());
			list.add(tx);
		}
		return list;
	}
}

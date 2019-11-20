package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.dao.IConsumableDao;
import cn.demi.res.dao.IConsumableLogDao;
import cn.demi.res.po.Consumable;
import cn.demi.res.po.ConsumableLog;
import cn.demi.res.service.IConsumableService;
import cn.demi.res.vo.ConsumableVo;

@Service("res.consumableService")
public class ConsumableServiceImpl extends BaseServiceImpl<ConsumableVo,Consumable> implements
		IConsumableService {
	@Autowired 
	private IConsumableDao consumableDao;
	@Autowired 
	private IConsumableLogDao consumableLogDao;
	@Autowired 
	private IAccountDao accountDao;
	@Autowired 
	private IOrgDao orgDao;
	@Override
	public void data2Vo(String[] values,ConsumableVo v,String param){
		v.setNo(values[1]);
		v.setName(values[2]);
		v.setType(values[3]);
		v.setUnit(values[4]);
		v.setModel(values[5]);
		v.setAmount(values[6].equals("")?0:Double.valueOf(values[6]));
		v.setSafeAmount(values[7].equals("")?0:Double.valueOf(values[7]));
		v.setExp(values[8]);
		//v.setKeeper(values[9]);
	}
	
	@Override
	public void add(ConsumableVo v) throws GlobalException {
		//v.setDep(getCurrent().getOrgCode());
		Consumable p = vo2Po(v);
		if(StrUtils.isNotBlankOrNull(v.getKeepId())) {
			Account at=accountDao.findById(v.getKeepId());
			p.setDeptId(at.getOrg().getId());
			p.setDeptName(at.getOrg().getName());
			Org org=orgDao.findOrg(at.getOrg().getId());
			p.setOrgId(org.getId());
			p.setOrgName(org.getName());
		}
		consumableDao.add(p);
		v.setId(p.getId());
		
		addLog(v,0d,p);
	}

	private void addLog(ConsumableVo v,Double lastAmount,Consumable p) {
		ConsumableLog rl = new ConsumableLog();
		BeanUtils.copyProperties(v, rl,rl.IGNORE_PROPERTY);
		rl.setRkNum(String.valueOf(v.getAmount()-lastAmount));
		rl.setLastAmount(lastAmount);
		rl.setDate(DateUtils.getCurrDateTimeStr());
		rl.setConsumable(p);
		consumableLogDao.add(rl);
	}
	
	@Override
	public void update(ConsumableVo v) throws GlobalException {
		Consumable p =consumableDao.findById(v.getId());
		addLog(v,p.getAmount(),p);
		p.toPo(v, p);
		if(StrUtils.isNotBlankOrNull(v.getKeepId())) {
			Account at=accountDao.findById(v.getKeepId());
			p.setDeptId(at.getOrg().getId());
			p.setDeptName(at.getOrg().getName());
			Org org=orgDao.findOrg(at.getOrg().getId());
			p.setOrgId(org.getId());
			p.setOrgName(org.getName());
		}
		consumableDao.update(p);
	}
	
	@Override
	public PageResult pageResult(ConsumableVo v, PageResult pageResult)
			throws GlobalException {
		pageResult.addCondition("dep", QueryCondition.EQ,getCurrent().getDepCode());
		return super.pageResult(v, pageResult);
	}

	@Override
	public List<QueryCondition> toQueryList(ConsumableVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if("Y".equals(v.getIsSafe())){
			queryConditions.add(new QueryCondition("safeAmount>amount"));
		}
		return queryConditions;
	}

	@Override
	public void importData(ConsumableVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)){
			for(Consumable p:consumableDao.list()) {
				p.setIsDel(Po.Y);
				consumableDao.update(p);
			}
		}
		ConsumableVo vo = null;
		for (int i = 4, j = dataArrays.length; i < j; i++) {
			if (dataArrays[i].length == 0)
				continue;
				
			String[] values = dataArrays[i];
			if(null==values[0] || "".equals(values[0].trim())){
				log.info("第 "+ i +"行无效数据不做导入");
				continue;
			}
			
			vo = v.instance();
			try {
				vo.setSort(Integer.valueOf(values[0]));
			} catch (Exception e) {
				vo.setSort(i);
			}
			// 数组数据对应至对象
			data2Vo(values, vo, param);
			add(vo);
		}
	}

	@Override
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException {
		String hql="FROM "+consumableDao.getEntityName(Consumable.class)+" WHERE isDel='"+Po.N+"' AND amount<=safeAmount ";
		List<Consumable> rtList=consumableDao.list(hql);
		if(null!=rtList) {
			for (Consumable rt : rtList) {
				TxVo tx=new TxVo();
				tx.setIcon("fa fa-glass");
				tx.setType("耗材");
				tx.setStatus("库存不足");
				tx.setName(rt.getNo()+" / "+rt.getName()+" "+rt.getModel());
				tx.setContent("警戒值："+rt.getSafeAmount()+" 实际库存："+rt.getAmount());
				list.add(tx);
			}
		}
		hql="FROM "+consumableDao.getEntityName(Consumable.class)+" WHERE isDel='"+Po.N+"' AND exp is not null AND exp<'"+DateUtils.getNextDate(DateUtils.getCurrDateStr(), 7)+"' ";
		List<Consumable> rtList2=consumableDao.list(hql);
		if(null!=rtList2) {
			for (Consumable rt : rtList2) {
				TxVo tx=new TxVo();
				tx.setIcon("fa fa-glass");
				tx.setType("耗材");
				tx.setStatus("库存过期");
				tx.setName(rt.getNo()+" / "+rt.getName()+" "+rt.getModel());
				tx.setContent("耗材有效期至"+rt.getExp());
				list.add(tx);
			}
		}
		return list;
	}
	
	@Override
	public String copy(ConsumableVo v) throws GlobalException {
		v = findById(v.getIds());
		v.setId(null);
		v.setNo(null);
		v.setAmount(0.0);
		Consumable p = vo2Po(v);
		consumableDao.add(p);
		return p.getId();
	}
	
}

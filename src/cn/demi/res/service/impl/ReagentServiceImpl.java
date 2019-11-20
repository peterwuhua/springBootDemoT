package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import cn.demi.res.dao.IReagentDao;
import cn.demi.res.dao.IReagentLogDao;
import cn.demi.res.po.Reagent;
import cn.demi.res.po.ReagentLog;
import cn.demi.res.service.IReagentService;
import cn.demi.res.vo.ReagentVo;

@Service("res.reagentService")
public class ReagentServiceImpl extends BaseServiceImpl<ReagentVo,Reagent> implements
		IReagentService {
	@Autowired 
	private IReagentDao reagentDao;
	@Autowired 
	private IReagentLogDao reagentLogDao;
	@Autowired 
	private IAccountDao accountDao;
	@Autowired 
	private IOrgDao orgDao;
	@Override
	public void data2Vo(String[] values,ReagentVo v,String param){
		v.setNo(values[1]);
		v.setName(values[2]);
		v.setSname(values[3]);
		v.setType(values[4]);
		v.setPurity(values[5]);
		v.setGrade(values[6]);
		v.setAmount(values[7].equals("")?0:Double.valueOf(values[7]));
		v.setSafeAmount(values[8].equals("")?0:Double.valueOf(values[8]));
		v.setExp(values[9]);
		//v.setKeeper(values[10]);
	}
	
	@Override
	public void add(ReagentVo v) throws GlobalException {
		Reagent p = vo2Po(v);
		if(StrUtils.isNotBlankOrNull(v.getKeepId())) {
			Account at=accountDao.findById(v.getKeepId());
			p.setDeptId(at.getOrg().getId());
			p.setDeptName(at.getOrg().getName());
			Org org=orgDao.findOrg(at.getOrg().getId());
			p.setOrgId(org.getId());
			p.setOrgName(org.getName());
		}
		reagentDao.add(p);
		v.setId(p.getId());
		addLog(v,0d,p);
	}

	private void addLog(ReagentVo v,Double lastAmount,Reagent p) {
		ReagentLog rl = new ReagentLog();
		BeanUtils.copyProperties(v, rl,rl.IGNORE_PROPERTY);
		rl.setRkNum(String.valueOf(v.getAmount()-lastAmount));
		rl.setLastAmount(lastAmount);
		rl.setDate(DateUtils.getCurrDateTimeStr());
		rl.setReagent(p);
		reagentLogDao.add(rl);
	}
	
	@Override
	public void update(ReagentVo v) throws GlobalException {
		Reagent p =reagentDao.findById(v.getId());
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
		reagentDao.update(p);
	}
	@Override
	public List<QueryCondition> toQueryList(ReagentVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if("Y".equals(v.getIsSafe())){
			queryConditions.add(new QueryCondition("safeAmount>amount"));
		}
		return queryConditions;
	}

	@Override
	public void importData(ReagentVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)){
			for(Reagent p:reagentDao.list()) {
				p.setIsDel(Po.Y);
				reagentDao.update(p);
			}
		}
		ReagentVo vo = null;
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
		String hql="FROM "+reagentDao.getEntityName(Reagent.class)+" WHERE isDel='"+Po.N+"' AND amount<=safeAmount ";
		List<Reagent> rtList=reagentDao.list(hql);
		if(null!=rtList) {
			for (Reagent rt : rtList) {
				TxVo tx=new TxVo();
				tx.setIcon("fa fa-flask");
				tx.setType("试剂");
				tx.setStatus("库存不足");
				tx.setName(rt.getNo()+" / "+rt.getName()+" "+(rt.getRule()==null?"":rt.getRule()));
				tx.setContent("警戒值："+rt.getSafeAmount()+" 实际库存："+rt.getAmount());
				list.add(tx);
			}
		}
		hql="FROM "+reagentDao.getEntityName(Reagent.class)+" WHERE isDel='"+Po.N+"' AND exp is not null AND exp<'"+DateUtils.getNextDate(DateUtils.getCurrDateStr(), 7)+"' ";
		List<Reagent> rtList2=reagentDao.list(hql);
		if(null!=rtList2) {
			for (Reagent rt : rtList2) {
				TxVo tx=new TxVo();
				tx.setIcon("fa fa-flask");
				tx.setType("试剂");
				tx.setStatus("库存过期");
				tx.setName(rt.getNo()+" / "+rt.getName()+" "+(rt.getRule()==null?"":rt.getRule()));
				tx.setContent("试剂有效期至"+rt.getExp());
				list.add(tx);
			}
		}
		return list;
	}
	@Override
	public String copy(ReagentVo v) throws GlobalException {
		v = findById(v.getIds());
		v.setId(null);
		v.setNo(null);
		v.setAmount(0.0);
		Reagent p = vo2Po(v);
		reagentDao.add(p);
		return p.getId();
	}
}

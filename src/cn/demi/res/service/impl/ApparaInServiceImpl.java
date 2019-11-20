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
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.dao.IApparaInDao;
import cn.demi.res.dao.IApparaUsedDao;
import cn.demi.res.po.Appara;
import cn.demi.res.po.ApparaIn;
import cn.demi.res.po.ApparaUsed;
import cn.demi.res.service.IApparaInService;
import cn.demi.res.vo.ApparaInVo;

@Service("res.apparaInService")
public class ApparaInServiceImpl extends BaseServiceImpl<ApparaInVo,ApparaIn> implements
		IApparaInService {
	
	@Autowired
	private IApparaDao apparaDao;
	@Autowired
	private IApparaInDao apparaInDao;
	@Autowired
	private IApparaUsedDao apparaUsedDao;
	@Override
	public void update(ApparaInVo v) throws GlobalException {
		ApparaIn p = apparaInDao.findById(v.getId());
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
		apparaInDao.update(p);
		if(v.getStatus().equals(ApparaIn.ST_1)) {
			//更新仪器状态
			Appara app=p.getAppara();
			app.setState(v.getAppStatus());
			apparaDao.update(app);
			
			ApparaUsed used=new ApparaUsed();
			used.setAppara(app);
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
			apparaUsedDao.add(used);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ApparaInVo v) throws GlobalException {
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
		pageResult = apparaInDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ApparaIn>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException {
		String hql="FROM "+apparaInDao.getEntityName(ApparaIn.class)+" WHERE isDel='"+Po.N
				+"' AND  status='"+ApparaIn.ST_0+"')";
		List<ApparaIn>  pList=apparaInDao.list(hql);
		for (ApparaIn ai : pList) {
			TxVo tx=new TxVo();
			tx.setIcon("fa fa-binoculars");
			tx.setType("仪器入库");
			tx.setStatus("待入库");
			tx.setName(ai.getAppara().getNo()+" / "+ai.getAppara().getName()+" "+ai.getAppara().getSpec());
			tx.setContent("仪器"+ai.getAppara().getName()+" "+ai.getAppara().getSpec()+"已出库，出库日期："+ai.getOut().getUseTime()+"，预约入库日期："+ai.getUseTime());
			list.add(tx);
		}
		return list;
	}
}



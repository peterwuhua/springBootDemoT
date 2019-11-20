package cn.demi.cus.customer.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.IClientPointDao;
import cn.demi.cus.customer.po.ClientPoint;
import cn.demi.cus.customer.service.IClientPointService;
import cn.demi.cus.customer.vo.ClientPointVo;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;

@Service("cus.clientPointService")
public class ClientPointServiceImpl extends BaseServiceImpl<ClientPointVo,ClientPoint> implements
		IClientPointService {

	@Autowired 
	private IClientPointDao clientPointDao;
	@Autowired 
	private IClientDao clientDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Override
	public void add(ClientPointVo v) throws GlobalException {
		ClientPoint p = vo2Po(v);
		if(null!=v.getClientVo()&&null!=v.getClientVo().getId())
			p.setClient(clientDao.findById(v.getClientVo().getId()));
		if(StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			List<SampType> stList=sampTypeDao.listByIds(v.getSampTypeId());
			Set<String> tySet=new HashSet<String>();
			for (SampType sampType : stList) {
				tySet.add(sampType.getType());
			}
			p.setType(String.join(",", tySet));
		}
		clientPointDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void update(ClientPointVo v) throws GlobalException {
		ClientPoint p = clientPointDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(null!=v.getClientVo()&&null!=v.getClientVo().getId())
			p.setClient(clientDao.findById(v.getClientVo().getId()));
		if(StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			List<SampType> stList=sampTypeDao.listByIds(v.getSampTypeId());
			Set<String> tySet=new HashSet<String>();
			for (SampType sampType : stList) {
				tySet.add(sampType.getType());
			}
			p.setType(String.join(",", tySet));
		}
		clientPointDao.update(p);
	}
	@Override
	public List<QueryCondition> toQueryList(ClientPointVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null!=v.getClientVo()&&!StrUtils.isBlankOrNull(v.getClientVo().getId()))
		queryList.add(new QueryCondition("client.id", QueryCondition.EQ, v.getClientVo().getId()));
		return queryList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ClientPointVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = clientPointDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ClientPoint>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Tab(GridVo gridVo, ClientPointVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = clientPointDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ClientPoint>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<ClientPointVo> list(ClientPointVo v) throws GlobalException {
		String jpql="FROM "+clientPointDao.getEntityName(ClientPoint.class)+" WHERE isDel='"+Po.N+"'";
		if(null!=v.getClientVo()) {
			if(StrUtils.isNotBlankOrNull(v.getClientVo().getId())) {
				jpql+=" AND client.id='"+v.getClientVo().getId()+"'";
			}
			if(StrUtils.isNotBlankOrNull(v.getClientVo().getName())) {
				jpql+=" AND client.name like '"+v.getClientVo().getName()+"'";
			}
		}
		if(StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			String[] idArr=v.getSampTypeId().split(",");
			String subHql="";
			for (String idStr : idArr) {
				if(idStr.trim().length()>0) {
					subHql+=" sampTypeId like '%"+idStr.trim()+"%' OR";
				}
			}
			if(subHql.length()>2) {
				subHql=subHql.substring(0,subHql.length()-2);
			}
			jpql+=" AND ("+subHql+")";
		}
		if(StrUtils.isNotBlankOrNull(v.getSampType())) {
			jpql+=" AND sampType like '"+v.getSampType()+"'";
		}
		jpql+=" order by sort asc";
		return toVoList(clientPointDao.list(jpql));
	}
	@Override
	public List<ClientPointVo> listByCustId(String custId) throws GlobalException {
		String jpql="FROM "+clientPointDao.getEntityName(ClientPoint.class)+" WHERE isDel='"+Po.N+"' AND client.id='"+custId+"' order by sort asc";
		List<ClientPoint>  pList=clientPointDao.list(jpql);
		return toVoList(pList);
	}

	@Override
	public List<ClientPointVo> listByCustName(String custName) throws GlobalException {
		String jpql="FROM "+clientPointDao.getEntityName(ClientPoint.class)+" WHERE isDel='"+Po.N+"' AND client.name like '"+custName+"' order by sort asc";
		List<ClientPoint>  pList=clientPointDao.list(jpql);
		return toVoList(pList);
	}
}

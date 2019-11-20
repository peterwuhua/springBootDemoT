package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Pstandard;
import cn.demi.init.std.service.IPstandardService;
import cn.demi.init.std.vo.PstandardVo;

@Service("init.pstandardService")
public class PstandardServiceImpl extends BaseServiceImpl<PstandardVo,Pstandard> implements
		IPstandardService {
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Override
	public PstandardVo findByCode(String code) throws GlobalException {
		code=code.replaceAll("　", " ").replaceAll("_", "-").replaceAll("－", "-").trim();
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("code LIKE '"+code+"'"));
		List<PstandardVo> list = super.list(queryConditions);
		PstandardVo vo = new PstandardVo();
		if(list.size()>0){
			vo = list.get(0);
		}
		return vo;
	}
	@Override
	public void add(PstandardVo v) throws GlobalException {
		Pstandard p = vo2Po(v);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		pstandardDao.add(p);
		excuteStop(p);
		v.setId(p.getId());
	}
	@Override
	public void update(PstandardVo v) throws GlobalException {
		Pstandard p = pstandardDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		if(p.getStatus().equals(Pstandard.ST_RUN)) {
			p.setPid(null);
			p.setPname(null);
		}
		pstandardDao.update(p);
		excuteStop(p);
	}
	//执行作废标准
	public void excuteStop(Pstandard p) {
		if(StrUtils.isNotBlankOrNull(p.getStandIds())) {
			List<Pstandard> pList=pstandardDao.listByIds(p.getStandIds());
			if(pList!=null&&pList.size()>0) {
				for (Pstandard pstandard : pList) {
					pstandard.setStatus(Pstandard.ST_STOP);
					pstandard.setPid(p.getId());
					pstandard.setPname(p.getCode()+" "+p.getName());
					pstandardDao.update(pstandard);
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, PstandardVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = pstandardDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Pstandard>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(PstandardVo v) throws GlobalException {
		List<QueryCondition> returnList=new ArrayList<QueryCondition>();
		if(StrUtils.isNotBlankOrNull(v.getStatus())) {
			returnList.add(new QueryCondition("status='"+v.getStatus()+"'"));
		}
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			Set<String> idsSet=new HashSet<String>();
			String idArr[]=v.getSampTypeId().split(",");
			for (String idStr : idArr) {
				String ids=sampTypeDao.findAllIds(idStr);
				idsSet.addAll(Arrays.asList(ids.split(",")));
			}
			String hql="";
			for (String id : idsSet) {
				if(id.trim().length()>0) {
					hql+=" sampTypeId like '%"+id+"%' or";
				}
			}
			if(hql.length()>2) {
				hql=hql.substring(0, hql.length()-2);
				returnList.add(new QueryCondition(hql));
			}
		}
		return returnList;
	}
	@Override
	public List<PstandardVo> list4Ajax(String sampTypeId) throws GlobalException {
		String jpql="FROM "+pstandardDao.getEntityName(Pstandard.class)+" WHERE isDel='"+Po.N+"' AND sampTypeId in('"+sampTypeId.replaceAll(",", "','")+"') GROUP BY sampTypeId order by sort asc";
		List<Pstandard> list=pstandardDao.list(jpql);
		return toVoList(list);
	}
	@Override
	public List<PstandardVo> list4Sels(String ids) throws GlobalException {
		List<Pstandard> plist=pstandardDao.listByIds(ids);
		return toVoList(plist);
	}
}

package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.dao.IMstandardDao;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.po.Mstandard;
import cn.demi.init.std.service.IMstandardService;
import cn.demi.init.std.vo.MstandardVo;

@Service("init.mstandardService")
public class MstandardServiceImpl extends BaseServiceImpl<MstandardVo,Mstandard> implements
		IMstandardService {
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private IMstandardDao mstandardDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Override
	public void update(MstandardVo v) throws GlobalException {
		Mstandard p = mstandardDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		mstandardDao.update(p);
		if("Y".equals(v.getIsSyncMethod())){
			syncMethod(v);
		}
	}
	@Override
	public void save(MstandardVo v) throws GlobalException {
		if(StrUtils.isEmpty(v.getId())){
			add(v);
		}else{
			update(v);
		}
	}
	@Override
	public void add(MstandardVo v) throws GlobalException {
		Mstandard p = vo2Po(v);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		mstandardDao.add(p);
		v.setId(p.getId());
	}
	public void syncMethod(MstandardVo v) throws GlobalException{
		List<Method> mList=methodDao.findByProperty("standId", v.getId());
		for (Method method : mList) {
			method.setStandName(v.getName());
			method.setCode(v.getCode());
			methodDao.update(method);
		}
	}
	@Override
	public MstandardVo findByCode(String code) throws GlobalException {
		code=code.replaceAll("　", " ").replaceAll("_", "-").replaceAll("－", "-").trim();
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("code LIKE '"+code+"'"));
		List<MstandardVo> list = super.list(queryConditions);
		MstandardVo vo = new MstandardVo();
		if(list.size()>0){
			vo = list.get(0);
		}
		return vo;
	}
	@Override
	public void delete(String... ids) throws GlobalException {
		List<Mstandard> list=mstandardDao.listByIds(ids);
		for (Mstandard mstandard : list) {
			methodDao.deleteByStandId(mstandard.getId());
			mstandardDao.delete(mstandard);
		}
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(Mstandard p:mstandardDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			methodDao.update4Del(p.getId());
			mstandardDao.update(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, MstandardVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			String ids=sampTypeDao.findAllIds(v.getSampTypeId());
			pageResult.addCondition(new QueryCondition("sampTypeId in('"+ids.replace(",", "','")+"')"));
		}
		pageResult = mstandardDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Mstandard>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<MstandardVo> list(MstandardVo v) throws GlobalException {
		String jpql="FROM "+mstandardDao.getEntityName(Mstandard.class)+" WHERE isDel='"+Po.N+"'";
		jpql+=" AND id in ('"+v.getIds().replace(",", "','")+"')";
		List<Mstandard> plist=mstandardDao.list(jpql);
		return toVoList(plist);
	}
}

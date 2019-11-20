package cn.demi.res.service.impl;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.vo.UserVo;
import cn.demi.res.dao.IEquipmentDao;
import cn.demi.res.po.Equipment;
import cn.demi.res.po.EquipmentOut;
import cn.demi.res.service.IEquipmentService;
import cn.demi.res.vo.EquipmentVo;

@Service("res.equipmentService")
public class EquipmentServiceImpl extends BaseServiceImpl<EquipmentVo,Equipment> implements IEquipmentService {
	
	@Autowired
	private IEquipmentDao equiptDao;
	@Autowired
	private IAccountDao accountDao;
	@Override
	public void data2Vo(String[] values, EquipmentVo v, String param) {
		v.setNo(values[0]);
		v.setName(values[1]);
		v.setSpec(values[2]);
		v.setAccuracy(values[3]);
		v.setMeasureRange(values[4]);
		//v.setQj(values[5]);
		v.setProducer(values[6]);
		v.setCode(values[7]);
		if(!StrUtils.isBlankOrNull(values[8])) {
			v.setProductDate(values[8].replace(".", "-"));
		}
		v.setStatus(values[10]);
//		if(!StrUtils.isBlankOrNull(values[17])) {
//			v.setLastverificationDate(values[17].replace(".", "-"));
//		}else if(!StrUtils.isBlankOrNull(values[16])) {
//			v.setLastverificationDate(values[16].replace(".", "-"));
//		}else if(!StrUtils.isBlankOrNull(values[15])) {
//			v.setLastverificationDate(values[15].replace(".", "-"));
//		}else if(!StrUtils.isBlankOrNull(values[14])) {
//			v.setLastverificationDate(values[14].replace(".", "-"));
//		}else if(!StrUtils.isBlankOrNull(values[13])) {
//			v.setLastverificationDate(values[13].replace(".", "-"));
//		}else if(!StrUtils.isBlankOrNull(values[12])) {
//			v.setLastverificationDate(values[12].replace(".", "-"));
//		}
//		String lvdate=v.getLastverificationDate();
//		if(!StrUtils.isBlankOrNull(lvdate)&&lvdate.length()>10) {
//			lvdate=lvdate.replace("、", "/");
//			String ss[]=lvdate.split("/");
//			if(ss.length==2){
//				v.setLastverificationDate(ss[0]);
//				v.setLastcalibrationDate(ss[1]);
//			}
//		}
//		if(!StrUtils.isBlankOrNull(values[18])) {
//			try {
//				if(values[18].contains("年")) {
//					v.setVerificationCycle(Integer.parseInt(values[18].replace("年", ""))*12+"");
//				}else {
//					v.setVerificationCycle(values[18].replace("个月", ""));
//				}
//			} catch (Exception e) {
//			}
//		}
//		if(!StrUtils.isBlankOrNull(v.getLastverificationDate())&&StrUtils.isBlankOrNull(v.getVerificationCycle())) {
//			try {
//				int cc=Integer.valueOf(v.getVerificationCycle());
//				v.setVerificationDate(DateUtils.getNextDate4M(v.getLastverificationDate(), cc));
//			} catch (Exception e) {
//			}
//		}
		
		if(!StrUtils.isBlankOrNull(values[19])) {
			v.setEffectDate(values[19].replace(".", "-"));
		}
		v.setVerificationUnit(values[20]);
		v.setDeptName(values[22]);
		v.setKeeper(values[23]);
		v.setProducerPhone(values[24]);
		v.setPurTime(values[25]);
	}
	@Override
	public EquipmentVo findById(String id) throws GlobalException {
		Equipment po = equiptDao.findById(id);
		EquipmentVo vo=po2Vo(po);;
		return vo;
	}
	@Override
	public void add(EquipmentVo v) throws GlobalException {
		v.setOrgId(getCurrent().getOrgId());
		v.setOrgName(getCurrent().getOrgName());
		super.add(v);
	}
	@Override
	public void update(EquipmentVo v) throws GlobalException {
		Equipment p = equiptDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(StrUtils.isBlankOrNull(p.getOrgId())) {
			p.setOrgId(getCurrent().getOrgId());
			p.setOrgName(getCurrent().getOrgName());
		}
		equiptDao.update(p);
	}
	 
	@Override
	public PageResult pageResult(EquipmentVo v, PageResult pageResult) throws GlobalException {
		 
		return super.pageResult(v, pageResult);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, EquipmentVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = equiptDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Equipment>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(EquipmentVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(StrUtils.isNotBlankOrNull(v.getStatus())) {
			queryConditions.add(new QueryCondition("status = '"+v.getStatus()+"'"));
		}
		return queryConditions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, EquipmentVo v, String startDate, String endDate,
			String type) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		pageResult = equiptDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Equipment>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public void importData(EquipmentVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)){
			for(Equipment p:equiptDao.list()) {
				p.setIsDel(Po.Y);
				equiptDao.update(p);
			}
		}
		EquipmentVo vo = null;
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
	public List<EquipmentVo> list(EquipmentVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(v.getIds())) {
			queryConditions.add(new QueryCondition("id in ('"+v.getIds().replace(",", "','")+"')"));
		}
		return super.list(queryConditions);
	}
	@Override
	public List<EquipmentVo> listAll(EquipmentVo v) throws GlobalException {
		String jpql="FROM "+equiptDao.getEntityName(Equipment.class)+" WHERE isDel='"+Po.N+"' ORDER BY no ASC";
		List<Equipment> list=equiptDao.list(jpql);
		List<EquipmentVo> vList = new ArrayList<EquipmentVo>();
		if(null==list||list.size()==0)
			return vList;
		
		for(Equipment p:list) {
			EquipmentVo vo=po2Vo(p);
			if(!StrUtils.isBlankOrNull(vo.getKeepId())) {
				Account acct=accountDao.findById(vo.getKeepId());
				UserVo userVo= new UserVo();
				userVo=userVo.toVo(acct.getUser());
				vo.setUserVo(userVo);
			}
			vList.add(vo);
		}
		return vList;
	}
	@Override
	public List<EquipmentVo> listData4Out(GridVo gridVo, EquipmentVo v) throws GlobalException {
		StringBuffer hql=new StringBuffer("SELECT equipt.id,equipt.name,equipt.no,equipt.spec,equipt.code,equipt.dept_name,equipt.keeper,equipt.status,count(ot.id) FROM "+tablePrefix+equiptDao.getEntityName(Equipment.class)+" equipt");
		hql.append(" LEFT JOIN "+tablePrefix+equiptDao.getEntityName(EquipmentOut.class)+" ot ON equipt.idequipt.equipt_id AND (ot.use_time>='"+DateUtils.getCurrDateStr()+"' or ot.use_time<'"+DateUtils.getCurrDateStr()+"' and ot.back_time>='"+DateUtils.getCurrDateStr()+"')");
		hql.append(" WHERE equipt.is_del='"+Po.N+"'");
		
		if(!StrUtils.isBlankOrNull(v.getType())) {
			hql.append(" AND equipt.type like '%"+v.getType()+"%'");
		}
		hql.append(" AND equipt.status like '"+Equipment.ST_0+"'");
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		if(QueryConditionList!=null) {
			for (QueryCondition condition : QueryConditionList) {
				if(!StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) {
					if(condition.getField().equals("deptName")) {
						hql.append(" AND (equipt.dept_name like '%"+String.valueOf(condition.getValue()).trim()+"%')");
					}else {
						hql.append(" AND (equipt."+condition.getField().trim()+" like '%"+String.valueOf(condition.getValue()).trim()+"%')");
					}
				}
			}
		}
		hql.append(" group by equipt.id,equipt.name,equipt.no,equipt.spec,equipt.code,equipt.dept_name,equipt.keeper,equipt.status");
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			hql.append(" order by equipt.name,equipt.no asc");
		}else{
			hql.append(" order by equipt."+gridVo.getSidx()+" "+gridVo.getSord());
		}
		@SuppressWarnings("unchecked")
		List<Object[]> equiptList=equiptDao.queryBySql(hql.toString());
		List<EquipmentVo> equiptVoList=null;
		if(null!=equiptList) {
			equiptVoList=new ArrayList<EquipmentVo>();
			for (Object[] obj : equiptList) {
				EquipmentVo vo=new EquipmentVo();
				vo.setId(String.valueOf(obj[0]));
				vo.setName(String.valueOf(obj[1]));
				vo.setNo(String.valueOf(obj[2]));
				vo.setSpec(String.valueOf(obj[3]));
				vo.setCode(String.valueOf(obj[4]));
				vo.setDeptName(String.valueOf(obj[5]));
				vo.setKeeper(String.valueOf(obj[6]));
				vo.setStatus(String.valueOf(obj[7]));
				vo.setAddress(String.valueOf(obj[8]));
				equiptVoList.add(vo);
			}
		}
		return equiptVoList;
	}
}

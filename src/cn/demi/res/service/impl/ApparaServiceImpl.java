package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
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
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;
import cn.demi.res.po.ApparaOut;
import cn.demi.res.service.IApparaService;
import cn.demi.res.vo.ApparaVo;

@Service("res.apparaService")
public class ApparaServiceImpl extends BaseServiceImpl<ApparaVo,Appara> implements IApparaService {
	
	@Autowired
	private IApparaDao apparaDao;
	@Autowired
	private IAccountDao accountDao;
	@Override
	public void data2Vo(String[] values, ApparaVo v, String param) {
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
		v.setState(values[10]);
		if(!StrUtils.isBlankOrNull(values[17])) {
			v.setLastverificationDate(values[17].replace(".", "-"));
		}else if(!StrUtils.isBlankOrNull(values[16])) {
			v.setLastverificationDate(values[16].replace(".", "-"));
		}else if(!StrUtils.isBlankOrNull(values[15])) {
			v.setLastverificationDate(values[15].replace(".", "-"));
		}else if(!StrUtils.isBlankOrNull(values[14])) {
			v.setLastverificationDate(values[14].replace(".", "-"));
		}else if(!StrUtils.isBlankOrNull(values[13])) {
			v.setLastverificationDate(values[13].replace(".", "-"));
		}else if(!StrUtils.isBlankOrNull(values[12])) {
			v.setLastverificationDate(values[12].replace(".", "-"));
		}
		String lvdate=v.getLastverificationDate();
		if(!StrUtils.isBlankOrNull(lvdate)&&lvdate.length()>10) {
			lvdate=lvdate.replace("、", "/");
			String ss[]=lvdate.split("/");
			if(ss.length==2){
				v.setLastverificationDate(ss[0]);
				v.setLastcalibrationDate(ss[1]);
			}
		}
		if(!StrUtils.isBlankOrNull(values[18])) {
			try {
				if(values[18].contains("年")) {
					v.setVerificationCycle(Integer.parseInt(values[18].replace("年", ""))*12+"");
				}else {
					v.setVerificationCycle(values[18].replace("个月", ""));
				}
			} catch (Exception e) {
			}
		}
		if(!StrUtils.isBlankOrNull(v.getLastverificationDate())&&StrUtils.isBlankOrNull(v.getVerificationCycle())) {
			try {
				int cc=Integer.valueOf(v.getVerificationCycle());
				v.setVerificationDate(DateUtils.getNextDate4M(v.getLastverificationDate(), cc));
			} catch (Exception e) {
			}
		}
		
		if(!StrUtils.isBlankOrNull(values[19])) {
			v.setEffectDate(values[19].replace(".", "-"));
		}
		v.setVerificationUnit(values[20]);
		v.setUserTeam(values[21]);
		v.setDeptName(values[22]);
		v.setKeeper(values[23]);
		v.setProducerPhone(values[24]);
		v.setPurTime(values[25]);
	}
	@Override
	public ApparaVo findById(String id) throws GlobalException {
		Appara po = apparaDao.findById(id);
		ApparaVo vo=po2Vo(po);;
		return vo;
	}
	@Override
	public void add(ApparaVo v) throws GlobalException {
		v.setOrgId(getCurrent().getOrgId());
		v.setOrgName(getCurrent().getOrgName());
		super.add(v);
	}
	@Override
	public void update(ApparaVo v) throws GlobalException {
		Appara p = apparaDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(StrUtils.isBlankOrNull(p.getOrgId())) {
			p.setOrgId(getCurrent().getOrgId());
			p.setOrgName(getCurrent().getOrgName());
		}
		apparaDao.update(p);
	}
	public void formatStr(Appara p) {
		String charge=p.getNoteCharge();
		if(null!=charge) {
			charge=charge.replace("\"", "&quot;").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		}
		p.setNoteCharge(charge);
		String order=p.getNoteOrder();
		if(null!=order) {
			order=order.replace("\"", "&quot;").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		}
		p.setNoteOrder(order);
		String samp=p.getNoteSamp();
		if(null!=samp) {
			samp=samp.replace("\"", "&quot;").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		}
		p.setNoteSamp(samp);
	}
	@Override
	public PageResult pageResult(ApparaVo v, PageResult pageResult) throws GlobalException {
		if(null != v.getIsMensuration() && !"".equals(v.getIsMensuration())){
			pageResult.addCondition("isMensuration",QueryCondition.EQ,v.getIsMensuration());
		}
		return super.pageResult(v, pageResult);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ApparaVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = apparaDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Appara>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(ApparaVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		//queryConditions.add(new QueryCondition("orgId is null or orgId = '"+getCurrent().getOrgId()+"'"));
		return queryConditions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ApparaVo v, String startDate, String endDate,
			String type) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult = setConditionList(v,pageResult,startDate,endDate,type);
		pageResult = apparaDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Appara>)pageResult.getResultList()));
 
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
	public PageResult setConditionList(ApparaVo v,PageResult pageResult,String startDate, String endDate,String type){
        if(StrUtils.isBlankOrNull(startDate)){
        	startDate = DateUtils.getCurrDateStr();
        }
        if(StrUtils.isBlankOrNull(endDate)){
        	endDate =DateUtils.getNextDate4M(startDate, 1);
        }
        List<QueryCondition> queryList = new ArrayList<QueryCondition>();
        if(type.equals("test")){//检定
			queryList.add(new QueryCondition("verificationDate >='"+startDate+ "' and verificationDate <='" + endDate+"' or verificationDate is null"));
		}else if(type.equals("calibration")){//校准
			queryList.add(new QueryCondition("calibrationDate >='"+startDate+ "' and calibrationDate <='" + endDate+"' or calibrationDate is null"));
		}
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		pageResult.addConditionList(queryList);
		return pageResult;
	}
	
	@Override
	public void importData(ApparaVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)){
			for(Appara p:apparaDao.list()) {
				p.setIsDel(Po.Y);
				apparaDao.update(p);
			}
		}
		ApparaVo vo = null;
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
	public List<ApparaVo> list(ApparaVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(v.getIds())) {
			queryConditions.add(new QueryCondition("id in ('"+v.getIds().replace(",", "','")+"')"));
		}
		return super.list(queryConditions);
	}
	@Override
	public List<ApparaVo> listAll(ApparaVo v) throws GlobalException {
		String jpql="FROM "+apparaDao.getEntityName(Appara.class)+" WHERE isDel='"+Po.N+"' ORDER BY no ASC";
		List<Appara> list=apparaDao.list(jpql);
		List<ApparaVo> vList = new ArrayList<ApparaVo>();
		if(null==list||list.size()==0)
			return vList;
		
		for(Appara p:list) {
			ApparaVo vo=po2Vo(p);
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
	public List<ApparaVo> listData4Out(GridVo gridVo, ApparaVo v) throws GlobalException {
		StringBuffer hql=new StringBuffer("SELECT app.id,app.name,app.no,app.spec,app.code,app.dept_name,app.keeper,app.state,count(ot.id) FROM "+tablePrefix+apparaDao.getEntityName(Appara.class)+" app");
		hql.append(" LEFT JOIN "+tablePrefix+apparaDao.getEntityName(ApparaOut.class)+" ot ON app.id=ot.appara_id AND (ot.use_time>='"+DateUtils.getCurrDateStr()+"' or ot.use_time<'"+DateUtils.getCurrDateStr()+"' and ot.back_time>='"+DateUtils.getCurrDateStr()+"')");
		hql.append(" WHERE app.is_del='"+Po.N+"'");
		
		if(!StrUtils.isBlankOrNull(v.getTypeId())) {
			hql.append(" AND app.type_id like '%"+v.getTypeId()+"%'");
		}
		if(!StrUtils.isBlankOrNull(v.getDeptName())) {
			hql.append(" AND app.dept_name like '%"+v.getDeptName()+"%'");
		}
		hql.append(" AND app.state like '"+Appara.ST_0+"'");
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		if(QueryConditionList!=null) {
			for (QueryCondition condition : QueryConditionList) {
				if(!StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) {
					if(condition.getField().equals("deptName")) {
						hql.append(" AND (app.dept_name like '%"+String.valueOf(condition.getValue()).trim()+"%')");
					}else {
						hql.append(" AND (app."+condition.getField().trim()+" like '%"+String.valueOf(condition.getValue()).trim()+"%')");
					}
				}
			}
		}
		hql.append(" group by app.id,app.name,app.no,app.spec,app.code,app.dept_name,app.keeper,app.state");
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			hql.append(" order by app.name,app.no asc");
		}else{
			hql.append(" order by app."+gridVo.getSidx()+" "+gridVo.getSord());
		}
		@SuppressWarnings("unchecked")
		List<Object[]> appList=apparaDao.queryBySql(hql.toString());
		List<ApparaVo> appVoList=null;
		if(null!=appList) {
			appVoList=new ArrayList<ApparaVo>();
			for (Object[] obj : appList) {
				ApparaVo vo=new ApparaVo();
				vo.setId(String.valueOf(obj[0]));
				vo.setName(String.valueOf(obj[1]));
				vo.setNo(String.valueOf(obj[2]));
				vo.setSpec(String.valueOf(obj[3]));
				vo.setCode(String.valueOf(obj[4]));
				vo.setDeptName(String.valueOf(obj[5]));
				vo.setKeeper(String.valueOf(obj[6]));
				vo.setState(String.valueOf(obj[7]));
				vo.setExt01(String.valueOf(obj[8]));
				appVoList.add(vo);
			}
		}
		return appVoList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Reppair(GridVo gridVo, ApparaVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.addOrder("name", OrderCondition.ORDER_ASC);
			pageResult.addOrder("no", OrderCondition.ORDER_ASC);
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("state='"+Appara.ST_1+"'"));
		pageResult = apparaDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Appara>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException {
		String hql="FROM "+apparaDao.getEntityName(Appara.class)+" WHERE isDel='"+Po.N+"' AND verificationDate is not null AND verificationDate<'"+DateUtils.getNextDate(DateUtils.getCurrDateStr(), 7)+"' ";
		List<Appara> rtList=apparaDao.list(hql);
		if(null!=rtList) {
			for (Appara app : rtList) {
				TxVo tx=new TxVo();
				tx.setIcon("fa fa-binoculars");
				tx.setType("仪器检定");
				tx.setStatus("待检定");
				tx.setName(app.getNo()+" / "+app.getName()+" "+app.getSpec());
				tx.setContent("仪器"+app.getName()+" "+app.getSpec()+" 预计检定日期为 "+app.getVerificationDate()+"，本次检定即将超期或已超期，请及时处理！");
				list.add(tx);
			}
		}
		hql="FROM "+apparaDao.getEntityName(Appara.class)+" WHERE isDel='"+Po.N+"' AND calibrationDate is not null AND calibrationDate<'"+DateUtils.getNextDate(DateUtils.getCurrDateStr(), 7)+"' ";
		List<Appara> rtList2=apparaDao.list(hql);
		if(null!=rtList2) {
			for (Appara app : rtList2) {
				TxVo tx=new TxVo();
				tx.setIcon("fa fa-binoculars");
				tx.setType("仪器校准");
				tx.setStatus("待校准");
				tx.setName(app.getNo()+" / "+app.getName()+" "+app.getSpec());
				tx.setContent("仪器"+app.getName()+" "+app.getSpec()+" 预计校准日期为 "+app.getCalibrationDate()+"，本次校准即将超期或已超期，请及时处理！");
				list.add(tx);
			}
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Record(GridVo gridVo, ApparaVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		
        String endDate =DateUtils.getNextDate4M(DateUtils.getCurrDateStr(), 1);
        pageResult.addCondition(new QueryCondition("(verificationDate <='"+endDate+ "' or verificationDate is null) or (calibrationDate <='"+endDate+ "' or calibrationDate is null)"));
        pageResult.addCondition(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		
		pageResult = apparaDao.getPageResult(pageResult);
		List<Appara> appList=(List<Appara>)pageResult.getResultList();
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for(Appara app:appList){
			Map<String, Object> map=po2map(app);
			String verificationDate=String.valueOf(map.get("verificationDate"));
			if(StrUtils.isBlankOrNull(verificationDate)) {
				map.put("verification", "1");
				map.put("lastverificationDate", "缺失");
			}else if(DateUtils.getIntevalDays(endDate, verificationDate)<=0) {
				map.put("verification", "1");
			}
			String calibrationDate=String.valueOf(map.get("calibrationDate"));
			if(StrUtils.isBlankOrNull(calibrationDate)) {
				map.put("calibration", "1");
				map.put("lastcalibrationDate", "缺失");
			}else if(DateUtils.getIntevalDays(endDate, calibrationDate)<=0) {
				map.put("calibration", "1");
			}
			tempList.add(map);
		}
		gridVo.setDatas(tempList);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public GridVo gridData4Out(GridVo gridVo, ApparaVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = apparaDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Appara>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<ApparaVo> listData4Sels(String ids) throws GlobalException {
		List<Appara> appaList = apparaDao.listByIds(ids);
		return toVoList(appaList);
	}
}

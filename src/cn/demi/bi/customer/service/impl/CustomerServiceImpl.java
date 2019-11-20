package cn.demi.bi.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bi.customer.service.ICustomerService;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.vo.CustomerVo;

@Service("bi.customerService")
public class CustomerServiceImpl extends BaseServiceImpl<CustomerVo, Customer> implements
		ICustomerService {
	@Autowired
	private ICustomerDao customerDao;
	
	@Override
	public void data2Vo(String[] values, CustomerVo v, String param) {
		v.setCode(values[1]);
		v.setName(values[2]);
		v.setGrade(values[3]);
		v.setSaler(values[4]);
		//v.setSouce(values[5]);
		v.setIndustry(values[6]);
		v.setCusType(values[7]);
		v.setBuildDate(values[8]);
		v.setAreaPath(values[9]);
		String[] area = values[9].split("/");
		if(area.length==2 && area[0].equals("中国")){
			v.setProvince(area[1]);
		}else if(area.length==3 && area[0].equals("中国")){
			v.setProvince(area[1]);
			v.setCity(area[2]);
		}else if(area.length==4 && area[0].equals("中国")){
			v.setProvince(area[1]);
			v.setCity(area[2]);
			v.setCounty(area[3]);
		}
		//v.setDataSouce(values[10]);
	}
	
	@Override
	public List<CustomerVo> list(CustomerVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null != v.getSaler() && StringUtils.isNotBlank(v.getSaler()))
			queryList.add(new QueryCondition(" saler IN ('"+v.getSaler().replace(",", "','")+"')"));
//		if(null != v.getSouce() && StringUtils.isNotBlank(v.getSouce()))
//			queryList.add(new QueryCondition(" souce IN ('"+v.getSouce().replace(",", "','")+"')"));
		if(null != v.getIndustry() && StringUtils.isNotBlank(v.getIndustry()))
			queryList.add(new QueryCondition(" industry IN ('"+v.getIndustry().replace(",", "','")+"')"));
		if(null != v.getCusType() && StringUtils.isNotBlank(v.getCusType()))
			queryList.add(new QueryCondition(" cusType IN ('"+v.getCusType().replace(",", "','")+"')"));
		if(null != v.getBuildDate() && StringUtils.isNotBlank(v.getBuildDate()))
			queryList.add(new QueryCondition(" buildDate IN ('"+v.getBuildDate().replace(",", "','")+"')"));
		if(null != v.getAreaPath() && StringUtils.isNotBlank(v.getAreaPath()))
			queryList.add(new QueryCondition("areaPath", QueryCondition.AK,v.getAreaPath()));
		if(null != v.getName() && StringUtils.isNotBlank(v.getName()))
			queryList.add(new QueryCondition(" name IN ('"+v.getName().replace(",", "','")+"')"));
		if(null != v.getCode() && StringUtils.isNotBlank(v.getCode()))
			queryList.add(new QueryCondition(" code IN ('"+v.getCode().replace(",", "','")+"')"));
		queryList.add(new QueryCondition("isDel",QueryCondition.EQ,Po.N));
		return toVoList(customerDao.list(queryList));
	}

	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		pageType = pageType.replaceAll("[A-Z]", "_$0").toLowerCase(); //字符大写转“——”加小写
		List<String> poList = customerDao.selectByPageType(pageType);
		return poList;
	}

	@Override
	public List<String> gradeList() throws GlobalException {
		return customerDao.gradeList();
	}

	@Override
	public List<String> selectByArea(String pageType, String data,String parentType)  throws GlobalException {
		pageType = pageType.replaceAll("[A-Z]", "_$0").toLowerCase(); //字符大写转“——”加小写
		parentType = parentType.replaceAll("[A-Z]", "_$0").toLowerCase(); 
		return customerDao.selectByArea(pageType,data,parentType);
	}

	@Override
	public List<Integer> countBySalerAndGrade(String saler, String grade)  throws GlobalException {
		return customerDao.countBySalerAndGrade(saler,grade);
	}
	
	@Override
	public List<QueryCondition> toQueryList(CustomerVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(StrUtils.isNotBlankOrNull(v.getSaler()))
			queryConditions.add(new QueryCondition("saler IN ('"+v.getSaler().replace(",", "','")+"')"));
		if(StrUtils.isNotBlankOrNull(v.getIndustry()))
			queryConditions.add(new QueryCondition("industry IN ('"+v.getIndustry().replace(",", "','")+"')"));
		if(StrUtils.isNotBlankOrNull(v.getCusType()))
			queryConditions.add(new QueryCondition("cusType IN ('"+v.getCusType().replace(",", "','")+"')"));
		if(StrUtils.isNotBlankOrNull(v.getBuildDate()))
			queryConditions.add(new QueryCondition("buildDate IN ('"+v.getBuildDate().replace(",", "','")+"')"));
		if(StrUtils.isNotBlankOrNull(v.getGrade()))
			queryConditions.add(new QueryCondition(" grade IN ('"+v.getGrade().replace(",", "','")+"')"));
		if(StrUtils.isNotBlankOrNull(v.getAreaPath()))
				queryConditions.add(new QueryCondition("areaPath", QueryCondition.AK,v.getAreaPath()));
		return queryConditions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CustomerVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = customerDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Customer>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
	@Override
	public List<String> salerList(CustomerVo vo) throws GlobalException {
		return customerDao.salerList(vo);
	}

	@Override
	public List<String> findCountGradesBySaler(CustomerVo vo,String saler) throws GlobalException {
		return customerDao.findCountGradesBySaler(vo,saler);
	}
}

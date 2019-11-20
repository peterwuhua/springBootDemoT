package cn.demi.res.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;

/** <strong>Create on : 2017年2月28日 下午1:24:43 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Repository("res.apparaDao")
public class ApparaDaoImpl extends BaseDaoImpl<Appara> implements IApparaDao {

	@Override
	public List<Appara> listByTime(String name,String startTime, String endTime, String type) throws GlobalException{
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(type.equals("test")){
			queryList.add(new QueryCondition("verificationDate >='"+startTime+ "' and verificationDate <='" + endTime+"'"));
		}else if(type.equals("calibration")){
			queryList.add(new QueryCondition("calibrationDate >='"+startTime+ "' and calibrationDate <='" + endTime+"'"));
		}else if(type.equals("inspect")){
			queryList.add(new QueryCondition("inspectDate >='"+startTime+ "' and inspectDate <='" + endTime+"'"));
		}
		if(null != name && !"".equals(name))
		queryList.add(new QueryCondition("name",QueryCondition.LK,name.trim()));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,
				Po.N));
		return super.list(queryList, null, -1, -1);
	}

	@Override
	public List<String[]> listTestByDate(String name) throws GlobalException {
		List<String[]> listStr = new ArrayList<String[]>();
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("verificationDate >='1970-01-01' and verificationDate <='"+DateUtils.getCurrDateStr()+"'"));
		if(null != name && !"".equals(name))
			queryList.add(new QueryCondition("name",QueryCondition.LK,name.trim()));
		List<Appara> list =  super.list(queryList, null, -1, -1);
		if(null==list) return listStr;
		for(Appara po:list){
			String[] task=new String[5];
			task[0]=po.getName()+"["+po.getNo()+"]";
			task[1]=po.getVerificationDate();
			task[2]=String.valueOf(DateUtils.getIntevalDays(po.getVerificationDate(),DateUtils.getCurrDateStr()));
			task[3]=po.getState();
			task[4]=po.getKeeper();
			listStr.add(task);
		}
		return listStr;
	}

	@Override
	public List<Appara> listByIds4Use(String ids) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("id in('"+ids.replace(",", "','")+ "')"));
		queryList.add(new QueryCondition("state='"+Appara.ST_0+"'"));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		return super.list(queryList, null, -1, -1);
	}
}

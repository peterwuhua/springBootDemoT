package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.IpAddressUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.ILogRecordDao;
import cn.demi.base.system.po.LogRecord;
import cn.demi.base.system.service.ILogRecordService;
import cn.demi.base.system.vo.LogRecordVo;
import net.sf.json.JSONObject;

@Service("sys.logRecordService")
public class LogRecordServiceImpl extends BaseServiceImpl<LogRecordVo,LogRecord> implements
		ILogRecordService {
	@Autowired
	ILogRecordDao logRecordDao;
	@Override
	public void add(String[] record) throws GlobalException {
		LogRecord red=new LogRecord();
		red.setCtime(DateUtils.getCurrDateTimeStr());
		red.setUser(getCurrent().getKey());
		red.setIp(record[0]);
		red.setModule(record[1]);
		red.setOpt(record[2]);
		red.setContent(record[3]);
		red.setMethod(record[4]);
		red.setParams(record[5]);
		logRecordDao.add(red);
	}
	@Override
	public void add4Login(String[] record) throws GlobalException {
		LogRecord red=new LogRecord();
		red.setCtime(DateUtils.getCurrDateTimeStr());
		red.setIp(record[0]);
		red.setModule(record[1]);
		red.setOpt(record[2]);
		red.setContent(record[3]);
		red.setMethod(record[4]);
		red.setUser(record[5]);
		logRecordDao.add(red);
	}
	@Override
	public List<String[]> delete4Quarter() throws GlobalException {
		List<String[]> recoreList=new ArrayList<String[]>();
		String date=DateUtils.getMonthOfBeforeQuarter(DateUtils.getYear()+DateUtils.getFirstMonthOfQuarter(DateUtils.getQuarter()));
		date=date.substring(0, 4)+"-"+date.substring(4, 6)+date+"-01";
		String jpql="FROM "+logRecordDao.getEntityName(LogRecord.class)+" WHERE ctime<'"+date+"'";
		List<LogRecord> recordList=logRecordDao.list(jpql);
		if(null!=recordList) {
			for (LogRecord record : recordList) {
				String[] str=new String[8];
				str[0]=record.getCtime();
				str[1]=record.getIp();
				str[2]=record.getUser();
				str[3]=record.getModule();
				str[4]=record.getOpt();
				str[5]=record.getContent();
				str[6]=record.getMethod();
				str[7]=record.getParams();
				recoreList.add(str);
			}
		}
		//清楚数据库数据
		jpql="DELETE FROM "+logRecordDao.getEntityName(LogRecord.class)+" WHERE ctime<'"+date+"'";
		logRecordDao.query(jpql).executeUpdate();
		return recoreList;
	}
	@Override
	public List<QueryCondition> toQueryList(LogRecordVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null!=getCurrent().getDuty()&&getCurrent().getDuty().contains("管理员")) {
		}else {
			queryList.add(new QueryCondition("user like '%【"+getCurrent().getLoginName()+"】%'" ));
		}
		return queryList;
	}
	@Override
	public LogRecordVo findById(String id) throws GlobalException {
		LogRecord po = logRecordDao.findById(id);
		LogRecordVo vo=new LogRecordVo();
		vo=vo.toVo(po);
		if(!StrUtils.isBlankOrNull(vo.getParams())) {
			String params=vo.getParams().replaceAll("&", "<br>");
			vo.setParams(params);
		}
		return vo;
	}
	@Override
	public int count(String user) throws GlobalException {
		String jpql="SELECT count(id) FROM "+logRecordDao.getEntityName(LogRecord.class)+" WHERE user like '%【"+user+"】%'";
		Object ct=logRecordDao.query(jpql).getSingleResult();
		int count=0;
		try {
			count=Integer.valueOf(ct.toString());
		} catch (NumberFormatException e) {
			count=0;
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, LogRecordVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = logRecordDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList1((List<LogRecord>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	public List<Map<String, Object>> poList2mapList1(List<LogRecord> list) throws GlobalException {
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		for(LogRecord p:list){
			Map<String, Object> map=po2map(p);
			String ip=map.get("ip").toString();
			if(ip!=null&&!ip.equals("0:0:0:0:0:0:0:1")) {
		        try {  
		        	String address = IpAddressUtils.getAddresses("ip="+ip, "utf-8"); 
		        	JSONObject json=JSONObject.fromObject(address);
		        	JSONObject data=json.getJSONObject("data");
					ip+="【"+data.get("region").toString()+" "+data.get("city").toString()+"】";
		        } catch (Exception e) {  
		        	log.error("根据ip获取位置异常，请检查网络", e);
		        }  
//				try {
//					
//					String rt=HttpClientUtil.httpGetRequest("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=Json&ip="+ip);
//					JSONObject json=JSONObject.fromObject(rt);
//					ip+="【"+json.get("province").toString()+" "+json.get("city").toString()+"】";
//				} catch (ClientProtocolException e) {
//					log.error("根据ip获取位置异常，请检查网络", e);
//				} catch (Exception e) {
//					log.error("根据ip获取位置异常，请检查网络", e);
//				}
				map.put("ip",ip);
			}
			tempList.add(map);
		}
		return tempList;
	}
	@Override
	public List<LogRecordVo> subList() throws GlobalException {
		String jpql="FROM "+logRecordDao.getEntityName(LogRecord.class)+" where user like '%【"+getCurrent().getLoginName()+"】%' order by lastUpdTime desc limit 100";
		List<LogRecord> pList=logRecordDao.list(jpql);
		return toVoList(pList);
	}
}

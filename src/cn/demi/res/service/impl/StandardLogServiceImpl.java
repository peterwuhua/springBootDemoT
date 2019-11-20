package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.IStandardLogDao;
import cn.demi.res.po.StandardLog;
import cn.demi.res.service.IStandardLogService;
import cn.demi.res.vo.StandardLogVo;

@Service("res.standardLogService")
public class StandardLogServiceImpl extends BaseServiceImpl<StandardLogVo,StandardLog> implements
		IStandardLogService {
	@Autowired private IStandardLogDao standardDao;
	@Override
	public List<StandardLogVo> listByStandardId(String StandardId) throws GlobalException {
		List<StandardLogVo> voList = new ArrayList<StandardLogVo>();
		List<StandardLog> poList = standardDao.listByStandardId(StandardId);
		StandardLogVo vo = null;
		if(CollectionUtils.isEmpty(poList))return voList;
		for (StandardLog StandardLog : poList) {
			vo = po2Vo(StandardLog);
			if(null != vo.getAmount() && null != vo.getLastAmount() ){
				if(vo.getAmount()>vo.getLastAmount()){
					vo.setStatus("入库");
				}else if(vo.getAmount().equals(vo.getLastAmount())){
					vo.setStatus("修改基本信息");
				}else{
					vo.setStatus("出库");
				}
			}
			voList.add(vo);
		}
		return voList;
	}
	
	
	
}

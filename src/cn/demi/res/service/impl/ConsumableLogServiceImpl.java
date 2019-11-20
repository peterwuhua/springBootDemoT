package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.IConsumableLogDao;
import cn.demi.res.po.ConsumableLog;
import cn.demi.res.service.IConsumableLogService;
import cn.demi.res.vo.ConsumableLogVo;

@Service("res.consumableLogService")
public class ConsumableLogServiceImpl extends BaseServiceImpl<ConsumableLogVo,ConsumableLog> implements
		IConsumableLogService {
	@Autowired private IConsumableLogDao consumableDao;
	@Override
	public List<ConsumableLogVo> listByConsumableId(String consumableId) throws GlobalException {
		List<ConsumableLogVo> voList = new ArrayList<ConsumableLogVo>();
		List<ConsumableLog> poList = consumableDao.listByConsumableId(consumableId);
		ConsumableLogVo vo = null;
		if(CollectionUtils.isEmpty(poList))return voList;
		
		for (ConsumableLog consumableLog : poList) {
			vo = po2Vo(consumableLog);
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

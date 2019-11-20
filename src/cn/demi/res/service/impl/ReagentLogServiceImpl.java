package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.IReagentLogDao;
import cn.demi.res.po.ReagentLog;
import cn.demi.res.service.IReagentLogService;
import cn.demi.res.vo.ReagentLogVo;

@Service("res.reagentLogService")
public class ReagentLogServiceImpl extends BaseServiceImpl<ReagentLogVo,ReagentLog> implements
		IReagentLogService {
	@Autowired private IReagentLogDao reagentDao;
	@Override
	public List<ReagentLogVo> listByReagentId(String reagentId) throws GlobalException {
		List<ReagentLogVo> voList = new ArrayList<ReagentLogVo>();
		List<ReagentLog> poList = reagentDao.listByReagentId(reagentId);
		ReagentLogVo vo = null;
		if(CollectionUtils.isEmpty(poList))return voList;
		for (ReagentLog reagentLog : poList) {
			vo = po2Vo(reagentLog);
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

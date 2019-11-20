package cn.demi.init.ct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.ct.po.Container;
import cn.demi.init.ct.service.IContainerService;
import cn.demi.init.ct.vo.ContainerVo;

@Service("init.containerService")
public class ContainerServiceImpl extends BaseServiceImpl<ContainerVo,Container> implements
		IContainerService {

	@Override
	public ContainerVo findByCode(String id, String code) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(id)) {
			queryConditions.add(new QueryCondition("id",QueryCondition.NE,id));
		}
		queryConditions.add(new QueryCondition("code LIKE '"+code+"'"));
		List<ContainerVo> list = super.list(queryConditions);
		ContainerVo vo = new ContainerVo();
		if(list.size()>0){
			vo = list.get(0);
		}
		return vo;
	}
}

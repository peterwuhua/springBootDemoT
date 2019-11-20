package cn.demi.bus.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.dao.ISampContainerDao;
import cn.demi.bus.sample.po.SampContainer;
import cn.demi.bus.sample.service.ISampContainerService;
import cn.demi.bus.sample.vo.SampContainerVo;

@Service("bus.sampContainerService")
public class SampContainerServiceImpl extends BaseServiceImpl<SampContainerVo,SampContainer> implements
		ISampContainerService {
	@Autowired
	private ISampContainerDao sampContainerDao;
	@SuppressWarnings("unchecked")
	@Override
	public List<SampContainerVo> listByTaskId(String taskId) throws GlobalException {
		String sql="select container,sum(num) from v_bus_samp_container where is_del="+Po.N+" AND task_id='"+taskId+"'";
		sql+=" group by container_id";
		sql+=" order by sort asc";
		List<Object[]> objList=sampContainerDao.queryBySql(sql);
		List<SampContainerVo> scVoList=new ArrayList<SampContainerVo>();
		for (Object[] obj : objList) {
			SampContainerVo scVo=new SampContainerVo();
			scVo.setContainer(String.valueOf(obj[0]));
			scVo.setNum(Integer.valueOf(String.valueOf(obj[1])));
			scVoList.add(scVo);
		}
		return scVoList;
	}
}

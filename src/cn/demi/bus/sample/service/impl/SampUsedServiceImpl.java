package cn.demi.bus.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.dao.ISampUsedDao;
import cn.demi.bus.sample.po.SampUsed;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.service.ISampUsedService;
import cn.demi.bus.sample.vo.SampUsedVo;

@Service("bus.sampUsedService")
public class SampUsedServiceImpl extends BaseServiceImpl<SampUsedVo,SampUsed> implements
		ISampUsedService {

	@Autowired
	private ISampUsedDao sampUsedDao;
	@Override
	public List<SampUsedVo> listBySampId(String sampId) throws GlobalException {
		String jpql="FROM "+sampUsedDao.getEntityName(SampUsed.class)+" WHERE isDel='"+Po.N+"'  AND sampling.id='"+sampId+"' order by useDate DESC";
		List<SampUsed> pList=sampUsedDao.list(jpql);
		List<SampUsedVo> vList=new ArrayList<SampUsedVo>();
		for (SampUsed su : pList) {
			SampUsedVo suVo=new SampUsedVo();
			suVo=suVo.toVo(su);
			if(suVo.getStatus()==null) {
				suVo.setStatus("");
			}else if(suVo.getStatus().equals(Sampling.ST_20)) {
				suVo.setStatus("待出库");
			}else if(suVo.getStatus().equals(Sampling.ST_21)) {
				suVo.setStatus("已出库");
			}else if(suVo.getStatus().equals(Sampling.ST_22)) {
				suVo.setStatus("待归还");
			}else if(suVo.getStatus().equals(Sampling.ST_23)) {
				suVo.setStatus("已归还");
			}
			if(suVo.getType().equals(SampUsed.TYPE_OUT)) {
				suVo.setType("出库");
			}else {
				suVo.setType("归还");
			}
			vList.add(suVo);
		}
		return vList;
	}
}

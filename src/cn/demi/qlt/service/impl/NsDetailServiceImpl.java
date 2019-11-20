package cn.demi.qlt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.dao.INsDao;
import cn.demi.qlt.dao.INsDetailDao;
import cn.demi.qlt.dao.INsItemDao;
import cn.demi.qlt.po.Ns;
import cn.demi.qlt.po.NsDetail;
import cn.demi.qlt.po.NsItem;
import cn.demi.qlt.service.INsDetailService;
import cn.demi.qlt.vo.NsDetailVo;

@Service("qlt.nsDetailService")
public class NsDetailServiceImpl extends BaseServiceImpl<NsDetailVo,NsDetail> implements
		INsDetailService {
	@Autowired
	private INsDao nsDao;
	@Autowired
	private INsDetailDao nsDetailDao;
	@Autowired
	private INsItemDao nsItemDao;
	
	@Override
	public void add(NsDetailVo v) throws GlobalException {
		Ns ns=nsDao.findById(v.getNsVo().getId());
		List<NsItem> itList=nsItemDao.listByIds(v.getIds());
		for (NsItem it : itList) {
			NsDetail dt=new NsDetail();
			dt.setNs(ns);
			dt.setCode(it.getCode());
			dt.setItemId(it.getId());
			dt.setName(it.getName());
			nsDetailDao.add(dt);
		}
	}
}

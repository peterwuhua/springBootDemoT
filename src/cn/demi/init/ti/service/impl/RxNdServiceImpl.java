package cn.demi.init.ti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.ti.dao.IRxNdDao;
import cn.demi.init.ti.po.RxNd;
import cn.demi.init.ti.service.IRxNdService;
import cn.demi.init.ti.vo.RxNdVo;

@Service("init.rxNdService")
public class RxNdServiceImpl extends BaseServiceImpl<RxNdVo,RxNd> implements
		IRxNdService {
	
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private IRxNdDao rxNdDao;
	
	
	@Override
	public RxNdVo findById(String id) throws GlobalException {
		RxNd po;
		try {
			po = rxNdDao.findById(id);
		} catch (Exception e) {
			throw new GlobalException("根据id获取对象失败  id:"+id,e);
		} 
		if(null==po) return null;
		return po2Vo(po);
	}
	
	@Override
	public void add(RxNdVo v) throws GlobalException {
		RxNd p = vo2Po(v);
		p.setItem(itemDao.findById(v.getItemVo().getId()));
		p.setName(StrUtils.getFmt4Cn(v.getName()));
		p.setCxbs(getBs(v.getItemVo().getName(), v.getPctwa()));
		rxNdDao.add(p);
		v.setId(p.getId());
	}
	//计算超限倍数
	public String getBs(String itemName,String twa) {
		//粉尘              超限倍数：2
		//其他
		//PC-TWA＜1       2.5
		//1≤PC-TWA＜100   2.0
		//10≤PC-TWA＜100  1.5
		//PC-TWA≥100	  3
		String bs=null;
		try {
			if(itemName.contains("粉尘")) {
				bs=String.valueOf(2);
			}else if(!StrUtils.isBlankOrNull(twa)) {
				double d=Double.valueOf(twa);
				if(d<1) {
					bs=String.valueOf(3);
				}else if(d>=1&&d<10) {
					bs=String.valueOf(2.5);
				}else if(d>=10&&d<100) {
					bs=String.valueOf(2);
				}else if(d>=100) {
					bs=String.valueOf(1.5);
				}
			}
		} catch (NumberFormatException e) {
			bs=null;
		}
		return bs;
	}
	@Override
	public void update(RxNdVo v) throws GlobalException {
		RxNd p = rxNdDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		p.setCxbs(getBs(v.getItemVo().getName(), v.getPctwa()));
		p.setName(StrUtils.getFmt4Cn(v.getName()));
		p.setItem(itemDao.findById(v.getItemVo().getId()));
		rxNdDao.update(p);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, RxNdVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = rxNdDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<RxNd>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}

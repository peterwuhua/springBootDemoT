package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.res.dao.ITrainDao;
import cn.demi.res.dao.ITrainDetailDao;
import cn.demi.res.po.Train;
import cn.demi.res.po.TrainDetail;
import cn.demi.res.service.ITrainService;
import cn.demi.res.vo.TrainDetailVo;
import cn.demi.res.vo.TrainVo;

@Service("res.trainService")
public class TrainServiceImpl extends BaseServiceImpl<TrainVo,Train> implements
		ITrainService {
	@Autowired
	private ITrainDao trainDao;
	@Autowired
	private ITrainDetailDao trainDetailDao;
	@Autowired
	private IOrgDao orgDao;
	
	@Override
	public void add(TrainVo v) throws GlobalException {
		Train p = vo2Po(v);
		if(StrUtils.isBlankOrNull(v.getStatus())) {
			v.setStatus(Train.ST_INIT);
			p.setStatus(Train.ST_INIT);
		}
		trainDao.add(p);
		v.setId(p.getId());
		if(v.getStatus().equals(Train.ST_RUN)) {
			saveDetail(p);
		}
	}
	@Override
	public void update(TrainVo v) throws GlobalException {
		Train p = trainDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		if(StrUtils.isBlankOrNull(v.getStatus())) {
			v.setStatus(Train.ST_INIT);
			p.setStatus(Train.ST_INIT);
		}
		p.toPo(v, p);
		trainDao.update(p);
		if(v.getStatus().equals(Train.ST_RUN)) {
			saveDetail(p);
		}
	}
	public void saveDetail(Train train) {
		String[] userIds=train.getTrainIds().split(",");
		String[] userNames=train.getTrainNames().split(",");
		trainDetailDao.deleteAll(trainDetailDao.findByProperty("train.id", train.getId()));
		for (int i=0;i<userIds.length;i++) {
			if(!StrUtils.isBlankOrNull(userIds[i].trim())) {
				TrainDetail detail=new TrainDetail();
				detail.setTrain(train);
				detail.setUserId(userIds[i]);
				detail.setUserName(userNames[i]);
				trainDetailDao.add(detail);
			}
		}
	}
	@Override
	public TrainVo findById(String id) throws GlobalException {
		Train po= trainDao.findById(id);
		TrainVo vo=po2Vo(po);
		List<TrainDetail> detailList=trainDetailDao.findByProperty("train.id", po.getId());
		List<TrainDetailVo> detaiVolList=new ArrayList<TrainDetailVo>();
		if(null!=detailList) {
			for (TrainDetail detail : detailList) {
				TrainDetailVo detailVo=new TrainDetailVo();
				detailVo=detailVo.toVo(detail);
				detaiVolList.add(detailVo);
			}
		}
		vo.setDetailList(detaiVolList);
		return vo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TrainVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
			String orgId = v.getOrgId();
			if(StrUtils.isBlankOrNull(orgId)) {
				orgId=getCurrent().getOrgId();
			}
			List<String> ordIds=orgDao.listChild(orgId);
			ordIds.add(orgId);
			pageResult.addCondition(new QueryCondition("orgId in ('"+String.join("','", ordIds)+"')"));
		}
		
		pageResult = trainDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Train>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public void update4Run(String ids) throws GlobalException {
		String[] idAr=ids.split(",");
		for (String id : idAr) {
			if(!StrUtils.isBlankOrNull(id)) {
				Train t=trainDao.findById(id);
				t.setStatus(Train.ST_RUN);
				trainDao.update(t);
				String[] userIds=t.getTrainIds().split(",");
				String[] userNames=t.getTrainNames().split(",");
				trainDetailDao.deleteAll(trainDetailDao.findByProperty("train.id", t.getId()));
				for (int i=0;i<userIds.length;i++) {
					if(!StrUtils.isBlankOrNull(userIds[i].trim())) {
						TrainDetail detail=new TrainDetail();
						detail.setTrain(t);
						detail.setUserId(userIds[i]);
						detail.setUserName(userNames[i]);
						trainDetailDao.add(detail);
					}
				}
			}
		}
	}
	@Override
	public void delete(String... ids) throws GlobalException {
		List<Train> lst=trainDao.listByIds(ids);
		for (Train train : lst) {
			trainDetailDao.deleteAll(trainDetailDao.findByProperty("train.id", train.getId()));
		}
		trainDao.deleteAll(lst);
	}
	@Override
	public void update4Comp(TrainVo v) throws GlobalException {
		Train t=trainDao.findById(v.getId());
		t.setStatus(Train.ST_COMP);
		trainDao.update(t);
		List<TrainDetailVo>  dtailList=v.getDetailList();
		for (TrainDetailVo detailVo : dtailList) {
			TrainDetail detail=trainDetailDao.findById(detailVo.getId());
			detail.setResult(detailVo.getResult());
			detail.setTrainDate(detailVo.getTrainDate());
			detail.setRemark(detailVo.getRemark());
			trainDetailDao.update(detail);
		}
	}
	@Override
	public void updateDetail(TrainVo v) throws GlobalException {
		List<TrainDetailVo>  dtailList=v.getDetailList();
		for (TrainDetailVo detailVo : dtailList) {
			TrainDetail detail=trainDetailDao.findById(detailVo.getId());
			detail.setResult(detailVo.getResult());
			detail.setTrainDate(detailVo.getTrainDate());
			detail.setRemark(detailVo.getRemark());
			trainDetailDao.update(detail);
		}
	}
}

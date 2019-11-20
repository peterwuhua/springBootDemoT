package cn.demi.bus.file.service.impl;

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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.dao.IArchiveDao;
import cn.demi.bus.file.dao.IArchiveFileDao;
import cn.demi.bus.file.dao.IArchiveTypeDao;
import cn.demi.bus.file.po.Archive;
import cn.demi.bus.file.po.ArchiveFile;
import cn.demi.bus.file.service.IArchiveConfirmService;
import cn.demi.bus.file.service.IArchiveViewService;
import cn.demi.bus.file.vo.ArchiveFileVo;
import cn.demi.bus.file.vo.ArchiveVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;

@Service("bus.archiveViewService")
public class ArchiveViewServiceImpl extends BaseServiceImpl<ArchiveVo, Archive> implements IArchiveViewService {
	@Autowired
	private IArchiveTypeDao archiveTypeDao;
	@Autowired
	private IArchiveDao archiveDao;
	@Autowired
	private IArchiveFileDao archiveFileDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public ArchiveVo findById(String id) throws GlobalException {
		Archive po = archiveDao.findById(id);
		ArchiveVo vo = po2Vo(po);
		List<ArchiveFile> fileList = archiveFileDao.listByArId(id);
		List<ArchiveFileVo> fileVoList = new ArrayList<>();
		if (null != fileList) {
			for (ArchiveFile file : fileList) {
				ArchiveFileVo fileVo = new ArchiveFileVo();
				fileVo = fileVo.toVo(file);
				fileVoList.add(fileVo);
			}
		}
		vo.setFileList(fileVoList);
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ArchiveVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition("status", QueryCondition.EQ, Archive.STATUS_YQR);
		pageResult.addCondition("viewerId", QueryCondition.EQ, getCurrent().getAccountId());
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = archiveDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Archive>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ArchiveVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("viewTime is not null"));
		pageResult.addCondition(new QueryCondition("viewerId", QueryCondition.EQ, getCurrent().getAccountId()));
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = archiveDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Archive>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(ArchiveVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if (!StrUtils.isBlankOrNull(v.getDirIds())) {
			List<String> ids = archiveTypeDao.findAllSubids(v.getDirIds());
			queryConditions.add(new QueryCondition("archiveType.id in('" + String.join("','", ids) + "')"));
		}
		return queryConditions;
	}

	@Override
	public void update(ArchiveVo v) throws GlobalException {
		Archive p = archiveDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		Progress pg=progressDao.findByBusId(p.getId());
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setStatus(Archive.STATUS_YCY);
			progressDao.update(pg.getId(), EunmTask.GD_CY.getStatus(),p.getOrgId(),p.getOrgName(),p.getViewerId(), p.getViewer());
		}
		progressLogDao.add(p.getId(), p.getId(), EunmTask.GD_CY.getStatus(), v.getIsCommit(), p.getViewCt());
		archiveDao.update(p);
	}

}

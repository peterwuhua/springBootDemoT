package cn.demi.bus.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.dao.IArchiveDao;
import cn.demi.bus.file.dao.IArchiveFileDao;
import cn.demi.bus.file.dao.IArchiveTypeDao;
import cn.demi.bus.file.po.Archive;
import cn.demi.bus.file.po.ArchiveFile;
import cn.demi.bus.file.po.ArchiveType;
import cn.demi.bus.file.service.IArchiveService;
import cn.demi.bus.file.vo.ArchiveFileVo;
import cn.demi.bus.file.vo.ArchiveVo;

@Service("bus.archiveService")
public class ArchiveServiceImpl extends BaseServiceImpl<ArchiveVo,Archive> implements
		IArchiveService {
	@Autowired 
	private IArchiveTypeDao archiveTypeDao;
	@Autowired 
	private IArchiveDao archiveDao;
	@Autowired 
	private IArchiveFileDao archiveFileDao;
	
	@Override
	public ArchiveVo findById(String id) throws GlobalException {
		Archive po = archiveDao.findById(id);
		ArchiveVo vo=po2Vo(po);
		List<ArchiveFile>  fileList=archiveFileDao.listByArId(id);
		List<ArchiveFileVo>  fileVoList=new ArrayList<>();
		if(null!=fileList) {
			for (ArchiveFile file : fileList) {
				ArchiveFileVo fileVo=new ArchiveFileVo();
				fileVo=fileVo.toVo(file);
				fileVoList.add(fileVo);
			}
		}
		vo.setFileList(fileVoList);
		return vo;
	}
	
	
	@Override
	public void add(ArchiveVo v) throws GlobalException {
		Archive po = vo2Po(v);
		if(null != v.getArchiveTypeVo() && !v.getArchiveTypeVo().getId().equals(po.getArchiveType().getId())){
			ArchiveType category = archiveTypeDao.findById(v.getArchiveTypeVo().getId());
			po.setArchiveType(category);
		}
		if(null != po.getArchiveType()){
			po.setPath(po.getArchiveType().getPath());
		}
		archiveDao.add(po);
		v.setId(po.getId());
	}
	
	
	@Override
	public void update(ArchiveVo v) throws GlobalException {
		Archive po = archiveDao.findById(v.getId());
		po=po.toPo(v, po);
		if(null != v.getArchiveTypeVo() && !v.getArchiveTypeVo().getId().equals(po.getArchiveType().getId())){
			ArchiveType category = archiveTypeDao.findById(v.getArchiveTypeVo().getId());
			po.setArchiveType(category);
		}
		if(null != po.getArchiveType()){
			po.setPath(po.getArchiveType().getPath());
		}
		archiveDao.update(po);
		List<ArchiveFileVo> fileList=v.getFileList();
		if(null!=fileList) {
			for (ArchiveFileVo fileVo : fileList) {
				ArchiveFile file=archiveFileDao.findById(fileVo.getId());
				file.setSign(fileVo.getSign());
				archiveFileDao.update(file);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ArchiveVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		String  roles=getCurrent().getRoleNames();
		if(roles.contains("档案管理员") || roles.contains("总经理")) {
			//所有不过滤
		}else if(roles.contains("副总")) {
			pageResult.addCondition(new QueryCondition("orgId like '"+getCurrent().getOrgId()+"'"));
		}else if(roles.contains("行政审批")) {
			pageResult.addCondition(new QueryCondition("deptId like '"+getCurrent().getDepId()+"'"));
		}else {
			pageResult.addCondition(new QueryCondition("userId like '"+getCurrent().getAccountId()+"'"));
		}
		pageResult = archiveDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Archive>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(ArchiveVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(v.getDirIds())) {
			List<String> ids=archiveTypeDao.findAllSubids(v.getDirIds());
			queryConditions.add(new QueryCondition("archiveType.id in('"+String.join("','", ids)+"')"));
		}
		return queryConditions;
	}
	@Override
	public List<ArchiveVo> list(ArchiveVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
//		if(null != v.getType() && StringUtils.isNotBlank(v.getType()))
//			queryList.add(new QueryCondition(" type IN ('"+v.getType().replace(",", "','")+"')"));
		return toVoList(archiveDao.list(queryList));
	}
	
	@Override
	public void update2del(String... ids) throws GlobalException {
		for (Archive p : archiveDao.listByIds(ids)) {
			p.setIsDel(Po.Y);
			archiveDao.update(p);
			archiveFileDao.upt4Del(p.getId());
		}
	}
	
}

package cn.demi.bus.simpjt.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmProject;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.file.dao.IArchiveDao;
import cn.demi.bus.file.dao.IArchiveFileDao;
import cn.demi.bus.file.dao.IArchiveTypeDao;
import cn.demi.bus.file.po.Archive;
import cn.demi.bus.file.po.ArchiveFile;
import cn.demi.bus.file.po.ArchiveType;
import cn.demi.bus.file.vo.ArchiveFileVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.service.IProjectReptGdService;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Service("bus.simProjectReptGdService")
public class ProjectReptGdServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectReptGdService {
	public static final String SERVER_BASE = (String) ApplicationUtils.getValue("config.server.base");
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IFilesDao filesDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IArchiveTypeDao archiveTypeDao;
	@Autowired
	private IArchiveDao archiveDao;
	@Autowired
	private IArchiveFileDao archiveFileDao;
	@Autowired
	private IAccountDao accountDao;

	@Override
	public ProjectVo findById(String id, List<FilesVo> files) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = vo.toVo(po);
		if (StrUtils.isBlankOrNull(vo.getFileGder())) {
			vo.setFileGder(CurrentUtils.getUserName());
			vo.setFileGderId(CurrentUtils.getAccountId());
		}
		if (StrUtils.isBlankOrNull(vo.getFileGdDate())) {
			vo.setFileGdDate(DateUtils.getCurrDateTimeStr());
		}
		vo.setArchList(listFile(files));
		return vo;
	}

	public List<ArchiveFileVo> listFile(List<FilesVo> files) throws GlobalException {
		List<ArchiveFileVo> archList = new ArrayList<>();
		if (files != null && files.size() > 0) {
			for (int i = 0; i < files.size(); i++) {
				FilesVo filesVo = files.get(i);
				String fileName = filesVo.getFileName();
				String filePath = filesVo.getFilePath();
				// 资料文件
				if (!StrUtils.isBlankOrNull(fileName) && !StrUtils.isBlankOrNull(filePath)) {
					ArchiveFileVo arch = new ArchiveFileVo();
					arch.setFileName(fileName);
					arch.setFilePath(filePath);
					arch.setSign(Archive.TY_ZL);
					archList.add(arch);
				}
			}
		}

		return archList;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project p = projectDao.findById(v.getId());
		p.setFileGdDate(v.getFileGdDate());
		p.setFileGder(v.getFileGder());
		p.setFileGderId(v.getFileGderId());
		p.setCategoryId(v.getCategoryId());
		p.setCategoryName(v.getCategoryName());
		p.setPosition(v.getPosition());
		saveArchive(p, v.getArchList());
		commit(v, p);
		
	}

	public void commit(ProjectVo v, Project p) throws GlobalException {
		Progress pg = p.getProgress();
		Org org = null;
		if (p.getItemType().contains("环境咨询")) {
			org = orgDao.findByName("环境");
		} else if (p.getItemType().contains("安全") || p.getItemType().contains("职业卫生")) {
			org = orgDao.findByName("职业卫生");
		}
		if (null != org) {
			p.setOrgId(org.getId());
			p.setOrgName(org.getName());
		}
		pg = progressDao.update4Project(p.getProgress().getId(), EunmProject.PROJECT_END.getStatus());
		p.setStatus(pg.getStatus());
		p.setProgress(null);
		projectDao.update(p);
		progressLogDao.add4Porject(p.getId(), p.getId(), EunmProject.PROJECT_GD.getStatus(), EunmProject.PROJECT_END.getStatus(), v.getFileMsg());
		progressDao.delete(pg);

	}

	public void saveArchive(Project p, List<ArchiveFileVo> archList) throws GlobalException {
		if (StrUtils.isNotBlankOrNull(p.getCategoryId())) {
			ArchiveType archiveType = archiveTypeDao.findById(p.getCategoryId());
			Archive archive = new Archive();
			archive.setArchiveType(archiveType);
			archive.setTime(p.getFileDate());
			archive.setUserId(p.getFileUserId());
			archive.setUserName(p.getFileUser());
			Account at = accountDao.findById(archive.getUserId());
			Org dept = at.getOrg();
			Org org = orgDao.findOrg(dept.getId());
			archive.setOrgId(org.getId());
			archive.setOrgName(org.getName());
			archive.setDeptId(dept.getId());
			archive.setDeptName(dept.getName());
			archive.setCode(p.getNo());
			archive.setTitle(p.getCust().getWtName());
			archive.setPath(archiveType.getPath());
			archive.setDescribtion(p.getPosition());
			archiveDao.add(archive);
			if (null != archList) {
				for (ArchiveFileVo fileVo : archList) {
					ArchiveFile file = new ArchiveFile();
					file = file.toPo(fileVo, file);
					file.setArchive(archive);
					file.setTime(archive.getTime());
					String fileName = fileVo.getFileName();
					if (fileName == null) {
						continue;
					}
					file.setType(fileName.substring(fileName.lastIndexOf(".")));
					File f = new File(SERVER_BASE + file.getFilePath());
					if (f != null && f.isFile()) {
						file.setOriginalSize(f.length());
					}
					file.setSize(convertFileSize(file.getOriginalSize()));
					archiveFileDao.add(file);
				}
			}
		}
	}

	/**
	 * Description : 计算文件大小工具方法 <br>
	 * 
	 * @param size
	 * @return String
	 */
	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}

		pageResult.addCondition(new QueryCondition("orgId like '%" + getCurrent().getOrgId() + "%'"));
		pageResult.addCondition(new QueryCondition("status='" + EunmProject.PROJECT_GD.getStatus() + "' "));
		pageResult = projectDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(ProjectVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		return queryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + projectDao.getEntityName(Project.class) + " t,"
				+ projectDao.getEntityName(ProgressLog.class) + " log where log.busId=t.id and t.isDel !=" + Po.Y);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmProject.PROJECT_GD.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		Query query = projectDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Project> taskList = projectDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

}

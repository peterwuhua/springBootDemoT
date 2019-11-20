package cn.demi.bus.report.service.impl;

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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.po.Org;
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
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.service.IReportFileService;
import cn.demi.bus.report.vo.ReportVo;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;

@Service("bus.reportFileService")
public class ReportFileServiceImpl extends BaseServiceImpl<ReportVo, Report> implements IReportFileService {
	public static final String SERVER_BASE = (String)ApplicationUtils.getValue("config.server.base");
	
	@Autowired
	private IReportDao reportDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private ISampCydDao sampCydDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IFilesDao filesDao;
	@Autowired
	private IArchiveTypeDao archiveTypeDao; 
	@Autowired
	private IArchiveDao archiveDao; 
	@Autowired
	private IArchiveFileDao archiveFileDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IAccountDao accountDao;
	@Override
	public ReportVo findById(String id) throws GlobalException {
		Report po = reportDao.findById(id);
		ReportVo vo = new ReportVo();
		vo = vo.toVo(po);
		vo.setArchList(listFile(vo));
		return vo;
	}

	@Override
	public void update(ReportVo v) throws GlobalException {
		Report po = reportDao.findById(v.getId());
		po.setFileDate(v.getFileDate());
		po.setFileUser(v.getFileUser());
		po.setFileUserId(v.getFileUserId());
		po.setPosition(v.getPosition());
		po.setCategoryId(v.getCategoryId());
		po.setCategoryName(v.getCategoryName());
		po.setFileMsg(v.getFileMsg());
		saveArchive(po, v.getArchList());
		//报告状态
		Progress pg = progressDao.update(po.getProgress().getId(), EunmTask.TASK_END.getStatus());
		po.setStatus(pg.getStatus());
		po.setProgress(null);
		reportDao.update(po);
		progressLogDao.add(po.getTask().getId(), po.getTask().getId(), EunmTask.TASK_GD.getStatus(),EunmTask.TASK_END.getStatus(), v.getFileMsg());
		Task task=po.getTask();
		if(null!=task.getProgress()) {
			task.setProgress(null);
		}
		task.setStatus(po.getStatus());
		taskDao.update(task);
		progressDao.delete(pg);
		List<TaskItem> timList=taskItemDao.listByTaskId(task.getId());
		if(null!=timList) {
			for (TaskItem tim : timList) {
				Progress timPg =tim.getProgress();
				if(null!=timPg) {
					tim.setProgress(null);
					taskItemDao.update(tim);
					progressDao.delete(timPg);
				}
			}
		}
		if(!StrUtils.isBlankOrNull(task.getProjectId())) {
			Project pj=projectDao.findById(task.getProjectId());
			Progress pjPg=pj.getProgress();
			if(null!=pjPg) {
				pj.setProgress(null);
				projectDao.update(pj);
				progressDao.delete(pjPg);
			}
		}
	}
	public void saveArchive(Report p,List<ArchiveFileVo> archList) throws GlobalException {
		if(StrUtils.isNotBlankOrNull(p.getCategoryId())) {
			 ArchiveType archiveType= archiveTypeDao.findById(p.getCategoryId());
			 Archive archive=new Archive();
			 archive.setArchiveType(archiveType);
			 archive.setTime(p.getFileDate());
			 archive.setUserId(p.getFileUserId());
			 archive.setUserName(p.getFileUser());
			 Account at= accountDao.findById(archive.getUserId());
			 Org dept= at.getOrg();
			 Org org= orgDao.findOrg(dept.getId());
			 archive.setOrgId(org.getId());
			 archive.setOrgName(org.getName());
			 archive.setDeptId(dept.getId());
			 archive.setDeptName(dept.getName());
			 archive.setCode(p.getTask().getNo());
			 archive.setTitle(p.getCust().getWtName());
			 archive.setPath(archiveType.getPath());
			 archive.setDescribtion(p.getPosition());
			 archiveDao.add(archive);
			 if(null!=archList) {
				 for (ArchiveFileVo fileVo : archList) {
					 ArchiveFile file=new ArchiveFile();
					 file=file.toPo(fileVo, file);
					 file.setArchive(archive);
					 file.setTime(archive.getTime());
					 String fileName= fileVo.getFileName();
					 if (fileName == null) {
						 continue;
					 }
					 file.setType(fileName.substring(fileName.lastIndexOf(".")));
					 File f=new File(SERVER_BASE+file.getFilePath());
					 if(f!=null&&f.isFile()) {
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
	public GridVo gridData(GridVo gridVo, ReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_GD.getStatus() + "' "));
		//pageResult.addCondition(new QueryCondition("orgId like '" + CurrentUtils.getCurrent().getOrgId() + "%' "));
		pageResult = reportDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Report>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(ReportVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		//queryList.add(new QueryCondition("type='" + Po.N + "' "));
		return queryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + reportDao.getEntityName(Report.class) + " t," + reportDao.getEntityName(ProgressLog.class) + " log where log.busId=t.task.id and t.isDel !=" + Po.Y);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmTask.TASK_GD.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		Query query = reportDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Report> taskList = reportDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<ArchiveFileVo> listFile(ReportVo v) throws GlobalException {
		List<ArchiveFileVo> archList=new ArrayList<>();
		//报告文件
		if(!StrUtils.isBlankOrNull(v.getFileName())&&!StrUtils.isBlankOrNull(v.getFilePath())) {
			ArchiveFileVo arch=new ArchiveFileVo();
			arch.setFileName(v.getFileName());
			arch.setFilePath(v.getFilePath());
			arch.setSign(Archive.TY_BG);
			archList.add(arch);
		}
		//报告文件2
		if(!StrUtils.isBlankOrNull(v.getFileName2())&&!StrUtils.isBlankOrNull(v.getFilePath2())) {
			ArchiveFileVo arch=new ArchiveFileVo();
			arch.setFileName(v.getFileName2());
			arch.setFilePath(v.getFilePath2());
			arch.setSign(Archive.TY_BG);
			archList.add(arch);
		}
		//任务
		TaskVo taskVo=v.getTaskVo();
		if(!StrUtils.isBlankOrNull(taskVo.getFileName())&&!StrUtils.isBlankOrNull(taskVo.getFilePath())) {
			ArchiveFileVo arch=new ArchiveFileVo();
			arch.setFileName(taskVo.getFileName());
			arch.setFilePath(taskVo.getFilePath());
			arch.setSign(Archive.TY_XY);
			archList.add(arch);
		}
		List<Files> fileList=filesDao.listByBusid(taskVo.getId());
		if(null!=fileList) {
			for (Files file : fileList) {
				if(!StrUtils.isBlankOrNull(file.getFileName())&&!StrUtils.isBlankOrNull(file.getFilePath())) {
					ArchiveFileVo arch=new ArchiveFileVo();
					arch.setFileName(file.getFileName());
					arch.setFilePath(file.getFilePath());
					arch.setSign("任务-"+Archive.TY_FJ);
					archList.add(arch);
				}
			}
		}
		//立项
		if(!StrUtils.isBlankOrNull(taskVo.getProjectId())) {
			Project p=projectDao.findById(taskVo.getProjectId());
			if(!StrUtils.isBlankOrNull(p.getHtName())&&!StrUtils.isBlankOrNull(p.getHtPath())) {
				ArchiveFileVo arch=new ArchiveFileVo();
				arch.setFileName(p.getHtName());
				arch.setFilePath(p.getHtPath());
				arch.setSign(Archive.TY_XY);
				archList.add(arch);
			}
			if(!StrUtils.isBlankOrNull(p.getPsFileName())&&!StrUtils.isBlankOrNull(p.getPsFilePath())) {
				ArchiveFileVo arch=new ArchiveFileVo();
				arch.setFileName(p.getPsFileName());
				arch.setFilePath(p.getPsFilePath());
				arch.setSign(Archive.TY_PS);
				archList.add(arch);
			}
			fileList=filesDao.listByBusid(p.getId());
			if(null!=fileList) {
				for (Files file : fileList) {
					if(!StrUtils.isBlankOrNull(file.getFileName())&&!StrUtils.isBlankOrNull(file.getFilePath())) {
						ArchiveFileVo arch=new ArchiveFileVo();
						arch.setFileName(file.getFileName());
						arch.setFilePath(file.getFilePath());
						arch.setSign("项目-"+Archive.TY_FJ);
						archList.add(arch);
					}
				}
			}
		}
		//采样单
		List<SampCyd> cydList=sampCydDao.listByTaskId(taskVo.getId());
		if(null!=cydList) {
			for (SampCyd cyd : cydList) {
				if(!StrUtils.isBlankOrNull(cyd.getFileName())&&!StrUtils.isBlankOrNull(cyd.getFilePath())) {
					ArchiveFileVo arch=new ArchiveFileVo();
					arch.setFileName(cyd.getFileName());
					arch.setFilePath(cyd.getFilePath());
					arch.setSign(Archive.TY_CY);
					archList.add(arch);
				}
			}
		}
		List<TaskItem> timList=taskItemDao.listByTaskId(taskVo.getId());
		if(null!=timList) {
			for (TaskItem tim : timList) {
				if(!StrUtils.isBlankOrNull(tim.getFileName())&&!StrUtils.isBlankOrNull(tim.getFilePath())) {
					ArchiveFileVo arch=new ArchiveFileVo();
					arch.setFileName(tim.getFileName());
					arch.setFilePath(tim.getFilePath());
					arch.setSign(Archive.TY_YJ);
					archList.add(arch);
				}
				fileList=filesDao.listByBusid(tim.getId());
				if(null!=fileList) {
					for (Files file : fileList) {
						if(!StrUtils.isBlankOrNull(file.getFileName())&&!StrUtils.isBlankOrNull(file.getFilePath())) {
							ArchiveFileVo arch=new ArchiveFileVo();
							arch.setFileName(file.getFileName());
							arch.setFilePath(file.getFilePath());
							arch.setSign("数据录入-"+Archive.TY_FJ);
							archList.add(arch);
						}
					}
				}
			}
		}
		return archList;
	}
}

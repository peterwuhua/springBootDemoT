package cn.demi.bus.pjt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.po.Files;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.IProjectFbDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.ProjectFb;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.service.ISchemeService;
import cn.demi.bus.pjt.vo.SchemeVo;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskFbDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskFb;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.po.Item;

@Service("bus.schemeService")
public class SchemeServiceImpl extends BaseServiceImpl<SchemeVo,Scheme> implements
		ISchemeService {
	@Autowired
	private ISchemePointDao schemePointDao;
	@Autowired
	private ISchemeDao schemeDao;
//	@Autowired
//	private ISampTypeDao sampTypeDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private IProjectFbDao projectFbDao;
	@Autowired
	private ITaskFbDao taskFbDao;
	@Autowired
	private IFilesDao filesDao;
	@Autowired
	private IImDao imDao;
//	@Autowired
//	private IMethodDao methodDao;
	@SuppressWarnings("unchecked")
	@Override
	public void executeSchedule() throws GlobalException {
		String date=DateUtils.getNextWorkDate(DateUtils.getCurrDateStr(), 1);
		String hql="FROM "+schemeDao.getEntityName(Scheme.class)+" WHERE isDel="+Po.N+" AND startDate<='"+date+"' AND status='"+Scheme.ST_WZX+"' AND project.status='"+EunmTask.PROJECT_JX.getStatus()+"'";
		List<Scheme> schList=schemeDao.query(hql).getResultList();
		for (Scheme scheme : schList) {
			//生成任务开始
			Project project=scheme.getProject();
			Task p = new Task();
			p.setProjectId(project.getId());
			p.setSchemeId(scheme.getId());
			p.setSampType(project.getSampType());
			p.setCust(project.getCust());
			p.setNo(checkCode(p.getTaskType()));
			p.setTaskType(project.getTaskType());
			p.setPj(project.getPj());
			p.setStandIds(project.getStandIds());
			p.setStandNames(project.getStandNames());
			p.setPrice(scheme.getFxPrice()+scheme.getCyPrice());
			p.setReportNum(project.getReportNum());
			p.setReportWay(project.getReportWay());
//			p.setCyDay(scheme.getCyDay());
			if(StrUtils.isBlankOrNull(scheme.getEndDate())) {
				p.setFinishDate(project.getFinishDate());
			}else {
				p.setFinishDate(scheme.getEndDate());
			}
			if(StrUtils.isBlankOrNull(scheme.getSampTypeId())) {
				p.setSampTypeId(project.getSampTypeId());
				p.setSampTypeName(project.getSampTypeName());
			}else {
				p.setSampTypeId(scheme.getSampTypeId());
				p.setSampTypeName(scheme.getSampTypeName());
			}
			if(StrUtils.isBlankOrNull(scheme.getSampName())) {
				p.setSampName(project.getSampName());
			}else {
				p.setSampName(scheme.getSampName());
			}
			p.setSampNum(scheme.getSampNum());
			p.setZq(project.getCycle());
			p.setSource(project.getZsy());
			p.setTaskSource(null);
			if(p.getSource().equals(Constants.SAMP_ZS)) {
				p.setDealRequest(scheme.getDealRequest());
				p.setSaveRequest(scheme.getSaveRequest());
				p.setCyName(scheme.getCyUserName());
				p.setCyDate(scheme.getCyDate());
				p.setCyEndDate(scheme.getCyEndDate());
			}
			p.setJj(project.getJj());
			p.setFb(project.getFb());
			p.setIsBack(Constants.N);
			p.setZk(Constants.F);
			taskDao.add(p);
			//流程信息
			Progress progress = progressDao.add(p.getId(), EunmTask.TASK_DJ.getStatus(),null,null,null,null);
			progressLogDao.add(p.getId(),p.getId(),EunmTask.TASK_DJ.getStatus(),EunmTask.PASS_Y,"系统自动生成任务",null,null,null,null);
			p.setProgress(progress);
			p.setStatus(progress.getStatus());
			//复制项目
			copyPoint(p, scheme.getId(),project.getCycle());
			//复制分包信息
			copyFb(project, scheme,p);
			//复制附件信息
			copyFiles(project, p);
			taskDao.update(p);
			//生成任务结束  并更新方案状态为  执行中
			scheme.setStatus(Scheme.ST_ZXZ);
			schemeDao.update(scheme);
		}
	}
	//复制检测点位
	public void copyPoint(Task p,String schemeId,int cycle) {
		List<SchemePoint> spList=schemePointDao.listBySchId(schemeId);
		Set<String> pointSet=new HashSet<String>();
		Set<String> itemNameSet=new HashSet<String>();
		if(null!=spList) {
			for (SchemePoint sp : spList) {
				TaskPoint tp=new TaskPoint();
				tp.setSort(sp.getSort());
				tp.setTask(p);
				tp.setSampTypeId(sp.getSampTypeId());
				tp.setSampTypeName(sp.getSampTypeName());
				tp.setType(sp.getPointType());
				tp.setSampType(p.getSampType());
				tp.setSampName(sp.getSampName());
				tp.setItemIds(sp.getItemId());
				tp.setItemNames(sp.getItemName());
				tp.setRoom(sp.getRoom());
				tp.setPointName(sp.getPointName());
				tp.setPointCode(sp.getPointCode());
				tp.setCyHours(sp.getCyHours());
				tp.setCyType(sp.getCyType());
				tp.setPc(sp.getPc());
				tp.setPcUnit(sp.getPcUnit());
				tp.setZq(cycle);
				taskPointDao.add(tp);
				imDao.uptIm(tp.getId(),  imDao.findByBusId(sp.getId()));
				pointSet.add(tp.getPointName());
				itemNameSet.addAll(Arrays.asList(tp.getItemNames().split(",")));
			}
		}
		p.setPointNames(String.join(",", pointSet));
		p.setItemNames(String.join(",", itemNameSet));
	}
	//复制分包信息
	public void copyFb(Project project,Scheme scheme,Task task) {
		List<ProjectFb>  pjfList=projectFbDao.listByProjectId(project.getId());
		List<SchemePoint> spList=schemePointDao.listBySchId(scheme.getId());
		Set<String> idSet=new HashSet<String>();
		Set<String> nameSet=new HashSet<String>();
		Set<String> unitSet=new HashSet<String>();
		if(null!=spList&&spList.size()>0&&null!=pjfList&&pjfList.size()>0) {
			for (ProjectFb projectFb : pjfList) {
				String fbItemIds=projectFb.getItemIds();
				if(StrUtils.isBlankOrNull(fbItemIds))continue;
				List<String> itIdList=new ArrayList<String>();
				List<String> itNameList=new ArrayList<String>();
				float price=0;
				for (SchemePoint sp : spList) {
					String itemIds=sp.getItemId();
					if(StrUtils.isBlankOrNull(itemIds))continue;
					String jpql="FROM "+itemDao.getEntityName(Item.class)+" WHERE isDel="+Po.N+" AND id in('"+fbItemIds.replace(",", "','")+"') AND id in('"+itemIds.replace(",", "','")+"')";
					List<Item> itList=itemDao.list(jpql);
					if(null!=itList&&itList.size()>0) {
						for (Item item : itList) {
							if(!itIdList.contains(item.getId())) {
								itIdList.add(item.getId());
								itNameList.add(item.getName());
								price+=item.getPrice();
							}
						}
					}
				}
				if(itIdList.size()>0) {
					TaskFb tfb=new TaskFb();
					tfb.setTask(task);
					tfb.setFb(projectFb.getFb());
					tfb.setFbMobile(projectFb.getFbMobile());
					tfb.setFbUser(projectFb.getFbUser());
					tfb.setNum(projectFb.getNum());
					tfb.setPrice(price);
					tfb.setItemIds(String.join(",", itIdList));
					tfb.setItemNames(String.join(",", itNameList));
					tfb.setRemark(projectFb.getRemark());
					taskFbDao.add(tfb);
					idSet.addAll(itIdList);
					nameSet.addAll(itNameList);
					unitSet.add(tfb.getFb().getName());
				}
			}
		}
		task.setFbItemId(String.join(",", idSet));
		task.setFbItemName(String.join(",", nameSet));
		task.setFbUnit(String.join(",", unitSet));
	}
	//复制附件信息
	public void copyFiles(Project p,Task t) {
		String jpql="FROM "+filesDao.getEntityName(Files.class)+" WHERE busId='"+p.getId()+"'";
		List<Files> fList=filesDao.list(jpql);
		if(null!=fList) {
			for (Files files : fList) {
				Files f=new Files();
				BeanUtils.copyProperties(files, f, new String[] {"id"});
				f.setBusType(Constants.FILE_TYPE_TASK);
				f.setBusId(t.getId());
				filesDao.add(f);
			}
		}
	}
	//更新项目方法关系信息
//	public void uptIm(String busId,String imStr) {
//		imDao.deleteByBusId(busId);
//		String imArr[]=imStr.split(",");
//		int n=0;
//		for (String i : imArr) {
//			if(!StrUtils.isBlankOrNull(i)&&i.contains("-")) {
//				String m[]=i.split("-");
//				if(m.length==2&&m[0].trim().length()>0) {
//					Im im=new Im();
//					im.setBusId(busId);
//					Item item=itemDao.findById(m[0].trim());
//					im.setItemId(item.getId());
//					im.setItemName(item.getName());
//					im.setPrice(item.getPrice());
//					if(!StrUtils.isBlankOrNull(m[1].trim())) {
//						Method method=methodDao.findById(m[1].trim());
//						im.setMethodId(method.getId());
//						im.setMethodName(method.getName());
//					}
//					im.setUserId(getCurrent().getAccountId());
//					im.setUserName(getCurrent().getUserName());
//					im.setDate(DateUtils.getCurrDateTimeStr());
//					im.setSort(n);
//					imDao.add(im);
//					n++;
//				}
//			}
//		}
//	}
	//生成任务编号
	public String checkCode(String taskType) throws GlobalException {
		String prefix="";
		prefix+=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+taskDao.getEntityName(Task.class)+" WHERE isDel="+Po.N+" AND no like '"+prefix+"%'";
		String ls=(String) taskDao.query(hql).getSingleResult();
		String no=prefix;
		if(ls==null) {
			no+="001";
		}else {
			ls=ls.replace(prefix, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no+="00"+sort;
			}else if(sort<100) {
				no+="0"+sort;
			}else {
				no+=sort;
			}
		}
		return no;
	}
}

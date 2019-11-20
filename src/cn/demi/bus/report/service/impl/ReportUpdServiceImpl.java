package cn.demi.bus.report.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.dao.IReportUpdDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.po.ReportUpd;
import cn.demi.bus.report.service.IReportUpdService;
import cn.demi.bus.report.vo.ReportUpdVo;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.po.Category;
import cn.demi.doc.po.Document;

@Service("bus.reportUpdService")
public class ReportUpdServiceImpl extends BaseServiceImpl<ReportUpdVo, ReportUpd> implements IReportUpdService {
	
	private String SERVER_BASE = (String)ApplicationUtils.getValue("config.server.base");
	@Autowired
	private IReportDao reportDao;
	@Autowired
	private IReportUpdDao reportUpdDao;  
	@Autowired
	private ICategoryDao categoryDao;
	@Autowired
	private IDocumentDao documentDao;
	@Override
	public ReportUpdVo findById(String id) throws GlobalException {
		ReportUpd po = reportUpdDao.findById(id);
		ReportUpdVo vo = new ReportUpdVo();
		vo = vo.toVo(po);
		 
		return vo;
	}
	@Override
	public void addReportUpd(String reportId) throws GlobalException {
		Report report=reportDao.findById(reportId);
		ReportUpd rtu=new ReportUpd();
		rtu.setReport(report);
		rtu.setOrgId(getCurrent().getDepId());
		rtu.setOrgName(getCurrent().getDepName());
		rtu.setUserId(getCurrent().getAccountId());
		rtu.setUserName(getCurrent().getUserName());
		rtu.setDate(DateUtils.getCurrDateTimeStr());
		rtu.setTask(report.getTask());
		rtu.setCust(report.getCust());
		rtu.setStatus(String.valueOf(Po.N));
		//复制文件名及路径
		List<ReportUpd> rl=reportUpdDao.listByReportId(report.getId());
		int sort=rl.size()+1;
		rtu.setReportNo(report.getReportNo()+"-"+sort);
		rtu.setFileName(rtu.getReportNo()+".doc");
		String filePath=report.getFilePath();
		filePath=filePath.substring(0, filePath.lastIndexOf("/"))+File.separator+report.getId()+"-"+sort+".doc";
		filePath=filePath.replace("\\", "/");
		rtu.setFilePath(filePath);
		String pdfPath=report.getFilePath();
		pdfPath=pdfPath.substring(0, pdfPath.lastIndexOf("/"))+File.separator+report.getId()+"-"+sort+".pdf";
		pdfPath=pdfPath.replace("\\", "/");
		rtu.setPdfPath(pdfPath);
		rtu.setPdfName(rtu.getReportNo()+".pdf");
		copyFile(SERVER_BASE+report.getFilePath(), SERVER_BASE+rtu.getFilePath());
		reportUpdDao.add(rtu);
	}
	 
	public void copyFile(String oldfilepath,String newpath) {//复制文件
        File oldfile=new File(oldfilepath);
        File newfile=new File(newpath);//创建新抽象文件
        if(!oldfile.exists()||!oldfile.isFile()) {
            log.error("源文件不存在或异常");
            return;
        }
        if(newfile.exists()) {//新文件路径下有同名文件
        	 newfile.delete();
             try {
                 newfile.createNewFile();
             } catch (IOException e) {
             	log.error("目标文件创建异常", e);
             }
        }
        else {
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                log.error("目标文件创建异常", e);
            }
        }
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
        	  fi = new FileInputStream(oldfile);
              fo = new FileOutputStream(newfile);
              in = fi.getChannel();//得到对应的文件通道
              out = fo.getChannel();//得到对应的文件通道
              in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException  e) {
            log.error("目标文件不存在", e);
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                log.error("文件复制关闭流异常", e);
            }
        }
    }
	@Override
	public void update(ReportUpdVo v) throws GlobalException {
		ReportUpd po = reportUpdDao.findById(v.getId());
		po.setContent(v.getContent());
		po.setRemark(v.getRemark());
		po.setDate(v.getDate());
		po.setPosition(v.getPosition());
		po.setCategoryId(v.getCategoryId());
		if(!StrUtils.isBlankOrNull(v.getIsCommit())&&v.getIsCommit().equals(Constants.PASS_Y)) {
			po.setStatus(Constants.PASS_Y);
			if(StrUtils.isNotBlankOrNull(v.getCategoryId())) {
				 Category category= categoryDao.findById(v.getCategoryId());
				 Document doc=new Document();
				 doc.setCategory(category);
				 doc.setTitle(po.getReportNo());
				 doc.setName(po.getReportNo());
				 doc.setTime(DateUtils.getCurrDateTimeStr());
				 doc.setSize(String.valueOf(0));
				 doc.setOriginalSize(0);
				 doc.setSign("检测报告");
				 doc.setPath(category.getPath()+"/"+doc.getName());
				 doc.setRelativePath(po.getPdfPath());
				 doc.setType("pdf");
				 doc.setState("现行");
				 doc.setIsAllVisible(Constants.S);
				 documentDao.add(doc);
				 po.setCategoryName(doc.getPath());
			}else {
				po.setCategoryName(v.getCategoryName());
			}
		}else {
			if(StrUtils.isNotBlankOrNull(v.getCategoryId())) {
				 Category category= categoryDao.findById(v.getCategoryId());
				 po.setCategoryName(category.getPath());
			}
			po.setStatus(Constants.PASS_S);
		}
		reportUpdDao.update(po);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ReportUpdVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = reportUpdDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ReportUpd>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(ReportUpdVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
//		queryList.add(new QueryCondition("type='" + Po.N + "' "));
		return queryList;
	}
	
	
	
	
	
	
}

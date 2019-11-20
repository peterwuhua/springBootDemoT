package cn.demi.app.wenjian.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.wenjian.service.FileService;
import cn.demi.app.wenjian.vo.AppFile;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.po.Document;
import cn.demi.office.dao.IDtDao;
import cn.demi.office.po.Dt;
import cn.demi.office.vo.DtVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.print.resources.serviceui;

@Service("app.fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private IDtDao iDtDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IDocumentDao documentDao;
	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private ICategoryDao categoryDao;
	@Autowired
	private IUserDao userdao;

	@Override
	
	public List<AppFile> list(ObjVo v, int type,String searchValue) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + iDtDao.getEntityName(Dt.class) + " WHERE isDel='" + Po.N + "' ");
 
		// 1: 登记列表 2:签发列表3:下发 列表4：查看列表
		String auditId = v.getUserId();
		switch (type) {
		case 1:
			hql.append("and user_id='" + v.getUserId() + "'");
			break;
		case 2:
			hql.append("and audit_id = '" + auditId + "' and status!='" + EunmTask.DT_SQ.getStatus() + "'  ");
			break;
		case 3:
			hql.append("and send_id='" + auditId + "'and status!='" + EunmTask.DT_SQ.getStatus() + "' and status!='"
					+ EunmTask.DT_QF.getStatus() + "' ");
			break;
		case 4:
			hql.append("and user_ids like'%" + auditId + "%'and status='" + "finish" + "'or status='"
					+ EunmTask.DT_CK.getStatus() + "' ");
			break;
		}
		if (searchValue != null && !("").equals(searchValue) && !("null").equals(searchValue) ) {
			  hql.append("and( file_name like '%"+searchValue+"%' or code like '%"+searchValue+"%' or title like '%"+searchValue+"%') ");
		}
     
		hql.append(" ORDER BY lastUpdTime DESC");
	
		Query query = iDtDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		@SuppressWarnings("unchecked")
		List<Dt> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppFile> outList = new ArrayList<>();
		if (null != list) {
			for (Dt dt : list) {
				AppFile filelist = new AppFile();
				filelist.setId(dt.getId());
				filelist.setCode(dt.getCode());
				filelist.setTitle(dt.getTitle());
				filelist.setSource(dt.getSource());
				filelist.setDj(dt.getDj());
				filelist.setPath(dt.getPath());
				filelist.setFileName(dt.getFileName());
				filelist.setFilePath(dt.getFilePath());
				filelist.setAuditId(dt.getAuditId());
				filelist.setAuditName(dt.getAuditName());
				filelist.setAuditCt(dt.getAuditCt());
				filelist.setDate(dt.getDate());
				filelist.setResult(dt.getResult());
				filelist.setContent(dt.getContent());
				filelist.setRemark(dt.getRemark());
				filelist.setAuditTime(dt.getAuditTime());
				filelist.setSendTime(dt.getSendTime());
				filelist.setUserId(dt.getUserId());
				filelist.setUserName(dt.getUserName());
				filelist.setSendId(dt.getSendId());
				filelist.setSendName(dt.getSendName());
				filelist.setUserIds(dt.getUserIds());
				filelist.setUserNames(dt.getUserNames());
				filelist.setStatus(dt.getStatus());
				filelist.setStatusChina(EunmTask.getName(dt.getStatus()));
				outList.add(filelist);
			}
		}
		return outList;

	}

	@Override
	public AppFile findfile(String id) throws GlobalException {
		Dt dt = iDtDao.findById(id);
		AppFile filelist = new AppFile();
		filelist.setId(dt.getId());
		filelist.setCode(dt.getCode());
		filelist.setTitle(dt.getTitle());
		filelist.setSource(dt.getSource());
		filelist.setDj(dt.getDj());
		filelist.setPath(dt.getPath());
		filelist.setFileName(dt.getFileName());
		filelist.setFilePath(dt.getFilePath());
		filelist.setType(dt.getType());
		filelist.setAuditId(dt.getAuditId());
		filelist.setAuditName(dt.getAuditName());
		filelist.setAuditCt(dt.getAuditCt());
		filelist.setDate(dt.getDate());
		filelist.setResult(dt.getResult());
		filelist.setContent(dt.getContent());
		filelist.setRemark(dt.getRemark());
		filelist.setAuditTime(dt.getAuditTime());
		filelist.setSendTime(dt.getSendTime());
		filelist.setUserId(dt.getUserId());
		filelist.setUserName(dt.getUserName());
		filelist.setSendId(dt.getSendId());
		filelist.setSendName(dt.getSendName());
		filelist.setUserIds(dt.getUserIds());
		filelist.setUserNames(dt.getUserNames());
		filelist.setStatus(dt.getStatus());
		filelist.setStatusChina(EunmTask.getName(dt.getStatus()));
		filelist.setSendCt(dt.getSendCt());
		filelist.setViewEdIds(dt.getViewEdIds());
		filelist.setViewEdNames(dt.getViewEdNames());
		if (dt.getCategory() != null) {
			filelist.setCategoryId(dt.getCategory().getId());
			filelist.setCategoryName(dt.getCategory().getName());
			filelist.setCategoryPath(dt.getCategory().getPath());
		}

		return filelist;
	}

	@Override
	public String updateAuditfile(DtVo v, ObjVo objVo) throws GlobalException {
		try {
			Dt p = iDtDao.findById(v.getId());
			p.setAuditCt(v.getAuditCt());
			p.setAuditId(v.getAuditId());
			p.setAuditName(v.getAuditName());
			p.setAuditTime(v.getAuditTime());
			p.setSendId(v.getSendId());
			p.setSendName(v.getSendName());

			Progress pg = progressDao.findByBusId(p.getId());
			if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
				p.setAuditResult("通过");
				progressDao.update(pg.getId(), EunmTask.DT_XF.getStatus(), null, null, p.getSendId(), p.getSendName());
				uptFile(p);
			} else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
				p.setAuditResult("不通过");
				progressDao.update(pg.getId(), EunmTask.DT_SQ.getStatus(), p.getOrgId(), p.getOrgName(), p.getUserId(),
						p.getUserName());
			}
			p.setStatus(pg.getStatus());
			progressLogDao.addApp(p.getId(), p.getId(), EunmTask.DT_QF.getStatus(), v.getIsCommit(), p.getAuditCt(),
					objVo);

			iDtDao.update(p);
			return null;
		} catch (Exception e) {
			return e.toString();
		}

	}

	@Override
	public String updateSendfile(DtVo v, ObjVo objVo) throws GlobalException {
		try {
			Dt p = iDtDao.findById(v.getId());
			p.setSendCt(v.getSendCt());
			p.setSendId(v.getSendId());
			p.setSendName(v.getSendName());
			p.setSendTime(v.getSendTime());
			p.setStatus(EunmTask.TASK_END.getStatus());
			p.setUserIds(v.getUserIds());
			p.setUserNames(v.getUserNames());
			iDtDao.update(p);
			Progress pg = progressDao.findByBusId(p.getId());
			progressDao.update(pg.getId(), EunmTask.DT_CK.getStatus(), null, null, p.getUserIds(), p.getUserNames());
			progressLogDao.addApp(p.getId(), p.getId(), EunmTask.DT_XF.getStatus(), v.getIsCommit(), p.getSendCt(),
					objVo);
			return null;
		} catch (Exception e) {
			return e.toString();
		}

	}

	public void uptFile(Dt p) {
		Document doc = new Document();
		doc.setCategory(p.getCategory());
		doc.setType(p.getType());
		doc.setTitle(p.getTitle());
		doc.setName(p.getTitle());
		doc.setTime(DateUtils.getCurrDateTimeStr());
		doc.setPath(p.getPath());
		doc.setRelativePath(p.getFilePath());
		doc.setIsAllVisible(Constants.F);
		doc.setOriginalSize(p.getOriginalSize());
		doc.setSize(p.getSize());
		doc.setSign(null);
		doc.setState("现行");
		documentDao.add(doc);
	}

	public void commit(Dt p, DtVo v) {
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			Progress pg = progressDao.findByBusId(p.getId());
			progressDao.update(pg.getId(), EunmTask.DT_QF.getStatus(), null, null, p.getAuditId(), p.getAuditName());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.DT_SQ.getStatus(), v.getIsCommit(), p.getRemark(),
					p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
			p.setStatus(pg.getStatus());
		}
		iDtDao.update(p);
	}

	/**
	 * 生成编号
	 */
	public String createCode() {
		String flag = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		String hql = "SELECT max(code) FROM " + iDtDao.getEntityName(Dt.class) + " WHERE isDel=" + Po.N
				+ " AND code like '" + flag + "%'";
		String ls = (String) iDtDao.query(hql).getSingleResult();
		String no = flag;
		if (ls == null) {
			no += "01";
		} else {
			ls = ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort = 0;
			}
			sort++;
			if (sort < 10) {
				no += "0" + sort;
			} else {
				no += sort;
			}
		}
		return no;
	}

	@Override
	public JSONArray userList() throws GlobalException {
		JSONArray arr = new JSONArray();
		List<Account> userList = accountDao.list();
		for (Account user : userList) {
			JSONObject obj = new JSONObject();
			obj.put("id", user.getId());
			obj.put("name", user.getUser().getName());
			obj.put("org", user.getOrg().getName());
			obj.put("avatar", "static/upload/" + user.getUser().getAvatar());
			arr.add(obj);
		}
		return arr;
	}

	@Override
	public String saveorupdateFile(String appFileId, String title, String code, String appFileName, String source,
			String dj, String path, String auditId, String auditName, String date, String content, String remark,
			String userId, String status, String filePath, String type, String categoryId, ObjVo objVo)
			throws GlobalException {
		Dt po;
		po = new Dt();
		po.setCode(code);
		po.setTitle(title);
		po.setSource(source);
		po.setDj(dj);
		po.setPath(path);
		po.setAuditId(auditId);
		po.setAuditName(auditName);
		po.setDate(date);
		po.setContent(content);
		po.setUserId(objVo.getUserId());
		po.setUserName(objVo.getName());
		po.setStatus(status);
		po.setFileName(appFileName);
		po.setRemark(remark);
		po.setFilePath(filePath);
		po.setType(type);
		po.setDeptId(objVo.getOrgId());
		po.setDeptName(objVo.getOrgName());
		po.setOrgId(objVo.getOrgId());
		po.setOrgName(objVo.getOrgName());
		po.setCategory(categoryDao.findById(categoryId));
		if (appFileId == null || appFileId.equals("") || appFileId.equals("null")) {
			try {
				iDtDao.add(po);
				Progress progress = new Progress();
				progress.setBusId(po.getId());
				progress.setStatus(EunmTask.DT_SQ.getStatus());
				progress.setOrgId(objVo.getOrgId());
				progress.setOrgName(objVo.getOrgName());
				progress.setUserId(objVo.getUserId());
				progress.setUserId(objVo.getName());
				progressDao.add(progress);
				return null;
			} catch (Exception e) {
				return e.toString();
			}
		} else {
			po = iDtDao.findById(appFileId);
			po.setCode(code);
			po.setTitle(title);
			po.setSource(source);
			po.setDj(dj);
			po.setPath(path);
			po.setAuditId(auditId);
			po.setAuditName(auditName);
			po.setContent(content);
			po.setDate(date);
			po.setStatus(status);
			po.setFileName(appFileName);
			po.setRemark(remark);
			po.setFilePath(filePath);
			po.setType(type);
			po.setCategory(categoryDao.findById(categoryId));
			try {
				po.setId(appFileId);
				iDtDao.update(po);
				return null;
			} catch (Exception e) {
				 
				return e.toString();
			}

		}

	}

	@Override
	public String updatefileView(DtVo v, ObjVo objVo) throws GlobalException {

		String sql = "SELECT id FROM  v_office_dt  WHERE is_del=" + Po.N + " AND view_ed_ids like '%"
				+ objVo.getUserId() + "%' and id = '" + v.getId() + "'";
	 
		List list = iDtDao.queryBySql(sql);

		if (list.size() < 1) {
			Dt p = iDtDao.findById(v.getId());
			String oldViewEdIds = p.getViewEdIds();
			String oleViewEdNames = p.getViewEdNames();
			if (oldViewEdIds == null || oldViewEdIds.equals("") || oldViewEdIds.equals("null")) {
				p.setViewEdIds(objVo.getUserId());
				p.setViewEdNames(objVo.getName());
			} else {
				p.setViewEdIds(oldViewEdIds + "," + objVo.getUserId());
				p.setViewEdNames(oleViewEdNames + "," + objVo.getName());
			}

			iDtDao.update(p);
		}
		return null;
	}

	@Override
	public List<AppFile> selectList(ObjVo v, String selectvalue, int type) throws GlobalException {

		StringBuffer hql = new StringBuffer("FROM " + iDtDao.getEntityName(Dt.class) + " WHERE isDel='" + Po.N + "' ");

		// 1: 登记列表 2:签发列表3:下发 列表4：查看列表
		String auditId = v.getUserId();
		switch (type) {
		case 1:
			hql.append("and user_id='" + v.getUserId() + "' and( code like '%" + selectvalue + "%' or title like '%"
					+ selectvalue + "%' ) ");
			break;
		case 2:
			hql.append("and audit_id = '" + auditId + "' and status!='" + EunmTask.DT_SQ.getStatus()
					+ "'  and( code like '%" + selectvalue + "%' or title like '%" + selectvalue + "%') ");
			break;
		case 3:
			hql.append("and send_id='" + auditId + "'and status!='" + EunmTask.DT_SQ.getStatus() + "' and status!='"
					+ EunmTask.DT_QF.getStatus() + "' and (code like '%" + selectvalue + "%' or title like '%"
					+ selectvalue + "%')");
			break;
		case 4:
			hql.append("and user_ids like'%" + auditId + "%'and status='" + "finish" + "'or status='"
					+ EunmTask.DT_CK.getStatus() + "' and( code like '%" + selectvalue + "%' or title like '%"
					+ selectvalue + "%') ");
			break;
		}

		hql.append(" ORDER BY lastUpdTime DESC");
	 
		Query query = iDtDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		@SuppressWarnings("unchecked")
		List<Dt> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppFile> outList = new ArrayList<>();
		if (null != list) {
			for (Dt dt : list) {
				AppFile filelist = new AppFile();
				filelist.setId(dt.getId());
				filelist.setCode(dt.getCode());
				filelist.setTitle(dt.getTitle());
				filelist.setDate(dt.getDate());
				filelist.setStatus(dt.getStatus());
				filelist.setStatusChina(EunmTask.getName(dt.getStatus()));
				outList.add(filelist);
			}
		}
		return outList;
	}

	/**
	 * 文件流转文件上传
	 */
	@Override
	public JSONArray uploadFile(MultipartHttpServletRequest multiRequest, Object temppath, String twoFilePath)
			throws GlobalException {
		String UPLOAD_DIR = ApplicationUtils.getValue("config.upload.path").toString().replace("\\", "/");
		JSONArray arr = new JSONArray();
		Iterator<String> iterator = multiRequest.getFileNames();
		while (iterator.hasNext()) {
			// 一次遍历所有文件
			MultipartFile file = multiRequest.getFile(iterator.next().toString());
			if (file != null) {
				String myFileName = file.getOriginalFilename(); // 取得当前上传文件的文件名称
				if (myFileName.trim() != "") { // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					String suffix = myFileName.substring(myFileName.lastIndexOf("."));
					String fileName = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay()
							+ new Date().getTime() + suffix;
					String subPath = UPLOAD_DIR + twoFilePath + File.separator + fileName;
					subPath = subPath.replace("\\", "/");
					File targetFile = new File(temppath + UPLOAD_DIR + twoFilePath + File.separator);
					if (!targetFile.exists()) // 检测目录
						targetFile.mkdirs();
					File tempFile = new File(targetFile.getPath() + File.separator + fileName);
					try {
						file.transferTo(tempFile);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
					FilesVo fileVo = new FilesVo();
					fileVo.setBusType(twoFilePath);
					fileVo.setFileName(myFileName);
					fileVo.setFilePath(subPath);
					fileVo.setFileType(suffix);

					JSONObject obj = new JSONObject();
					obj.put("filePath", subPath);
					obj.put("fileName", myFileName);
					obj.put("type", suffix);
					arr.add(obj);

				}
			}
		}

		return arr;
	}

}

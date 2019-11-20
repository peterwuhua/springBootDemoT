package cn.demi.res.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.User;
import cn.demi.res.dao.ICertificateDao;
import cn.demi.res.po.Certificate;
import cn.demi.res.service.ICertificateService;
import cn.demi.res.vo.CertificateVo;

@Service("res.certificateService")
public class CertificateServiceImpl extends BaseServiceImpl<CertificateVo,Certificate> implements
		ICertificateService {
	@Autowired
	private ICertificateDao certificateDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IUserDao userDao;
	@Override
	public void data2Vo(String[] values, CertificateVo v, String param) {
		v.setUserName(values[1]);
		v.setType(values[2]);
		v.setName(values[3]);
		v.setCode(values[4]);
		v.setDuty(values[5]);
		v.setUnit(values[6]);
		v.setReleaseDate(values[7]);
		v.setStartTime(values[8]);
		v.setEndTime(values[9]);
		v.setValidDate(values[10]);
		v.setRemark(values[11]);
	}

	@Override
	public void removeFile(CertificateVo v) throws GlobalException {
		Certificate p = certificateDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.setFileName(null);
		p.setFilePath(null);
		certificateDao.update(p);
	}

	@Override
	public void importData(CertificateVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)){
			for(Certificate p:certificateDao.list()) {
				p.setIsDel(Po.Y);
				certificateDao.update(p);
			}
		}
		CertificateVo vo = null;
		for (int i = 4, j = dataArrays.length; i < j; i++) {
			if (dataArrays[i].length == 0)
				continue;
				
			String[] values = dataArrays[i];
			if(null==values[0] || "".equals(values[0].trim())){
				log.info("第 "+ i +"行无效数据不做导入");
				continue;
			}
			
			vo = v.instance();
			try {
				vo.setSort(Integer.valueOf(values[0]));
			} catch (Exception e) {
				vo.setSort(i);
			}
			// 数组数据对应至对象
			data2Vo(values, vo, param);
			add(vo);
		}
	}
	@Override
	public void add(CertificateVo v) throws GlobalException {
		Certificate p = vo2Po(v);
		User user = userDao.findById(p.getUserId());
		p.setOrgId(user.getOrgId());
		p.setOrgName(user.getOrgName());
		certificateDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void update(CertificateVo v) throws GlobalException {
		Certificate p = certificateDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		User user = userDao.findById(p.getUserId());
		p.setOrgId(user.getOrgId());
		p.setOrgName(user.getOrgName());
		certificateDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CertificateVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
			String orgId = v.getOrgId();
			if(StrUtils.isBlankOrNull(orgId)) {
				orgId=getCurrent().getOrgId();
			}
			List<String> ordIds=orgDao.listChild(orgId);
			ordIds.add(orgId);
			pageResult.addCondition(new QueryCondition("orgId in ('"+String.join("','", ordIds)+"')"));
		}
		pageResult = certificateDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Certificate>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	 
}

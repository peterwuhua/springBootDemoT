package cn.demi.app.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.res.service.ApparaService;
import cn.demi.app.res.vo.AppApparain;
import cn.demi.app.res.vo.AppApparaout;
import cn.demi.app.res.vo.AppAppare;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.dao.IApparaInDao;
import cn.demi.res.dao.IApparaOutDao;
import cn.demi.res.po.Appara;
import cn.demi.res.po.ApparaIn;
import cn.demi.res.po.ApparaOut;
import cn.demi.res.po.Reagent;


@Service("app.apparaService")
public class AppApparaServiceimpl implements ApparaService {

	@Autowired
	private  IApparaDao apparadao;
	
	@Autowired
	private IApparaOutDao apparaOUtDao;
	
	@Autowired
	private IApparaInDao apparaindao;
	
	 
	@Override
	public List<AppAppare> apparalist(ObjVo v) throws GlobalException {
		  StringBuffer hql = new StringBuffer(
	                "FROM " + apparadao.getEntityName(Appara.class) + " WHERE isDel= " + Po.N);
	        hql.append(" ORDER BY lastUpdTime desc");
	        Query query = apparadao.query(hql.toString());
	        int page = v.getPage();
	        if (page < 1) {
	            page = 1;
	        }
	        @SuppressWarnings("unchecked")
	        List<Appara> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
	        List<AppAppare> outList = new ArrayList<>();
	        if (null != list) {
	            for (Appara appara : list) {
	            	AppAppare appAppare = new AppAppare();
	            	appAppare.setId(appara.getId());
	            	appAppare.setNo(appara.getNo());
	            	appAppare.setName(appara.getName());
	            	appAppare.setSpec(appara.getSpec());
	            	appAppare.setCode(appara.getCode());
	            	appAppare.setDeptId(appara.getDeptId());
	            	appAppare.setDeptName(appara.getDeptName());
	            	appAppare.setVerificationUnit(appara.getVerificationUnit());
	            	appAppare.setVerificationCycle(appara.getVerificationCycle());
	            	appAppare.setVerificationDate(appara.getVerificationDate());
	            	appAppare.setComment(appara.getComment());
	            	appAppare.setState(appara.getState());
	                outList.add(appAppare);
	            }
	        }
	        return outList;
	}

	@Override
	public AppAppare findappare(String id) throws GlobalException {
		Appara appara = apparadao.findById(id);
		AppAppare appAppare = new AppAppare();
		appAppare.setId(appara.getId());
    	appAppare.setNo(appara.getNo());
    	appAppare.setName(appara.getName());
    	appAppare.setSpec(appara.getSpec());
    	appAppare.setCode(appara.getCode());
    	appAppare.setDeptId(appara.getDeptId());
    	appAppare.setDeptName(appara.getDeptName());
    	appAppare.setVerificationUnit(appara.getVerificationUnit());
    	appAppare.setVerificationCycle(appara.getVerificationCycle());
    	appAppare.setVerificationDate(appara.getVerificationDate());
    	appAppare.setComment(appara.getComment());
    	appAppare.setState(appara.getState());
    	appAppare.setMeasureRange(appara.getMeasureRange());
    	appAppare.setExt01(appara.getExt01());
    	appAppare.setProducer(appara.getProducer());
    	appAppare.setProductDate(appara.getProductDate());
    	appAppare.setProducerPhone(appara.getProducerPhone());
    	appAppare.setPurTime(appara.getPurTime());
    	appAppare.setLastverificationDate(appara.getLastverificationDate());
    	appAppare.setEffectDate(appara.getEffectDate());
    	appAppare.setFileId(appara.getFileId());
    	appAppare.setFileName(appara.getFileName());
    	appAppare.setFilePath(appara.getFilePath());
    	appAppare.setKeepId(appara.getKeepId());
    	appAppare.setKeeper(appara.getKeeper());
    	appAppare.setLogo(appara.getLogo());
    	appAppare.setAccuracy(appara.getAccuracy());
    	appAppare.setVerificationResult(appara.getVerificationResult());
    	appAppare.setLastcalibrationDate(appara.getLastcalibrationDate());
		return appAppare;
	}
	
	@Override
	public List<AppAppare> apparajdjclist(ObjVo v) throws GlobalException {
		  StringBuffer hql = new StringBuffer(
	                "FROM " + apparadao.getEntityName(Appara.class) + " WHERE isDel= " + Po.N);
	        hql.append(" ORDER BY lastUpdTime desc");
	        Query query = apparadao.query(hql.toString());
	        int page = v.getPage();
	        if (page < 1) {
	            page = 1;
	        }
	        @SuppressWarnings("unchecked")
	        List<Appara> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
	        List<AppAppare> outList = new ArrayList<>();
	        if (null != list) {
	            for (Appara appara : list) {
	            	AppAppare appAppare = new AppAppare();
	            	
	            	appAppare.setNo(appara.getNo());
	            	appAppare.setName(appara.getName());
	            	appAppare.setSpec(appara.getSpec());
	            	appAppare.setCode(appara.getCode());
	            	appAppare.setDeptId(appara.getDeptId());
	            	appAppare.setDeptName(appara.getDeptName());
	            	appAppare.setKeeper(appara.getKeeper());
	            	appAppare.setKeepId(appara.getKeepId());
	            	appAppare.setLastverificationDate(appara.getLastverificationDate());
	            	appAppare.setLastcalibrationDate(appara.getLastcalibrationDate());
	                outList.add(appAppare);
	            }
	        }
	        return outList;
	}

	@Override
	public List<AppApparaout> apparaoutlist(ObjVo v) throws GlobalException {
		 StringBuffer hql = new StringBuffer(
	                "FROM " + apparaOUtDao.getEntityName(ApparaOut.class) + " WHERE isDel= " + Po.N);
	        hql.append(" ORDER BY lastUpdTime desc");
	        Query query = apparadao.query(hql.toString());
	        int page = v.getPage();
	        if (page < 1) {
	            page = 1;
	        }
	        @SuppressWarnings("unchecked")
	        List<ApparaOut> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
	        List<AppApparaout> outList = new ArrayList<>();
	        if (null != list) {
	            for (ApparaOut apparaOut : list) {
	            	AppApparaout appApparaout = new AppApparaout();
	            	appApparaout.setAppara(apparaOut.getAppara());
	            	appApparaout.setUseName(apparaOut.getUserName());
	            	appApparaout.setUseTime(apparaOut.getUseTime());
	            	appApparaout.setAppStatus(apparaOut.getAppStatus());
	            	appApparaout.setStatus(appApparaout.getStatus());
	                outList.add(appApparaout);
	            }
	        }
	        return outList;
	}

	@Override
	public List<AppApparain> apparainlist(ObjVo v) throws GlobalException {
		 StringBuffer hql = new StringBuffer(
	                "FROM " + apparaindao.getEntityName(Appara.class) + " WHERE isDel= " + Po.N);
	        hql.append(" ORDER BY lastUpdTime desc");
	        Query query = apparadao.query(hql.toString());
	        int page = v.getPage();
	        if (page < 1) {
	            page = 1;
	        }
	        @SuppressWarnings("unchecked")
	        List<ApparaIn> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
	        List<AppApparain> outList = new ArrayList<>();
	        if (null != list) {
	            for (ApparaIn apparaIn : list) {
	            	AppApparain appApparain = new AppApparain();
	            	appApparain.setAppara(apparaIn.getAppara());
	            	appApparain.setUseName(apparaIn.getUserName());
	            	appApparain.setUseTime(apparaIn.getUseTime());
	            	appApparain.setDuration(apparaIn.getDuration());
	            	appApparain.setAppStatus(apparaIn.getAppStatus());
	            	appApparain.setStatus(apparaIn.getStatus());
	                outList.add(appApparain);
	            }
	        }
	        return outList;
	}
	
	

}

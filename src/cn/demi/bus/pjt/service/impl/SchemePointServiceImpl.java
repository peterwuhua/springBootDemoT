package cn.demi.bus.pjt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.service.ISchemePointService;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.cus.customer.dao.IClientPointDao;
import cn.demi.cus.customer.po.ClientPoint;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.std.dao.IItemDao;

@Service("bus.schemePointService")
public class SchemePointServiceImpl extends BaseServiceImpl<SchemePointVo,SchemePoint> implements
		ISchemePointService {
	@Autowired
	private ISchemePointDao schemePointDao;
	@Autowired
	private ISchemeDao schemeDao;
	@Autowired
	private IClientPointDao clientPointDao;
//	@Autowired
//	private ISampTypeDao sampTypeDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private IImDao imDao;
//	@Autowired
//	private IMethodDao methodDao;
	@Override
	public List<SchemePointVo> listBySchemeId(String schemeId) throws GlobalException {
		String jpql="FROM "+schemePointDao.getEntityName(SchemePoint.class)+" WHERE isDel='"+Po.N+"' AND scheme.id ='"+schemeId+"' ORDER BY sort ASC";
		List<SchemePoint> detailList=schemePointDao.list(jpql);
		return toVoList(detailList);
	}
	@Override
	public List<SchemePointVo> list(SchemePointVo v) throws GlobalException {
		StringBuffer jpql=new StringBuffer("FROM "+schemePointDao.getEntityName(SchemePoint.class)+" WHERE isDel='"+Po.N+"' ");
		if(null!=v.getSchemeVo()&&!StrUtils.isBlankOrNull(v.getSchemeVo().getId())) {
			jpql.append(" AND scheme.id ='"+v.getSchemeVo().getId()+"'");
		}
		jpql.append(" ORDER BY sort ASC");
		List<SchemePoint> detailList=schemePointDao.list(jpql.toString());
		return toVoList(detailList);
	}
	@Override
	public void addSp(String schemeId, String projectId,String pointIds) throws GlobalException {
		if(!StrUtils.isBlankOrNull(schemeId)) {
			Scheme scheme=schemeDao.findById(schemeId);
			int sort=schemePointDao.findMaxSort(scheme.getId());
			List<ClientPoint> cpList=clientPointDao.listByIds(pointIds);
			if(null!=cpList&&cpList.size()>0) {//弹窗选点位
				for (ClientPoint cp : cpList) {
					SchemePoint sp=new SchemePoint();
					sp.setProjectId(scheme.getProject().getId());
					sp.setScheme(scheme);
					sp.setSort(sort+1);
					sp.setSampName(cp.getSampName());
					sp.setPc(1);
					sp.setPcUnit(PcUnit.C.getName());
					sp.setPointName(cp.getName());
					sp.setPointCode(cp.getCode());
					sp.setPointType(cp.getType());
					sp.setItemId(cp.getItemId());
					sp.setItemName(cp.getItemName());
					if(!StrUtils.isBlankOrNull(cp.getItemId())) {
						sp.setFxPrice(itemDao.getSumPrice(cp.getItemId()));
					}
					if(!scheme.getProject().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
						sp.setPc(3);
						sp.setPcUnit(PcUnit.CT.getName());
						sp.setRoom(cp.getRoom());
						sp.setCyType(Constants.CY_DD);
						sp.setCyHours(15);
					}else {
						sp.setSampTypeId(cp.getSampTypeId());
						sp.setSampTypeName(cp.getSampTypeName());
					}
					schemePointDao.add(sp);
					if(!StrUtils.isBlankOrNull(cp.getImId())&&!cp.getImId().equals("null")) {
						imDao.uptIm(sp.getId(), cp.getImId());
					}
				}
			}else {//手动添加
				SchemePoint sp=new SchemePoint();
				sp.setProjectId(scheme.getProject().getId());
				sp.setScheme(scheme);
				sp.setSort(sort+1);
				sp.setPc(1);
				sp.setPcUnit(PcUnit.C.getName());
				if(!scheme.getProject().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					sp.setPc(3);
					sp.setPcUnit(PcUnit.CT.getName());
					sp.setCyType(Constants.CY_DD);
					sp.setCyHours(15);
				}
				schemePointDao.add(sp);
			}
		}else if(!StrUtils.isBlankOrNull(projectId)&&!StrUtils.isBlankOrNull(pointIds)) {//批量添加点位
			List<Scheme> schList=schemeDao.listByPjId(projectId);
			List<ClientPoint> cpList=clientPointDao.listByIds(pointIds);
			if(null!=schList&&null!=cpList) {
				for (Scheme scheme : schList) {
					int sort=schemePointDao.findMaxSort(scheme.getId());
					for (ClientPoint cp : cpList) {
						SchemePoint sp=new SchemePoint();
						sp.setProjectId(projectId);
						sp.setScheme(scheme);
						sp.setSort(sort+1);
						sp.setSampName(cp.getSampName());
						sp.setPc(1);
						sp.setPcUnit(PcUnit.C.getName());
						sp.setPointName(cp.getName());
						sp.setPointCode(cp.getCode());
						sp.setPointType(cp.getType());
						sp.setItemId(cp.getItemId());
						sp.setItemName(cp.getItemName());
						if(!StrUtils.isBlankOrNull(cp.getItemId())) {
							sp.setFxPrice(itemDao.getSumPrice(cp.getItemId()));
						}
						if(!scheme.getProject().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
							sp.setPc(3);
							sp.setPcUnit(PcUnit.CT.getName());
							sp.setRoom(cp.getRoom());
							sp.setCyType(Constants.CY_DD);
							sp.setCyHours(15);
						}else {
							sp.setSampTypeId(cp.getSampTypeId());
							sp.setSampTypeName(cp.getSampTypeName());
						}
						schemePointDao.add(sp);
						if(!StrUtils.isBlankOrNull(cp.getImId())&&!cp.getImId().equals("null")) {
							imDao.uptIm(sp.getId(), cp.getImId());
						}
					}
				}
			}
		}
		
	}
	@Override
	public void delete(String... ids) throws GlobalException {
		List<SchemePoint> spList=schemePointDao.listByIds(ids);
		for (SchemePoint sp : spList) {
			imDao.deleteByBusId(sp.getId());
			schemePointDao.delete(sp);
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
}

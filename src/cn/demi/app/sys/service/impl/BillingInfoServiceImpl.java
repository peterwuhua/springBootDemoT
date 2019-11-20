package cn.demi.app.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.dao.IBillingInfoDao;
import cn.demi.app.sys.po.BillingInfo;
import cn.demi.app.sys.service.IBillingInfoService;
import cn.demi.app.sys.vo.AppBillingInfoVo;
import cn.demi.app.sys.vo.BillingInfoVo;
import cn.demi.app.sys.vo.ObjVo;

@Service("office.billingInfoService")
public class BillingInfoServiceImpl extends BaseServiceImpl<BillingInfoVo, BillingInfo> implements IBillingInfoService {

	@Autowired
	private IBillingInfoDao billingInfoDao;

	@Override
	public List<AppBillingInfoVo> listByUserId(String userId) {
		StringBuffer hql = new StringBuffer(
				"select id,companyName,tfn,companyAddress,tel,depositBank,depositNum,userId,userName FROM "
						+ billingInfoDao.getEntityName(BillingInfo.class) + " WHERE isDel= " + Po.N);
		hql.append(" and userId='" + userId + "'");
		hql.append(" ORDER BY lastUpdTime desc");
		List list = billingInfoDao.query(hql.toString()).getResultList();
		List<AppBillingInfoVo> outList = new ArrayList<>();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			AppBillingInfoVo vo = new AppBillingInfoVo();
			vo.setId(objects[0] == null ? "" : objects[0].toString());
			vo.setCompanyName(objects[1] == null ? "" : objects[1].toString());
			vo.setTfn(objects[2] == null ? "" : objects[2].toString());
			vo.setCompanyAddress(objects[3] == null ? "" : objects[3].toString());
			vo.setTel(objects[4] == null ? "" : objects[4].toString());
			vo.setDepositBank(objects[5] == null ? "" : objects[5].toString());
			vo.setDepositNum(objects[6] == null ? "" : objects[6].toString());
			vo.setUserId(objects[7] == null ? "" : objects[7].toString());
			vo.setUserName(objects[8] == null ? "" : objects[8].toString());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public String addBillingInfo(BillingInfoVo v, ObjVo objVo) throws GlobalException {
		// TODO Auto-generated method stub
		BillingInfo p = new BillingInfo();
		p.setTfn(v.getTfn());
		p.setCompanyAddress(v.getCompanyAddress());
		p.setCompanyName(v.getCompanyName());
		p.setDepositBank(v.getDepositBank());
		p.setDepositNum(v.getDepositNum());
		p.setTel(v.getTel());
		p.setUserId(objVo.getUserId());
		p.setUserName(objVo.getName());
		try {
			billingInfoDao.add(p);
			return null;
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public String updateBillingInfo(BillingInfoVo v) throws GlobalException {
		BillingInfo po = billingInfoDao.findById(v.getId());
		po.setCompanyAddress(v.getCompanyAddress());
		po.setCompanyName(v.getCompanyName());
		po.setTfn(v.getTfn());
		po.setTel(v.getTel());
		po.setDepositBank(v.getDepositBank());
		po.setDepositNum(v.getDepositNum());
		po.setIsDel(Integer.parseInt(v.getIsDel()));
		try {
			billingInfoDao.update(po);
			return null;
		} catch (Exception e) {
			return e.toString();
		}

	}
}

package cn.demi.app.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.service.IAppSysMsgService;
import cn.demi.app.sys.vo.AppSysMsgVo;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.ISysMsgDao;
import cn.demi.base.system.po.SysMsg;
import cn.demi.base.system.vo.SysMsgVo;

@Service("app.msgService")
public class AppSysMsgServiceImpl extends BaseServiceImpl<SysMsgVo, SysMsg> implements IAppSysMsgService {
	@Autowired
	private ISysMsgDao sysMsgDao;

	@Override
	public int getCount4Wd(ObjVo objVo) throws GlobalException {
		String jpql = "SELECT count(id) FROM v_sys_msg WHERE is_del='" + Po.N + "'  AND receiver_id='"
				+ objVo.getUserId() + "' ";
		jpql += " AND receiver_type='" + SysMsg.RT_USER + "'";
		jpql += " AND status='" + Po.N + "'";
		Object obj = sysMsgDao.queryBysql(jpql).getSingleResult();
		int n = 0;
		try {
			n = Integer.valueOf(obj.toString());
		} catch (NumberFormatException e) {
			n = 0;
		}
		return n;
	}

	@Override
	public List<AppSysMsgVo> list4Wd(ObjVo objVo) throws GlobalException {
		StringBuffer hql =  new StringBuffer( "FROM " + sysMsgDao.getEntityName(SysMsg.class) + " WHERE isDel='" + Po.N + "' ");
		hql.append(" AND receiverId='" + objVo.getUserId() + "' ")  ;
		hql.append( " AND receiverType='" + SysMsg.RT_USER + "'");
		hql.append(" ORDER BY  STATUS asc,createTime DESC  ");

		Query query = sysMsgDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<SysMsg> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows()).getResultList();
		List<AppSysMsgVo>  outList = new ArrayList<>();
		for (SysMsg p:list) {
			AppSysMsgVo vo = new AppSysMsgVo();
			vo.setId(p.getId());
			vo.setBusId(p.getBusId());
			vo.setBusType(p.getBusType());
			vo.setTitle(p.getTitle());
			vo.setContent(p.getContent());
			vo.setReceiver(p.getReceiver());
			vo.setReceiverId(p.getReceiverId());
			vo.setReceiverType(p.getReceiverType());
			vo.setStatus(p.getStatus());
			vo.setSendTime(p.getSendTime());
			vo.setSendId(p.getSendId());
			vo.setSendName(p.getSendName());
			vo.setRemark(p.getRemark());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public String updateMsg(SysMsgVo v) throws GlobalException {
		SysMsg po = sysMsgDao.findById(v.getId());
		po.setStatus(v.getStatus());
		try {
			sysMsgDao.update(po);
			return null;
		} catch (Exception e) {
			return e.toString();
		}

	}

}

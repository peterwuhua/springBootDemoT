package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.res.dao.IFbDao;
import cn.demi.res.po.Fb;
import cn.demi.res.service.IFbService;
import cn.demi.res.vo.FbObj;
import cn.demi.res.vo.FbVo;

@Service("res.fbService")
public class FbServiceImpl extends BaseServiceImpl<FbVo,Fb> implements
		IFbService {
	@Autowired
	private IFbDao fbDao;
	@Autowired
	private IAccountDao accountDao;
	@Override
	public void excutSchedule() throws GlobalException {
		String hql="FROM "+fbDao.getEntityName(Fb.class)+" WHERE isDel='"+Po.N+"' AND endDate is not null and endDate!='' and endDate<'"+DateUtils.getCurrDateStr()+"'";
		List<Fb> fbList=fbDao.list(hql);
		if(null!=fbList) {
			for (Fb fb : fbList) {
				try {
					if(!StrUtils.isBlankOrNull(fb.getUserId())) {
						OpenIM.pushMsg(Constants.SUADMIN_LOGIN, accountDao.findById(fb.getUserId()).getLoginName(),"【系统提示】你所负责的分包单位 : "+fb.getName()+" 资质已经到期，请及时跟进处理！");
					}
				} catch (Exception e) {
					log.error("定时任务：分包单位【"+fb.getName()+"】到期定时检查异常！",e);
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FbObj> list4Full(FbVo v) throws GlobalException {
		String sql="select fb.id,fb.name,fb.code "
				+ " FROM "+tablePrefix+fbDao.getEntityName(Fb.class)+" fb  WHERE fb.is_del='"+Po.N+"' ";
		if(StrUtils.isNotBlankOrNull(v.getName())) {
			sql+="AND fb.name like '%"+v.getName().trim()+"%' ";
		}
		sql+="order by fb.sort asc";
		List<Object[]> custList=fbDao.queryBySql(sql);
		List<FbObj> list=new ArrayList<FbObj>();
		if(null!=custList) {
			for (Object[] obj : custList) {
				FbObj vo=new FbObj();
				vo.setId(String.valueOf(obj[0]));
				vo.setName(String.valueOf(obj[1]));
				String code=String.valueOf(obj[2]);
				vo.setCode((code==null || code.equals("null"))?"":code);
				list.add(vo);
			}
		}
		return list;
	}
}

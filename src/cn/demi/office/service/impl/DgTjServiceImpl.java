package cn.demi.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.po.User;
import cn.demi.office.dao.IDgDao;
import cn.demi.office.dao.IDgTjDao;
import cn.demi.office.dao.IKqDao;
import cn.demi.office.po.Dg;
import cn.demi.office.po.DgTj;
import cn.demi.office.service.IDgTjService;
import cn.demi.office.vo.DgTjVo;

@Service("office.dgTjService")
public class DgTjServiceImpl extends BaseServiceImpl<DgTjVo,DgTj> implements
		IDgTjService {

	@Autowired
	private IDgTjDao dgTjDao;
	@Autowired
	private IDgDao dgDao;
	@Autowired
	private IKqDao kqDao;
	@Override
	public void excutSchedule() throws GlobalException {
		String curdate=DateUtils.getCurrDateStr();
		int n=DateUtils.getDayOfWeek(Integer.valueOf(curdate.substring(0, 4)), Integer.valueOf(curdate.substring(5,7)),Integer.valueOf(curdate.substring(8, 10)));
		if(n<=5) {//只计算工作日
			String sql="select u.id,u.name,u.no,u.org_id,u.org_name,min(dg.date),max(dg.date) from "+tablePrefix+dgDao.getEntityName(User.class)+" u";
			sql+=" left join "+tablePrefix+dgDao.getEntityName(Dg.class)+" dg on dg.user_id=u.id AND dg.date like '"+curdate+"%'";
			sql+=" where u.is_del='"+Po.N+"' ";
			sql+=" group by u.id order by u.sort asc";
			@SuppressWarnings("unchecked")
			List<Object[]> l=dgDao.queryBySql(sql);
			for (Object[] obj : l) {
				DgTj tj=new DgTj();
				String userName=String.valueOf(obj[1]);
				if(StrUtils.isBlankOrNull(userName)||userName.equals("null")||userName.equals("管理员")) {
					continue;
				}
				tj.setUserId(String.valueOf(obj[0]));
				tj.setUserName(String.valueOf(obj[1]));
				tj.setUserCode(String.valueOf(obj[2]));
				tj.setDeptId(String.valueOf(obj[3]));
				tj.setDeptName(String.valueOf(obj[4]));
				tj.setDate(curdate);
				String stime=String.valueOf(obj[5]);
				String etime=String.valueOf(obj[6]);
				if(StrUtils.isBlankOrNull(stime)||stime.equals("null")) {
					tj.setStime(null);
					tj.setEtime(null);
					String st=kqDao.findByUserId(tj.getUserId(),curdate);
					if(!StrUtils.isBlankOrNull(st)) {
						tj.setStatus(st);
					}else {
						tj.setStatus(DgTj.ST_0);
					}
				}else {
					tj.setStime(stime);
					tj.setEtime(etime);
					tj.setStatus(DgTj.ST_1);
				}
				dgTjDao.insertDgTj(tj);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DgTjVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		if(!StrUtils.isBlankOrNull(v.getUserName())) {
			pageResult.addCondition(new QueryCondition("userName", QueryCondition.AK, v.getUserName()));
		}
		if(!StrUtils.isBlankOrNull(v.getUserCode())) {
			pageResult.addCondition(new QueryCondition("userCode", QueryCondition.AK, v.getUserCode()));
		}
		if(!StrUtils.isBlankOrNull(v.getStartDate())&&!StrUtils.isBlankOrNull(v.getEndDate())){
			pageResult.addCondition(new QueryCondition("date>='"+v.getStartDate()+"'"));
			pageResult.addCondition(new QueryCondition("date<='"+v.getEndDate()+"'"));
		}else if(!StrUtils.isBlankOrNull(v.getStartDate())){
			pageResult.addCondition(new QueryCondition("date>='"+v.getStartDate()+"'"));
		}else if(!StrUtils.isBlankOrNull(v.getEndDate())){
			pageResult.addCondition(new QueryCondition("dat<='"+v.getEndDate()+"'"));
		}
		pageResult = dgTjDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<DgTj>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}

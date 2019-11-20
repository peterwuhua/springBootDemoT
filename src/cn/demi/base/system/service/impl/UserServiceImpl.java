package cn.demi.base.system.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.po.User;
import cn.demi.base.system.service.IUserService;
import cn.demi.base.system.vo.UserVo;
import cn.demi.cus.customer.vo.Saler;

/**
 * <strong>Create on : 2016年11月2日 下午5:13:25 </strong> <br>
 * <strong>Description : UserServiceImpl </strong> <br>
 * 
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.userService")
public class UserServiceImpl extends BaseServiceImpl<UserVo, User> implements IUserService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public void update2del(String... ids) throws GlobalException {
		super.update2del(ids);
		List<Account> accountList = accountDao.listByUserIds(ids);
		for (Account account : accountList) {
			account.setIsDel(Po.Y);
			accountDao.update(account);
		}
		// 人员删除帐户同时删除
		/*
		 * for(String id : toString(ids).split(",")){ List<QueryCondition>
		 * queryCondition = new ArrayList<QueryCondition>(); queryCondition.add(new
		 * QueryCondition("user.id",QueryCondition.EQ,id)); List<Account> accountList =
		 * accountDao.list(queryCondition); for(Account account : accountList ){
		 * account.setIsDel(Account.Y); accountDao.update(account); } }
		 */
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:00:10 </strong> <br>
	 * <strong>Description : 数组转字符串</strong> <br>
	 * 
	 * @param ids
	 * @return String
	 */
	public String toString(String... ids) {
		String str = "";
		for (int i = 0, k = ids.length; i < k; i++) {
			if (0 != i)
				str += ",";
			str += ids[i];
		}
		return str;
	}

	@Override
	public void update4Grid(UserVo v) throws GlobalException {
		User p = userDao.findById(v.getId());
		p.setName(v.getName());
		p.setNo(v.getNo());
		p.setCredentialsNo(v.getCredentialsNo());
		p.setEmail(v.getEmail());
		p.setSex(v.getSex());
		p.setMobile(v.getMobile());
		p.setTelephone(v.getTelephone());
		p.setSort(v.getSort());
		userDao.update(p);
	}

	@Override
	public void update(UserVo v) throws GlobalException {
		User p = userDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if (!StrUtils.isBlankOrNull(v.getOrgId())) {
			List<Org> orgList = orgDao.listByIds(v.getOrgId());
			List<String> name = new ArrayList<String>();
			for (Org o : orgList) {
				if (o.getParent() != null && o.getParent().getParent() != null) {
					name.add(o.getParent().getName() + "/" + o.getName());
				} else {
					name.add(o.getName());
				}
			}
			p.setOrgName(String.join("，", name));
		}
		userDao.update(p);
	}

	@Override
	public void add(UserVo v) throws GlobalException {
		User p = vo2Po(v);
		if (!StrUtils.isBlankOrNull(v.getOrgId())) {
			List<Org> orgList = orgDao.listByIds(v.getOrgId());
			List<String> name = new ArrayList<String>();
			for (Org o : orgList) {
				if (o.getParent() != null && o.getParent().getParent() != null) {
					name.add(o.getParent().getName() + "/" + o.getName());
				} else {
					name.add(o.getName());
				}
			}
			p.setOrgName(String.join("，", name));
		}
		p.setTheme("skin-1");
		userDao.add(p);
		v.setId(p.getId());
	}

	@Override
	public void add4Grid(UserVo v) throws GlobalException {
		add(v);
	}

	@Override
	public void updateAvatar(String id, String path) throws GlobalException {
		User p = userDao.findById(id);
		p.setAvatar(path);
		userDao.update(p);
	}

	@Override
	public UserVo instanceVo(String[] values, UserVo v, String type, String param) throws GlobalException {
		v = super.instanceVo(values, v, type, param);
		List<QueryCondition> queryList = new ArrayList<>();
		List<User> userList = null;
		if (MATCHING_ADD.equals(StringUtils.trim(type))) {
			queryList.add(new QueryCondition("name", QueryCondition.EQ, StringUtils.trim(values[2])));
			userList = userDao.list(queryList);
		}
		if (CollectionUtils.isEmpty(userList) || userList.size() > 1) {
			return v;
		} else {
			return po2Vo(userList.get(0));
		}
	}

	@Override
	public void data2Vo(String[] values, UserVo v, String param) throws GlobalException {
		v.setNo(values[1]);
		v.setName(values[2]);
		v.setDuty(values[3]);
		v.setTechTitle(values[4]);// 职称
		v.setMajorSkill(values[5]);// 专业技术
		if (DateUtils.checkDateFormat(values[6].replace("/", "-").replace(".", "-"))) {
			v.setWorkDate(values[6]);// 到岗日期
		} else {
			log.info(values[6] + "-日期格式错误：" + values[6]);
		}
		if (DateUtils.checkDateFormat(values[7].replace("/", "-").replace(".", "-"))) {
			v.setRegularDate(values[7]);// 转正日期
		} else {
			log.info(values[7] + "-日期格式错误：" + values[7]);
		}
		if (DateUtils.checkDateFormat(values[8].replace("/", "-").replace(".", "-"))) {
			v.setWorkYear(values[8]);// 工作满一年时间
		} else {
			log.info(values[8] + "-日期格式错误：" + values[8]);
		}
		v.setPostSubsidy(values[9]);// 岗位津贴
		v.setConDeadline(values[10]);// 合同期限
		v.setCredentialsType(values[11]);// 证件类型
		v.setCredentialsNo(values[12]);// 证件编号
		v.setResidenceType(values[13]);// 户口性质
		v.setSex(values[14]);// 用户性别
		if (DateUtils.checkDateFormat(values[15].replace("/", "-").replace(".", "-"))) {
			v.setBirthday(values[15]);// 用户生日
		} else {
			log.info(values[15] + "-日期格式错误：" + values[15]);
		}
		v.setMobile(values[16]);// 手机
		v.setTelephone(values[17]);// 电话
		v.setEmail(values[18]);// 邮箱
		v.setAddress(values[19]);// 住址
		v.setNation(values[20]);// 名族
		v.setGraduationSchool(values[21]);// 毕业学校
		if (DateUtils.checkDateFormat(values[22].replace("/", "-").replace(".", "-"))) {
			v.setGraduationDate(values[22]);// 毕业时间
		} else {
			log.info(values[22] + "-日期格式错误：" + values[22]);
		}
		v.setEducation(values[23]);// 学历
		v.setProfession(values[24]);// 专业
		v.setRemark(values[25]);// 备注
		// 备注
		//
	}

	@Override
	public List<UserVo> list(GridVo gridVo, UserVo v) throws GlobalException {
		List<QueryCondition> conditions = getFilterRules(gridVo.getFilters());
		if (conditions == null)
			conditions = new ArrayList<>();
		conditions.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		List<User> list = userDao.list(conditions);
		List<UserVo> userList = new ArrayList<UserVo>();
		if (null != list) {
			for (User po : list) {
				UserVo vo = new UserVo();
				vo = vo.toVo(po);
				userList.add(vo);
			}
		}
		return userList;
	}

	@Override
	public void updateTheme(String id, String theme) throws GlobalException {
		User p = userDao.findById(id);
		p.setTheme(theme);
		userDao.update(p);
	}

	@Override
	public List<QueryCondition> toQueryList(UserVo v) throws GlobalException {
		List<QueryCondition> queryCondition = new ArrayList<QueryCondition>();
		if (!StrUtils.isBlankOrNull(v.getOrgId())) {
			String orgStr = "orgId like '%" + v.getOrgId() + "%'";
			List<String> ids = orgDao.listChild(v.getOrgId());
			for (String orgId : ids) {
				if (StrUtils.isNotBlankOrNull(orgId)) {
					orgStr += " or orgId like '%" + orgId + "%'";
				}
			}
			queryCondition.add(new QueryCondition(orgStr));
		}
		return queryCondition;
	}

	@Override
	public GridVo gridData(GridVo gridVo, UserVo v) throws GlobalException {
		return super.gridData(gridVo, v);
	}

	@Override
	public void saveUserInfo(UserVo v) throws GlobalException {
		User p = userDao.findById(v.getId());
		p.setEmail(v.getEmail());
		p.setMobile(v.getMobile());
		p.setPolitical(v.getPolitical());
		p.setEducation(v.getEducation());
		p.setGraduationSchool(v.getGraduationSchool());
		p.setProfession(v.getProfession());
		p.setAddress(v.getAddress());
		p.setMarriage(v.getMarriage());
		p.setUrgentUser(v.getUrgentUser());
		p.setUrgentPhone(v.getUrgentPhone());
		userDao.update(p);
	}

	/**
	 * 
	 * <p>
	 * Title: fetchSaleRler
	 * </p>
	 * <p>
	 * Description: 获取所有销售人员id,name, accountid
	 * </p>
	 * 
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.base.system.service.IRoleService#fetchSaleRler()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Saler> fetchSaleRler() throws GlobalException {
		String sql = "SELECT t1.id,t1.NAME,t0.id accountid FROM v_sys_account t0 INNER JOIN v_sys_user t1 ON t0.user_id = t1.id";
		Query query = userDao.queryBysql(sql);
		List<Saler> userList = new ArrayList<Saler>();
		List<Object> userList0 = query.getResultList();
		for (int i = 0; i < userList0.size(); i++) {
			Object[] user = (Object[]) userList0.get(i);
			String id = user[0].toString();
			String name = user[1].toString();
			String accountid = user[2].toString();
			Saler saler = new Saler();
			saler.setId(id);
			saler.setName(name);
			saler.setAccountId(accountid);
			userList.add(saler);
		}
		return userList;
	}

	/**
	 * 
	 * <p>
	 * Title: saleJLAuth
	 * </p>
	 * <p>
	 * Description:判断当前登录用户是销售经理还是销售人员
	 * </p>
	 * 
	 * @param accountId
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.base.system.service.IUserService#saleJLAuth(java.lang.String)
	 */
	@Override
	public String saleJLAuth(String accountId) throws GlobalException {
		String sql = "SELECT count(*) FROM v_sys_role t0 INNER JOIN v_sys_account_role t1 ON t0.id = t1.role_id INNER JOIN v_sys_account t2 ON t1.account_id = t2.id "
				+ " WHERE t0. CODE = 'XSJL' and t2.id='" + accountId + "'";
		Query query = userDao.queryBysql(sql);
		BigInteger count = (BigInteger) query.getSingleResult();
		String name = "";
		if (count.equals(BigInteger.ONE)) {
			name = UserVo.USER_AUTH_XSJL;
		} else {
			name = UserVo.USER_AUTH_XSRY;
		}
		return name;
	}
}

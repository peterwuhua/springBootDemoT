package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemUserDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.ItemUser;
import cn.demi.init.std.service.IItemUserService;
import cn.demi.init.std.vo.ItemUserVo;

@Service("init.itemUserService")
public class ItemUserServiceImpl extends BaseServiceImpl<ItemUserVo,ItemUser> implements
		IItemUserService {
	@Autowired
	private IAccountDao accountDao; 
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IItemUserDao itemUserDao;
	@Autowired
	private IItemDao itemDao;
	@Override
	public void add(ItemUserVo v) throws GlobalException {
		ItemUser p = vo2Po(v);
		p.setOrgName(orgDao.findById(v.getOrgId()).getName());
		Account at=accountDao.findById(v.getUserId());
		p.setUserName(at.getUser().getName());
		itemUserDao.add(p);
		v.setId(p.getId());
		//saveMethod(p, v);
		Item item=itemDao.findById(p.getItemId());
		String userIds=item.getUserIds();
		if(StrUtils.isBlankOrNull(userIds)) {
			item.setUserIds(at.getId());
			item.setUserNames(at.getUser().getName());
		}else if(!userIds.contains(at.getId())) {
			item.setUserIds(item.getUserIds()+","+at.getId());
			item.setUserNames(item.getUserNames()+","+at.getUser().getName());
		}
		itemDao.update(item);
	}
	public boolean  addData(ItemUserVo v) throws GlobalException {
		String hql="FROM "+itemUserDao.getEntityName(ItemUser.class)+" where itemId='"+v.getItemId()+"'";
		hql+=" AND orgId='"+v.getOrgId()+"' ";
		@SuppressWarnings("unchecked")
		List<ItemUser> pList = itemUserDao.query(hql).getResultList();
		if(pList!=null&&pList.size()>0) {
			return false;
		}
		ItemUser p = vo2Po(v);
		p.setOrgName(orgDao.findById(v.getOrgId()).getName());
		p.setUserName(accountDao.findById(v.getUserId()).getUser().getName());
		itemUserDao.add(p);
		v.setId(p.getId());
		//saveMethod(p, v);
		return true;
	}
	
	@Override
	public void update(ItemUserVo v) throws GlobalException {
		ItemUser p = itemUserDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		p.setOrgName(orgDao.findById(v.getOrgId()).getName());
		Account at=accountDao.findById(v.getUserId());
		p.setUserName(at.getUser().getName());
		itemUserDao.update(p);
		Item item=itemDao.findById(p.getItemId());
		String userIds=item.getUserIds();
		if(StrUtils.isBlankOrNull(userIds)) {
			item.setUserIds(at.getId());
			item.setUserNames(at.getUser().getName());
		}else if(!userIds.contains(at.getId())) {
			item.setUserIds(item.getUserIds()+","+at.getId());
			item.setUserNames(item.getUserNames()+","+at.getUser().getName());
		}
		itemDao.update(item);
	}
	@Override
	public List<QueryCondition> toQueryList(ItemUserVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		
		if(v.getItemId()!=null){
			queryConditions.add(new QueryCondition("itemId", QueryCondition.EQ,v.getItemId()));
		}
		if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
			List<String> ids=orgDao.listChild(getCurrent().getOrgId());
			ids.add(getCurrent().getOrgId());
			queryConditions.add(new QueryCondition("orgId in('"+String.join("','", ids)+"')"));
		}
		return queryConditions;
	}
  
	@Override
	public List<AccountVo> findTestUserList(String itemId) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("itemId", QueryCondition.EQ,itemId));
		List<ItemUserVo> imList=super.list(queryConditions);
		String userIds="";
		if(imList!=null) {
			for (ItemUserVo iuVo : imList) {
				userIds+=iuVo.getUserId();
			}
		}
		if(userIds.endsWith(",")) {
			userIds=userIds.substring(0, userIds.length()-1);
		}
		List<Account> userlist=accountDao.listByIds(userIds.split(","));
		List<AccountVo> voList=new ArrayList<AccountVo>();
		if(null!=userlist) {
			for (Account account : userlist) {
				List<String> orgIds=orgDao.listChild(getCurrent().getOrgId());
				if(account.getOrg().getId().equals(getCurrent().getOrgId())
						||orgIds.contains(account.getOrg().getId())) {
					AccountVo accountVo=new AccountVo();
					accountVo=accountVo.toVo(account);
					voList.add(accountVo);	
				}
			}
		}
		return voList;
	}
	@Override
	public void delete(String... ids) throws GlobalException {
		List<ItemUser> iuList=itemUserDao.listByIds(ids);
		for (ItemUser itemUser : iuList) {
			itemUserDao.delete(itemUser);
		}
	}
}

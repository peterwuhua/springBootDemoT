package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IRoleDao;
import cn.demi.base.system.po.Role;
import cn.demi.base.system.service.IRoleService;
import cn.demi.base.system.vo.RoleVo;
/**
 * <strong>Description : RoleServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleVo, Role> implements
		IRoleService {
	@Autowired private IRoleDao roleDao;
	@Override
	public void update4Grid(RoleVo v) throws GlobalException {
		Role po = roleDao.findById(v.getId());
		po.setCode(v.getCode());
		po.setName(v.getName());
		po.setSort(v.getSort());
		roleDao.update(po);
	}
	@Override
	public List<RoleVo> list4Login() throws GlobalException {
		String loginName =  getCurrent().getLoginName();
		List<QueryCondition> conditions = new ArrayList<>();
		if(!"suadmin".equals(loginName)){
			conditions.add(new QueryCondition("code NOT IN ('999','998')"));
		}
		return toVoList(roleDao.list(conditions));
	}
	

	
	
}

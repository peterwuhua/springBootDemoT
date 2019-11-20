package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IFunctionDao;
import cn.demi.base.system.dao.IPermissionDao;
import cn.demi.base.system.po.Function;
import cn.demi.base.system.po.Permission;
import cn.demi.base.system.service.IPermissionService;
import cn.demi.base.system.vo.PermissionVo;
/**
 * Create on : 2016年11月22日 下午2:25:49  <br>
 * Description :  <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Service("sys.permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<PermissionVo,Permission> implements
		IPermissionService {
	@Autowired
	private IPermissionDao permissionDao;
	@Autowired
	private IFunctionDao functionDao;
	@Override
	public void add(PermissionVo v) throws GlobalException {
		Permission po = vo2Po(v);
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("code", QueryCondition.EQ,
				v.getCode()));
		List<Permission> accounts = permissionDao.list(queryConditions);
		if (accounts.size() > 0) {
			throw new GlobalException("权限已经存在:" + v.getName());
		}
		if(null != v.getFunctionVo() && StrUtils.isNotBlankOrNull(v.getFunctionVo().getId())){
			Function function  = functionDao.findById(v.getFunctionVo().getId());
			po.setFunction(function);
			v.setFunId(po.getFunction().getId());
		}
		permissionDao.add(po);
		v.setId(po.getId());
	}

	@Override
	public void update(PermissionVo v) throws GlobalException {
		Permission po = permissionDao.findById(v.getId());
		BeanUtils.copyProperties(v, po, new String[] {"id","createTime","lastUpdTime","version"});

		if(null !=v.getFunctionVo() && !po.getFunction().getId().equals(v.getFunctionVo().getId())){
			Function function = functionDao.findById(v.getFunctionVo().getId());
			po.setFunction(function);
		}

		permissionDao.update(po);
		v.setId(po.getId());
		v.setFunId(po.getFunction().getId());
	}

	@Override
	public PermissionVo po2Vo(Permission p) throws GlobalException {
		PermissionVo vo = super.po2Vo(p);
		/*FunctionVo functionVo = new FunctionVo().toVo(p.getFunction());
		vo.setFunctionVo(functionVo);*/
		return vo;
	}

	@Override
	public List<QueryCondition> toQueryList(PermissionVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		
		queryConditions.add(new QueryCondition("function.isDel", QueryCondition.EQ,Po.N));
		queryConditions.add(new QueryCondition("function.id", QueryCondition.EQ,null==v.getFunId()?"--":v.getFunId()));
		return queryConditions;
	}

	@Override
	public List<PermissionVo> list(PermissionVo v) throws GlobalException {
		List<QueryCondition> queryConditions = this.toQueryList(v);
		List<OrderCondition> orderConditionsList = new ArrayList<>();
		return toVoList(permissionDao.list(queryConditions, orderConditionsList));
	}
	@Override
	public void update4Grid(PermissionVo v) throws GlobalException {
		Permission permission = permissionDao.findById(v.getId());
		permission.setCode(v.getCode());
		permission.setName(v.getName());
		permission.setDescribtion(v.getDescribtion());
		permission.setSort(v.getSort());
		permissionDao.update(permission);
	}

	@Override
	public PermissionVo findByCode(String code) throws GlobalException {
		PermissionVo vo = null;
		try {
			Permission permission = permissionDao.findByCode(code);
			if(null != permission){
				vo = this.po2Vo(permission);
			}
		} catch (Exception e) {
			throw new GlobalException("查询权限失败");
		}
		return vo;
	}
	
}

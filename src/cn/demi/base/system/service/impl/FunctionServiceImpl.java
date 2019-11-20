package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IFunctionDao;
import cn.demi.base.system.po.Function;
import cn.demi.base.system.service.IFunctionService;
import cn.demi.base.system.vo.FunctionVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:11:45 </strong> <br>
 * <strong>Description : FunctionServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.functionService")
public class FunctionServiceImpl extends BaseServiceImpl<FunctionVo, Function>
		implements IFunctionService {

	@Autowired
	private IFunctionDao functionDao;

	@Override
	public void add(FunctionVo v) throws GlobalException {
		Function po = vo2Po(v);
		Function function = functionDao.findById(v.getParentVo().getId());
		po.setParent(function);
		po.setLevel(function.getLevel()+1);
		functionDao.add(po);
		v.setId(po.getId());
		v.setPid(function.getId());
	}

	@Override
	public void update(FunctionVo v) throws GlobalException {
		Function function = functionDao.findById(v.getId());
		String oldPath = function.getPath();
		String oldName = function.getName();
		BeanUtils.copyProperties(v, function, new String[] {"id","createTime","lastUpdTime","version","level"});
		if(!v.getParentVo().getId().equals(function.getParent().getId())){
			Function parent = functionDao.findById(v.getParentVo().getId());
			function.setParent(parent);
			function.setLevel(parent.getLevel()+1);
		}else {
			function.setLevel(function.getParent().getLevel()+1);
		}
		String newPath = "";
		if(null != function.getParent()){
			newPath=function.getPath()+"/"+v.getName();
		}else {
			newPath=v.getName();
		}
		if(!oldName.equals(v.getName())){
			functionDao.updatePath(oldPath,newPath);//修改path
		}
		functionDao.update(function);
		v.setId(function.getId());
		v.setPid(function.getParent().getId());
	}

	@Override
	public void update4Grid(FunctionVo v) throws GlobalException {
		Function function = functionDao.findById(v.getId());
		function.setCode(v.getCode());
		function.setName(v.getName());
		function.setUrl(v.getUrl());
		function.setWfCode(v.getWfCode());
		function.setSort(v.getSort());
		functionDao.update(function);
	}
	
	@Override
	public List<QueryCondition> toQueryList(FunctionVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		String pid = v.getPid();
		if(StrUtils.isBlankOrNull(pid)) 
			pid = findRoot().getId();
		queryConditions.add(new QueryCondition("parent.id", "=",pid));
		return queryConditions;
	}
	
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(Function p:functionDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			functionDao.update(p);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("path", QueryCondition.RK,p.getPath()));
			List<Function> listChildNode= functionDao.list(queryConditions);
			for (Function function : listChildNode) {
				function.setIsDel(Po.Y);
				functionDao.update(function);
			}
		}
		
	}
	
}

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
import cn.demi.base.system.dao.IAreaDao;
import cn.demi.base.system.po.Area;
import cn.demi.base.system.service.IAreaService;
import cn.demi.base.system.vo.AreaVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:11:05 </strong> <br>
 * <strong>Description : AreaServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.areaService")
public class AreaServiceImpl extends BaseServiceImpl<AreaVo, Area> implements
		IAreaService {

	@Autowired
	private IAreaDao areaDao;

	@Override
	public void add(AreaVo v) throws GlobalException {
		Area po = vo2Po(v);
		Area area = areaDao.findById(v.getParentVo().getId());
		po.setParent(area);
		areaDao.add(po);
		v.setId(po.getId());
		if(null != po.getParent())
			v.setPid(po.getParent().getId());
	}

	@Override
	public void update(AreaVo v) throws GlobalException {
		Area area = areaDao.findById(v.getId());
		String oldPath = area.getPath();
		String oldName = area.getName();
		BeanUtils.copyProperties(v, area, new String[] {"id","createTime","lastUpdTime","version"});
		if(!v.getParentVo().getId().equals(area.getParent().getId())){
			Area parent = areaDao.findById(v.getParentVo().getId());
			area.setParent(parent);
		}
		String newPath = "";
		if(null != area.getParent()){
			newPath=area.getPath()+"/"+v.getName();
		}else {
			newPath=v.getName();
		}
		if(!oldName.equals(v.getName())){
			areaDao.updatePath(oldPath,newPath);//修改path
		}
		areaDao.update(area);
		v.setId(area.getId());
		if(null != area.getParent())
			v.setPid(area.getParent().getId());
	}
	@Override
	public void update4Grid(AreaVo v) throws GlobalException {
		Area area  = areaDao.findById(v.getId());
		area.setCode(v.getCode());
		area.setName(v.getName());
		area.setSort(v.getSort());
		areaDao.update(area);
		v.setPid(area.getParent().getId());
	}

	@Override
	public List<QueryCondition> toQueryList(AreaVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("parent.id", "=", v.getPid()));
		return queryConditions;
	}
	@Override
	public AreaVo po2Vo(Area p) throws GlobalException {
		AreaVo vo = super.po2Vo(p);
		if (null != p.getParent())
			vo.setPid(p.getParent().getId());
		return vo;
	}

	@Override
	public List<AreaVo> list(AreaVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if (StrUtils.isBlankOrNull(v.getPid())) {
			if (null != v.getParentVo() && !StrUtils.isBlankOrNull(v.getParentVo().getId())) {
				queryList.add(new QueryCondition("parent.id", QueryCondition.EQ,v.getParentVo().getId()));
			}
		}else {
			queryList.add(new QueryCondition("parent.id", QueryCondition.EQ,v.getPid()));
		}
		
		return super.list(queryList);
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(Area p:areaDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			areaDao.update(p);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("path", QueryCondition.RK,p.getPath()));
			List<Area> listChildNode= areaDao.list(queryConditions);
			for (Area area  : listChildNode) {
				area.setIsDel(Po.Y);
				areaDao.update(area);
			}
		}
		
	}
	@Override
	public List<AreaVo> listName(String pname) throws GlobalException {
		String jpql="FROM "+areaDao.getEntityName(Area.class)+" WHERE isDel='"+Po.N+"' AND parent.name like '"+pname+"' ORDER BY id ASC";
		List<Area> areaList=areaDao.list(jpql);
		return toVoList(areaList);
	}
}

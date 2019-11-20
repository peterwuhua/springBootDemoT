package cn.demi.bus.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
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
import cn.demi.bus.file.dao.IArchiveDao;
import cn.demi.bus.file.dao.IArchiveFileDao;
import cn.demi.bus.file.dao.IArchiveTypeDao;
import cn.demi.bus.file.po.Archive;
import cn.demi.bus.file.po.ArchiveType;
import cn.demi.bus.file.service.IArchiveTypeService;
import cn.demi.bus.file.vo.ArchiveTypeVo;

@Service("bus.archiveTypeService")
public class ArchiveTypeServiceImpl extends BaseServiceImpl<ArchiveTypeVo,ArchiveType> implements
		IArchiveTypeService {

	
	@Autowired 
	private IArchiveTypeDao archiveTypeDao;
	@Autowired 
	private IArchiveDao archiveDao;
	@Autowired 
	private IArchiveFileDao archiveFileDao;
	@Override
	public void add(ArchiveTypeVo v) throws GlobalException {
		ArchiveType po = vo2Po(v);
		ArchiveType type = archiveTypeDao.findById(v.getParentVo().getId());
		po.setParent(type);
		po.setLevel(type.getLevel()+1);
		po.setTime(DateUtils.getCurrDateTimeStr());
		if(po.getLevel()==1) {
			po.setPath(po.getName());
		}else {
			po.setPath(po.getParent().getPath()+"/"+po.getName());
		}
		archiveTypeDao.add(po);
		v.setId(po.getId());
	}
	
	@Override
	public void update(ArchiveTypeVo v) throws GlobalException {
		ArchiveType type = archiveTypeDao.findById(v.getId());
		String prePath = type.getPath();
		String preName = type.getName();
		type= type.toPo(v, type);
		if (!v.getParentVo().getId().equals(type.getParent().getId())) {
			ArchiveType parent = archiveTypeDao.findById(v.getParentVo().getId());
			type.setParent(parent);
			type.setLevel(parent.getLevel()+1);
		}
		type.setTime(DateUtils.getCurrDateTimeStr());
		if(!StrUtils.isNull(v.getSort())) {
			type.setSort(v.getSort());
		}
		if(type.getLevel()==1) {
			type.setPath(type.getName());
		}else {
			type.setPath(type.getParent().getPath()+"/"+type.getName());
		}
		archiveTypeDao.update(type);
		//替换子类 路径
		if (!preName.equals(type.getName())) {
			archiveTypeDao.updatePath(prePath, type.getPath());// 修改path
		}
	}
	
	@Override
	public void update4Grid(ArchiveTypeVo v) throws GlobalException {
		ArchiveType type = archiveTypeDao.findById(v.getId());
		type.setCode(v.getCode());
		type.setName(v.getName());
		type.setSort(v.getSort());
		archiveTypeDao.update(type);
	}
	
	@Override
	public List<QueryCondition> toQueryList(ArchiveTypeVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		String pid = v.getPid();
		if(StrUtils.isBlankOrNull(pid)) 
			pid = findRoot().getId();
		queryConditions.add(new QueryCondition("parent.id", QueryCondition.EQ, null == pid ? "--" : pid));
		return queryConditions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ArchiveTypeVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = archiveTypeDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ArchiveType>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<ArchiveTypeVo> list() throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM "+ archiveTypeDao.getEntityName(ArchiveType.class) + " WHERE isDel= "+Po.N);
		jpql.append(" ORDER BY sort ASC");
		List<ArchiveType> list=archiveTypeDao.list(jpql.toString());
		List<ArchiveTypeVo> volist= new ArrayList<ArchiveTypeVo>();
		if(list!=null) {
			for (ArchiveType at : list) {
				ArchiveTypeVo atVo=new ArchiveTypeVo();
				atVo=atVo.toVo(at);
				volist.add(atVo);
			}
		}
		for (ArchiveTypeVo atVo : volist) {
			for (ArchiveTypeVo subVo : volist) {
				if(null!=subVo.getParentVo()&&atVo.getId().equals(subVo.getParentVo().getId())) {
					atVo.setHasChild(true);
				}
			}
		}
		return volist;
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		for (ArchiveType p : archiveTypeDao.listByIds(ids)) {
			p.setIsDel(Po.Y);
			archiveTypeDao.update(p);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("path", QueryCondition.RK,p.getPath()));
			// 假删文件夹下子级文件夹
			List<ArchiveType> listChildDir = archiveTypeDao.list(queryConditions);
			for (ArchiveType type : listChildDir) {
				type.setIsDel(Po.Y);
				archiveTypeDao.update(type);
			}
			// 假删文件
			List<Archive> listChildDoc = archiveDao.list(queryConditions);
			for (Archive doc : listChildDoc) {
				doc.setIsDel(Po.Y);
				archiveDao.update(doc);
				archiveFileDao.upt4Del(doc.getId());
			}
		}
	}
	
	@Override
	public void data2Vo(String[] values, ArchiveTypeVo v, String param) throws GlobalException {
		v.setCode(values[1]);
		v.setName(values[2]);
		if(StrUtils.isNotBlankOrNull(values[3])){
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("name", QueryCondition.EQ,values[3]));
			List<ArchiveType> categoryList = archiveTypeDao.list(queryConditions);
			if(!CollectionUtils.isEmpty(categoryList)){
				v.setParentVo(po2Vo(categoryList.get(0)));
			}else{
				throw new GlobalException("无效的文件夹名 " + values[3]);
			}
		}
		
		v.setDescribtion(values[4]);
	}

	@Override
	public boolean isExistName(String name,String id) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("name", QueryCondition.EQ,name));
		queryConditions.add(new QueryCondition("id", QueryCondition.NE,id));
		List<ArchiveType> pList = archiveTypeDao.list(queryConditions);
		if(null!=pList && pList.size()>0){
			return true;
		}else{
			return false;
		}
	}
}

package cn.demi.bus.sample.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.PropertiesTools;
import cn.demi.bus.sample.dao.ISampContainerDao;
import cn.demi.bus.sample.po.SampContainer;

@Repository("bus.sampContainerDao")
public class SampContainerDaoImpl extends BaseDaoImpl<SampContainer> implements ISampContainerDao {
	protected String tablePrefix = PropertiesTools.getPropertiesValueByFileAndKey("resources/jdbc.properties","namingStrategy.tablePrefix");// 数据库表名前缀
	
	@Override
	public void deleteByNo(String no) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(SampContainer.class)+" WHERE no ='"+no+"'").executeUpdate();
	}
	@Override
	public void deleteBySamp(String sampId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(SampContainer.class)+" WHERE sampId ='"+sampId+"'").executeUpdate();
	}
	@Override
	public void deleteByTask(String taskId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(SampContainer.class)+" WHERE taskId ='"+taskId+"'").executeUpdate();
	}

	@Override
	public void deleteByPoint(String pointId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(SampContainer.class)+" WHERE pointId ='"+pointId+"'").executeUpdate();
	}

	@Override
	public List<SampContainer> listByPointId(String pointId) {
		String hql="FROM "+getEntityName(SampContainer.class)+" WHERE isDel='"+Po.N+"' AND pointId ='"+pointId+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampContainer> listByTaskId(String taskId) {
		String sql="SELECT container_id,container,sum(num) FROM "+tablePrefix+getEntityName(SampContainer.class)+" WHERE is_del='"+Po.N+"' AND AND task_Id ='"+taskId+"' group by containerId ORDER BY sort asc";
		List<Object[]> objList=super.queryBySql(sql);
		List<SampContainer> scList=new ArrayList<SampContainer>();
		if(null!=objList) {
			for (Object[] obj : objList) {
				SampContainer sc=new SampContainer();
				sc.setTaskId(taskId);
				sc.setContainerId(String.valueOf(obj[0]));
				sc.setContainer(String.valueOf(obj[1]));
				try {
					sc.setNum(Integer.valueOf(String.valueOf(obj[2])));
				} catch (NumberFormatException e) {
					sc.setNum(0);
				}
				scList.add(sc);
			}
		}
		return scList;
	}

	@Override
	public List<SampContainer> listBySampId(String sampId) {
		String hql="FROM "+getEntityName(SampContainer.class)+" WHERE isDel='"+Po.N+"' AND sampId ='"+sampId+"' ORDER BY sort asc";
		return super.list(hql);
	}
}

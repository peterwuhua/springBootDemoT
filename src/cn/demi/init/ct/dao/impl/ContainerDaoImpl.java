package cn.demi.init.ct.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.init.ct.dao.IContainerDao;
import cn.demi.init.ct.po.Container;

@Repository("init.containerDao")
public class ContainerDaoImpl extends BaseDaoImpl<Container> implements IContainerDao {
}

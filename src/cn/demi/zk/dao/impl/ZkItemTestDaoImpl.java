package cn.demi.zk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.zk.dao.IZkItemTestDao;
import cn.demi.zk.po.ZkItemTest;

@Repository("zk.itemTestDao")
public class ZkItemTestDaoImpl extends BaseDaoImpl<ZkItemTest> implements IZkItemTestDao {
}

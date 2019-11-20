package cn.demi.zk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.zk.dao.IZkSamplingDao;
import cn.demi.zk.po.ZkSampling;

@Repository("zk.samplingDao")
public class ZkSamplingDaoImpl extends BaseDaoImpl<ZkSampling> implements IZkSamplingDao {
}

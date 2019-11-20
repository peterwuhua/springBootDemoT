package cn.demi.init.std.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.init.std.dao.ISampSourceDao;
import cn.demi.init.std.po.SampSource;

@Repository("init.sampSourceDao")
public class SampSourceDaoImpl extends BaseDaoImpl<SampSource> implements ISampSourceDao {
}

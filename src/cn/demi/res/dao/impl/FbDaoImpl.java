package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.IFbDao;
import cn.demi.res.po.Fb;

@Repository("res.fbDao")
public class FbDaoImpl extends BaseDaoImpl<Fb> implements IFbDao {
}

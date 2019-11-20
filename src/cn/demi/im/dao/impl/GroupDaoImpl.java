package cn.demi.im.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.im.dao.IGroupDao;
import cn.demi.im.po.Group;

@Repository("im.groupDao")
public class GroupDaoImpl extends BaseDaoImpl<Group> implements IGroupDao {
}

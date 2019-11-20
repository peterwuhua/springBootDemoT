package cn.demi.qlt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.qlt.dao.IAbilityDao;
import cn.demi.qlt.po.Ability;

@Repository("qlt.abilityDao")
public class AbilityDaoImpl extends BaseDaoImpl<Ability> implements IAbilityDao {
}

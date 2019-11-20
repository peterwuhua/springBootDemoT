package cn.demi.pfm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.demi.pfm.dao.ISetDao;
import cn.demi.pfm.po.Set;

@Repository("pfm.setDao")
public class SetDaoImpl extends BaseDaoImpl<Set> implements ISetDao {


    @Override
    public Set findByCode(String code) throws GlobalException {
        List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
        queryConditions.add(new QueryCondition("code", QueryCondition.EQ, code));
        return super.query0(queryConditions, null);
    }
}

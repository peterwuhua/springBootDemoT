package cn.demi.pfm.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.pfm.po.Set;

public interface ISetDao extends IBaseDao<Set> {
    /**
     * 根据编号查找某条记录
     *
     * @param code
     * @return
     * @throws GlobalException
     */
    public Set findByCode(String code) throws GlobalException;
}

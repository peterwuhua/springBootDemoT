package cn.demi.app.sys.service.impl;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.service.IDgTjService;
import cn.demi.app.sys.vo.CheckOnVo;
import cn.demi.office.dao.IDgTjDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("app.dgTjService")
public class DgTjServiceImpl implements IDgTjService {
    @Autowired
    private IDgTjDao dgTjDao;

    @Override
    public List<CheckOnVo> list() throws GlobalException {
        String sql = "SELECT user_name,`status` ,user_code FROM v_office_dg_tj WHERE date = ( SELECT MAX( date ) FROM v_office_dg_tj )";
        //StringBuffer hql = new StringBuffer("FROM " + dgTjDao.getEntityName(DgTj.class) + " WHERE isDel='" + Po.N + "' ");
        //hql.append(" AND date = ( SELECT MAX( date ) FROM v_office_dg_tj )");
        List<Object[]> temp = dgTjDao.queryBySql(sql);
        List<CheckOnVo> list = new ArrayList<>();
        for (Object[] obj : temp) {
            CheckOnVo checkOnVo = new CheckOnVo();
            checkOnVo.setName(obj[0].toString());
            checkOnVo.setStatus(obj[1].toString());
            checkOnVo.setUserCode(obj[2].toString());
            list.add(checkOnVo);
        }
        return list;
    }
}

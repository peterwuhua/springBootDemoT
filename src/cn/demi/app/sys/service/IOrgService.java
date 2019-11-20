package cn.demi.app.sys.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.exception.GlobalException;
import net.sf.json.JSONArray;

@Transactional
public interface IOrgService {
    /**
     * 组部门信息
     *
     * @return
     * @throws GlobalException
     */
    public JSONArray orgList() throws GlobalException;
}

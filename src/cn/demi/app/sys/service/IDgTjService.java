package cn.demi.app.sys.service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.CheckOnVo;
import cn.demi.office.vo.DgTjVo;

import java.util.List;

public interface IDgTjService {
    /**
     * 获取列表页面
     *
     * @return
     * @throws GlobalException
     */
    public List<CheckOnVo> list() throws GlobalException;
}
